object Deps {

    //region Core
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinStdLib}"
    //endregion

    //region Jetpack
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val rv = "androidx.recyclerview:recyclerview:${Versions.rv}"
    val jetpack = arrayOf(
            appCompat,
            coreKtx,
            rv
    )
    //endregion

    //region Rx
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    val rx = arrayOf(
            rxJava,
            rxAndroid
    )
    //endregion

    //region Network
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.gsonConverter}"
    const val rxAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.rxAdapter}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
    val network = arrayOf(
            retrofit,
            gsonConverter,
            rxAdapter,
            okhttp,
            loggingInterceptor
    )
    //endregion

    //region DI
    const val koinAndroid = "org.koin:koin-android:${Versions.koinAndroid}"
    const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koinViewModel}"
    val di = arrayOf(
            koinAndroid,
            koinViewModel
    )
    //endregion

    //region Test
    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val runner = "androidx.test:runner:${Versions.runner}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    //endregion
}