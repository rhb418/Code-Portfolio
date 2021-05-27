package ooga.model.actions;

import ooga.model.objects.GameObject;

/**
 * Meant to be used by a collision system to describe a collision apart of its action
 */
public record CollisionInfo(GameObject self, GameObject other, String position) {

}
