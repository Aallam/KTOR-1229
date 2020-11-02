package com.aallam.ktor1229

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            HttpClient(Android) {
                install(Logging) {
                    level = LogLevel.ALL
                    logger = Logger.SIMPLE
                }
                install(HttpTimeout)
            }.use { client ->
                val request = HttpRequestBuilder().apply {
                    url.protocol = URLProtocol.HTTPS
                    url.host = "httpbin.org"
                    url.path("delay/1000")
                    timeout {
                        socketTimeoutMillis = 100
                    }
                }
                client.get<String>(request)
            }
        }
    }
}
