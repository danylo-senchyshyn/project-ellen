package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.Objects;

public class Ammo extends AbstractActor implements Usable<Ripley> {

    public Ammo() {
        Animation ammoAnimation = new Animation("sprites/ammo.png", 16,16);
        setAnimation(ammoAnimation);
    }

    @Override
    public void useWith(Ripley ripley) {
        if (ripley == null || ripley.getAmmo() == 500) return;

        if (ripley.getEnergy() <= 450) {
            ripley.setAmmo(ripley.getAmmo() + 50);
        } else {
            ripley.setAmmo(500);
        }

        (Objects.requireNonNull(getScene())).removeActor(this);
    }
}
