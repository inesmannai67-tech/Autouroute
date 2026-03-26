package com.example.autouroute.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object ApiClient {
    // For physical device: use your PC's WiFi IP (phone + PC must be on same WiFi)
    // For emulator only: change to http://10.0.2.2/Autouroute-master/api/
    // NOTE: If your PC IPv4 changes, update this value (see `ipconfig`).
    private const val BASE_URL = "http://192.168.1.153/Autouroute-master/api/"

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

    suspend fun submitReclamation(
        userPhone: String,
        userName: String,
        type: String,
        category: String,
        message: String = "",
        latitude: Double,
        longitude: Double
    ): Pair<Boolean, String> {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("${BASE_URL}reclamation.php")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json")
                conn.setRequestProperty("Connection", "close")
                conn.doOutput = true
                conn.connectTimeout = 15000
                conn.readTimeout = 15000

                val jsonBody = JSONObject().apply {
                    put("user_phone", userPhone)
                    put("user_name", userName)
                    put("type", type)
                    put("category", category)
                    put("message", message)
                    put("latitude", latitude)
                    put("longitude", longitude)
                }

                Log.d("ApiClient", "Submitting reclamation: $jsonBody")

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
                Log.d("ApiClient", "Reclamation response ($responseCode): $responseStr")

                val resJson = try { JSONObject(responseStr) } catch (_: Exception) {
                    return@withContext Pair(false, responseStr)
                }
                val success = resJson.optBoolean("success", false)
                val msg = resJson.optString("message", "")

                return@withContext Pair(success, msg.ifEmpty { "Erreur serveur" })
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext Pair(false, "Connection failed: ${e.message}")
            }
        }
    }
}
