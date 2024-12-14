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
    public Item item;
    public enum Item {mjolnir, wrench, energy, ammo}

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
                getScene().addActor(new Mjolnir(), getPosX(), getPosY());
                break;
            case wrench:
                getScene().addActor(new Wrench(), getPosX(), getPosY());
                break;
            case energy:
                getScene().addActor(new Energy(), getPosX(), getPosY());
                break;
            case ammo:
                getScene().addActor(new Ammo(), getPosX(), getPosY());
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
