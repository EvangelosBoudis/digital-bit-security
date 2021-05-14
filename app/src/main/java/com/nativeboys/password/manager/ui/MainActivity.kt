package com.nativeboys.password.manager.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.presentation.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        ) // hide content of program from Android “Overview Screen” + Do not allow ScreenShots
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_activity_fragment) as NavHostFragment
        navController = navHostFragment.navController
        viewModel.initIfRequired()
        viewModel.darkThemeMode.observe(this) { mode ->
            if (mode != AppCompatDelegate.getDefaultNightMode()) {
                AppCompatDelegate.setDefaultNightMode(mode)
            }
        }
    }

}

/*
* TODO:
*  1. Settings Modify Pass, Fast Login enable / disable via Settings (master password prompt), Buy me a coffee
*  2. Secret Key dialog on import / export database + require master password
*  3. Welcome Screen with basic Information about the app
*  4. replace PreferenceManager & CipherStorage with userRepository/appRepository + requestPermission not in ViewModel!!! + Remove ZeusKit + Interface Injection (Impl) + Code improvement
*
*  (maybe): Regular Expression Weak-Medium-Strong Password, Password Generator Fragment
* */

/*
  IconMaker: https://www.namecheap.com/logo-maker/app/editor

  Biometrics:
  https://developer.android.com/codelabs/biometric-login#1
  https://android-developers.googleblog.com/2019/10/one-biometric-api-over-all-android.html
  https://developer.android.com/training/sign-in/biometric-auth

  Account Manager for saving tokens:
  https://developer.android.com/reference/android/accounts/AccountManager.html

  DataStore Security with SQLCipher:
  https://blog.stylingandroid.com/datastore-security/

  Encrypted Shared Preferences:
  https://developer.android.com/reference/kotlin/androidx/security/crypto/EncryptedSharedPreferences.html

  KeyStore usage ( blog entry 1 and blog entry 2):
  https://stackoverflow.com/questions/21987170/how-to-store-the-key-used-in-sqlcipher-for-android

  Import/Export:
  https://github.com/rafi0101/Android-Room-Database-Backup
  https://github.com/android/architecture-components-samples/issues/340

  Keystore Manager:
  https://github.com/sauravpradhan/AndroidKeyStore
  https://github.com/phanirajabhandari/android-keystore-example

  Documentation:
  https://developer.android.com/training/data-storage/room/relationships
  https://developer.android.com/training/dependency-injection/hilt-android
  https://developer.android.com/training/dependency-injection/dagger-basics
  https://developer.android.com/codelabs/advanced-kotlin-coroutines#0
  https://material.io/components/buttons/android#toggle-button

  Tutorials:
  Nice profile: https://github.com/jisungbin
  https://github.com/sberoch/RickAndMorty-AndroidArchitectureSample/blob/master/app/src/main/java/com/example/rickandmorty/di/AppModule.kt
  https://stackoverflow.com/questions/64831704/how-can-i-use-hilt-to-inject-retrofit-to-repository-which-is-injected-to-viewmo
  https://stackoverflow.com/questions/63321751/android-dagger-hilt-do-we-need-scope-annotations-for-viewmodels
  https://guides.codepath.com/android/handling-scrolls-with-coordinatorlayout

  Animations:
  https://developer.android.com/guide/fragments/animate
  https://codelabs.developers.google.com/codelabs/material-motion-android#4
  https://medium.com/@j.c.moreirapinto/recyclerview-shared-transitions-in-android-navigation-architecture-component-16eb902b39ba

* */