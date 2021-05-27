package ooga.model.components;

import ooga.model.objects.GameObject;

public class ScoreComponent extends Component {

  private double score = 0;

  protected ScoreComponent() {
    super();
  }

  public ScoreComponent(int id, GameObject owner) {
    super(id, owner);
  }

  public double getScore() {
    return score;
  }

  public void changeScore(double score, boolean allowNegative) {
    this.score = score + this.score;
    if (this.score < 0) {
      if (!allowNegative) {
        this.score = 0;
      }
    }
  }

  public String typeUnerasure() {
    return ScoreComponent.class.getName();
  }
}
