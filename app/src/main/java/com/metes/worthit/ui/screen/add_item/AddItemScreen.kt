package com.metes.worthit.ui.screen.add_item

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.metes.worthit.R
import com.metes.worthit.ui.component.LoadingScreen
import com.metes.worthit.ui.component.WorthItTextField
import com.metes.worthit.ui.entity.Currency
import com.metes.worthit.ui.entity.CustomSnackbarVisuals
import com.metes.worthit.ui.screen.add_item.component.currency.CurrenciesDialog
import com.metes.worthit.ui.screen.add_item.component.date.DateField
import com.metes.worthit.ui.screen.add_item.component.date.PastOrPresentDatePickerDialog
import com.metes.worthit.ui.screen.add_item.component.other.ItemImage
import com.metes.worthit.ui.screen.add_item.component.other.PriceField
import com.metes.worthit.ui.screen.add_item.component.other.WorthItSnackbar
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

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

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                AddItemEvent.NavigateToItems -> onNavigateToItems()

                is AddItemEvent.ShowSnackbar -> {
                    launch {
                        snackbarHostState
                            .showSnackbar(
                                CustomSnackbarVisuals(
                                    message = event.message.asString(context),
                                    isError = event.isError,
                                )
                            )
                    }
                }
            }
        }
    }

    LaunchedEffect(imageUri) {
        if (imageUri != null) {
            viewModel.processCommand(AddItemCommand.SelectImage(imageUri))
        }
    }

    AddItemScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        modifier = modifier,
        onAddItemClick = { viewModel.processCommand(AddItemCommand.AddItem) },
        onNameChange = { viewModel.processCommand(AddItemCommand.ChangeName(it)) },
        onPriceChange = { viewModel.processCommand(AddItemCommand.ChangePrice(it)) },
        onDescriptionChange = { viewModel.processCommand(AddItemCommand.ChangeDescription(it)) },
        onBackClick = onBackClick,
        onImageClick = {
            photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        },
        onCurrencyChange = { viewModel.processCommand(AddItemCommand.ChangeCurrency(it)) },
        onRemoveImageClick = { viewModel.processCommand(AddItemCommand.RemoveImage) },
        onRemoveNameClick = { viewModel.processCommand(AddItemCommand.RemoveName) },
        onRemoveDescriptionClick = { viewModel.processCommand(AddItemCommand.RemoveDescription) },
        onSelectBoughtDate = { viewModel.processCommand(AddItemCommand.SelectBoughtDate(it)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(
    uiState: AddItemUiState,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onAddItemClick: () -> Unit,
    onNameChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onImageClick: () -> Unit,
    onRemoveImageClick: () -> Unit,
    onRemoveNameClick: () -> Unit,
    onRemoveDescriptionClick: () -> Unit,
    onCurrencyChange: (Currency) -> Unit,
    onSelectBoughtDate: (Long) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.new_item_title)) },
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
            if (uiState is AddItemUiState.Success) {
                FloatingActionButton(onClick = onAddItemClick) {
                    Icon(
                        painter = painterResource(R.drawable.add_24dp),
                        contentDescription = stringResource(R.string.add_item_desc)
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                val visuals = snackbarData.visuals as? CustomSnackbarVisuals
                val isError = visuals?.isError ?: return@SnackbarHost

                WorthItSnackbar(
                    snackbarData = snackbarData,
                    isError = isError
                )
            }
        }
    ) { innerPadding ->
        when (uiState) {
            AddItemUiState.Loading -> {
                LoadingScreen(modifier = Modifier.padding(innerPadding))
            }

            is AddItemUiState.Success -> {
                val showDatePicker = rememberSaveable { mutableStateOf(false) }
                val showCurrencies = rememberSaveable { mutableStateOf(false) }

                CurrenciesDialog(
                    show = showCurrencies.value,
                    onDismissRequest = { showCurrencies.value = false },
                    onCurrencyClick = {
                        showCurrencies.value = false
                        onCurrencyChange(it)
                    }
                )

                PastOrPresentDatePickerDialog(
                    show = showDatePicker.value,
                    currentDate = uiState.currentDate,
                    selectedDateMillis = uiState.boughtDateMillis,
                    onDismissRequest = { showDatePicker.value = false },
                    onButtonClick = { selectedDateMillis ->
                        selectedDateMillis?.let {
                            onSelectBoughtDate(selectedDateMillis)
                        }
                        showDatePicker.value = false
                    }
                )

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
                        model = uiState.imageUri,
                        defaultImage = R.drawable.image_search_24dp,
                        contentDescription = stringResource(R.string.select_image_desc),
                        onRemoveClick = onRemoveImageClick
                    )

                    WorthItTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.name,
                        keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
                        onValueChange = onNameChange,
                        isError = uiState.nameError != null,
                        errorMessage = uiState.nameError?.asString(),
                        label = { Text(text = stringResource(R.string.name_hint)) },
                        trailingIcon = {
                            if (uiState.name.isNotEmpty()) {
                                IconButton(
                                    onClick = onRemoveNameClick
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.close_24dp),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    )

                    WorthItTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.description,
                        keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
                        maxLines = Int.MAX_VALUE,
                        singleLine = false,
                        onValueChange = onDescriptionChange,
                        label = { Text(text = stringResource(R.string.description_hint)) },
                        trailingIcon = {
                            if (uiState.description.isNotEmpty()) {
                                IconButton(
                                    onClick = onRemoveDescriptionClick
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.close_24dp),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        DateField(
                            modifier = Modifier.weight(1f),
                            date = uiState.formattedDate,
                            onIconClick = { showDatePicker.value = true },
                        )

                        PriceField(
                            modifier = Modifier.weight(1f),
                            currency = uiState.currency,
                            price = uiState.price,
                            onIconClick = { showCurrencies.value = true },
                            onPriceChange = onPriceChange
                        )
                    }
                }
            }
        }
    }
}