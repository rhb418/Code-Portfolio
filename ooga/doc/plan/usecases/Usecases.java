
// 1.  The user wants to exit a Game

public class GSettingsPane extends StackPane {

	public GSettingsPane() {
		getChildren().add(exitButton);
		exitButton.setOnAction( e -> {
			exit.run();
		});
	}
}

// 2. The internal object moves forward

public class ObjectView {
	public ObjectView(ObservableObject obj) {
		obj.setOnUpdate( () -> {
			updatePosition();
		});
	}

	public void updatePosition() {
		setTranslateX(obj.getX());
		setTranslateY(obj.getY());
		setTranslateRotate(obj.getRotate());
	}
}

// 3. The user presses a button that corresponds to a mapping

public class GameScene {
	private Map<String, String> funcMappings;

	public GameScene(ObservableModel game, ObservableResource resources) {
		this.controller = game.getController();
		this.funcMappings = controller.getMappings();

		setOnKeyPress( e -> {
			if (funcMappings.contains(e.getText())) {
				controller.handle(e.getText());
			}
		});
	}

}

public class ModelController {
	public void handle(String text) {
		for (String action : funcMaps.keySet()) {
			funcMaps.get(text).run();
		}
	}
}

// 4. An object handles a collision
public void collided(GameObject colObj) {
		Set<String> colObj.getIDs();
    for (String key : colObj.keySet()) {
			collisionMap.computeIfPresent(key, (id, handler) -> {
					handler.handleCollision(obj2);
			});
    }

// 5. An object collides with another object

public void checkCollisions() {
	// Sort the collidble objects and use an algorithm to find overlapping ranges
	// ...
	obj1.collide(obj2);
	obj2.collide(obj1);
}
