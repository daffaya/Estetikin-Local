package com.codegeniuses.estetikin.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Module(
    val iconModule: Int,
    val titleModule: String
): Parcelable
