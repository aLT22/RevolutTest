package co.netmonet.revoluttest

import android.app.Application
import co.netmonet.revoluttest.di.appComponent
import org.koin.android.ext.android.startKoin


class RevolutApp : Application() {

    override fun onCreate() {
        super.onCreate()
        configureDi()
    }

    private fun configureDi() = startKoin(
            this,
            provideComponent()
    )

    private fun provideComponent() = appComponent
}