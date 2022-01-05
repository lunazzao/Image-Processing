import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Test;
import transform.ColorTransform;
import transform.ImageColorTransform;

/**
 * for test imageColorTransform class.
 */
public class TransformTest {

  @Test(expected = IllegalArgumentException.class)
  public void grayNullException() {
    ImageColorTransform transform = new ColorTransform();
    transform.grey(null, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void sepiaNullException() {
    ImageColorTransform transform = new ColorTransform();
    transform.sepia(null, null, null);
  }

  @Test
  public void graySingleElement() {
    ImageColorTransform transform = new ColorTransform();
    List<int[][]> cache = transform.grey(new int[][]{{1}}, new int[][]{{1}}, new int[][]{{1}});
    assertEquals(cache.get(0)[0][0], 1);
    assertEquals(cache.get(1)[0][0], 1);
    assertEquals(cache.get(2)[0][0], 1);
  }

  @Test
  public void sepiaSingleElement() {
    ImageColorTransform transform = new ColorTransform();
    List<int[][]> cache = transform.sepia(new int[][]{{1}}, new int[][]{{1}}, new int[][]{{1}});
    assertEquals(cache.get(0)[0][0], 1);
    assertEquals(cache.get(1)[0][0], 1);
    assertEquals(cache.get(2)[0][0], 0);
  }


}