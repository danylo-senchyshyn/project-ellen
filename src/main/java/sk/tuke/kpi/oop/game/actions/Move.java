package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

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
        this.isDone = false;
    }
    public Move(Direction direction) {
        this.direction = direction;
        duration = 0;
        isFirstTime = false;
        isDone = false;
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
        if (actor == null) return;

        duration -= deltaTime;
        isFirstTime = true;

        if (isFirstTime && !isDone) {
            actor.startedMoving(direction);
        }

        actor.setPosition(
            actor.getPosX() + direction.getDx() * actor.getSpeed(),
            actor.getPosY() + direction.getDy() * actor.getSpeed());

        if (Math.abs(deltaTime - this.duration) <= 1e-5f) {
            this.stop();
            isFirstTime = false;

        }
    }

    public void stop() {
        isDone = true;
        actor.stoppedMoving();
    }

}
