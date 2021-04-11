package com.nativeboys.password.manager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nativeboys.password.manager.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

/*TODO:
*  1. Regular Expression Weak-Medium-Strong Password
*  2. Confirmation Dialog
*  3. Category Thumbnail Chooser
*  4. Master Password Constructor Fragment
*  5. Master Password Fragment
*  6. Password Generator Fragment
*  7. Welcome Screen with basic Information about the app
*  8. Settings (exit, theme, patreon link)*/

/*
* Tutorials:
* Nice profile: https://github.com/jisungbin
* https://github.com/sberoch/RickAndMorty-AndroidArchitectureSample/blob/master/app/src/main/java/com/example/rickandmorty/di/AppModule.kt
* https://stackoverflow.com/questions/64831704/how-can-i-use-hilt-to-inject-retrofit-to-repository-which-is-injected-to-viewmo
*
* Documentation:
* https://developer.android.com/training/data-storage/room/relationships
* https://developer.android.com/training/dependency-injection/hilt-android
* https://developer.android.com/training/dependency-injection/dagger-basics
* https://developer.android.com/codelabs/advanced-kotlin-coroutines#0
* https://material.io/components/buttons/android#toggle-button
*
* Animations:
* https://developer.android.com/guide/fragments/animate
* https://codelabs.developers.google.com/codelabs/material-motion-android#4
* https://medium.com/@j.c.moreirapinto/recyclerview-shared-transitions-in-android-navigation-architecture-component-16eb902b39ba
*
* */
