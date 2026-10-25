
package ca.gbc.comp3074.Mirpour_Jamshaid.Lab5

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                LocationScreen()
            }
        }
    }
}

@Composable
fun LocationScreen() {
    val context = LocalContext.current

    val locationClient = remember(context) {
        LocationServices.getFusedLocationProviderClient(context)
    }

    var locationText by remember {
        mutableStateOf("No location retrieved yet.")
    }

    var status by remember {
        mutableStateOf("Press the button to get your location.")
    }

    fun hasLocationPermission(): Boolean {
        val fine = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarse = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return fine || coarse
    }

    fun fetchLocation() {
        if (!hasLocationPermission()) {
            status = "Location permission not granted."
            return
        }

        status = "Getting location..."

        try {
            val cancellationToken = CancellationTokenSource()

            locationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationToken.token
            ).addOnSuccessListener { location ->
                if (location != null) {
                    locationText = "Latitude: ${location.latitude}\n" +
                            "Longitude: ${location.longitude}"
                    status = "Location retrieved successfully."
                } else {
                    status = "Location unavailable. Check device GPS."
                }
            }.addOnFailureListener { exception ->
                status = "Location error: ${exception.localizedMessage}"
            }
        } catch (exception: SecurityException) {
            status = "Location permission is required."
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.values.any { it }) {
            fetchLocation()
        } else {
            status = "Location permission denied."
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Lab 5 - GPS Location",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = locationText)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = status)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (hasLocationPermission()) {
                    fetchLocation()
                } else {
                    permissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
            }
        ) {
            Text("Get GPS Location")
        }
    }
}
