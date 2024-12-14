package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Body extends AbstractActor {
    public Body() {
        Animation deadAlienAnimation = new Animation("maps/alienbreed-sprites/body.png", 64, 48);
        setAnimation(deadAlienAnimation);
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
    }
}
