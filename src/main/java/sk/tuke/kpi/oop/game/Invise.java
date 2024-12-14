package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Invise extends AbstractActor {
    public Invise() {
        setAnimation(new Animation("maps/alienbreed-sprites/invisible.png"));
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
    }
}
