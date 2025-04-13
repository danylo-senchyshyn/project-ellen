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
import sk.tuke.kpi.gamelib.graphics.Point;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;

import java.util.List;
import java.util.Random;

public class Alien extends AbstractActor implements Movable, Enemy, Alive {
    private Health health;
    private Disposable alienAttack = null;
    private Behaviour<? super Alien> behavior;
    public static final Topic<Point> ALIEN_DEAD = Topic.create("alien dead", Point.class);

    public Alien() {
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
        health = new Health(1000, 1000);
        health.onFatigued(() -> {
            spawnItemFromAlien();
            getScene().getMessageBus().publish(ALIEN_DEAD, new Point(this.getPosX(), this.getPosY()));
            getScene().removeActor(this);
        });
    }
    public Alien(int healthValue, Behaviour<? super Alien> behaviour) {
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
        health = new Health(healthValue, 1000);
        this.behavior = behaviour;
        health.onFatigued(() -> {
            getScene().getMessageBus().publish(ALIEN_DEAD, new Point(this.getPosX(), this.getPosY()));
            getScene().removeActor(this);
        });
    }

    public void spawnItemFromAlien() {
        for (Actor actor : getScene().getActors()) {
            if (actor instanceof Alien) {
                Ammo ammo = new Ammo();
                Energy energy = new Energy();
                Point pointAlien = new Point(actor.getPosX(), actor.getPosY());

                Random random = new Random();
                if (random.nextBoolean()) {
                    if (random.nextBoolean()) {
                        getScene().addActor(ammo, pointAlien.getX(), 960 - pointAlien.getY() - 32);
                    } else {
                        getScene().addActor(energy, pointAlien.getX(), 960 - pointAlien.getY() - 32);
                    }
                }
            }
        }
    }

    @Override
    public int getSpeed() {
        return 1;
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
