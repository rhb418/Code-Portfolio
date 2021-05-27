package slogo.model.parser;

import java.util.ResourceBundle;

public class Files {

  public enum LanguageFiles {
    CHINESE("slogo.resources.languages.Chinese"),
    ENGLISH("slogo.resources.languages.English"),
    FRENCH("slogo.resources.languages.French"),
    GERMAN("slogo.resources.languages.German"),
    ITALIAN("slogo.resources.languages.Italian"),
    PORTUGUESE("slogo.resources.languages.Portuguese"),
    RUSSIAN("slogo.resources.languages.Russian"),
    SPANISH("slogo.resources.languages.Spanish"),
    URDU("slogo.resources.languages.Urdu");

    private String file;

    LanguageFiles(String file) {
      this.file = file;
    }

    public ResourceBundle returnBundle() {
      return ResourceBundle.getBundle(this.getFile());
    }

    public String getFile() {
      return file;
    }
  }

  public enum ParserFiles {
    TOKENIZER_INFO_FILE("slogo.resources.classInfo.TokenizerInfo"),
    COMMAND_INFO_FILE("slogo.resources.languages.English"),
    COMMAND_CONVERTER_FILE("slogo.resources.classInfo.CommandConverter"),
    CONSTRUCTOR_INFO_FILE("slogo.resources.classInfo.ClassParams"),
    CONSTANTS_FILE("slogo.resources.classInfo.CommandConstants"),
    FORMAT_FILE("slogo.resources.classInfo.FormattingType"),
    CLASS_NAMES_FILE("slogo.resources.classInfo.ClassNames"),
    ARGS_FILE("slogo.resources.classInfo.ExpectedArgs"),
    NUM_BRACKETS("slogo.resources.classInfo.SuperCommands"),
    PARAMETERS_METHOD("slogo.resources.classInfo.ParametersMethod"),
    GENERATOR_CLASS("slogo.resources.classInfo.CommandGeneratorClassInfo"),
    GROUPING_METHOD("slogo.resources.classInfo.GroupingMethods");

    private String file;

    ParserFiles(String file) {
      this.file = file;
    }

    public ResourceBundle returnBundle() {
      return ResourceBundle.getBundle(this.getFile());
    }

    public String getFile() {
      return file;
    }
  }

}
