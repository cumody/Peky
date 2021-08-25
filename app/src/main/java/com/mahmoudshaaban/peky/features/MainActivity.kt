package com.mahmoudshaaban.peky.features

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.mahmoudshaaban.peky.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        const val APP_UPDATE_REQUEST_CODE = 3
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
        configureStatusBarForFullScreen()


    }

    private fun configureStatusBarForFullScreen() {
        var flags = (
                // to prevent your layout from resizing when the system bars hide and show
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        //make status bar icons dark
                        or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        // its mandatory flag for fullmode screen
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }

        window.decorView.systemUiVisibility = flags
    }

    override fun onResume() {
        super.onResume()
        checkUpdate()
    }

    private fun checkUpdate() {
        Log.e(TAG, "checking")
        val updateManager = AppUpdateManagerFactory.create(this)
        val updateInfoTask = updateManager.appUpdateInfo
        updateInfoTask.addOnSuccessListener { updateInfo ->
            Log.i("MainActivity", updateInfo.updateAvailability().toString())
            val availability = updateInfo.availableVersionCode()
            if (availability == UpdateAvailability.UPDATE_AVAILABLE) {
                updateManager.startUpdateFlowForResult(
                    updateInfo,
                    AppUpdateType.IMMEDIATE,
                    this,
                    APP_UPDATE_REQUEST_CODE
                )
            } else if (availability == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                updateManager.startUpdateFlowForResult(
                    updateInfo,
                    AppUpdateType.IMMEDIATE,
                    this,
                    // A request code so that cancellations / failures can be caught within the calling component
                    APP_UPDATE_REQUEST_CODE
                )
            }
        }.addOnFailureListener { error ->
            Log.e("MainActivity", error.toString())
        }
    }



}