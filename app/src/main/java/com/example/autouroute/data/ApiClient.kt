package com.example.autouroute.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object ApiClient {
    // Use your computer's actual IPv4 address for real device testing
    private const val BASE_URL = "http://192.168.1.178/Autouroute-main/api/"

    suspend fun authRequest(
        action: String,
        prenom: String = "",
        nom: String = "",
        genre: String = "",
        age: String = "",
        phone: String = "",
        code: String = ""
    ): Pair<Boolean, String> {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("${BASE_URL}auth.php")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json")
                conn.doOutput = true

                val jsonBody = JSONObject().apply {
                    put("action", action)
                    put("prenom", prenom)
                    put("nom", nom)
                    put("genre", genre)
                    put("age", age)
                    put("phone", phone)
                    put("code", code)
                }

                OutputStreamWriter(conn.outputStream).use { writer ->
                    writer.write(jsonBody.toString())
                    writer.flush()
                }

                val responseCode = conn.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val responseStr = conn.inputStream.bufferedReader().use { it.readText() }
                    Log.d("ApiClient", "Response: $responseStr")
                    val resJson = JSONObject(responseStr)
                    val success = resJson.optBoolean("success", false)
                    val message = resJson.optString("message", "")
                    val otp = resJson.optString("otp", "")
                    
                    if (success) {
                        return@withContext Pair(true, otp.ifEmpty { message })
                    } else {
                        return@withContext Pair(false, message)
                    }
                } else {
                    return@withContext Pair(false, "Server Error: $responseCode")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext Pair(false, "Connection failed: ${e.message}")
            }
        }
    }
}
