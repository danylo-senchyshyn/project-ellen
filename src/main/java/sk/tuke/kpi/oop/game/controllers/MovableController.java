package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener {
    private Movable actor;
    private Set<Input.Key> pressedKeys;
    private Input.Key key1 = null;
    private Input.Key key2 = null;
    private Disposable disposable;
    private Move<Movable> moveAction = null;

    public MovableController(Movable actor) {
        this.actor = actor;
        pressedKeys = new HashSet<>();
    }

    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.RIGHT, Direction.EAST),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.LEFT, Direction.WEST)
    );


    @Override
    public void keyPressed(Input.Key key) {
        if (keyDirectionMap.containsKey(key)) {
            pressedKeys.add(key);

            if (key1 == null) {
                key1 = key;
            } else if (key2 == null) {
                key2 = key;
            }

            updateMove();
        }
    }

    private void updateMove() {
        Direction currentDirection = Direction.NONE;
        int i = 0;
        for (Input.Key key : pressedKeys) {
            if (i == 0) {
                currentDirection = keyDirectionMap.get(key);
            } else if (i == 1) {
                currentDirection = currentDirection.combine(keyDirectionMap.get(key));
            }

            i++;
        }
        stopMoving();

        if (currentDirection != null) {
            moveAction = new Move<>(currentDirection, Float.MAX_VALUE);
            disposable = moveAction.scheduleFor( (Ripley) actor);
        }
    }

    private void stopMoving() {
        if (moveAction != null) {
            moveAction.stop();
            if (disposable != null) {
                disposable.dispose();
            }
            moveAction = null;
        }
    }

    @Override
    public void keyReleased(Input.Key key) {
        if (keyDirectionMap.containsKey(key)) {
            pressedKeys.remove(key);

            if (key == key1) {
                key1 = null;
            } else if (key == key2) {
                key2 = null;
            }

            updateMove();
        }
    }
}
