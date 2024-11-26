package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;

import java.util.Objects;

public class Ripley extends AbstractActor implements Movable, Keeper {
    private int speed;
    private int energy;
    private int ammo;
    private Animation ripleyAnimation;
    private Backpack backpack;
    private Disposable disposable;
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("ripley died", Ripley.class);


    public Ripley() {
        super("Ellen");
        ripleyAnimation = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        ripleyAnimation.stop();
        setAnimation(ripleyAnimation);
        speed = 2;
        energy = 100;
        ammo = 100;
        backpack = new Backpack("Ripley's backpack", 10);
        disposable = null;
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    @Override
    public void startedMoving(Direction direction) {
        ripleyAnimation.setRotation(direction.getAngle());
        ripleyAnimation.play();
        setAnimation(ripleyAnimation);
    }

    @Override
    public void stoppedMoving() {
        ripleyAnimation.stop();
        setAnimation(ripleyAnimation);
    }

    public void decreaseEnergy() {
        if (this.getEnergy() <= 0) {
            this.setAnimation(new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE));
            getScene().getMessageBus().publish(RIPLEY_DIED, this);
        } else {
            disposable = new Loop<>(
                new ActionSequence<>(
                    new Invoke<>(() -> {
                        if (this.getEnergy() <= 0) {
                            this.setAnimation(new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE));
                            getScene().getMessageBus().publish(RIPLEY_DIED, this);
                            return;
                        } else {
                            this.setEnergy(this.getEnergy() - 5);
                        }
                    }),
                    new Wait<>(1)
                )
            ).scheduleFor(this);
        }
    }

    public Disposable stopDecresingEnergy() {
        return disposable;
    }

    public void showRipleyState() {
        int windowHeight = getScene().getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        getScene().getGame().getOverlay().drawText("Energy " + this.getEnergy(), 120, yTextPos);
        getScene().getGame().getOverlay().drawText("Ammo " + this.getAmmo(), 320, yTextPos);
    }
}
