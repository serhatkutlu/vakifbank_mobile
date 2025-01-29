package com.example.login.presentation.fragments

import BaseFragment
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher

import com.example.login.databinding.FragmentIndividualBinding
import com.example.login.util.Constants


class IndividualFragment():BaseFragment<FragmentIndividualBinding>(FragmentIndividualBinding::inflate) {


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
    }

    private fun initTextOnChange() {

        binding.tilId.addTextChangedListener(IdTextWatcher())
        binding.tilPassword.addTextChangedListener(passwordTextWatcher())

    }

    override fun initObservers() {

    }


    private fun checkIsValide() {
        binding.button.isEnabled = idIsValidate && passwordIsValidate
    }


    private fun IdTextWatcher() = object : TextWatcher {
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
            checkIsValide()
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
                checkIsValide()
                binding.tilPassword.text = null
            }
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            if (!isButtonLocked) passwordIsValidate =
                (s?.length ?: 0) >= Constants.INDIVIDUAL_PASSWORD_MIN_LENGTH
            checkIsValide()
            isPasswordChanged = false

        }

    }

}