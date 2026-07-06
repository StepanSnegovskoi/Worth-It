package com.metes.worthit.ui.screen.main.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metes.worthit.domain.entity.Item

@Composable
fun Item(
    item: Item,
    modifier: Modifier = Modifier
) {
    Text(text = item.name, modifier = modifier)
}