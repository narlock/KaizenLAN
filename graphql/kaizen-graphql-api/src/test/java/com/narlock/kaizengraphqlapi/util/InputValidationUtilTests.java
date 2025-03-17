package com.narlock.kaizengraphqlapi.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class InputValidationUtilTests {

  @Test
  void testIsISOLocalDate() {
    assertTrue(InputValidationUtil.isISOLocalDate("2024-01-02"));
  }

  @Test
  public void testInvalidISOLocalDate_WrongFormat() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              InputValidationUtil.isISOLocalDate("07-05-2024");
            });
    assertEquals("Invalid date format. Expected YYYY-MM-DD.", exception.getMessage());
  }

  @Test
  public void testInvalidISOLocalDate_NonDateChars() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              InputValidationUtil.isISOLocalDate("abcd-ef-gh");
            });
    assertEquals("Invalid date format. Expected YYYY-MM-DD.", exception.getMessage());
  }
}
