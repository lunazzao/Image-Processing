package view;

import java.awt.image.BufferedImage;

/**
 * A windowed view that inherits from IView and added the functionality of showing images. This
 * interface represent this program's GUI.
 */
public interface IWindow extends IView {

  /**
   * Update the image to the window according to the given image.
   *
   * @param im the given image
   */
  void updateImage(BufferedImage im);
}
