package com.example.login.model

sealed interface UserInformation {
    data class Retail(val id:Long,val password:String): UserInformation
    data class Commercial(val customerNumber:Long,val UserCode:Long,val password:String): UserInformation
    data object Idle: UserInformation
}