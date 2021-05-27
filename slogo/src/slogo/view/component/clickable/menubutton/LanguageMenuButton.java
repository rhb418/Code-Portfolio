package slogo.view.component.clickable.menubutton;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;

/**
 * The LanguageMenuButton class changes the language that commands will be read in.
 *
 * Subscribes to the WorkspaceMediator in order to notify it when a new language has been clicked.
 *
 * @author Bill Guo, Tomas Esber
 */
public class LanguageMenuButton extends StaticMenuButtonClickable {

  private String myLanguage;

  /**
   * Constructor of the LanguageMenuButton.
   * @param image Icon for MenuButton
   * @param menuBundle Resource file to get the chosen language in English
   */
  public LanguageMenuButton(Image image, ResourceBundle menuBundle) {
    super(image, menuBundle);
  }

  /**
   * Returns an EventHandler for each menuItem. In this case, the MenuItem will set the chosen
   * language to the English spelling.
   * @return
   */
  @Override
  protected EventHandler<ActionEvent> onClick() {
    return event -> chosenLanguage(event);
  }

  private void chosenLanguage(ActionEvent event) {
    MenuItem item = (MenuItem) event.getSource();
    myLanguage = menuBundle.getString(item.getText());
    notifyMediator();
  }

  /**
   * Returns the language chosen. Used by the WorkspaceMediator to get the chosen language and
   * change the language.
   * @return String that represents language
   */
  public String getLanguage() {
    return myLanguage;
  }
}
