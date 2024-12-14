package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.*;

public class Locker extends AbstractActor implements Usable<Ripley> {
    private Animation lockerOpen;
    private Animation lockerClosed;
    private boolean isUsed;
    private Item item;
    public enum Item {mjolnir, wrench, energy, ammo}
    private Mjolnir mjolnir = new Mjolnir();
    private Wrench wrench = new Wrench();
    private Energy energy = new Energy();
    private Ammo ammo = new Ammo();

    public Locker(Item item) {
        lockerClosed = new Animation("maps/alienbreed-sprites/chest.png", 16, 16);
        lockerOpen = new Animation("maps/alienbreed-sprites/chest_open.png", 16, 16);
        setAnimation(lockerClosed);
        lockerClosed.setRotation(180);
        this.item = item;
        isUsed = false;
    }

    @Override
    public void useWith(Ripley actor) {
        if (isUsed) return;
        if (item == null) return;
        isUsed = true;

        switch (item) {
            case mjolnir:
                getScene().addActor(mjolnir, getPosX(), getPosY() + 16);
                break;
            case wrench:
                getScene().addActor(wrench, getPosX() + 16, getPosY() + 16);
                break;
            case energy:
                getScene().addActor(energy, getPosX(), getPosY() - 16);
                break;
            case ammo:
                getScene().addActor(ammo, getPosX(), getPosY() - 16);
                break;
        }

        setAnimation(lockerOpen);
        lockerClosed.setRotation(180);
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
    }
}
