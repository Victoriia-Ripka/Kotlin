import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pw_6.data.EPInput
import com.example.pw_6.ui.components.EPInputFields
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

@Composable
fun EPInputsScreen() {
    val epInputs = remember { mutableStateListOf<EPInput>() }

    LaunchedEffect(Unit) {
        repeat(8) { epInputs.add(EPInput()) }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        epInputs.forEachIndexed { index, epInput ->
            Text(
                text = "ЕП #${index + 1}",
                style = MaterialTheme.typography.displayLarge
            )
            EPInputFields(
                epInput = epInput,
                onUpdate = { updatedInput ->
                    epInputs[index] = updatedInput
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            val json = loadJsonFromFile("path/to/your/json/file.json")
            val parsedInputs = parseJsonToEPInputs(json)

            epInputs.clear()
            epInputs.addAll(parsedInputs)
        }) {
            Text("Заповнити поля")
        }
    }
}

// Function to load JSON from a file
fun loadJsonFromFile(filePath: String): String {
    return File(filePath).readText()
}

// Function to parse JSON string into a list of EPInput objects
fun parseJsonToEPInputs(json: String): List<EPInput> {
    return try {
        Json.decodeFromString<List<EPInput>>(json)
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }
}
