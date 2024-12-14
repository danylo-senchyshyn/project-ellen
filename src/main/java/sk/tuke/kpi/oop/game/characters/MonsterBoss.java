package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class MonsterBoss extends Alien {
    public static final Topic<MonsterBoss> MONSTER_BOSS_DEAD = Topic.create("monster boss dead", MonsterBoss.class);

    public MonsterBoss(int healthValue, Behaviour<? super Alien> behaviour) {
        super(healthValue, behaviour);
        setAnimation(new Animation("maps/alienbreed-sprites/monster_2.png", 78, 127, 0.1f));
    }

    public void checkIsAlive() {
        if (this.getHealth().getValue() <= 0) {
            getScene().getMessageBus().publish(MONSTER_BOSS_DEAD, this);
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
