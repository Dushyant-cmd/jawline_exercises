package com.bytezaptech.jawlineexercise_faceyoga.utils

class Constants {
    companion object {
        //Shared Pref Key
        val KEY_IS_USER_LOGGED = "isUserLogin"
        val NAME = "name"
        val EMAIL = "email"
        val isDetailFilled = "isDetailFilled"
        val LANGUAGE_SELECTED = "languageSelected"
        val PRIVACY_POLICY_URL = "privacyUrl"
        val TERMS_CONDITIONS_URL = "termsUrl"
        val UPI = "upi"
        val UPDATE_INFO = "update_info"
        val IS_SHOW_ADS = "is_show_ads"
        //Firestore Collections
        val COL_USERS = "users"
        val COL_CONSTANTS = "Constants"
        //Firestore Documents
        val DOC_CONSTANTS = "constants"
        //Plan
        val FREE_PLAN = "free"
        val FREE_PLAN_TYPE = 0
        val PAID_PLAN = "paid"
        val PAID_PLAN_TYPE = 0
    }
}