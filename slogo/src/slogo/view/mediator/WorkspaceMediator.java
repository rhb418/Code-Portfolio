package slogo.view.mediator;

import java.util.ResourceBundle;
import slogo.view.Workspace;
import slogo.view.component.Component;
import slogo.view.component.clickable.buttonclickable.LoadPreferencesButton;
import slogo.view.component.clickable.menubutton.LanguageMenuButton;

/**
 * The WorkspaceMediator class handles all components that interact with the Workspace.
 *
 * Classes that use this mediator include the LanguageMenuButton and LoadPreferencesButton.
 *
 * @author Bill Guo
 */
public class WorkspaceMediator extends Mediator {

  private static final String METHOD_FILE = "slogo/resources/mediators/Workspace";

  private Workspace target;

  /**
   * Constructor of the WorkspaceMediator.
   * @param workspace Workspace to change the language of
   */
  public WorkspaceMediator(Workspace workspace) {
    target = workspace;
    setMethodBundle(ResourceBundle.getBundle(METHOD_FILE));
  }

  /**
   * Sets the language of the Workspace to the selected language of the LanguageMenuButton.
   * @param component
   */
  protected void handleLanguageMenuButton(Component component) {
    LanguageMenuButton data = (LanguageMenuButton) component;
    target.setLanguage(data.getLanguage().split("\\|")[1]);
  }

  /**
   * Sets the language of the Workspace to the language found in the LoadPreferencesButton.
   * @param component
   */
  protected void handleLoadPreferencesButton(Component component) {
    LoadPreferencesButton data = (LoadPreferencesButton) component;
    target.setLanguage(data.getLanguage());
  }
}
