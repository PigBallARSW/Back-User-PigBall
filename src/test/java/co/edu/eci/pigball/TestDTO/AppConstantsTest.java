package co.edu.eci.pigball.TestDTO;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import co.edu.eci.pigball.user.utils.AppConstants;

class AppConstantsTest {

    @Test
    void testSimpleConstantsCheck() {
        // Verificación básica de valores
        assertAll(
                () -> assertEquals("0", AppConstants.PAGE_NUMBER_BY_DEFAULT),
                () -> assertEquals("10", AppConstants.SIZE_PAGE_BY_DEFAULT),
                () -> assertEquals("id", AppConstants.SORT_BY_DEFAULT),
                () -> assertEquals("asc", AppConstants.SORT_DIRECTION_BY_DEFAULT));
    }
}
