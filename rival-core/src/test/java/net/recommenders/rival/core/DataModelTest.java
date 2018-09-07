package net.recommenders.rival.core;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for {@link net.recommenders.rival.core.DataModel}.
 *
 * @author <a href="http://github.com/alansaid">Alan</a>
 */
@RunWith(value = JUnit4.class) public class DataModelTest<U extends java.lang.Object, I extends java.lang.Object> {
  /**
     * The data model.
     */
  private DataModel<Long, Long> dm = new DataModel<>();

  /**
     * The number of users in the data model.
     */
  private static final int USERS = 3;

  /**
     * The number of items in the data model.
     */
  private static final int ITEMS = 3;

  @Before public void initialize() {
    for (long u = 1L; u <= USERS; u++) {
      for (long i = 1L; i <= ITEMS; i++) {
        dm.addPreference(u, i, 1.0 * u * i);
      }
    }
  }

  @Test public void testGetUserPreferences() {
    for (long u = 1L; u <= USERS; u++) {
      for (long i = 1L; i <= ITEMS; i++) {
        assertEquals(1.0 * u * i, dm.getUserItemPreference(u, i), 0.0);
      }
    }
  }

  @Test public void testGetNumItems() {
    assertEquals(ITEMS, dm.getNumItems());
  }

  @Test public void testGetNumUsers() {
    assertEquals(USERS, dm.getNumUsers());
  }

  @Test public void testGetItems() {
    assertEquals(ITEMS, dm.getItems().size());
  }

  @Test public void testClearItems() {
    dm.clear();
    assertEquals(0, dm.getNumItems());
  }

  @Test public void testDuplicatePreferences() {
    DataModel<Long, Long> unconstrainedModel = new DataModel<>();
    for (long u = 1L; u <= USERS; u++) {
      for (long i = 1L; i <= ITEMS; i++) {
        unconstrainedModel.addPreference(u, i, 1.0 * u * i);
      }
      for (long i = 1L; i <= ITEMS; i++) {
        unconstrainedModel.addPreference(u, i, 1.0 * u * i);
      }
    }
    for (long u = 1L; u <= USERS; u++) {
      for (long i = 1L; i <= ITEMS; i++) {
        assertEquals(2.0 * u * i, unconstrainedModel.getUserItemPreference(u, i), 0.0);
      }
    }
    DataModel<Long, Long> constrainedModel = new DataModel<>(true);
    for (long u = 1L; u <= USERS; u++) {
      for (long i = 1L; i <= ITEMS; i++) {
        constrainedModel.addPreference(u, i, 1.0 * u * i);
      }
      for (long i = 1L; i <= ITEMS; i++) {
        constrainedModel.addPreference(u, i, 1.0 * u * i);
      }
    }
    for (long u = 1L; u <= USERS; u++) {
      for (long i = 1L; i <= ITEMS; i++) {
        assertEquals(1.0 * u * i, constrainedModel.getUserItemPreference(u, i), 0.0);
      }
    }
  }
}