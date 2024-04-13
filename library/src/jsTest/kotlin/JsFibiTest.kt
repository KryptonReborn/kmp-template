import kotlin.test.Test
import kotlin.test.assertEquals

class JsFibiTest {

    @Test
    fun test() {
        assertEquals( 11, fibi.take(3).last())
    }
}
