package com.ahmety.kotlinmvvm

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    // Hilt ile yönetilecek bağımlılıkların başlatılması bu sınıfta otomatik olur
}