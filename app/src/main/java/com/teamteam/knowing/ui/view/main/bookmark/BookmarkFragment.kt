package com.teamteam.knowing.ui.view.main.bookmark

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamteam.knowing.R
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.databinding.FragmentBookmarkBinding
import com.teamteam.knowing.ui.adapter.MainBookmarkRCAdapter
import com.teamteam.knowing.ui.base.BaseFragment
import com.teamteam.knowing.ui.viewmodel.BookmarkFragmentViewModel

class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>(FragmentBookmarkBinding::bind, R.layout.fragment_bookmark){
    //뷰모델 변수 선언
    private lateinit var bookmarkFragmentViewModel: BookmarkFragmentViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //statusbar 높이 구하는 방법
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")

        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }

        //statusbar가 투명해진 만큼 화면이 밀려 올라가는 것을 방지하기 위해 statusbar 높이 만큼 마진을 줌줌
       var params = binding.constraint.layoutParams as FrameLayout.LayoutParams
        params.setMargins(0,result,0,0)
        binding.constraint.layoutParams=params


        //뷰모델 장착
        bookmarkFragmentViewModel=ViewModelProvider(this).get(BookmarkFragmentViewModel::class.java)

        //북마크 리사이클러뷰를 위해 api로 받아온 라이브데이터 관찰
        bookmarkFragmentViewModel.currentRcList.observe(viewLifecycleOwner, Observer {

            //만약 북마크의 데이터가 0개라면 게시물이 없다는 일러스트 보이게 처리리
           if (it.size==0){
                binding.rcBookmark.visibility=View.INVISIBLE
                binding.constraintNoData.visibility = View.VISIBLE
            }else{
               binding.constraintNoData.visibility = View.INVISIBLE
                binding.rcBookmark.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                val adapter = MainBookmarkRCAdapter(it,requireContext())
                binding.rcBookmark.adapter = adapter
                adapter.notifyDataSetChanged()

                //리사이클러뷰의 스와이프 후 삭제버튼 클릭 리스너 구현
                adapter.setOnItemClickListener(object : MainBookmarkRCAdapter.OnItemClickListener{
                    override fun onItemClick(welfareUid: String) {
                        //북마크 삭제 api 호출 및 결과 리사이클러뷰 라이브데이터에 저장
                        bookmarkFragmentViewModel.tryDeleteBookmarkList(ApplicationClass.USER_UID,welfareUid)
                    }
                })
            }
        })


        //총 건수 설정할 라이브데이터 관찰
        bookmarkFragmentViewModel.currentBookmarkTotal.observe(viewLifecycleOwner, Observer {
            binding.txWelfareTotal.text="총 ${it}건"
        })
    }

    override fun onResume() {
        super.onResume()

        //api 호출 해서 라이브 데이터에 결과 저장
        bookmarkFragmentViewModel.tryGetBookmarkList(ApplicationClass.USER_UID)
    }
}