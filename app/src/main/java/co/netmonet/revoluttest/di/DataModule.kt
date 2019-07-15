package co.netmonet.revoluttest.di

import co.netmonet.revoluttest.data.repository.RevolutRepository
import co.netmonet.revoluttest.data.repository.RevolutRepositoryImpl
import org.koin.dsl.module.module


val dataModule = module {
    single<RevolutRepository> { RevolutRepositoryImpl(get()) }
}