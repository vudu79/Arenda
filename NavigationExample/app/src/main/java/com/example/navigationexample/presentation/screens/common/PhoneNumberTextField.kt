package com.example.navigationexample.presentation.screens.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp


//@Composable
//fun LoginScreen() {
//    var phoneNumber by rememberSaveable { mutableStateOf("") }
//    Column {
//        PhoneField(phoneNumber,
//            mask = "000 000 00 00",
//            maskNumber = '0',
//            onPhoneChanged = { phoneNumber = it })
//        Spacer(modifier = Modifier.padding(8.dp))
//
//        PhoneField(phoneNumber,
//            mask = "(000) 000 00 00",
//            maskNumber = '0',
//            onPhoneChanged = { phoneNumber = it })
//        Spacer(modifier = Modifier.padding(8.dp))
//
//        PhoneField(phoneNumber,
//            mask = "+7-000-000-00-00",
//            maskNumber = '0',
//            onPhoneChanged = { phoneNumber = it })
//    }
//}

@Composable
fun PhoneField(
    value: String,
    placeHolder: String,
    modifier: Modifier = Modifier,
    mask: String = "000 000 00 00",
    maskNumber: Char = '0',
    onPhoneChanged: (String) -> Unit,
    errorMessage: String?
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = value,
        onValueChange = { it ->
            onPhoneChanged(it.take(mask.count { it == maskNumber }))
        },
        placeholder = { Text(text = placeHolder, color = Color.Black) },
        isError = errorMessage != null,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding( bottom = 5.dp, start = 5.dp, end = 5.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Black,
            textColor = Color.Black,
            backgroundColor = Color(142, 143, 138)
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next, keyboardType = KeyboardType.Phone
        ),
        keyboardActions = KeyboardActions(onNext = {
            focusManager.moveFocus(FocusDirection.Down)
        }),

        visualTransformation = PhoneVisualTransformation(mask, maskNumber),
    )

    if (errorMessage != null) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colors.error,
            modifier = modifier
        )
    }
}

class PhoneVisualTransformation(val mask: String, val maskNumber: Char) : VisualTransformation {

    private val maxLength = mask.count { it == maskNumber }

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.length > maxLength) text.take(maxLength) else text

        val annotatedString = buildAnnotatedString {
            if (trimmed.isEmpty()) return@buildAnnotatedString

            var maskIndex = 0
            var textIndex = 0
            while (textIndex < trimmed.length && maskIndex < mask.length) {
                if (mask[maskIndex] != maskNumber) {
                    val nextDigitIndex = mask.indexOf(maskNumber, maskIndex)
                    append(mask.substring(maskIndex, nextDigitIndex))
                    maskIndex = nextDigitIndex
                }
                append(trimmed[textIndex++])
                maskIndex++
            }
        }

        return TransformedText(annotatedString, PhoneOffsetMapper(mask, maskNumber))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
//        if (other !is PhonedVisualTransformation) return false
//        if (mask != other.mask) return false
//        if (maskNumber != other.maskNumber) return false
        return true
    }

    override fun hashCode(): Int {
        return mask.hashCode()
    }
}

private class PhoneOffsetMapper(val mask: String, val numberChar: Char) : OffsetMapping {

    override fun originalToTransformed(offset: Int): Int {
        var noneDigitCount = 0
        var i = 0
        while (i < offset + noneDigitCount) {
            if (mask[i++] != numberChar) noneDigitCount++
        }
        return offset + noneDigitCount
    }

    override fun transformedToOriginal(offset: Int): Int =
        offset - mask.take(offset).count { it != numberChar }
}
