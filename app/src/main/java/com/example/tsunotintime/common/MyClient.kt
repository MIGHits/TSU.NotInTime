package com.example.tsunotintime.common

import com.example.tsunotintime.data.remote.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateException
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory

object MyClient {

    fun getUnsafeOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient? {
        return try {
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                        return arrayOf()
                    }
                }
            )

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            val sslSocketFactory = sslContext.socketFactory
            val trustManagerFactory: TrustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(null as KeyStore?)
            val trustManagers: Array<TrustManager> =
                trustManagerFactory.trustManagers
            check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
                "Unexpected default trust managers:" + trustManagers.contentToString()
            }

            val trustManager =
                trustManagers[0] as X509TrustManager


            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val builder = OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                .hostnameVerifier(HostnameVerifier { _, _ -> true }) // Disable hostname verification
                .addInterceptor(loggingInterceptor) // Add the logging interceptor
                .addInterceptor(authInterceptor)
            builder.build()

        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
