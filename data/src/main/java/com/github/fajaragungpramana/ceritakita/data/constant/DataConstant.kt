package com.github.fajaragungpramana.ceritakita.data.constant

object DataConstant {

    object Preferences {
        const val AUTH = "ceritakita.preferences.auth"
    }

    object Http {

        object Code {
            const val UNAUTHORIZED = 401
        }

        object Param {
            const val AUTHORIZATION = "Authorization"

            const val NAME = "name"
            const val EMAIL = "email"
            const val PASSWORD = "password"
        }

        object Route {
            const val LOGIN = "login"
            const val REGISTER = "register"
        }

    }
}