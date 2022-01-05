package view;

/**
 * Interface for view. Has function of rendering messages to the viewer. View can be text view or
 * graphics view.
 */
public interface IView {

  /**
   * To render messages to view according to the given string.
   *
   * @param mess the message string to be updated
   */
  void renderMessage(String mess);
}
