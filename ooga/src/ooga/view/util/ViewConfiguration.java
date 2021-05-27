package ooga.view.util;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ViewConfiguration {

  public static final String DEFAULT_RESOURCES = "ooga.view.resources.languages.";

  private ObservableResource language = new ObservableResource();
  private static List<String> supportedLanguages;
  private static ResourceBundle systemProperties =
      ResourceBundle.getBundle("ooga.view.util.resources.System");

  public ViewConfiguration() {}

  public ObservableResource getResources() {
    return this.language;
  }

  public void setLanguage(String lang) {
    ResourceBundle bundle = ResourceBundle.getBundle(DEFAULT_RESOURCES + lang);
    language.setResources(bundle);
  }

  public static List<String> getSupportedLanguages() {
    if (supportedLanguages == null) {
      String langRaw = systemProperties.getString("SupportedLanguages");
      String[] langs = langRaw.split(",");
      supportedLanguages = Arrays.asList(langs);
    }
    return supportedLanguages;
  }
}
