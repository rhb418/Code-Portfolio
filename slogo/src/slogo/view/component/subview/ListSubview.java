package slogo.view.component.subview;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import slogo.controller.BackEndController;

/**
 * The ListSubview class represents a Subview that shows a ListView.
 *
 * ListSubviews implement the makeContent() method by adding a ListView to the content made from
 * an observableList that can modify the ListView dynamically.
 *
 * @author Bill Guo
 */
public abstract class ListSubview extends Subview {

  protected List<String> list;
  protected ObservableList<String> observableList;
  protected ListView<String> listView;

  /**
   * Constructor of the ListSubview
   * @param labelKey key value that corresponds to this Subview's title in the header
   * @param labelBundle Resource file that contains necessary labels
   * @param backEndController BackEndController that all Subview's have access to
   */
  public ListSubview(String labelKey, ResourceBundle labelBundle,
      BackEndController backEndController) {
    super(labelKey, labelBundle, backEndController);
    list = new ArrayList<>();
    observableList = FXCollections.observableList(list);
    listView = new ListView<>(observableList);
    listView.getStyleClass().add("listsubview-listview");
  }
}
