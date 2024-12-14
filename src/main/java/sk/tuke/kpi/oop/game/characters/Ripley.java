package sk.tuke.kpi.oop.game.characters;

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
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

import java.text.DecimalFormat;
import java.util.Objects;

public class Ripley extends AbstractActor implements Movable, Keeper, Alive, Armed {
    private int speed;
    private int ammo;
    private Animation ripleyAnimation;
    private Backpack backpack;
    private Disposable disposable;
    private Health health;
    private Firearm gun;
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("ripley died", Ripley.class);

    public Ripley() {
        super("Ellen");
        ripleyAnimation = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        ripleyAnimation.stop();
        setAnimation(ripleyAnimation);
        speed = 2;
        ammo = 100;
        backpack = new Backpack("Ripley's backpack", 10);
        disposable = null;
        gun = new Gun(50, 150);
        health = new Health(100, 100);
        health.onFatigued(() -> {
            ripleyDied();
        });
    }

    @Override
    public int getSpeed() {
        return this.speed;
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
        setAnimation(ripleyAnimation);
        ripleyAnimation.setRotation(direction.getAngle());
        ripleyAnimation.play();
    }

    @Override
    public void stoppedMoving() {
        setAnimation(ripleyAnimation);
        ripleyAnimation.stop();
    }

    public void decreaseEnergy() {
        if (this.health.getValue() <= 0) {
            ripleyDied();
        } else {
            disposable = new Loop<>(
                new ActionSequence<>(
                    new Invoke<>(() -> {
                        if (this.health.getValue() <= 0) {
                            ripleyDied();
                            return;
                        } else {
                            this.getHealth().drain(3);
                        }
                    }),
                    new Wait<>(1)
                )
            ).scheduleFor(this);
        }
    }

    private void ripleyDied() {
        Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED, this);
        this.setAnimation(new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE));
    }

    @Override
    public Health getHealth() {
        return health;
    }

    public Disposable stopDecreasingEnergy() {
        return disposable;
    }

    @Override
    public Firearm getFirearm() {
        return gun;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        gun = weapon;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void showRipleyState() {
        int windowHeight = Objects.requireNonNull(getScene()).getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;

        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        String formattedHealth = decimalFormat.format(health.getValue());

        getScene().getGame().getOverlay().drawText("Energy " + formattedHealth, 120, yTextPos);
        getScene().getGame().getOverlay().drawText("Your Ammo " + this.getFirearm().getAmmo(), 280, yTextPos);
        getScene().getGame().getOverlay().drawText("Maximum Ammo " + this.getFirearm().getMaxAmmo(), 450, yTextPos);
    }
}
