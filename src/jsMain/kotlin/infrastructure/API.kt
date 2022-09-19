package infrastructure

import com.plainconcepts.hello.common.LanguageDTO
import com.plainconcepts.hello.common.LanguageRequest
import com.plainconcepts.hello.common.Message
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.browser.window

internal class API(engine: HttpClientEngine = JsClient().create()) {
    private val endpoint = window.location.host

    private val client = HttpClient(engine) {
        install(ContentNegotiation) {
            json()
        }
        defaultRequest {
            url.host = endpoint
            contentType(ContentType.Application.Json)
        }
    }

    suspend fun getHello(lang: String? = null): String {
        return client.get("/api/hello") {
            lang?.let { url { parameters.append("lang", lang) } }
        }.body<Message>().message
    }

    suspend fun upsertHello(code: String, lang: String, message: String): HttpResponse {
        return client.put("/api/hello") {
            setBody(LanguageRequest(code = code, name=lang, hello = message))
        }
    }

    suspend fun getLanguages(): List<LanguageDTO> {
        return client.get("/api/languages").body()
    }
}