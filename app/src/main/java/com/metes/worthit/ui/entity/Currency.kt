package com.metes.worthit.ui.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.metes.worthit.R

enum class Currency(
    @param:DrawableRes val iconRes: Int,
    @param:StringRes val titleRes: Int
) {
    EUR(R.drawable.euro_24dp, R.string.currency_eur),
    USD(R.drawable.usd_24dp, R.string.currency_usd),
    GBP(R.drawable.gbp_24dp, R.string.currency_gbp),
    JPY(R.drawable.jpy_24dp, R.string.currency_jpy),
    INR(R.drawable.inr_24dp, R.string.currency_inr),
}