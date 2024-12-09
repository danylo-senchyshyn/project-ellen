package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Helicopter extends AbstractActor {
    private Animation animationDown;
    private Animation animationUp;
    private Animation animationLeft;
    private Animation animationRight;

    public Helicopter() {
        animationDown = new Animation("sprites/heli_down.png", 64, 64, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        animationUp = new Animation("sprites/heli_up.png", 64, 64, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        animationLeft = new Animation("sprites/heli_left.png", 64, 64, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        animationRight = new Animation("sprites/heli_right.png", 64, 64, 0.2f, Animation.PlayMode.LOOP_PINGPONG);

        setAnimation(animationUp);
    }

    public void searchAndDestroy() {
        new Loop<>(new Invoke<>(this::searchActor)).scheduleFor(this);
    }

    private void searchActor() {
        Player refActor = getScene().getLastActorByType(Player.class);
        if (refActor == null) return;

        int deltaX = (int) Math.signum(refActor.getPosX() - this.getPosX());
        int deltaY = (int) Math.signum(refActor.getPosY() - this.getPosY());
        int newPositionX = 0, newPositionY = 0;

        newPositionX = this.getPosX() + deltaX;
        newPositionY = this.getPosY() + deltaY;

        this.setPosition(newPositionX, newPositionY);

        updateAnimationHelicopter(deltaX, deltaY);

        if (intersects(refActor)) {
            refActor.setEnergy(refActor.getEnergy() - 1);
        }
    }

    public void updateAnimationHelicopter(int deltaX, int deltaY) {
        if (deltaX > 0) {
            setAnimation(animationRight);
        } else if (deltaX < 0) {
            setAnimation(animationLeft);
        } else if (deltaY > 0) {
            setAnimation(animationDown);
        } else if (deltaY < 0) {
            setAnimation(animationUp);
        }
    }
}
