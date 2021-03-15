package com.zeustech.zeuskit.tools.other

import android.content.Context
import okhttp3.OkHttpClient
import java.io.File
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class CertificateManager {
    private fun generateKeyStoreFromAssets(
        context: Context,
        directory: String
    ): KeyStore? {
        try {
            val certificates: MutableList<Certificate> =
                ArrayList()
            val certificateFactory =
                CertificateFactory.getInstance("X.509")
            val assetManager = context.assets
            assetManager.list(directory)?.let {
                for (fileName in it) {
                    val path = directory + File.separator + fileName
                    assetManager.open(path)
                        .use { `is` -> certificates.add(certificateFactory.generateCertificate(`is`)) }
                }
                // Create a KeyStore containing our trusted CAs
                val keyStore =
                    KeyStore.getInstance(KeyStore.getDefaultType())
                // New stream, no password
                keyStore.load(null, null)
                for (i in certificates.indices) {
                    keyStore.setCertificateEntry(it[i], certificates[i])
                }
                return keyStore
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun applySystemCertificates(keyStore: KeyStore) {
        try {
            KeyStore.getInstance("AndroidCAStore")?.let { defaultCAs ->
                defaultCAs.load(null, null)
                val keyAliases = defaultCAs.aliases()
                while (keyAliases.hasMoreElements()) {
                    val alias = keyAliases.nextElement()
                    val cert = defaultCAs.getCertificate(alias)
                    try {
                        if (!keyStore.containsAlias(alias)) {
                            keyStore.setCertificateEntry(alias, cert)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun generateSSLClient(
        context: Context,
        assetsDir: String
    ): OkHttpClient {
        try {
            val keyStore =
                generateKeyStoreFromAssets(context, assetsDir)
            keyStore?.let { applySystemCertificates(it) }
            val trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(keyStore)
            val trustManagers =
                trustManagerFactory.trustManagers
            check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
                ("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers))
            }
            val trustManager =
                trustManagers[0] as X509TrustManager
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
            val sslSocketFactory = sslContext.socketFactory
            return OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                .build()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return OkHttpClient()
    }
}

// Get Certificate example Terminal: openssl s_client -connect cooksclub.zeushotels.gr:443