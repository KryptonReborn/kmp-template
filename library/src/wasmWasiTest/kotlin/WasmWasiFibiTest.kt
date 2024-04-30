import kotlin.test.Test
import kotlin.test.assertEquals

class WasmWasiFibiTest {

    @Test
    fun test() {
        assertEquals(6, fibi.take(3).last())
    }
}
