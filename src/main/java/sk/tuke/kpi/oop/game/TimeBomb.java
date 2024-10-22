package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class TimeBomb extends AbstractActor {
    private final float time;
    private boolean isActivated;
    private boolean isExploded;
    private final Animation activatedBomb;
    private final Animation explodedBomb;

    public TimeBomb(float time) {
        this.time = time;
        isActivated = false;
        isExploded = false;

        Animation normalBomb = new Animation("sprites/bomb.png");
        activatedBomb = new Animation("sprites/bomb_activated.png", 16, 16, 0.2f);
        explodedBomb = new Animation("sprites/small_explosion.png", 64, 32, 0.1f, Animation.PlayMode.ONCE);

        setAnimation(normalBomb);
    }

    public void activate() {
        isActivated = true;
        setAnimation(activatedBomb);
        new ActionSequence<>(
            new Wait<>(this.time),
            new Invoke<>(this::explode),
            new Invoke<>(this::remove)).scheduleFor(this);
    }

    public boolean isActivated() {
        return isActivated;
    }

    public boolean isExploded() {
        return isExploded;
    }

    public void remove() {
        getScene().removeActor(this);
    }

    public void explode() {
        isExploded = true;
        setAnimation(explodedBomb);
    }
}
