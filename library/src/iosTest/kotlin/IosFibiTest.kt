import kotlin.test.Test
import kotlin.test.assertEquals

class IosFibiTest {

    @Test
    fun test() {
        assertEquals(6, fibi.take(3).last())
    }
}
