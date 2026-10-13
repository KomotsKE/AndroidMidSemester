package io.github.dekabrsky.androidMidSemester.di

import io.github.dekabrsky.androidMidSemester.data.cache.MatchesMemoryCache
import io.github.dekabrsky.androidMidSemester.data.repository.MatchRepositoryImpl
import io.github.dekabrsky.androidMidSemester.data.source.EspnMatchesDataSource
import io.github.dekabrsky.androidMidSemester.data.source.MatchesRemoteDataSource
import io.github.dekabrsky.androidMidSemester.data.source.SStatsMatchesDataSource
import io.github.dekabrsky.androidMidSemester.domain.repository.MatchRepository
import org.koin.dsl.module

enum class MatchesProvider { SSTATS, ESPN }

val ACTIVE_MATCHES_PROVIDER = MatchesProvider.ESPN

val repositoryModule = module {
    single { SStatsMatchesDataSource(get()) }
    single { EspnMatchesDataSource(get()) }
    single<MatchesRemoteDataSource> {
        when (ACTIVE_MATCHES_PROVIDER) {
            MatchesProvider.SSTATS -> get<SStatsMatchesDataSource>()
            MatchesProvider.ESPN -> get<EspnMatchesDataSource>()
        }
    }

    single { MatchesMemoryCache() }
    single<MatchRepository> { MatchRepositoryImpl(get(), get()) }
}
