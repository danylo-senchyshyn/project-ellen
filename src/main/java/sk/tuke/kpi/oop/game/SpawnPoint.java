package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.FollowActor;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class SpawnPoint extends AbstractActor {
    private int remainingEnemies;

    public SpawnPoint(int maxEnemies) {
        remainingEnemies = maxEnemies;
        setAnimation(new Animation("sprites/spawn.png", 32, 32, 0.1f));
        //Animation birthplaceAnimation = new Animation("maps/alienbreed-sprites/hole.png", 32, 32, 0.3f, Animation.PlayMode.ONCE);
        //setAnimation(birthplaceAnimation);
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        new Loop<>(
            new When<>(
                this::checkDistance,
                new ActionSequence<>(
                    new Invoke<>(this::spawnEnemy),
                    new Wait<>(3)
                )
            )
        ).scheduleFor(this);
    }

    private void spawnEnemy() {
        Scene scene = getScene();

        if (scene == null) {
            return;
        } else if (remainingEnemies <= 0) {
            dispose();
            return;
        } else {
            Alien alien = new Alien(100, new FollowActor());
            //Alien alien = new Alien(100, new RandomlyMoving());
            scene.addActor(alien, getPosX() + getWidth() / 2, getPosY() + getHeight() / 2);
            remainingEnemies--;
        }
    }

    public void dispose() {
        remainingEnemies = 0;
        if (getScene() != null) {
            getScene().cancelActions(this);
        }
    }

    private double distanceTo(Actor actor) {
        int dx = actor.getPosX() + getWidth() / 2 - getPosX() - actor.getWidth() / 2;
        int dy = actor.getPosY() + getHeight() / 2 - getPosY() - actor.getHeight() / 2;

        return Math.sqrt(dx * dx + dy * dy);
    }

    private boolean checkDistance() {
        Scene scene = getScene();
        Actor ripley = scene.getFirstActorByType(Ripley.class);

        if (scene == null || ripley == null) return false;

        return this.intersects(ripley) || distanceTo(ripley) <= 50;
    }
}


//package sk.tuke.kpi.oop.game;
//
//import org.jetbrains.annotations.NotNull;
//import sk.tuke.kpi.gamelib.Actor;
//import sk.tuke.kpi.gamelib.Scene;
//import sk.tuke.kpi.gamelib.actions.ActionSequence;
//import sk.tuke.kpi.gamelib.actions.Invoke;
//import sk.tuke.kpi.gamelib.actions.Wait;
//import sk.tuke.kpi.gamelib.actions.When;
//import sk.tuke.kpi.gamelib.framework.AbstractActor;
//import sk.tuke.kpi.gamelib.framework.actions.Loop;
//import sk.tuke.kpi.gamelib.graphics.Animation;
//import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
//import sk.tuke.kpi.oop.game.characters.Alien;
//import sk.tuke.kpi.oop.game.characters.Ripley;
//
//import java.util.Objects;
//
//
//public class SpawnPoint extends AbstractActor {
//    private int remainingCount;
//
//    public SpawnPoint(int count) {
//        this.remainingCount = count;
//        setAnimation(new Animation("sprites/spawn.png", 32, 32, 0.1f));
//    }
//
//    @Override
//    public void addedToScene(@NotNull Scene scene) {
//        super.addedToScene(scene);
//        new Loop<>(
//            new When<>(
//                this::checkDistance,
//                new ActionSequence<>(
//                    new Invoke<>(this::spawnAlien),
//                    new Wait<>(3)
//                )
//            )
//        ).scheduleFor(this);
//    }
//
//    private boolean checkDistance() {
//        Scene scene = getScene();
//        if (scene == null) return false;
//
//        Actor ripley = scene.getFirstActorByType(Ripley.class);
//        if (ripley == null) return false;
//
//        return this.intersects(ripley) || distanceTo(ripley) <= 50;
//    }
//
//    private void spawnAlien() {
//        if (remainingCount <= 0) return;
//
//        Scene scene = getScene();
//        if (scene == null) return;
//
//        Alien alien = new Alien(100,new RandomlyMoving());
//        scene.addActor(alien, getPosX() + getWidth()/2, getPosY() + getHeight()/2);
//
//        remainingCount--;
//    }
//
//
//    public void dispose() {
//        remainingCount = 0;
//        Objects.requireNonNull(getScene()).cancelActions(this);
//    }
//
//    private double distanceTo(Actor actor) {
//        int dx = actor.getPosX() + getWidth()/2 - getPosX() - actor.getWidth()/2;
//        int dy = actor.getPosY() + getHeight()/2 - getPosY() - actor.getHeight()/2;
//        return Math.sqrt(dx * dx + dy * dy);
//    }
//}
