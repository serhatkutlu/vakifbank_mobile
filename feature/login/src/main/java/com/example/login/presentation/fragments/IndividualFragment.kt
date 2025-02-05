package com.example.login.presentation.fragments

import com.example.ui.base.BaseFragment
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import androidx.fragment.app.viewModels

import com.example.login.databinding.FragmentIndividualBinding
import com.example.login.model.UserInformation
import com.example.login.presentation.state_event_effect.loginevent.LoginEvent
import com.example.login.presentation.viewmodel.LoginViewModel
import com.example.login.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IndividualFragment() :
    BaseFragment<FragmentIndividualBinding>(FragmentIndividualBinding::inflate) {

    private val viewmodel by viewModels<LoginViewModel>({ requireParentFragment() })

    private var idIsValidate = false
    private var passwordIsValidate = false
    private var isPasswordChanged = false
    private var isButtonLocked = false

    private val filterArrayId =
        arrayOf<InputFilter>(InputFilter.LengthFilter(Constants.INDIVIDUAL_CUSTOMER_OR_TC_ID__MAX_LENGTH))
    private val filterArrayPassword =
        arrayOf<InputFilter>(InputFilter.LengthFilter(Constants.INDIVIDUAL_PASSWORD_MAX_LENGTH))

    override fun initUi() {
        initTextOnChange()
        binding.tilId.filters = filterArrayId
        binding.tilPassword.filters = filterArrayPassword
        initOnClickListeners()
    }

    private fun initOnClickListeners() {
        binding.button.setOnClickListener { loginOnClick() }
    }

    private fun loginOnClick() {
        val userInformation= UserInformation.Retail(
            binding.tilId.text.toString().toLong(),
            binding.tilPassword.text.toString()
        )
        viewmodel.setLoginStateUserInformation(userInformation)
        viewmodel.setEvent(LoginEvent.LoginClicked)
    }

    private fun initTextOnChange() {

        binding.tilId.addTextChangedListener(idTextWatcher())
        binding.tilPassword.addTextChangedListener(passwordTextWatcher())

    }

    override fun initObservers() {

    }


    private fun checkIsValidate() {
        binding.button.isEnabled = idIsValidate && passwordIsValidate
    }


    private fun idTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            idIsValidate = (s?.length ?: 0) >= Constants.INDIVIDUAL_CUSTOMER_OR_TC_ID__MIN_LENGTH
            checkIsValidate()
        }
    }

    private fun passwordTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
            if (after > 0 && isButtonLocked) isButtonLocked = false
            if (count > 0 && after == 0 && !isPasswordChanged) {
                isButtonLocked = true
                passwordIsValidate = false
                isPasswordChanged = true
                checkIsValidate()
                binding.tilPassword.text = null
            }
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            if (!isButtonLocked) passwordIsValidate =
                (s?.length ?: 0) >= Constants.INDIVIDUAL_PASSWORD_MIN_LENGTH
            checkIsValidate()
            isPasswordChanged = false

        }

    }

}