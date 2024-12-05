package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class SpawnPoint extends AbstractActor {
    private int remainingEnemies;
    private final Animation birthplaceAnimation;

    public SpawnPoint(int maxEnemies) {
        remainingEnemies = maxEnemies;
        birthplaceAnimation = new Animation("sprites/spawn.png", 32, 32, 0.1f);
        setAnimation(birthplaceAnimation);
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        new Loop<>(
            new When<>(
                () -> remainingEnemies > 0,
                new ActionSequence<>(
                    new Invoke<>(this::trySpawnEnemy),
                    new Wait<>(3)
                )
            )
        ).scheduleFor(this);
    }

    private void trySpawnEnemy() {
        if (remainingEnemies > 0 && isRipleyNear()) {
            spawnEnemy();
        }
    }

    private void spawnEnemy() {
        Scene scene = getScene();
        if (scene == null || remainingEnemies <= 0) return;

        Alien alien = new Alien(100, new RandomlyMoving());
        scene.addActor(alien, getPosX() + getWidth() / 2, getPosY() + getHeight() / 2);

        remainingEnemies--;
    }

    private boolean isRipleyNear() {
        Actor ripley = getScene().getFirstActorByType(Ripley.class);
        if (ripley == null) return false;

        return this.intersects(ripley) || distanceTo(ripley) <= 50;
    }

    private double distanceTo(Actor actor) {
        int dx = actor.getPosX() + actor.getWidth() / 2 - getPosX() - getWidth() / 2;
        int dy = actor.getPosY() + actor.getHeight() / 2 - getPosY() - getHeight() / 2;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void dispose() {
        remainingEnemies = 0;
        if (getScene() != null) {
            getScene().cancelActions(this);
        }
    }
}
