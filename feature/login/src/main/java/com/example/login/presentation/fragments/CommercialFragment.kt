package com.example.login.presentation.fragments

import BaseFragment
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import com.example.login.databinding.FragmentCommercialBinding
import com.example.login.util.Constants


class CommercialFragment :
    BaseFragment<FragmentCommercialBinding>(FragmentCommercialBinding::inflate) {

    private val filterArrayCustomerNumber =
        arrayOf<InputFilter>(InputFilter.LengthFilter(Constants.COMMERCIAL_CUSTOMER_NUMBER_MAX_LENGTH))
    private val filterArrayUerCode =
        arrayOf<InputFilter>(InputFilter.LengthFilter(Constants.COMMERCIAL_USER_CODE_MAX_LENGTH))
    private val filterArrayPassword =
        arrayOf<InputFilter>(InputFilter.LengthFilter(Constants.COMMERCIAL_PASSWORD_MAX_LENGTH))



    private var CustomerIdIsValidate = false
    private var UserCodeIsValidate = false
    private var passwordIsValidate = false
    private var isPasswordChanged = false
    private var isButtonLocked = false

    override fun initUi() {
        initTextOnChange()
        binding.tilCustomerNumber.filters = filterArrayCustomerNumber
        binding.tilUserCode.filters = filterArrayUerCode
        binding.tilPassword.filters = filterArrayPassword

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
            CustomerIdIsValidate = (s?.length ?: 0) >= Constants.COMMERCIAL_CUSTOMER_NUMBER_MIN_LENGTH
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
            UserCodeIsValidate = (s?.length ?: 0) >= Constants.COMMERCIAL_USER_CODE_MIN_LENGTH
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
                isPasswordChanged=true
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
        binding.buttonContinue.isEnabled = CustomerIdIsValidate&&UserCodeIsValidate&&passwordIsValidate
    }



}