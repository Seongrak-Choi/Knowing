package com.example.knowing.ui.view.more_information

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.knowing.R
import com.example.knowing.databinding.DialogSelectDoBinding
import com.example.knowing.ui.adapter.SelectDoDialogRCAdapter
import com.example.knowing.ui.viewmodel.MoreInformationActivityViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectDoDialog(context: Context) : BottomSheetDialogFragment(){
    private lateinit var binding : DialogSelectDoBinding
    private lateinit var moreInformationActivityViewModel: MoreInformationActivityViewModel

    //다이얼로그 상태 조정하는 코드인데 쓸모 없지만, 나중에 참고용으로 남겨둠
//    override fun setupDialog(dialog: Dialog, style: Int) {
//        var bottomSheetDialog = dialog as BottomSheetDialog
//        bottomSheetDialog.setContentView(R.layout.dialog_select_do)
//
//        try {
//            var behaviorField = bottomSheetDialog.javaClass.getDeclaredField("behavior")
//            behaviorField.isAccessible=true
//            val behavior = behaviorField.get(bottomSheetDialog) as BottomSheetBehavior<*>
//           // behavior.isDraggable=false
//            behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
//                override fun onStateChanged(bottomSheet: View, newState: Int) {
//                    if (newState == BottomSheetBehavior.STATE_DRAGGING){
//                        behavior.state=BottomSheetBehavior.STATE_EXPANDED
//                    }
//                }
//
//                override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                }
//            })
//        }catch (e:NoSuchFieldException){
//            e.printStackTrace()
//        }catch (e:IllegalAccessException){
//            e.printStackTrace()
//        }
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogSelectDoBinding.inflate(layoutInflater) //viewBinding

        //뷰모델 장착
        moreInformationActivityViewModel=ViewModelProvider(this).get(MoreInformationActivityViewModel::class.java)


        //리사이클러뷰 어댑터에 들어가는 리스트를 컨트롤하기 위해 뷰모델에 있는 라이브 데이터를 옵저버 패턴으로 관찰해서 어댑터 생성 후 장착
        moreInformationActivityViewModel.currentDoList.observe(this, Observer {
            binding.rcDo.layoutManager=GridLayoutManager(context,2)
            var adapter=SelectDoDialogRCAdapter(it)
            binding.rcDo.adapter=adapter
            adapter.notifyDataSetChanged()
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val offsetFromTop = 650
//        (dialog as? BottomSheetDialog)?.behavior?.apply {
//            isFitToContents = false
//            expandedOffset = offsetFromTop
//            state = BottomSheetBehavior.STATE_SETTLING
//        }
    }
}