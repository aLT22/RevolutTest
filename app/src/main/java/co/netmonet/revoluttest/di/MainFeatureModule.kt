package co.netmonet.revoluttest.di

import co.netmonet.revoluttest.RevolutApp
import co.netmonet.revoluttest.ui.MainActivityVM
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val mainFeatureModule = module {
    viewModel { MainActivityVM(androidApplication() as RevolutApp, get()) }
}