package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.Random;

public class RandomlyMoving implements Behaviour<Movable> {
    private final Random random = new Random();

    public void randomMove(Movable actor) {
        int pomX = (int) (Math.random() * (3)) - 1;
        int pomY = (int) (Math.random() * (3)) - 1;

        Direction direction = null;

        for (Direction value : Direction.values()) {
            if (pomX == value.getDx() && pomY == value.getDy()) {
                direction = value;
            }
        }
        assert direction != null;
        actor.getAnimation().setRotation(direction.getAngle());
        new Move<>(direction, 2).scheduleFor(actor);
    }


    @Override
    public void setUp(Movable actor) {
        if (actor == null) return;

        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(() -> randomMove(actor)),
                new Wait<>(2)
            )
        ).scheduleFor(actor);
    }
}
