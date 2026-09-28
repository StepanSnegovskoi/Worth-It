package com.metes.worthit.core.testing

import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.domain.entity.Item
import java.math.BigDecimal
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun testItem(
    id: Int = 77,
    name: String = "name",
    price: BigDecimal? = BigDecimal.ONE,
    currency: Currency = Currency.EUR,
    createdAt: Instant = Instant.parse("2025-01-01T10:00:00Z"),
    dateOfPurchase: LocalDate = LocalDate.of(2025, 1, 1),
    description: String? = "description",
    imageLocalPath: String? = null,
) = Item(
    id = id,
    name = name,
    price = price,
    currency = currency,
    createdAt = createdAt,
    dateOfPurchase = dateOfPurchase,
    description = description,
    imageLocalPath = imageLocalPath,
)

val testItem = testItem()

val testClock = Clock.fixed(Instant.parse("2026-09-24T10:00:00Z"), ZoneId.of("UTC"))
