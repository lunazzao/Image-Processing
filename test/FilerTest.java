import static org.junit.Assert.assertEquals;

import filter.Filter;
import filter.ImageFilter;
import java.util.List;
import org.junit.Test;

/**
 * for test the imagefilter class.
 */
public class FilerTest {

  @Test(expected = IllegalArgumentException.class)
  public void blurNullException() {
    ImageFilter filter = new Filter();
    filter.blur(null, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void sharpNullException() {
    ImageFilter filter = new Filter();
    filter.sharp(null, null, null);
  }

  @Test
  public void blurSingleElement() {
    ImageFilter filter = new Filter();
    List<int[][]> cache = filter.blur(new int[][]{{1}}, new int[][]{{1}}, new int[][]{{1}});
    assertEquals(cache.get(0)[0][0], 0);
    assertEquals(cache.get(1)[0][0], 0);
    assertEquals(cache.get(2)[0][0], 0);
  }

  @Test
  public void sharpSingleElement() {
    ImageFilter filter = new Filter();
    List<int[][]> cache = filter.sharp(new int[][]{{1}}, new int[][]{{1}}, new int[][]{{1}});
    assertEquals(cache.get(0)[0][0], 1);
    assertEquals(cache.get(1)[0][0], 1);
    assertEquals(cache.get(2)[0][0], 1);
  }
}
