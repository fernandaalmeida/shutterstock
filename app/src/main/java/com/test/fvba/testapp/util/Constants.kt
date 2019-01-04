package com.test.fvba.testapp.util

class Constants {
    companion object {
        const val BASE_URL = "https://api.shutterstock.com"
        const val API_KEY = "8d4d7-e2114-24a93-6c372-2a9a2-0ee3e"
        const val API_SECRET = "a5ed9-5be47-d6d83-954c6-81524-0cfa1"

        const val IMAGE_DEFAULT_SIZE = 100 //in seconds

        const val ERROR_NO_CONNECTION = 999
        /**
         * Unexpected error
         */
        const val HTTP_GENERIC_ERROR_CODE = 998

        /**
         * Request Timeout error
         */
        const val HTTP_ERROR_CODE_TIMEOUT = 997
        const val HEADER_AUTH: String = "Authorization"
    }
}