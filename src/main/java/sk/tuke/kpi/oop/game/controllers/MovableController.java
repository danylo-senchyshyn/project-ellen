package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;
import sk.tuke.kpi.oop.game.Direction;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener {
    private Move<Movable> move;
    private Set<Direction> keys = new HashSet<>();
    private Movable movable;
    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.RIGHT, Direction.EAST),
        Map.entry(Input.Key.LEFT, Direction.WEST)

    );

    public MovableController(Movable movable) {
        this.movable = movable;
        move = null;
    }

    @Override
    public void keyReleased(Input.Key key) {
        if (keyDirectionMap.containsKey(key)) {
            keys.remove(keyDirectionMap.get(key));
            updateMove();
        }
    }

    @Override
    public void keyPressed(Input.Key key) {
        if (keyDirectionMap.containsKey(key)) {
            keys.add(keyDirectionMap.get(key));
            updateMove();
        }
    }

    private void updateMove() {
        Direction helpDirection = Direction.NONE;
        for (Direction d : keys) {
            if (d != null) {
                helpDirection = helpDirection.combine(d);
            }
        }
        keyIsEmpty();
        if (helpDirection != Direction.NONE) {
            helpDirectionForMove(helpDirection);
        }
    }

    private void helpDirectionForMove(Direction helpDirection) {
        move = new Move<>(helpDirection, 999999);
        move.scheduleFor(movable);
    }

    private void keyIsEmpty() {
        if (keys.isEmpty() || move != null) {
            move.stop();
        }
    }
}
