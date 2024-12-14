package sk.tuke.kpi.oop.game.Logo;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class GameOver extends AbstractActor {
    public GameOver() {
        Animation animation = new Animation("maps/alienbreed-sprites/popup_level_failed.png", 288, 128);
        setAnimation(animation);
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
    }
}
