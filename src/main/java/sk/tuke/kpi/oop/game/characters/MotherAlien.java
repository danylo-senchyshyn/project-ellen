package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class MotherAlien extends Alien {
    public static final Topic<MotherAlien> MOTHER_ALIEN_DEAD = Topic.create("mother alien dead", MotherAlien.class);

    public MotherAlien(Behaviour<? super Alien> behaviour) {
        super(300, behaviour);
        setAnimation(new Animation("sprites/mother.png", 112, 162, 0.2f, Animation.PlayMode.LOOP_PINGPONG));
    }

    public void checkIsAlive() {
        if (this.getHealth().getValue() <= 0) {
            getScene().getMessageBus().publish(MOTHER_ALIEN_DEAD, this);
            System.out.println("mother dead");
        }
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        new Loop<>(
            new Invoke<>(this::checkIsAlive)
        ).scheduleFor(this);
    }

    @Override
    public void attackNearbyAlive() {
        super.attackNearbyAlive();
    }

    @Override
    public void stopAttack() {
        super.stopAttack();
    }
}
