package com.example.smarcityv1.mapScreen

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.smarcityv1.ui.theme.SmarCityV1Theme
import com.google.ar.core.Anchor
import com.google.ar.core.Config
import com.google.ar.core.TrackingState
import kotlin.collections.forEach
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.arcore.ArFrame
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.ArNode
import io.github.sceneview.ar.node.PlacementMode

class ARActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val model = intent.getStringExtra("model") ?: "model3"
        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)
        val routeCoordinates = intent.getParcelableArrayListExtra<Location>("routeCoordinates") ?: arrayListOf()

        setContent {
            SmarCityV1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ARScreen(model, latitude, longitude, routeCoordinates)
                }
            }
        }
    }
}

@Composable
fun ARScreen(model: String, latitude: Double, longitude: Double, routeCoordinates: List<Location>) {
    val nodes = remember { mutableListOf<ArNode>() }
    val modelNode = remember { mutableStateOf<ArModelNode?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        ARScene(
            modifier = Modifier.fillMaxSize(),
            nodes = nodes,
            planeRenderer = true,
            onCreate = { arSceneView ->
                arSceneView.lightEstimationMode = Config.LightEstimationMode.DISABLED
                arSceneView.planeRenderer.isShadowReceiver = false
                modelNode.value = ArModelNode(arSceneView.engine, PlacementMode.INSTANT).apply {
                    loadModelGlbAsync(
                        glbFileLocation = "models/model3.glb",
                        scaleToUnits = 0.8f
                    ) {
                        // Model loaded
                    }
                }
                nodes.add(modelNode.value!!)
            },
            onSessionCreate = {
                planeRenderer.isVisible = false
            },
            onFrame = { frame: ArFrame ->
                val earth = frame.session?.earth
                if (earth?.trackingState == TrackingState.TRACKING) {
                    routeCoordinates.forEach { location ->
                        val anchor: Anchor? = earth.createAnchor(
                            location.latitude,
                            location.longitude,
                            0.0,  // Altitude
                            0.0f, 0.0f, 0.0f, 1.0f  // Quaternion rotation
                        )
                        modelNode.value?.anchor = anchor
                    }
                }
            }
        )
    }

    LaunchedEffect(key1 = model) {
        modelNode.value?.loadModelGlbAsync(
            glbFileLocation = "models/model3.glb",
            scaleToUnits = 0.8f
        )
        Log.e("errorloading", "ERROR LOADING MODEL")
    }
}