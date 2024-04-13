import kotlin.test.Test
import kotlin.test.assertEquals

class AndroidFibiTest {

    @Test
    fun test() {
        assertEquals(3, fibi.take(3).last())
    }
}
