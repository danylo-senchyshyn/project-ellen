package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;

import java.util.Objects;

public class Ripley extends AbstractActor implements Movable, Keeper<Actor> {
    private int speed;
    private int energy;
    private int ammo;
    private Animation ripleyAnimation;
    private Backpack backpack;

    public Ripley() {
        super("Ellen");
        speed = 2;
        energy = 50;
        ammo = 100;
        backpack = new Backpack("Ripley's backpack",10);
        ripleyAnimation = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        ripleyAnimation.stop();
        setAnimation(ripleyAnimation);
    }

    @Override
    public int getSpeed() {
        return this.speed;
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
}
