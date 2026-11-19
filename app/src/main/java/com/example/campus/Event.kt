
package com.example.campus

import android.content.Context

data class Event(
    val id: Int,
    val title: String,
    val category: String,
    val location: String?,
    val capacity: Int,
    val description: String
)

// Liste de 5 événements pour l'exercice
fun demoEvents(context: Context): List<Event> = listOf(
    Event(
        1,
        context.getString(R.string.event1_title),
        context.getString(R.string.category_tech),
        context.getString(R.string.event1_location),
        12,
        context.getString(R.string.event1_description)
    ),
    Event(
        2,
        context.getString(R.string.event2_title),
        context.getString(R.string.category_tech),
        context.getString(R.string.event2_location),
        8,
        context.getString(R.string.event2_description)
    ),
    Event(
        3,
        context.getString(R.string.event3_title),
        context.getString(R.string.category_campus),
        null,
        0,
        context.getString(R.string.event3_description)
    ),
    Event(
        4,
        context.getString(R.string.event4_title),
        context.getString(R.string.category_sport),
        context.getString(R.string.event4_location),
        24,
        context.getString(R.string.event4_description)
    ),
    Event(
        5,
        context.getString(R.string.event5_title),
        context.getString(R.string.category_loisirs),
        context.getString(R.string.event5_location),
        16,
        context.getString(R.string.event5_description)
    )
)
