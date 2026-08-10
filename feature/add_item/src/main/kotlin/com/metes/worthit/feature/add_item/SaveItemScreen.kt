@file:OptIn(ExperimentalLayoutApi::class)

package com.metes.worthit.feature.add_item

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.metes.worthit.core.designsystem.component.other.ItemImage
import com.metes.worthit.core.designsystem.component.other.LoadingScreen
import com.metes.worthit.core.designsystem.component.snackbar.CustomSnackbarVisuals
import com.metes.worthit.core.designsystem.util.rememberDateFormatter
import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.ui.toUiText
import com.metes.worthit.feature.add_item.component.currency.CurrenciesDialog
import com.metes.worthit.feature.add_item.component.date.DateField
import com.metes.worthit.feature.add_item.component.date.PastOrPresentDatePickerDialog
import com.metes.worthit.feature.add_item.component.other.DescriptionTextField
import com.metes.worthit.feature.add_item.component.other.NameTextField
import com.metes.worthit.feature.add_item.component.other.PriceField
import com.metes.worthit.feature.add_item.component.other.WorthItSnackbar
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import com.metes.worthit.core.designsystem.R as DesignR

private val FAB_HEIGHT_DP = 56.dp

@Composable
fun SaveItemRoute(
    modifier: Modifier = Modifier,
    viewModel: SaveItemViewModel = hiltViewModel(),
    onNavigateToItems: () -> Unit,
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val photoPicker =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                viewModel.processCommand(SaveItemCommand.SelectImage(uri))
            }
        }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                SaveItemEvent.NavigateToItems -> onNavigateToItems()

                is SaveItemEvent.ShowErrors -> {
                    launch {
                        snackbarHostState
                            .showSnackbar(
                                CustomSnackbarVisuals(
                                    message = event.errors.joinToString(separator = "\n") { error ->
                                        error.toUiText().asString(context)
                                    },
                                    isError = true
                                )
                            )
                    }
                }
            }
        }
    }

    when (val currentState = uiState) {
        SaveItemUiState.Loading -> LoadingScreen()

        is SaveItemUiState.Success -> SaveItemScreen(
            uiState = currentState,
            modifier = modifier,
            onAddItemClick = { viewModel.processCommand(SaveItemCommand.SaveItem) },
            onNameChange = { viewModel.processCommand(SaveItemCommand.ChangeName(it)) },
            onPriceChange = { viewModel.processCommand(SaveItemCommand.ChangePrice(it)) },
            onDescriptionChange = { viewModel.processCommand(SaveItemCommand.ChangeDescription(it)) },
            onBackClick = onBackClick,
            onImageClick = {
                photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
            onCurrencyChange = { viewModel.processCommand(SaveItemCommand.ChangeCurrency(it)) },
            onRemoveImageClick = { viewModel.processCommand(SaveItemCommand.RemoveImage) },
            onRemoveNameClick = { viewModel.processCommand(SaveItemCommand.RemoveName) },
            onRemoveDescriptionClick = { viewModel.processCommand(SaveItemCommand.RemoveDescription) },
            onSelectBoughtDate = { viewModel.processCommand(SaveItemCommand.SelectBoughtDate(it)) },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveItemScreen(
    uiState: SaveItemUiState.Success,
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
    val dateFormatter = rememberDateFormatter()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val formattedDate = remember(uiState.dateOfPurchaseMillis) {
        uiState.dateOfPurchaseMillis.let {
            dateFormatter.format(Instant.ofEpochMilli(it))
        } ?: ""
    }

    val showDatePicker = rememberSaveable { mutableStateOf(false) }
    val showCurrencies = rememberSaveable { mutableStateOf(false) }
    val isImeVisible = WindowInsets.isImeVisible

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
        selectedDateMillis = uiState.dateOfPurchaseMillis,
        onDismissRequest = { showDatePicker.value = false },
        onButtonClick = { selectedDateMillis ->
            selectedDateMillis?.let { onSelectBoughtDate(it) }
            showDatePicker.value = false
        }
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        TopAppBar(
            title = {
                Text(text = stringResource(if (uiState.isEditingMode) R.string.editing_item else R.string.adding_item))
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(DesignR.drawable.back_24dp),
                        contentDescription = stringResource(R.string.back_desc)
                    )
                }
            },
            scrollBehavior = scrollBehavior,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
                    .padding(bottom = if (isImeVisible) 16.dp else FAB_HEIGHT_DP + 16.dp),
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

                NameTextField(
                    name = uiState.name,
                    isError = !uiState.isValidName,
                    onRemoveNameClick = onRemoveNameClick,
                    onNameChange = onNameChange
                )

                DescriptionTextField(
                    description = uiState.description,
                    onRemoveDescriptionClick = onRemoveDescriptionClick,
                    onDescriptionChange = onDescriptionChange
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DateField(
                        modifier = Modifier.weight(1f),
                        date = formattedDate,
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

            androidx.compose.animation.AnimatedVisibility(
                visible = !isImeVisible,
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut(),
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(bottom = 16.dp, end = 16.dp),
                    onClick = onAddItemClick,
                ) {
                    Icon(
                        painter = painterResource(DesignR.drawable.add_24dp),
                        contentDescription = stringResource(R.string.add_item_desc)
                    )
                }
            }
        }
    }
}
