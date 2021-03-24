package com.nativeboys.password.manager.data

import android.text.InputType

data class InputTypeItem(
    val name: String,
    val description: String,
    val value: Int
) {

    companion object {

        val inputTypes = listOf(
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
            InputTypeItem("time", "Time", InputType.TYPE_CLASS_DATETIME or InputType.TYPE_DATETIME_VARIATION_TIME)
        )

        fun findByName(name: String): InputTypeItem? {
            return inputTypes.firstOrNull { it.name == name }
        }

        fun findByDescription(description: String): InputTypeItem? {
            return inputTypes.firstOrNull { it.description == description }
        }

        fun getSubset(indexes: List<Int>): List<InputTypeItem> {
            val subset = mutableListOf<InputTypeItem>()
            indexes.forEach { subset.add(inputTypes[it]) }
            return subset
        }

    }

}
