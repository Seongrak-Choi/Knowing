package com.teamteam.knowing.ui.view.main.bookmark

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamteam.knowing.R
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.data.model.network.response.WelfareInfo
import com.teamteam.knowing.databinding.FragmentBookmarkBinding
import com.teamteam.knowing.ui.adapter.MainBookmarkRCAdapter
import com.teamteam.knowing.ui.base.BaseFragment
import com.teamteam.knowing.ui.view.main.home.customwelfare.SelectFilterDialog
import com.teamteam.knowing.ui.viewmodel.BookmarkFragmentViewModel

class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>(FragmentBookmarkBinding::bind, R.layout.fragment_bookmark){
    //뷰모델 변수 선언
    private lateinit var bookmarkFragmentViewModel: BookmarkFragmentViewModel

    //복지 뿌려주는 리사이클러뷰 어댑터 선언
    private lateinit var adapter : MainBookmarkRCAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var array = ArrayList<WelfareInfo>()

        //어댑터가 만들어 지기 전에 adapter를 참조했다고 오류나서 이렇게 미리 세팅함
        adapter = MainBookmarkRCAdapter(array,requireContext())
    }


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
        bookmarkFragmentViewModel=ViewModelProvider(requireActivity()).get(BookmarkFragmentViewModel::class.java)

        //북마크 리사이클러뷰를 위해 api로 받아온 라이브데이터 관찰
        bookmarkFragmentViewModel.currentRcList.observe(viewLifecycleOwner, Observer {

            //만약 북마크의 데이터가 0개라면 게시물이 없다는 일러스트 보이게 처리리
           if (it.size==0){
                binding.rcBookmark.visibility=View.INVISIBLE
                binding.constraintNoData.visibility = View.VISIBLE
            }else{
                binding.constraintNoData.visibility = View.INVISIBLE
               binding.rcBookmark.visibility=View.VISIBLE
               binding.rcBookmark.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                adapter = MainBookmarkRCAdapter(it,requireContext())
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


        //정렬 부분 나타낼 라이브데이터 관찰
        bookmarkFragmentViewModel.currentFilter.observe(viewLifecycleOwner, Observer {
            binding.txFilter.text=it.toString()
        })



        //EditText검색을 위한 텍스트 체인지 리스너
        binding.edtSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //에디트텍스트가 비어있으면 초기화 버튼 안보이게 설정
                if (s!!.isEmpty()){
                    binding.imgSearchCancel.visibility=View.INVISIBLE
                }else{
                    binding.imgSearchCancel.visibility=View.VISIBLE
                }

                adapter.filter.filter(s) //검색을 위해 EditText에 입력을 하면 어댑터의 필터메소드 호출
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })


        //editSearch 포커싱 리스너
        binding.edtSearch.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
            }else{
                binding.imgSearchCancel.visibility=View.INVISIBLE
            }
        }



        //EditText검색 할 때 나오는 키보드 종류가 완료이도록 설정
        binding.edtSearch.imeOptions = EditorInfo.IME_ACTION_DONE


        //EditText검색 키보드 완료 버튼 클릭 이벤트. 완료 버튼을 누르면 포커싱을 없애주기 위한 코드
        binding.edtSearch.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) { //액션 iD가 완료이면

                //키보드 밑으로 내리는 코드
                val imm = requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.edtSearch.windowToken, 0)

                binding.imgSearchCancel.visibility=View.INVISIBLE

                handled = true
            }
            handled
        }


        //edtSearch 초기화 버튼 클릭 리스너
        binding.imgSearchCancel.setOnClickListener {
            binding.edtSearch.text = null
        }



        //btn_filter 필터 버튼 클릭 리스너
        binding.btnFilter.setOnClickListener {
            val bottomSheet = SelectBookmarkFilterDialog()
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }
    }

    override fun onResume() {
        super.onResume()

        //api 호출 해서 라이브 데이터에 결과 저장
        bookmarkFragmentViewModel.tryGetBookmarkList(ApplicationClass.USER_UID)

        //edtSearch 검색 기록 초기화
        binding.edtSearch.setText("")
    }
}