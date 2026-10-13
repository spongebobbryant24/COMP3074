
package com.example.labex3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Represents one message in the list
data class ChatMessage(
    val sender: String,
    val text: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MessageList()
            }
        }
    }
}

// Displays the scrollable list of messages
@Composable
fun MessageList() {

    val sampleMessages = listOf(
        ChatMessage("Joe", "Hi!"),
        ChatMessage("Jim", "How are you?"),
        ChatMessage("Joe", "Test..1..2...3"),
        ChatMessage("Joe", "I hate coding!!!")
    )

    // Repeat the four messages three times
    val messages = List(3) { sampleMessages }.flatten()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9FF)),
        contentPadding = PaddingValues(
            start = 28.dp,
            end = 16.dp,
            top = 16.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(messages) { message ->
            MessageItem(message)
        }
    }
}

// Custom Composable for a single message
@Composable
fun MessageItem(message: ChatMessage) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {

        // Circular Android avatar
        MessageAvatar()

        Spacer(modifier = Modifier.width(12.dp))

        Column {

            // Sender's name
            Text(
                text = message.sender,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF625D71)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Rounded message bubble
            Surface(
                shape = RoundedCornerShape(22.dp),
                color = Color(0xFFFFF9FF),
                shadowElevation = 2.dp,
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    Color(0xFFF0EAF2)
                )
            ) {
                Text(
                    text = message.text,
                    fontSize = 17.sp,
                    color = Color(0xFF202020),
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 8.dp
                    )
                )
            }
        }
    }
}

// Draws a circular avatar with a red border
@Composable
fun MessageAvatar() {

    Box(
        modifier = Modifier
            .size(52.dp)
            .border(
                width = 2.dp,
                color = Color.Red,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = Modifier.size(36.dp)) {

            val w = size.width
            val h = size.height

            // Android robot antennae
            drawLine(
                color = Color.LightGray,
                start = Offset(w * 0.35f, h * 0.35f),
                end = Offset(w * 0.28f, h * 0.20f),
                strokeWidth = 2f
            )

            drawLine(
                color = Color.LightGray,
                start = Offset(w * 0.65f, h * 0.35f),
                end = Offset(w * 0.72f, h * 0.20f),
                strokeWidth = 2f
            )

            // Android robot head
            drawArc(
                color = Color(0xFFE6E6E6),
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(w * 0.10f, h * 0.25f),
                size = Size(w * 0.80f, h * 0.80f)
            )

            // Robot eyes
            drawCircle(
                color = Color.White,
                radius = w * 0.045f,
                center = Offset(w * 0.36f, h * 0.48f)
            )

            drawCircle(
                color = Color.White,
                radius = w * 0.045f,
                center = Offset(w * 0.64f, h * 0.48f)
            )
        }
    }
}
