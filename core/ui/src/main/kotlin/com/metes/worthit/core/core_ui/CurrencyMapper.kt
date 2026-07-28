package com.metes.worthit.core.core_ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.ui.R as DesignCoreUi
import com.metes.worthit.core.designsystem.R as DesignR

val Currency.iconResId: Int
    @DrawableRes get() = when (this) {
        Currency.EUR -> DesignR.drawable.euro_24dp
        Currency.USD -> DesignR.drawable.usd_24dp
        Currency.GBP -> DesignR.drawable.gbp_24dp
        Currency.JPY -> DesignR.drawable.jpy_24dp
        Currency.INR -> DesignR.drawable.inr_24dp
        Currency.CNY -> DesignR.drawable.cny_24dp
    }

val Currency.titleResId: Int
    @StringRes get() = when (this) {
        Currency.EUR -> DesignCoreUi.string.currency_eur
        Currency.USD -> DesignCoreUi.string.currency_usd
        Currency.GBP -> DesignCoreUi.string.currency_gbp
        Currency.JPY -> DesignCoreUi.string.currency_jpy
        Currency.INR -> DesignCoreUi.string.currency_inr
        Currency.CNY -> DesignCoreUi.string.chinese_yuan
    }