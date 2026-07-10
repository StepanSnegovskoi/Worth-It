package com.metes.worthit.ui.screen.add_item

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.metes.worthit.R
import com.metes.worthit.ui.screen.add_item.component.ItemImage

@Composable
fun AddItemRoute(
    imageUri: Uri?,
    modifier: Modifier = Modifier,
    viewModel: AddItemViewModel = hiltViewModel(),
    onNavigateToItems: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val photoPicker =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                viewModel.processCommand(AddItemCommand.SelectImage(uri))
            }
        }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                AddItemEvent.NavigateToItems -> onNavigateToItems()
                is AddItemEvent.ShowToast -> Toast.makeText(
                    context,
                    event.message.asString(context),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    LaunchedEffect(imageUri) {
        if (imageUri != null) {
            viewModel.processCommand(AddItemCommand.SelectImage(imageUri))
        }
    }

    AddItemScreen(
        name = uiState.name,
        description = uiState.description,
        modifier = modifier,
        imageUri = uiState.imageUri,
        onAddItemClick = { viewModel.processCommand(AddItemCommand.AddItem) },
        onNameChange = { viewModel.processCommand(AddItemCommand.ChangeName(it)) },
        onDescriptionChange = { viewModel.processCommand(AddItemCommand.ChangeDescription(it)) },
        onBackClick = onBackClick,
        onImageClick = {
            photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(
    name: String,
    description: String,
    modifier: Modifier = Modifier,
    imageUri: Uri?,
    onAddItemClick: () -> Unit,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onImageClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier
            .imePadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.new_item_title))
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.back_24dp),
                            contentDescription = stringResource(R.string.back_desc)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddItemClick

            ) {
                Icon(
                    painter = painterResource(R.drawable.add_24dp),
                    contentDescription = stringResource(R.string.add_item_desc)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ItemImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(onClick = onImageClick)
                    .size(240.dp)
                    .align(Alignment.CenterHorizontally),
                model = imageUri,
                defaultImage = R.drawable.image_search_24dp,
                contentDescription = stringResource(R.string.select_image_desc)
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = onNameChange,
                label = {
                    Text(text = stringResource(R.string.name_hint))
                }
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = description,
                onValueChange = onDescriptionChange,
                label = {
                    Text(text = stringResource(R.string.description_hint))
                }
            )
        }
    }
}