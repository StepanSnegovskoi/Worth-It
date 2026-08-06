package com.metes.worthit.feature.share.di

import dagger.MapKey

@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ActionKey(val value: String)
