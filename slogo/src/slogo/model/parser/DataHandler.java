package slogo.model.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import slogo.model.parser.Files.LanguageFiles;


public class DataHandler {

  private ResourceBundle currentLanguageBundle;

  private final String language;

  public DataHandler(String language) {
    this.language = language;
    setCurrentLanguageBundle();
  }

  private void setCurrentLanguageBundle() {
    this.currentLanguageBundle = LanguageFiles.valueOf(language.toUpperCase()).returnBundle();
  }

  public String[] languageConverter(String[] inputs) {
    List<String> translated = new ArrayList<>();
    for (String input : inputs) {
      boolean added = false;
      if (input.equals(" ") || input.equals("")) {
        translated.add(input);
        continue;
      }
      added = checkAllCommands(translated, input, added);
      if (!added) {
        translated.add(input);
      }
    }
    String[] translatedArray = new String[translated.size()];
    for (int i = 0; i < translated.size(); i++) {
      translatedArray[i] = translated.get(i);
    }
    return translatedArray;
  }

  private boolean checkAllCommands(List<String> translated, String input, boolean added) {
    for (String key : currentLanguageBundle.keySet()) {
      String curValue = currentLanguageBundle.getString(key);
      String[] curValueArray = curValue.split("\\|");
      for (String s : curValueArray) {
        s = s.replace("\\", "");
        if (s.equalsIgnoreCase(input)) {
          translated.add(key);
          added = true;
          break;
        }
      }
      if (added) {
        break;
      }
    }
    return added;
  }
}
