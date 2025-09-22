package tw.edu.pu.csim.tcyang.lotto

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.detectDragGestures
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

import androidx.compose.runtime.setValue
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
    // 樂透數字的狀態
    var lucky by remember {
        mutableStateOf((1..100).random())
    }

    // 儲存觸控座標的狀態
    var touchX by remember { mutableStateOf(0f) }
    var touchY by remember { mutableStateOf(0f) }

    // 儲存計數器數值的狀態
    var count by remember { mutableStateOf(50) }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            // 使用 pointerInput 偵測整個畫面的拖曳手勢，即時更新座標
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, _ ->
                        touchX = change.position.x
                        touchY = change.position.y
                    },
                    onDragStart = { offset ->
                        // 拖曳開始時的訊息
                        Toast.makeText(context, "拖曳開始: x=${offset.x.toInt()}, y=${offset.y.toInt()}", Toast.LENGTH_SHORT).show()
                    },
                    onDragEnd = {
                        // 拖曳結束時的訊息
                        Toast.makeText(context, "拖曳結束", Toast.LENGTH_SHORT).show()
                    }
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "樂透數字(1-100)為 $lucky"
        )

        Button(
            onClick = { lucky = (1..100).random() }
        ) {
            Text("重新產生樂透碼")
        }
        Text("與Brian共同編輯程式")

        // 顯示即時更新的座標
        Text(
            text = "目前座標: x=${touchX.toInt()}, y=${touchY.toInt()}"
        )

        // 帶有短按和長按功能的計數器
        Text(
            text = "計數器: $count",
            modifier = Modifier
                .padding(top = 16.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            count-- // 短按，數值減 1
                            Toast.makeText(context, "短按: 計數器 -1", Toast.LENGTH_SHORT).show()
                        },
                        onLongPress = {
                            count++ // 長按，數值加 1
                            Toast.makeText(context, "長按: 計數器 +1", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
        )
    }
}