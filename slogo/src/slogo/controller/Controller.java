package slogo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class Controller {

  protected Map<Double, double[]> startingColorPalette;

  public Controller(ResourceBundle colorBundle) {
    startingColorPalette = makeColorPalette(colorBundle);
  }

  protected Map<Double, double[]> makeColorPalette(ResourceBundle colorBundle) {
    Map<Double, double[]> returnMap = new HashMap<>();
    for (String indexOfColorBundle : colorBundle.keySet()) {
      String[] rgbString = colorBundle.getString(indexOfColorBundle).split("\\|");
      double[] rgb = new double[rgbString.length];
      for (int indexOfRgbString = 0; indexOfRgbString < rgbString.length; indexOfRgbString++) {
        rgb[indexOfRgbString] = Double.valueOf(rgbString[indexOfRgbString]);
      }
      returnMap.put(Double.valueOf(indexOfColorBundle), rgb);
    }
    return returnMap;
  }
}
