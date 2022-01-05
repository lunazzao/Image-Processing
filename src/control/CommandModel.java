package control;

import java.io.IOException;

/**
 * to represent a controller. this part can read the user's output and update the model or read and
 * write image.
 */
public interface CommandModel {

  /**
   * read command and operate according to the command.
   *
   * @throws IllegalArgumentException wrong command input.
   * @throws IOException              can not output message.
   */
  void readScript() throws IOException;

  /**
   * Update the readable to add new command.
   *
   * @param rd the readable used
   */
  void updateReadable(Readable rd);

  /**
   * Get a specific layer according to the given int.
   *
   * @return the layer number of the layer returning
   */
  int getLayer();
}
