import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import imageio.PPMReadWrite;
import java.io.IOException;
import java.util.Arrays;
import multiimage.MultiImageControl;
import multiimage.MultiImageControlModel;
import org.junit.Test;
import sinimage.AImageModifyControl;
import sinimage.ImageModifyControlModel;

/**
 * test MultiImageControlMode class.
 */
public class MultiImageControlModeTest {

  @Test
  public void putImageAndRemoveTest() throws IOException {
    MultiImageControl multi = new MultiImageControlModel();
    AImageModifyControl image = new ImageModifyControlModel(
        new PPMReadWrite().readRGB("/Users/hzh-mac/Desktop/test/HW5/res/d.ppm"),
        "/Users/hzh-mac/Desktop/test/HW5/res/d.ppm");
    multi.putImage(0, image);
    assertEquals(multi.maxLayer(), 1);
    multi.removeImage(0);
    assertEquals(multi.maxLayer(), 0);
  }

  @Test
  public void blurImageTest() throws IOException {
    MultiImageControl multi = new MultiImageControlModel();
    AImageModifyControl image = new ImageModifyControlModel(
        new PPMReadWrite().readRGB("/Users/hzh-mac/Desktop/test/HW5/res/d.ppm"),
        "/Users/hzh-mac/Desktop/test/HW5/res/d.ppm");
    AImageModifyControl image2 = new ImageModifyControlModel(
        new PPMReadWrite().readRGB("/Users/hzh-mac/Desktop/test/HW5/res/d.ppm"),
        "/Users/hzh-mac/Desktop/test/HW5/res/d.ppm");
    image2.blur();
    multi.putImage(0, image);
    multi.blurSingleImage(0);
    assertTrue(Arrays.deepEquals(multi.outputTopImage().get("-1").getColor().get(1),
        image2.getColor().get(1)));
  }
}
