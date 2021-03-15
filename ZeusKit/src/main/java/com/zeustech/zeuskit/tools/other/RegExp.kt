package com.zeustech.zeuskit.tools.other

import java.util.regex.Pattern

object RegExp {
    val NAME_PATTERN = Pattern.compile(
        "^" +
                "(?=.*[a-zA-Z])" +  // any character
                ".+" +  // at least 1 character
                "$"
    )
    val CARD_NUMBER_PATTERN = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +  // any number
                "(?=\\S+$)" +  // no white spaces
                ".{9,}" +  // at least 9 characters
                "$"
    )
    val CCV_PATTERN = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +  // any number
                "(?=\\S+$)" +  // no white spaces
                ".{3,}" +  // at least 3 characters
                "$"
    )
    val DATE_PATTERN = Pattern.compile(
        "^" +
                "[0-9]{2}" +
                "/" +
                "[0-9]{2}" +
                "$"
    )
    val DATE_PATTERN2 = Pattern.compile(
        "^" +
                "[0-9]{2}" +
                "/" +
                "[0-9]{2}" +
                "/" +
                "[0-9]{4}" +
                "$"
    )
    val POSTAL_CODE_PATTERN = Pattern.compile(
        "^" +
                "[a-zA-Z0-9 -]{2,10}" +
                "$"
    )
}