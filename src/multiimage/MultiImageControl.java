package multiimage;

import java.util.HashMap;
import sinimage.AImageModifyControl;

/**
 * represent a multi_layer image structure.
 */
public interface MultiImageControl {

  /**
   * putImage for put a image to specify layer.
   *
   * @param layer layer No
   * @param img   image we need put
   * @throws IllegalArgumentException wrong layer number
   */
  void putImage(int layer, AImageModifyControl img) throws IllegalArgumentException;

  /**
   * remove a image from specify layer.
   *
   * @param layer layer No
   * @throws IllegalArgumentException layer number wrong
   */
  void removeImage(int layer) throws IllegalArgumentException;

  /**
   * this method can blur a image at specify layer.
   *
   * @param layer layer No
   * @throws IllegalArgumentException layer number wrong
   */
  void blurSingleImage(int layer) throws IllegalArgumentException;

  /**
   * this method can sharp a image at specify layer.
   *
   * @param layer layer No
   * @throws IllegalArgumentException layer number wrong
   */
  void sharpSingleImage(int layer) throws IllegalArgumentException;

  /**
   * this method can gray a image at specify layer.
   *
   * @param layer layer No
   * @throws IllegalArgumentException layer number wrong
   */
  void greySingleImage(int layer) throws IllegalArgumentException;

  /**
   * switch transparent state to another state.
   *
   * @param layer layer No
   * @throws IllegalArgumentException layer number wrong
   */
  void invisibleSingleImage(int layer) throws IllegalArgumentException;

  /**
   * this method can sepia a image at specify layer.
   *
   * @param layer layer No
   * @throws IllegalArgumentException layer number wrong
   */
  void sepiaSingleImage(int layer) throws IllegalArgumentException;

  /**
   * this method will return a image object packed by hashmap. the key always "-1" for sign that it
   * is not a multi-layer output.
   *
   * @return a image object
   */
  HashMap<String, AImageModifyControl> outputTopImage();

  /**
   * this method will return all image objects packed by hash-map. the key record transparent state
   * and order of images. first number is the transparent state, reest number is the order.
   *
   * @return all image objects and information of those images
   */
  HashMap<String, AImageModifyControl> outputMultiImage();

  /**
   * return how many layer we have.
   *
   * @return layer numbers
   */
  int maxLayer();

  /**
   * This method would mosaic a single image. The seed would represent how many pieces this image
   * would be broken down into. This creates a cluster of pixels for each seed. Then the color of
   * each pixel in the image is replaced with the average color of its cluster. The less seeds are,
   * the more blurry this image would be.
   *
   * @param layer the layer to be mosaic-ed.
   * @param seeds the seeds num. The less seeds are, the more blurry this image would be.
   */
  void mosaicSingleImage(int layer, int seeds);

  /**
   * This method will enable resize(downscale) of an image as the designated height and width. The
   * method enables mapping among triangles and transfer nums at the corresponding location in rgb
   * matrix.
   *
   * @param layer  the layer number to be transformed
   * @param height the designated height
   * @param width  the designated width
   */
  void downscale(int layer, int height, int width);

}
