
package com.example.campus

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.campus.ui.theme.CampusTheme

// Carte d'un événement
@Composable
fun EventCard(
    event: Event,
    onClick: () -> Unit,
    onBook: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(onClick = onClick, modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    text = event.category,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = event.location ?: stringResource(R.string.location_unknown),
                style = MaterialTheme.typography.bodyMedium
            )

            if (event.capacity == 0) {
                Text(
                    text = stringResource(R.string.full),
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                Text(
                    text = pluralStringResource(
                        R.plurals.available_seats,
                        event.capacity,
                        event.capacity
                    )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onClick) {
                    Text(stringResource(R.string.see_details))
                }

                Button(
                    onClick = onBook,
                    enabled = event.capacity > 0
                ) {
                    Text(stringResource(R.string.book))
                }
            }
        }
    }
}

// Écran avec la liste des événements
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(
    events: List<Event>,
    onEventClick: (Event) -> Unit,
    onBook: (Event) -> Unit
) {
    val categories = events.map { it.category }.distinct()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) }
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Liste horizontale des catégories
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.categories),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(
                            start = 16.dp,
                            top = 12.dp
                        )
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(categories) { category ->
                            Surface(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = MaterialTheme.shapes.medium,
                                modifier = Modifier.padding(start = 8.dp)
                            ) {
                                Text(
                                    text = category,
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier.padding(
                                        horizontal = 12.dp,
                                        vertical = 8.dp
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Affichage des 5 événements
            items(events, key = { it.id }) { event ->
                EventCard(
                    event = event,
                    onClick = { onEventClick(event) },
                    onBook = { onBook(event) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }

            item {
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

// Écran de détail (pas encore de navigation)
@Composable
fun EventDetailScreen(
    event: Event,
    onBook: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.event_banner),
            contentDescription = stringResource(R.string.event_image_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        )

        Text(
            text = event.title,
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = event.category,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelLarge
        )

        Text(
            text = event.location ?: stringResource(R.string.location_unknown)
        )

        Text(
            text = stringResource(R.string.description),
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = event.description,
            style = MaterialTheme.typography.bodyLarge
        )

        if (event.capacity == 0) {
            Text(
                text = stringResource(R.string.full),
                color = MaterialTheme.colorScheme.error
            )
        } else {
            Text(
                text = pluralStringResource(
                    R.plurals.available_seats,
                    event.capacity,
                    event.capacity
                )
            )
        }

        Button(
            onClick = onBook,
            enabled = event.capacity > 0,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.book))
        }
    }
}

// Preview d'un événement complet
@Preview(name = "Carte complete", showBackground = true)
@Composable
private fun CompleteCardPreview() {
    CampusTheme(dynamicColor = false) {
        EventCard(
            event = demoEvents(LocalContext.current)[2],
            onClick = {},
            onBook = {},
            modifier = Modifier.fillMaxWidth().padding(12.dp)
        )
    }
}

// Preview liste claire
@Preview(name = "Liste - clair", showBackground = true)
@Composable
private fun ListLightPreview() {
    CampusTheme(dynamicColor = false) {
        EventListScreen(
            demoEvents(LocalContext.current),
            {},
            {}
        )
    }
}

// Preview liste sombre
@Preview(
    name = "Liste - sombre",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun ListDarkPreview() {
    CampusTheme(dynamicColor = false) {
        EventListScreen(
            demoEvents(LocalContext.current),
            {},
            {}
        )
    }
}

// Preview détail clair
@Preview(name = "Detail - clair", showBackground = true)
@Composable
private fun DetailLightPreview() {
    CampusTheme(dynamicColor = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            EventDetailScreen(demoEvents(LocalContext.current)[0])
        }
    }
}

// Preview détail sombre
@Preview(
    name = "Detail - sombre",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DetailDarkPreview() {
    CampusTheme(dynamicColor = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            EventDetailScreen(demoEvents(LocalContext.current)[0])
        }
    }
}
