package com.example.ui.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.example.ui.databinding.ItemDialogMessageBinding
import com.example.ui.util.UiComponents
import kotlinx.coroutines.launch

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T



abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment(), UiComponents {
    private var _binding: VB? = null
    val binding: VB get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initUi()
        initObservers()

    }




    override fun showCustomDialog(
        message: String,
        oktext: String,
        okCallBack: (() -> Unit)?
    ) {
        val dialogView=ItemDialogMessageBinding.inflate(LayoutInflater.from(requireContext()))

        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView.root)
            .setCancelable(true)

        val dialog = dialogBuilder.create()
         dialog.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), com.example.ui.R.drawable.white_rounded_bg))

        val button = dialogView.buttonPositive
        button.text = oktext
        button.setOnClickListener {
            dialog.dismiss()
            okCallBack?.invoke()
        }

        dialog.show()
        val messageTextView = dialogView.tvMessage
        messageTextView.text = message
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}