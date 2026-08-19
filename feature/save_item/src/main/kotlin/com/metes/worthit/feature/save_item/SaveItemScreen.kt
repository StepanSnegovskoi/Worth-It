@file:OptIn(ExperimentalLayoutApi::class)

package com.metes.worthit.feature.save_item

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.metes.worthit.core.designsystem.component.other.ItemImage
import com.metes.worthit.core.designsystem.component.other.LoadingScreen
import com.metes.worthit.core.designsystem.component.other.WorthItAnimatedVisibility
import com.metes.worthit.core.designsystem.component.other.WorthItIcon
import com.metes.worthit.core.designsystem.component.other.WorthItIconButton
import com.metes.worthit.core.designsystem.component.other.WorthItText
import com.metes.worthit.core.designsystem.component.snackbar.CustomSnackbarVisuals
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.designsystem.util.rememberDateFormatter
import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.ui.ObserveAsEvents
import com.metes.worthit.core.ui.asCombinedString
import com.metes.worthit.feature.save_item.component.currency.CurrenciesDialog
import com.metes.worthit.feature.save_item.component.date.DateField
import com.metes.worthit.feature.save_item.component.date.PastOrPresentDatePickerDialog
import com.metes.worthit.feature.save_item.component.other.DescriptionTextField
import com.metes.worthit.feature.save_item.component.other.NameTextField
import com.metes.worthit.feature.save_item.component.other.PriceField
import com.metes.worthit.feature.save_item.component.other.SaveItemFloatingActionButton
import com.metes.worthit.feature.save_item.component.other.TitleText
import com.metes.worthit.feature.save_item.component.other.WorthItSnackbar
import kotlinx.coroutines.launch
import java.time.Instant
import com.metes.worthit.core.designsystem.R as DesignR

private val FabHeight = 56.dp
private val FabSpaceAround = 16.dp
private val ScrollableFabClearance = FabHeight + FabSpaceAround

@Composable
fun SaveItemRoute(
    modifier: Modifier = Modifier,
    viewModel: SaveItemViewModel,
    onNavigateToItems: () -> Unit,
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val nameFocusRequester = remember { FocusRequester() }
    val priceFocusRequester = remember { FocusRequester() }

    val photoPicker =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                viewModel.processCommand(SaveItemCommand.SelectImage(uri))
            }
        }

    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            SaveItemEvent.NavigateToItems -> onNavigateToItems()

            is SaveItemEvent.ShowErrors -> {
                val currentState = uiState

                if (currentState is SaveItemUiState.Success) {
                    if (!currentState.isValidName) {
                        nameFocusRequester.requestFocus()
                    } else if (!currentState.isValidPrice) {
                        priceFocusRequester.requestFocus()
                    }
                }

                snackbarHostState.currentSnackbarData?.dismiss()
                launch {
                    snackbarHostState
                        .showSnackbar(
                            CustomSnackbarVisuals(
                                message = event.errors.asCombinedString(context = context),
                                isError = true
                            )
                        )
                }
            }
        }
    }

    when (val currentState = uiState) {
        SaveItemUiState.Loading -> LoadingScreen()

        is SaveItemUiState.Success -> SaveItemScreen(
            uiState = currentState,
            snackbarHostState = snackbarHostState,
            nameFocusRequester = nameFocusRequester,
            priceFocusRequester = priceFocusRequester,
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
    snackbarHostState: SnackbarHostState,
    nameFocusRequester: FocusRequester,
    priceFocusRequester: FocusRequester,
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
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val dateFormatter = rememberDateFormatter()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val formattedDate = remember(uiState.dateOfPurchaseMillis) {
        dateFormatter.format(Instant.ofEpochMilli(uiState.dateOfPurchaseMillis))
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

    Box(
        modifier = modifier
            .fillMaxSize()
            // experimental
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()
            }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = AppTheme.colorScheme.background,
            floatingActionButton = {
                WorthItAnimatedVisibility(visible = !isImeVisible) {
                    SaveItemFloatingActionButton(isEditingMode = uiState.isEditingMode) {
                        keyboardController?.hide()
                        onAddItemClick()
                    }
                }
            },
            topBar = {
                TopAppBar(
                    title = {
                        TitleText(isEditingMode = uiState.isEditingMode)
                    },
                    colors = TopAppBarColors(
                        containerColor = AppTheme.colorScheme.background,
                        scrolledContainerColor = AppTheme.colorScheme.background,
                        navigationIconContentColor = AppTheme.colorScheme.primary,
                        titleContentColor = AppTheme.colorScheme.onBackground,
                        actionIconContentColor = AppTheme.colorScheme.primary,
                        subtitleContentColor = AppTheme.colorScheme.primary
                    ),
                    scrollBehavior = scrollBehavior,
                    navigationIcon = {
                        WorthItIconButton(onClick = {
                            keyboardController?.hide()
                            onBackClick()
                        }) {
                            WorthItIcon(
                                drawableRes = DesignR.drawable.back_24dp,
                                contentDescriptionRes = R.string.cd_back
                            )
                        }
                    },
                    actions = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            WorthItAnimatedVisibility(visible = uiState.imageUri != null) {
                                WorthItIconButton(onClick = onRemoveImageClick) {
                                    WorthItIcon(
                                        drawableRes = R.drawable.remove_image_36dp,
                                        contentDescriptionRes = R.string.cd_remove_image,
                                    )
                                }
                            }

                            WorthItAnimatedVisibility(visible = isImeVisible) {
                                SaveItemFloatingActionButton(isEditingMode = uiState.isEditingMode) {
                                    keyboardController?.hide()
                                    onAddItemClick()
                                }
                            }
                        }
                    }
                )
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = ScrollableFabClearance)
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
            ) {
                ItemImage(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 8.dp)
                        .size(240.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable(
                            onClick = onImageClick,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple()
                        ),
                    model = uiState.imageUri,
                    contentScale = ContentScale.Fit,
                    defaultImageDrawableRes = R.drawable.image_search_24dp,
                    contentDescription = stringResource(R.string.select_image_desc),
                )

                NameTextField(
                    name = uiState.name,
                    isError = !uiState.isValidName,
                    modifier = Modifier.focusRequester(nameFocusRequester),
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
                        date = formattedDate,
                        modifier = Modifier.weight(1f),
                        onIconClick = { showDatePicker.value = true },
                    )

                    PriceField(
                        price = uiState.price,
                        currency = uiState.currency,
                        modifier = Modifier
                            .weight(1f)
                            .focusRequester(priceFocusRequester),
                        onIconClick = { showCurrencies.value = true },
                        onPriceChange = onPriceChange
                    )
                }
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = (FabHeight - FabSpaceAround) / 2)
                    .padding(end = FabHeight + 32.dp, start = 32.dp)
            ) { data ->
                val visuals = data.visuals
                val customVisuals = (visuals as? CustomSnackbarVisuals) ?: return@SnackbarHost

                WorthItSnackbar(
                    snackbarData = data,
                    isError = customVisuals.isError,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}
