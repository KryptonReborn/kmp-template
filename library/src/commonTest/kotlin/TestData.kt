import com.goncalossilva.resources.Resource
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
class TestDataJson

fun loadTestDataJson(): TestDataJson {
    val json = Json {
        ignoreUnknownKeys = true
    }

    return json.decodeFromString(Resource("src/commonTest/resources/test_data.json").readText())
}
