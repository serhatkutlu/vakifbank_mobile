package com.example.login.presentation.fragments

import com.example.ui.base.BaseFragment
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import androidx.fragment.app.viewModels
import com.example.login.databinding.FragmentCommercialBinding
import com.example.login.model.UserInformation
import com.example.login.presentation.state_event_effect.loginevent.LoginEvent
import com.example.login.presentation.viewmodel.LoginViewModel
import com.example.login.util.Constants


class CommercialFragment :
    BaseFragment<FragmentCommercialBinding>(FragmentCommercialBinding::inflate) {

    private val viewmodel by viewModels<LoginViewModel>({ requireParentFragment() })

    private val filterArrayCustomerNumber =
        arrayOf<InputFilter>(InputFilter.LengthFilter(Constants.COMMERCIAL_CUSTOMER_NUMBER_MAX_LENGTH))
    private val filterArrayUerCode =
        arrayOf<InputFilter>(InputFilter.LengthFilter(Constants.COMMERCIAL_USER_CODE_MAX_LENGTH))
    private val filterArrayPassword =
        arrayOf<InputFilter>(InputFilter.LengthFilter(Constants.COMMERCIAL_PASSWORD_MAX_LENGTH))


    private var customerIdIsValidate = false
    private var userCodeIsValidate = false
    private var passwordIsValidate = false
    private var isPasswordChanged = false
    private var isButtonLocked = false

    override fun initUi() {
        initTextOnChange()
        binding.tilCustomerNumber.filters = filterArrayCustomerNumber
        binding.tilUserCode.filters = filterArrayUerCode
        binding.tilPassword.filters = filterArrayPassword

        initOnClickListeners()

    }

    private fun initOnClickListeners() {
        binding.buttonContinue.setOnClickListener { loginOnClick() }
    }

    private fun loginOnClick() {
        val userInformation = UserInformation.Commercial(
            binding.tilCustomerNumber.text.toString().toLong(),
            binding.tilUserCode.text.toString().toLong(),
            binding.tilPassword.text.toString()
        )
        viewmodel.setLoginStateUserInformation(userInformation)
        viewmodel.setEvent(LoginEvent.LoginClicked)
    }

    private fun initTextOnChange() {
        binding.tilCustomerNumber.addTextChangedListener(CustomerNumberTextWatcher())
        binding.tilUserCode.addTextChangedListener(UserCodeTextWatcher())
        binding.tilPassword.addTextChangedListener(passwordTextWatcher())
    }

    override fun initObservers() {
    }


    private fun CustomerNumberTextWatcher() = object : TextWatcher {
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
            customerIdIsValidate =
                (s?.length ?: 0) >= Constants.COMMERCIAL_CUSTOMER_NUMBER_MIN_LENGTH
            checkIsValidate()
        }
    }

    private fun UserCodeTextWatcher() = object : TextWatcher {
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
            userCodeIsValidate = (s?.length ?: 0) >= Constants.COMMERCIAL_USER_CODE_MIN_LENGTH
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
                (s?.length ?: 0) >= Constants.COMMERCIAL_PASSWORD_MIN_LENGTH
            checkIsValidate()
            isPasswordChanged = false
        }

    }

    private fun checkIsValidate() {
        binding.buttonContinue.isEnabled =
            customerIdIsValidate && userCodeIsValidate && passwordIsValidate
    }


}