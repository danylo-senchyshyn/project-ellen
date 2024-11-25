package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

import java.util.Objects;

public class Move<A extends Movable> implements Action<A> {
    private float duration;
    private boolean isFirstTime;
    private boolean isDone;
    private Direction direction;
    private A actor;

    public Move(Direction direction, float duration) {
        this.duration = duration;
        this.direction = direction;
        isFirstTime = false;
        isDone = false;
    }

    public Move(Direction direction) {
        this(direction, 0);
    }

    @Override
    public @Nullable A getActor() {
        return actor;
    }

    @Override
    public void setActor(A actor) {
        this.actor = actor;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public void reset() {
        actor.stoppedMoving();
        isDone = false;
        isFirstTime = false;
        duration = 0;
    }

    @Override
    public void execute(float deltaTime) {
        if (actor == null || isDone) return;

        if (!isFirstTime) {
            actor.startedMoving(direction);
            isFirstTime = true;
        }

        actor.setPosition(
            actor.getPosX() + direction.getDx() * actor.getSpeed(),
            actor.getPosY() + direction.getDy() * actor.getSpeed());

        assert getActor() != null;
        if (Objects.requireNonNull(getActor().getScene()).getMap().intersectsWithWall(actor)) {
            actor.setPosition(
                actor.getPosX() - direction.getDx() * actor.getSpeed(),
                actor.getPosY() - direction.getDy() * actor.getSpeed());
        }

        duration -= deltaTime;

        if (Math.abs(deltaTime - this.duration) <= 1e-5) {
            this.stop();
        }
    }

    public void stop() {
        isDone = true;
        actor.stoppedMoving();
    }

}
