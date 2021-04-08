package com.nativeboys.password.manager.data.preferences

enum class SortOrder { BY_NAME, BY_DATE }

data class ItemSettings(val sortOrder: SortOrder, val hideNonFavorites: Boolean)