package multiimage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import sinimage.AImageModifyControl;

/**
 * this class for the multi_image control model. In this model, we can manage several
 * AImageModifyControl objects. Each object correspond a image.
 */
public class MultiImageControlModel implements MultiImageControl {

  private List<Boolean> transparent;
  private List<AImageModifyControl> images;

  /**
   * multiimage.MultiImageControlModel constructor.
   */
  public MultiImageControlModel() {
    this.images = new ArrayList<>();
    this.transparent = new ArrayList<>();
  }

  /**
   * putImage for put a image object to specify layer.
   *
   * @param layer layer No
   * @param img   image we need put
   * @throws IllegalArgumentException wrong layer number
   */
  @Override
  public void putImage(int layer, AImageModifyControl img) throws IllegalArgumentException {
    if (layer < 0 || img == null) {
      throw new IllegalArgumentException("invalid input");
    }
    if (layer > this.images.size()) {
      throw new IllegalArgumentException("no such layer");
    }

    this.images.add(layer, img);
    this.transparent.add(layer, false);
  }

  /**
   * remove a image object from specify layer.
   *
   * @param layer layer No
   * @throws IllegalArgumentException layer number wrong
   */
  @Override
  public void removeImage(int layer) throws IllegalArgumentException {
    if (layer < 0) {
      throw new IllegalArgumentException("invalid input");
    }
    if (layer > this.images.size() - 1) {
      throw new IllegalArgumentException("no such layer");
    }

    this.images.remove(layer);
    this.transparent.remove(layer);
  }

  /**
   * this method can blur a image at specify layer.
   *
   * @param layer layer No
   * @throws IllegalArgumentException layer number wrong
   */
  @Override
  public void blurSingleImage(int layer) throws IllegalArgumentException {
    if (layer < 0) {
      throw new IllegalArgumentException("invalid input");
    }
    if (layer > this.images.size() - 1) {
      throw new IllegalArgumentException("no such layer");
    }

    this.images.get(layer).blur();
  }

  /**
   * this method can sharp a image at specify layer.
   *
   * @param layer layer No
   * @throws IllegalArgumentException layer number wrong
   */
  @Override
  public void sharpSingleImage(int layer) throws IllegalArgumentException {
    if (layer < 0) {
      throw new IllegalArgumentException("invalid input");
    }
    if (layer > this.images.size() - 1) {
      throw new IllegalArgumentException("no such layer");
    }

    this.images.get(layer).sharp();
  }

  /**
   * this method can gray a image at specify layer.
   *
   * @param layer layer No
   * @throws IllegalArgumentException layer number wrong
   */
  @Override
  public void greySingleImage(int layer) throws IllegalArgumentException {
    if (layer < 0) {
      throw new IllegalArgumentException("invalid input");
    }
    if (layer > this.images.size() - 1) {
      throw new IllegalArgumentException("no such layer");
    }

    this.images.get(layer).grey();
  }

  /**
   * switch transparent state to another state.
   *
   * @param layer layer No
   * @throws IllegalArgumentException layer number wrong
   */
  @Override
  public void invisibleSingleImage(int layer) throws IllegalArgumentException {
    if (layer < 0) {
      throw new IllegalArgumentException("invalid input");
    }
    if (layer > this.images.size() - 1) {
      throw new IllegalArgumentException("no such layer");
    }

    this.transparent.set(layer, !this.transparent.get(layer));
  }

  /**
   * this method can sepia a image at specify layer.
   *
   * @param layer layer No
   * @throws IllegalArgumentException layer number wrong
   */
  @Override
  public void sepiaSingleImage(int layer) throws IllegalArgumentException {
    if (layer < 0) {
      throw new IllegalArgumentException("invalid input");
    }
    if (layer > this.images.size()) {
      throw new IllegalArgumentException("no such layer");
    }

    this.images.get(layer).sepia();
  }

  /**
   * this method will return a image object packed by hashmap. the key always "-1" for sign that it
   * is not a multi-layer output.
   *
   * @return a image object
   */
  @Override
  public HashMap<String, AImageModifyControl> outputTopImage() {
    for (int i = this.transparent.size() - 1; i >= 0; i--) {
      if (!this.transparent.get(i)) {
        HashMap<String, AImageModifyControl> cache = new HashMap<String, AImageModifyControl>();
        cache.put("-1", this.images.get(i));
        return cache;
      }
    }
    return null;
  }

  /**
   * this method will return all image objects packed by hashmap. the key record transparent state
   * and order of images. first number is the transparent state, reest number is the order.
   *
   * @return all image objects and imformation of those images
   */
  @Override
  public HashMap<String, AImageModifyControl> outputMultiImage() {
    HashMap<String, AImageModifyControl> cache = new HashMap<>();
    for (int i = 0; i < this.images.size(); i++) {
      int state = this.transparent.get(i) ? 1 : 0;
      cache.put(String.valueOf(state) + i, this.images.get(i));
    }
    return cache;
  }

  /**
   * return how may layer we have.
   *
   * @return layer numbers
   */
  @Override
  public int maxLayer() {
    return this.images.size();
  }

  /**
   * This method would mosaic a single image. The seed would represent how many pieces this image
   * would be broken down into. This creates a cluster of pixels for each seed. Then the color of
   * each pixel in the image is replaced with the average color of its cluster. The less seeds are,
   * the more blurry this image would be.
   *
   * @param layer the layer to be mosaic-ed.
   * @param seeds the seeds num how many
   */
  @Override
  public void mosaicSingleImage(int layer, int seeds) {
    if (layer < 0 || seeds < 0) {
      throw new IllegalArgumentException("invalid input");
    }
    if (layer > this.images.size() - 1) {
      throw new IllegalArgumentException("no such layer");
    }
    this.images.get(layer).mosaic(seeds);
  }

  /**
   * This method will enable resize(downscale) of an image as the designated height and width. The
   * method enables mapping among triangles and transfer nums at the corresponding location in rgb
   * matrix.
   *
   * @param layer  the layer number to be transformed
   * @param height the designated height
   * @param width  the designated width
   */
  @Override
  public void downscale(int layer, int height, int width) {
    if (layer < 0) {
      throw new IllegalArgumentException("invalid input");
    }
    if (layer > this.images.size()) {
      throw new IllegalArgumentException("no such layer");
    }

    this.images.get(layer).downscale(height, width);
  }
}
