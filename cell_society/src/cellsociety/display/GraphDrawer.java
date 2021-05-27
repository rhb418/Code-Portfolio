package cellsociety.display;

import static cellsociety.Constants.CSS_PIE_COLOR_FX;
import static cellsociety.Constants.PIE_CHART_COLOR_MAP;

import cellsociety.evolution.Evolution;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import javafx.collections.FXCollections;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

/**
 * Display cell values in a Pie Chart
 *
 * Assumes it is starting off as a link, but still can be on its own
 *
 * Regardless of linkage or not, this class doesn't need to know or care, it just displays whatever
 * evolution is passed to it
 *
 *      Drawer myDrawer = new GraphDrawer()
 *
 *
 * @author Felix Jiang
 */
public class GraphDrawer implements Drawer {

  /**
   * Display a PieChart of the total number of cells at each step of the simulation
   *
   * Changes to a single cell through clicking updates the pie chart as well
   *
   * @param myEvolution current Evolution that represents each cell's value
   * @param cellPaneWidth width of canvas to draw on
   * @param cellPaneHeight height of canvas to draw on
   * @param myDisplayer current window to display these drawings
   */
  @Override
  public void displayCurrentGeneration(Evolution myEvolution, double cellPaneWidth,
      double cellPaneHeight, Displayer myDisplayer) {
    HashMap<Integer, Integer> cellMap = myEvolution.getCellMap();
    List<Data> pieData = new ArrayList<>();
    for (int i = 0; i < cellMap.size(); i++) {
      pieData.add(new PieChart.Data("", 0));
      // add some random value, just need to have enough for each kind of cell
    }
    PieChart chart = new PieChart(FXCollections.observableArrayList(pieData));
    int pieDataIndex = 0;
    for (Entry<Integer, Integer> e : cellMap.entrySet()) {
      PieChart.Data curr = chart.getData().get(pieDataIndex);
      curr.setPieValue(e.getValue());
      curr.getNode().setStyle(CSS_PIE_COLOR_FX + PIE_CHART_COLOR_MAP[e.getKey()] + ";");
      pieDataIndex++;
    }
    chart.setLegendVisible(false);
    myDisplayer.addToCenterPane(chart);
  }
}
