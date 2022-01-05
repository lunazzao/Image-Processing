package view;

import java.io.IOException;

/**
 * A concrete view class that implements view. This class contains an appendable and has a inherit
 * the function of renderMessage.
 */
public class MyView implements IView {

  private Appendable ap;

  /**
   * Basic constructor for my view.
   *
   * @param ap the appendable to be passed.
   */
  public MyView(Appendable ap) {
    this.ap = ap;
  }

  @Override
  public void renderMessage(String mess) {
    if (this.ap == null) {
      System.out.println(mess);
    } else {
      try {
        this.ap.append(mess);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
