package com.zeustech.zeuskit.ui.fragments

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

data class PermissionModel(
    val description: String,
    val denied: Boolean
)

open class IntentFragment(
    @LayoutRes contentLayoutId: Int
): Fragment(contentLayoutId) {

    private val PERMISSIONS_REQUEST = 100

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode != PERMISSIONS_REQUEST) return
        val result = permissions.mapIndexed { index, permission ->
            PermissionModel(
                permission,
                grantResults[index] == PackageManager.PERMISSION_DENIED
            )
        }
        onPermissionResult(result)
    }

    protected fun requestPermission(permission: String) =  requestPermissions(arrayOf(permission))

    protected fun requestPermissions(permissions: Array<String>) {
        requestPermissions(
            permissions.filter { isPermissionDenied(it) }.toTypedArray(),
            PERMISSIONS_REQUEST
        )
    }

    protected fun isPermissionDenied(permission: String): Boolean {
        activity?.let {
            return ContextCompat.checkSelfPermission(
                it,
                permission
            ) == PackageManager.PERMISSION_DENIED
        }
        return false
    }

    protected fun startIntent(intent: Intent) = activity?.startActivity(intent)

    private fun onPermissionResult(permissions: List<PermissionModel>) {
        permissions.forEach { permission ->
            when (permission.description) {
                Manifest.permission.CALL_PHONE -> {
                    onPhoneCallPermission(permission.denied)
                }
                Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                    onWriteExternalStoragePermission(permission.denied)
                }
            }
            // TODO: Here Implement Other Permissions & add abstract methods
        }
    }

    protected fun requestPhoneCallPermission() = requestPermission(Manifest.permission.CALL_PHONE)

    protected fun isPhonePermissionDenied(): Boolean = isPermissionDenied(Manifest.permission.CALL_PHONE)

    protected fun requestWriteExternalStoragePermission() = requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    protected fun isWriteExternalStoragePermissionDenied() = isPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    protected fun onPhoneCallPermission(denied: Boolean) { }

    protected fun onWriteExternalStoragePermission(denied: Boolean) { }

    protected fun startCallPhoneIntent(phoneNumber: String) {
        try {
            startIntent(Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:$phoneNumber")))
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    protected fun startBrowserIntent(url: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    protected fun requestToOpenApp(appPackageName: String) {
        // val appPackageName = context?.packageName ?: return
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (anfe: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }

    protected fun requestToOpenPdf(uri: Uri) {
        val target = Intent(Intent.ACTION_VIEW)
            .setDataAndType(uri, "application/pdf")
            .setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        val intent = Intent.createChooser(target, "Open File")
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Instruct the user to install a PDF reader here, or something
        }
    }
}