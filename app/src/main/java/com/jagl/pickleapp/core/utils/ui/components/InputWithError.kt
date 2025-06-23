package com.jagl.pickleapp.core.utils.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InputWithError(
    value: String,
    placeholderText: String,
    errorMessage: String?,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = value,
            isError = isError,
            onValueChange = onValueChange,
            placeholder = { Text(placeholderText) },
        )
        errorMessage?.let { error -> ErrorLabel(errorMessage = error) }
    }

}

@Preview(showBackground = true)
@Composable
private fun InputWithErrorPreview() {
    var text by remember { mutableStateOf("") }
    InputWithError(
        value = text,
        placeholderText = "Enter text here",
        errorMessage = "This is an error message",
        onValueChange = { text = it }
    )
}