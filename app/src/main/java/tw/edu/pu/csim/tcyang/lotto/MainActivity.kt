package tw.edu.pu.csim.tcyang.lotto

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import tw.edu.pu.csim.tcyang.lotto.ui.theme.LottoTheme

import androidx.compose.runtime.setValue // 引入 setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LottoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Play(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Play(modifier: Modifier = Modifier) {
    //var lucky = (1..100).random()
    var lucky by remember {
        mutableStateOf((1..100).random())
    }

    var touchX by remember { mutableStateOf(0f) }
    var touchY by remember { mutableStateOf(0f) }
    var count by remember { mutableStateOf(50) }

    val context = LocalContext.current // 取得當前 Context

    Column (modifier = modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = { offset: Offset ->
                    touchX = offset.x
                    touchY = offset.y
                    Toast.makeText(context, "螢幕觸控: x= ${touchX.toInt()}, y= ${touchY.toInt()}", Toast.LENGTH_SHORT).show()
                }
            )
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "樂透數字(1-100)為 $lucky"
        )

        Button(
            onClick = { lucky = (1..100).random() }
        ) {
            Text("重新產生樂透碼")
        }
        Text(
            text = "x 軸座標:${touchX.toInt()}, y 軸座標:${touchY.toInt()}"
        )
        Text(
            text = "計數器:$count",
            modifier = Modifier
                .padding(top = 16.dp) // 增加一些間距，讓排版更清楚
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            count-- // 短按時，計數器減 1
                            Toast.makeText(context, "短按: 計數器 -1", Toast.LENGTH_SHORT).show()
                        },
                        onLongPress = {
                            count++ // 長按時，計數器加 1
                            Toast.makeText(context, "長按: 計數器 +1", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
        )
    }



}
