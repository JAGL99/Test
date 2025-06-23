package com.jagl.pickleapp.features.episode_detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jagl.pickleapp.core.utils.ui.components.CharacterImage
import com.jagl.pickleapp.core.utils.ui.components.RoundedContainer
import com.jagl.pickleapp.domain.model.CharacterDomain
import com.jagl.pickleapp.ui.theme.PickleAppTheme

@Composable
fun CharacterItem(
    modifier: Modifier = Modifier,
    item: CharacterDomain,
    onClick: (id: Long) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable(onClick = { onClick(item.id) }),
        shape = RoundedCornerShape(corner = CornerSize(12.dp))
    ) {
        RoundedContainer(
            modifier = Modifier.fillMaxSize()
        ) {
            CharacterImage(item.image)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCharacterItem() {
    PickleAppTheme {
        CharacterItem(
            item = CharacterDomain(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                image = "",
                origin = "Earth (C-137)",
                location = "Citadel of Ricks",
                episodes = emptyList()
            ),
            onClick = {}
        )
    }
}