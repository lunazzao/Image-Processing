package sinimage;

/**
 * An interface for specify what operation can use in ImageModifyModel. this mode can change the
 * image's rgb value and return the image path.
 */
public interface AImageModifyControl extends ImageModifyControl {

  /**
   * blur method for add blur effect to image.
   */
  void blur();

  /**
   * sharp method for add blur effect to image.
   */
  void sharp();

  /**
   * gray method for add blur effect to image.
   */
  void grey();

  /**
   * sepia method for add blur effect to image.
   */
  void sepia();

  /**
   * return the image path.
   *
   * @return image path
   */
  String returnImagePath();

  /**
   * mosaic method for giving the image a stained glass window" effect.
   *
   * @param seed the seeds num. The less seeds are, the more blurry this image would be.
   */
  void mosaic(int seed);

  /**
   * downscale method for resizing (downscale) of an image as the designated height and width.
   *
   * @param height the designated height
   * @param width  the designated width
   */
  void downscale(int height, int width);
}
