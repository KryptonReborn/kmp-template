import kotlin.test.Test
import kotlin.test.assertEquals

class FibiTest {
    private val testData = loadTestDataJson()

    @Test
    fun test() {
        assertEquals(firstElement + secondElement, fibi.take(3).last())
    }
}
