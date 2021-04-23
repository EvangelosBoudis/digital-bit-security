package com.nativeboys.password.manager.util

import android.text.InputType

enum class InputTypeItem(val description: String, val code: String, val type: Int) {
    TEXT("Text","text", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL),
    NUMBER("Number", "number", InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL),
    TIME("Time","time", InputType.TYPE_CLASS_DATETIME or InputType.TYPE_DATETIME_VARIATION_TIME),
    TEXT_URI("Uri","textUri", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_URI),
    DATE("Date","date", InputType.TYPE_CLASS_DATETIME or InputType.TYPE_DATETIME_VARIATION_DATE),
    PHONE("Phone","phone", InputType.TYPE_CLASS_PHONE),
    TEXT_PASSWORD("Password","textPassword", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD),
    TEXT_EMAIL_ADDRESS("Email","textEmailAddress", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS),
    TEXT_POSTAL_ADDRESS("Address", "textPostalAddress", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS),
}

fun allTypes() = enumValues<InputTypeItem>().asList()

fun findByCode(code: String) = allTypes().firstOrNull { it.code == code }

fun findByDescription(description: String) = allTypes().firstOrNull { it.description == description }

/*
    // 8, 0, 7, 14, 20, 25, 30
InputTypeItem("date",  "Date", InputType.TYPE_CLASS_DATETIME or InputType.TYPE_DATETIME_VARIATION_DATE),
            InputTypeItem("datetime", "Date Time", InputType.TYPE_CLASS_DATETIME or InputType.TYPE_DATETIME_VARIATION_NORMAL),
            InputTypeItem("none", "None", InputType.TYPE_NULL),
            InputTypeItem("number",  "Number", InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL),
            InputTypeItem("numberDecimal",  "Number Decimal", InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL),
            InputTypeItem("numberPassword",  "Number Password", InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD),
            InputTypeItem("numberSigned", "Number Signed", InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED),
            InputTypeItem("phone",  "Phone", InputType.TYPE_CLASS_PHONE),
            InputTypeItem("text",  "Text", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL),
            InputTypeItem("textAutoComplete", "Auto Complete", InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE),
            InputTypeItem("textAutoCorrect",  "Auto Correct", InputType.TYPE_TEXT_FLAG_AUTO_CORRECT),
            InputTypeItem("textCapCharacters",  "Cap Characters", InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS),
            InputTypeItem("textCapSentences",  "Cap Sentences", InputType.TYPE_TEXT_FLAG_CAP_SENTENCES),
            InputTypeItem("textCapWords", "Cap Words", InputType.TYPE_TEXT_FLAG_CAP_WORDS),
            InputTypeItem("textEmailAddress", "Email Address", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS),
            InputTypeItem("textEmailSubject", "Email Subject", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT),
            InputTypeItem("textFilter", "Filter", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_FILTER),
            InputTypeItem("textLongMessage", "Long Message", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE),
            InputTypeItem("textMultiLine", "MultiLine", InputType.TYPE_TEXT_FLAG_MULTI_LINE),
            InputTypeItem("textNoSuggestions", "No Suggestions", InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS),
            InputTypeItem("textPassword",  "Password", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD),
            InputTypeItem("textPersonName", "Person Name", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME),
            InputTypeItem("textPhonetic", "Phonetic", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PHONETIC),
            InputTypeItem("textPostalAddress", "Postal Address", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS),
            InputTypeItem("textShortMessage", "Short Message", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE),
            InputTypeItem("textUri", "Uri", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_URI),
            InputTypeItem("textVisiblePassword", "Visible Password", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD),
            InputTypeItem("textWebEditText", "Web Text", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT),
            InputTypeItem("textWebEmailAddress", "Web Email Address", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS),
            InputTypeItem("textWebPassword", "Web Password", InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD),
            InputTypeItem("time", "Time", InputType.TYPE_CLASS_DATETIME or InputType.TYPE_DATETIME_VARIATION_TIME)*/
