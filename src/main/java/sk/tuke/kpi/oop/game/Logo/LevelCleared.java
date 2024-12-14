package sk.tuke.kpi.oop.game.Logo;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class LevelCleared extends AbstractActor {
    public LevelCleared() {
        Animation animation = new Animation("maps/alienbreed-sprites/popup_level_done.png", 288, 128);
        setAnimation(animation);
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        new Wait<>(2).scheduleFor(this);
    }
}
