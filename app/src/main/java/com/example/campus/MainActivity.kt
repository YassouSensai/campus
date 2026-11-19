
package com.example.campus

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.campus.ui.theme.CampusTheme

private const val TAG = "Campus"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val events = demoEvents(this)

        setContent {
            CampusTheme(dynamicColor = false) {
                EventListScreen(
                    events = events,
                    onEventClick = { event ->
                        Log.d(TAG, getString(R.string.log_open_event, event.title))
                    },
                    onBook = { event ->
                        Log.d(TAG, getString(R.string.log_book_event, event.title))
                    }
                )
            }
        }
    }
}
