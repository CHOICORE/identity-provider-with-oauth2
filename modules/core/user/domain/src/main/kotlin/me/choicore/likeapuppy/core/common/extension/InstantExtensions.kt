package me.choicore.likeapuppy.core.common.extension

import java.time.Instant
import java.time.temporal.ChronoUnit

fun Instant.plusDays(days: Long): Instant {
    return this.plus(days, ChronoUnit.DAYS)
}

fun Instant.plusHours(hours: Long): Instant {
    return this.plus(hours, ChronoUnit.HOURS)
}

fun Instant.plusYears(years: Long): Instant {
    return this.plus(years, ChronoUnit.YEARS)
}
