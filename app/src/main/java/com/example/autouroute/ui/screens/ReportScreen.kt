package com.example.autouroute.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.outlined.LocationOff
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.autouroute.data.ApiClient
import com.example.autouroute.data.Strings
import com.example.autouroute.ui.components.AppHeader
import com.example.autouroute.ui.components.PrimaryButton
import com.example.autouroute.ui.theme.DarkBackground
import com.example.autouroute.ui.theme.InputGray
import com.example.autouroute.ui.theme.LightGreenCard
import com.example.autouroute.ui.theme.Primary
import com.example.autouroute.ui.theme.TextDark
import com.example.autouroute.ui.theme.TextGray
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.launch
import java.io.File

val reportTypes = mapOf(
    "entretien" to Pair("Entretient des Routes", listOf(
        "Marquages dégradées",
        "Pannaux de signalisation endommagées ou manquantes",
        "Trous ou ondulations sur la route"
    )),
    "peage" to Pair("Probléme de Péage", listOf(
        "Dysfonctionnment du systéme",
        "Ereur de facturation"
    )),
    "eclairage" to Pair("Eclirage défectueux", listOf(
        "Lampadaires non allumés",
        "Lampadaires endommagés",
        "Eclirage interlittent",
        "Eclirage insuifisantes"
    )),
    "securite" to Pair("Problème de Sécurité", listOf(
        "Obstacles et objets sur la route",
        "Barriére de sécurité endommagées",
        "Glisséres de sécurité défectuuses",
        "Travaux routier dangereux"
    )),
    "usagers" to Pair("Probléme de Service aux usagers", listOf(
        "Toilette publiques non opérationnelle",
        "Station de service non opérationnelle"
    ))
)

@SuppressLint("MissingPermission")
@Composable
fun ReportScreen(
    type: String,
    userPhone: String,
    onValider: () -> Unit
) {
    val config = reportTypes[type] ?: reportTypes["entretien"]!!
    val (title, options) = config
    val isDark = type == "securite"
    var selected by remember { mutableStateOf<Int?>(null) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var showPhotoOptionsDialog by remember { mutableStateOf(false) }
    var isSubmitting by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // ── Geolocation state ──
    var latitude by remember { mutableDoubleStateOf(0.0) }
    var longitude by remember { mutableDoubleStateOf(0.0) }
    var locationAcquired by remember { mutableStateOf(false) }
    var locationLoading by remember { mutableStateOf(false) }
    var locationPermissionGranted by remember { mutableStateOf(false) }
    var locationError by remember { mutableStateOf<String?>(null) }

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    // Function to fetch current GPS position
    fun fetchLocation() {
        locationLoading = true
        locationError = null
        val cancellationToken = CancellationTokenSource()
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationToken.token
        ).addOnSuccessListener { location ->
            locationLoading = false
            if (location != null) {
                latitude = location.latitude
                longitude = location.longitude
                locationAcquired = true
            } else {
                locationError = Strings.LOCATION_FAILED
            }
        }.addOnFailureListener {
            locationLoading = false
            locationError = Strings.LOCATION_FAILED
        }
    }

    // Location permission launcher
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        locationPermissionGranted = fineGranted || coarseGranted
        if (locationPermissionGranted) {
            fetchLocation()
        } else {
            locationError = Strings.LOCATION_PERMISSION_DENIED
        }
    }

    // Request location permission on screen load
    LaunchedEffect(Unit) {
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    // ── Camera / Gallery launchers ──
    val takePictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            // URI was set before launch, image saved to it
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val photoFile = File(context.cacheDir, "report_photo_${System.currentTimeMillis()}.jpg")
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                photoFile
            )
            selectedImageUri = uri
            takePictureLauncher.launch(uri)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri = uri
    }

    val bg = if (isDark) DarkBackground else Color.White
    val titleColor = Primary
    val instructionsColor = if (isDark) Color(0xFFCCCCCC) else TextGray
    val cardBg = LightGreenCard
    val photoBg = if (isDark) TextGray else InputGray

    if (showPhotoOptionsDialog) {
        AlertDialog(
            onDismissRequest = { showPhotoOptionsDialog = false },
            title = { Text(Strings.CHOISIR_PHOTO) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showPhotoOptionsDialog = false
                                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Primary)
                        Spacer(Modifier.size(16.dp))
                        Text(Strings.PHOTO_TAKE, fontSize = 16.sp, color = TextDark)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showPhotoOptionsDialog = false
                                galleryLauncher.launch("image/*")
                            }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.PhotoLibrary, contentDescription = null, tint = Primary)
                        Spacer(Modifier.size(16.dp))
                        Text(Strings.PHOTO_GALLERY, fontSize = 16.sp, color = TextDark)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showPhotoOptionsDialog = false }) {
                    Text("Annuler", color = Primary)
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .verticalScroll(rememberScrollState())
    ) {
        AppHeader()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                title,
                fontSize = 24.sp,
                color = titleColor,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                Strings.REPORT_INSTRUCTIONS,
                fontSize = 15.sp,
                color = instructionsColor,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            PhotoPickerArea(
                selectedImageUri = selectedImageUri,
                photoBg = photoBg,
                onClick = { showPhotoOptionsDialog = true }
            )
            Spacer(Modifier.height(24.dp))

            // ── GPS Location status card ──
            LocationStatusCard(
                locationAcquired = locationAcquired,
                locationLoading = locationLoading,
                latitude = latitude,
                longitude = longitude,
                locationError = locationError,
                isDark = isDark,
                onRetry = {
                    if (locationPermissionGranted) {
                        fetchLocation()
                    } else {
                        locationPermissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }
                }
            )

            Spacer(Modifier.height(20.dp))

            options.forEachIndexed { index, label ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .background(cardBg, RoundedCornerShape(12.dp))
                        .padding(16.dp, 14.dp)
                        .clickable { selected = index },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (selected == index) Icons.Default.RadioButtonChecked else Icons.Outlined.RadioButtonUnchecked,
                        contentDescription = null,
                        tint = if (selected == index) Primary else TextDark,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.size(12.dp))
                    Text(label, fontSize = 16.sp, color = TextDark, modifier = Modifier.weight(1f))
                }
            }
            Spacer(Modifier.height(16.dp))

            // ── Validate button — blocked if no GPS coordinates or no selection ──
            if (isSubmitting) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Primary, modifier = Modifier.size(36.dp))
                }
            } else {
                PrimaryButton(
                    text = Strings.VALIDER,
                    onClick = {
                        if (!locationAcquired) {
                            Toast.makeText(context, Strings.LOCATION_REQUIRED, Toast.LENGTH_LONG).show()
                            return@PrimaryButton
                        }
                        if (selected == null) {
                            Toast.makeText(context, "Veuillez sélectionner une option", Toast.LENGTH_SHORT).show()
                            return@PrimaryButton
                        }
                        if (userPhone.isBlank()) {
                            Toast.makeText(context, "Veuillez vous connecter pour envoyer une réclamation", Toast.LENGTH_LONG).show()
                            return@PrimaryButton
                        }
                        // Submit complaint to the backend
                        isSubmitting = true
                        val selectedCategory = options[selected!!]
                        coroutineScope.launch {
                            val (success, msg) = ApiClient.submitReclamation(
                                userPhone = userPhone,
                                userName = "",
                                type = title,
                                category = selectedCategory,
                                message = "$title - $selectedCategory",
                                latitude = latitude,
                                longitude = longitude
                            )
                            isSubmitting = false
                            if (success) {
                                Toast.makeText(context, "Réclamation envoyée!", Toast.LENGTH_SHORT).show()
                                onValider()
                            } else {
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                )
            }

            // Show warning if location not acquired
            AnimatedVisibility(
                visible = !locationAcquired && !locationLoading,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Outlined.LocationOff,
                        contentDescription = null,
                        tint = Color(0xFFE53935),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        Strings.LOCATION_REQUIRED,
                        fontSize = 13.sp,
                        color = Color(0xFFE53935)
                    )
                }
            }
        }
    }
}

// ── GPS Location status card ──
@Composable
private fun LocationStatusCard(
    locationAcquired: Boolean,
    locationLoading: Boolean,
    latitude: Double,
    longitude: Double,
    locationError: String?,
    isDark: Boolean,
    onRetry: () -> Unit
) {
    val bgColor = when {
        locationAcquired -> Color(0xFF1B5E20).copy(alpha = 0.12f)
        locationError != null -> Color(0xFFE53935).copy(alpha = 0.10f)
        else -> if (isDark) Color.White.copy(alpha = 0.08f) else Color(0xFFF5F5F5)
    }
    val iconColor = when {
        locationAcquired -> Color(0xFF2E7D32)
        locationError != null -> Color(0xFFE53935)
        else -> Primary
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor, RoundedCornerShape(14.dp))
            .clickable(enabled = !locationLoading) { onRetry() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (locationLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(22.dp),
                color = Primary,
                strokeWidth = 2.dp
            )
        } else {
            Icon(
                imageVector = if (locationAcquired) Icons.Default.LocationOn else Icons.Default.MyLocation,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = when {
                    locationLoading -> Strings.LOCATION_ACQUIRING
                    locationAcquired -> Strings.LOCATION_ACQUIRED
                    locationError != null -> locationError
                    else -> Strings.LOCATION_SHARE
                },
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (isDark) Color.White else TextDark
            )
            if (locationAcquired) {
                Text(
                    text = "Lat: ${"%.6f".format(latitude)}, Lng: ${"%.6f".format(longitude)}",
                    fontSize = 12.sp,
                    color = if (isDark) Color(0xFFBBBBBB) else TextGray
                )
            }
            if (!locationAcquired && !locationLoading && locationError == null) {
                Text(
                    text = Strings.LOCATION_REQUIRED,
                    fontSize = 12.sp,
                    color = Color(0xFFE53935)
                )
            }
        }
    }
}

@Composable
private fun PhotoPickerArea(
    selectedImageUri: Uri?,
    photoBg: Color,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val bitmap = remember(selectedImageUri) {
        selectedImageUri?.let { uri ->
            try {
                context.contentResolver.openInputStream(uri)?.use { stream ->
                    BitmapFactory.decodeStream(stream)
                }
            }
            catch (e: Exception) {
                null
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(photoBg, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            Text(
                Strings.CHOISIR_PHOTO,
                fontSize = 16.sp,
                color = TextDark
            )
        }
    }
}