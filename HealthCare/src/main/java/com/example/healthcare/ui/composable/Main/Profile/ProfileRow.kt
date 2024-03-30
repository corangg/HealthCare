package com.example.healthcare.ui.composable.Main.Profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthcare.R

@Composable
fun profileRow(
    itemImg : Int,
    itemText : String,
    itemDetail : String,
    unit : String,
    editClicked : ()-> Unit) {
    Row(modifier = Modifier.padding(top = 20.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = itemImg),
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .border(border = BorderStroke(3.dp, Color.White), shape = CircleShape)
                .padding(8.dp),
            colorFilter = ColorFilter.tint(Color.White))

        Text(
            text = itemText,
            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(start = 16.dp)
                .width(64.dp))

        Text(
            text = itemDetail,
            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(start = 10.dp)
                .width(80.dp),
            textAlign = TextAlign.End)

        Text(
            text = unit,
            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(start = 6.dp)
                .width(32.dp),
            textAlign = TextAlign.End)

        Image(
            painter = painterResource(id = R.drawable.ic_right),
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .size(32.dp)
                .padding(start = 18.dp)
                .clickable(onClick = editClicked)) }
}
