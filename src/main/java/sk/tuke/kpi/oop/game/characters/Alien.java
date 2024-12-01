package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

import java.util.List;

public class Alien extends AbstractActor implements Movable, Enemy, Alive {
    private Animation alienAnimation;
    private Health health;
    private Disposable alienAttack = null;
    private Behaviour<? super Alien> behavior;

    public Alien() {
        alienAnimation = new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(alienAnimation);
        health = new Health(100, 100);
        health.onFatigued(() -> getScene().removeActor(this));
    }

    public Alien(int healthValue, Behaviour<? super Alien> behaviour) {
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
        health = new Health(healthValue, 100);
        this.behavior = behaviour;
        health.onFatigued(() -> getScene().removeActor(this));
    }

    @Override
    public int getSpeed() {
        return 2;
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        if (behavior != null) {
            behavior.setUp(this);
        }

        alienAttack = new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::attackNearbyAlive),
                new Wait<>(0.3f)
            )).scheduleFor(this);
    }

    public void stopAttack() {
        if (alienAttack != null) {
            alienAttack.dispose();
            alienAttack = null;
        }
    }

    public void attackNearbyAlive() {
        if (getScene() == null) {
            return;
        }

        List<Actor> aliveActorsList;
        aliveActorsList = getScene().getActors();

        for (Actor aliveActor : aliveActorsList) {
            if (aliveActor instanceof Alive && !(aliveActor instanceof Enemy) && this.intersects(aliveActor)) {
                ((Alive) aliveActor).getHealth().drain(3);
                new ActionSequence<>(
                    new Invoke<>(this::stopAttack),
                    new Wait<>(1),
                    new Invoke<>(this::attackNearbyAlive)
                ).scheduleFor(this);
            }
        }
    }
}
