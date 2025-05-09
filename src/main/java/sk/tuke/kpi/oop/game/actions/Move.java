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

    private Move(Direction direction) {
        this.direction = direction;
        duration = 0;
        isDone = false;
        isFirstTime = false;
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

        if (Objects.requireNonNull(getActor().getScene()).getMap().intersectsWithWall(actor)) {
            actor.setPosition(
                actor.getPosX() - direction.getDx() * actor.getSpeed(),
                actor.getPosY() - direction.getDy() * actor.getSpeed());

            actor.collidedWithWall();
        }

        duration -= deltaTime;

        if (duration <= 0) {
            stop();
        }
    }

    public void stop() {
        if (actor == null) return;
        isDone = true;
        actor.stoppedMoving();
    }
}
