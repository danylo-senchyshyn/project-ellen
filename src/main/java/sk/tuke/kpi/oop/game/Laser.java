package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Enemy;

public class Laser extends AbstractActor {

    public Laser() {
        Animation animation = new Animation("maps/alienbreed-sprites/laser.png", 16 , 48, 0.1f, Animation.PlayMode.LOOP);
        setAnimation(animation);
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        new Loop<>(new Invoke<>(this::applyDamage)).scheduleFor(this);
    }

    private void applyDamage() {
        for (Actor actor : getScene().getActors()) {
            if (actor instanceof Alive && !(actor instanceof Enemy) && this.intersects(actor)) {
                Alive aliveActor = (Alive) actor;
                aliveActor.getHealth().drain(1);
            }
        }
    }
}
