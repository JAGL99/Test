package com.jagl.pickleapp.features.character_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jagl.pickleapp.R
import com.jagl.pickleapp.core.utils.ui.components.CharacterImage
import com.jagl.pickleapp.core.utils.ui.components.RoundedContainer
import com.jagl.pickleapp.domain.model.CharacterDomain
import com.jagl.pickleapp.ui.theme.PickleAppTheme

@Composable
fun CharacterDetail(
    modifier: Modifier = Modifier,
    item: CharacterDomain
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp),
        shape = RoundedCornerShape(corner = CornerSize(12.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            RoundedContainer(
                modifier = Modifier.fillMaxWidth()
            ) {
                CharacterImage(item.image)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = item.name,
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "${item.status} - ${item.species}",
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.first_seen_in),
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = item.origin,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    fontSize = 24.sp,
                    text = stringResource(R.string.last_known_location),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = item.location,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCharacterItem() {
    PickleAppTheme {
        CharacterDetail(
            item = CharacterDomain(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                image = "",
                origin = "Earth (C-137)",
                location = "Citadel of Ricks",
                episodes = (1..10).map { "Episode $it" }
            )
        )
    }
}