package com.example.autouroute.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object ApiClient {
    // CHANGE THIS: Run ipconfig on PC, use your IPv4. Phone + PC on same WiFi.
    private const val BASE_URL = "http://172.29.137.13/Autouroute-main/api/"

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
                conn.setRequestProperty("Connection", "close")  // Prevents "connection reset" with Apache/XAMPP
                conn.doOutput = true
                conn.connectTimeout = 15000  // 15 seconds - prevents infinite loading if server unreachable
                conn.readTimeout = 15000    // 15 seconds

                val jsonBody = JSONObject().apply {
                    put("action", action)
                    put("prenom", prenom)
                    put("nom", nom)
                    put("genre", genre)
                    put("age", age)
                    put("num_telephone", phone)  // PHP expects num_telephone, not phone
                    put("code", code)
                }

                OutputStreamWriter(conn.outputStream).use { writer ->
                    writer.write(jsonBody.toString())
                    writer.flush()
                }

                val responseCode = conn.responseCode
                val responseStr = if (responseCode == HttpURLConnection.HTTP_OK) {
                    conn.inputStream.bufferedReader().use { it.readText() }
                } else {
                    (conn.errorStream?.bufferedReader()?.use { it.readText() } ?: "Server error $responseCode")
                }
                Log.d("ApiClient", "Response ($responseCode): $responseStr")

                val resJson = try { JSONObject(responseStr) } catch (_: Exception) {
                    return@withContext Pair(false, responseStr)
                }
                val success = resJson.optBoolean("success", false)
                val message = resJson.optString("message", "")
                val otp = resJson.optString("otp", "")

                if (success) return@withContext Pair(true, otp.ifEmpty { message })
                return@withContext Pair(false, message.ifEmpty { "Erreur serveur" })
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext Pair(false, "Connection failed: ${e.message}")
            }
        }
    }
}
