package bootcamp.sparta.nb_deepen_assignment.di

import android.app.Application
import bootcamp.sparta.nb_deepen_assignment.repository.ContentRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication: Application() {
    val repository by lazy { ContentRepository() }
}