package com.teamteam.knowing.ui.view.main.alert

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.teamteam.knowing.R
import com.teamteam.knowing.databinding.FragmentAlertBinding
import com.teamteam.knowing.ui.base.BaseFragment

class AlertFragment : BaseFragment<FragmentAlertBinding>(FragmentAlertBinding::bind, R.layout.fragment_alert) {
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
    }
}