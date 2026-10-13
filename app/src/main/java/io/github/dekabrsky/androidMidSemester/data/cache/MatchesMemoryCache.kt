package io.github.dekabrsky.androidMidSemester.data.cache

import io.github.dekabrsky.androidMidSemester.domain.model.Match
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.LocalDate

class MatchesMemoryCache {

    private val mutex = Mutex()
    private val matchesByDate = mutableMapOf<LocalDate, List<Match>>()

    suspend fun getOrLoad(
        date: LocalDate,
        load: suspend () -> List<Match>,
    ): List<Match> = mutex.withLock {
        matchesByDate[date] ?: load().also { matchesByDate[date] = it }
    }

    suspend fun clear() = mutex.withLock { matchesByDate.clear() }
}
