package com.metes.worthit.feature.share.di

import android.content.Intent
import com.metes.worthit.feature.share.SendImageIntentProcessor
import com.metes.worthit.intent.IntentProcessor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
interface IntentProcessorModule {

    @Binds
    @IntoMap
    @ActionKey(Intent.ACTION_SEND)
    fun bindSendIntentProcessor(
        processor: SendImageIntentProcessor
    ): IntentProcessor
}