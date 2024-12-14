package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Helicopter extends AbstractActor {
    private Animation animationDown;
    private Animation animationUp;
    private Animation animationLeft;
    private Animation animationRight;
    private int newPositionX = 0, newPositionY = 0;
    public static final Topic<Helicopter> HELICOPTER_EXPLODED = Topic.create("helicopter have been exploded", Helicopter.class);

    public Helicopter() {
        animationDown = new Animation("sprites/heli_down.png", 64, 64, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        animationUp = new Animation("sprites/heli_up.png", 64, 64, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        animationLeft = new Animation("sprites/heli_left.png", 64, 64, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        animationRight = new Animation("sprites/heli_right.png", 64, 64, 0.2f, Animation.PlayMode.LOOP_PINGPONG);

        setAnimation(animationUp);
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::searchActor)
            )
        ).scheduleFor(this);

        new ActionSequence<>(
            new Wait<>(10),
            new Invoke<>(() -> {
                if (getScene() != null) {
                    getScene().getMessageBus().publish(HELICOPTER_EXPLODED, this);
                    getScene().removeActor(this);
                }
            })
        ).scheduleFor(this);
    }

    private void searchActor() {
        Ripley ripley = getScene().getLastActorByType(Ripley.class);
        if (ripley == null) return;

        if (this.getPosY() != ripley.getPosY()) {
            if(this.getPosY() > ripley.getPosY()) {
                newPositionY = this.getPosY()- 1;
            } else {
                newPositionY = this.getPosY() + 1;
            }
            updateAnimationHelicopter(newPositionX, newPositionY);
        }
        if (this.getPosX() != ripley.getPosX()) {
            if(this.getPosX() > ripley.getPosX()) {
                newPositionX = this.getPosX()- 1;
            } else {
                newPositionX = this.getPosX() + 1;
            }
            updateAnimationHelicopter(newPositionX, newPositionY);
        }

        this.setPosition(newPositionX, newPositionY);

        if (this.intersects(ripley)) {
            damage(ripley);
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

    public void damage(Ripley ripley) {
        ripley.getHealth().drain(0.5f);
    }
}
