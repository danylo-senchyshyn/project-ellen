package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class FollowActor implements Behaviour<Movable> {

    public void followActor(Movable actor) {
        Ripley ripley = actor.getScene().getLastActorByType(Ripley.class);
        if (ripley == null) return;

        int deltaX = Integer.compare(ripley.getPosX(), actor.getPosX());
        int deltaY = Integer.compare(ripley.getPosY(), actor.getPosY());

        //System.out.println("x " + deltaX);
        //System.out.println("y " + deltaY);

        Direction direction = null;

        for (Direction value : Direction.values()) {
            if (deltaX == value.getDx() && deltaY == value.getDy()) {
                direction = value;
                break;
            }
        }

        assert direction != null;
        actor.getAnimation().setRotation(direction.getAngle());
        new Move<>(direction, 0.5f).scheduleFor(actor);
    }


    @Override
    public void setUp(Movable actor) {
        if (actor == null) return;

        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(() -> followActor(actor)),
                new Wait<>(0.5f)
            )
        ).scheduleFor(actor);
    }
}
