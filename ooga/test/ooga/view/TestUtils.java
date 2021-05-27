package ooga.view;

import java.util.function.Predicate;
import javafx.scene.Node;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class TestUtils {
    // how close do real valued numbers need to be to count as the same
    private static final Predicate<Node> verifyFade = node -> node.getOpacity() < 1;
    public static final Predicate<Node> isPresent = Node::isVisible;
}
