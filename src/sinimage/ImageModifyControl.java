package sinimage;

import java.util.List;

/**
 * The interface to control image modifying.ImagemodifyControl now included modify a image and
 * generate a image.
 */
public interface ImageModifyControl {


  /**
   * return this image's rgb value. Those value be packed in a list. First is red, second is blue
   * and third is green.
   *
   * @return a list of image color information.
   */
  List<int[][]> getColor();
}
