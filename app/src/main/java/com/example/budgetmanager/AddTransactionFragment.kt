package com.example.budgetmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.budgetmanager.databinding.FragmentAddTransactionBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddTransactionFragment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var binding: FragmentAddTransactionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentAddTransactionBinding.inflate(inflater)

        binding.incomeBtn.setOnClickListener{view ->
            binding.incomeBtn.setBackground(context?.getDrawable(R.drawable.income_selector))
            binding.expenseBtn.setBackground(context?.getDrawable(R.drawable.default_selector))
            context?.let { binding.incomeBtn.setTextColor(it.getColor(R.color.greenColor)) }
            context?.let { binding.expenseBtn.setTextColor(it.getColor(R.color.textColor)) }
        }

        binding.expenseBtn.setOnClickListener{view ->
            binding.incomeBtn.setBackground(context?.getDrawable(R.drawable.default_selector))
            binding.expenseBtn.setBackground(context?.getDrawable(R.drawable.expense_selector))
            context?.let { binding.incomeBtn.setTextColor(it.getColor(R.color.textColor)) }
            context?.let { binding.expenseBtn.setTextColor(it.getColor(R.color.redColor)) }
        }

        return binding.root
    }

    /*companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddTransactionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
}