package com.metes.worthit.core.intent

import android.content.Intent
import com.metes.worthit.intent.AppIntentEvent
import com.metes.worthit.intent.IntentParser
import com.metes.worthit.intent.IntentProcessor
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class IntentParserTest {

    private val processors: Map<String, IntentProcessor> = hashMapOf(
        Intent.ACTION_SEND to object : IntentProcessor {
            override fun extractEvent(intent: Intent): AppIntentEvent {
                return AppIntentEvent.Ignored
            }
        },
    )
    private lateinit var intentParser: IntentParser

    @Before
    fun setup() {
        intentParser = IntentParser(processors = processors)
    }

    @Test
    fun `should return correct value from processors by action`() {
        val intent = Intent(Intent.ACTION_SEND)
        val event = intentParser.parse(intent)
        assertEquals(AppIntentEvent.Ignored, event)
    }
}
