package com.girrafeecstud.final_loan_app_zalessky.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.girrafeecstud.final_loan_app_zalessky.R
import com.girrafeecstud.final_loan_app_zalessky.app.App
import com.girrafeecstud.final_loan_app_zalessky.presentation.HomeViewModel
import com.girrafeecstud.final_loan_app_zalessky.ui.instruction.LoanRequestInstructionActivity

class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var listener: HomeFragmentListener

    private val homeViewModel: HomeViewModel by viewModels {
        (activity?.applicationContext as App).appComponent.mainViewModelFactory()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when (context) {
            is HomeFragmentListener ->
                listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getLoanBtn = view.findViewById<Button>(R.id.getLoanBtn)
        val instr = view.findViewById<Button>(R.id.instructionsBtn)

        instr.setOnClickListener(this)
        getLoanBtn.setOnClickListener(this)

//        // Close all activities when back press in home fragment
//        val onBackPressedCallback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                activity?.finishAffinity()
//            }
//        }
//
//        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.getLoanBtn -> {
                val intent = Intent(activity, LoanRequestActivity::class.java)
                startActivity(intent)
            }
            R.id.instructionsBtn -> {
                val intent = Intent(activity, LoanRequestInstructionActivity::class.java)
                startActivity(intent)
            }
        }
    }

    interface HomeFragmentListener {
        fun enableBottomNavigationViewHomeItem()
    }

}