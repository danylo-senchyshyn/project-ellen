package sk.tuke.kpi.oop.game.SpawnPoint;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Enemy;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Birthplace extends AbstractActor {
    private int remainingEnemies;
    private Disposable spawningAction;
    private final Animation birthplaceAnimation;

    public Birthplace(int maxEnemies) {
        remainingEnemies = maxEnemies;
        birthplaceAnimation = new Animation("sprites/spawn.png", 32, 32);
        setAnimation(birthplaceAnimation);
    }


    @Override
    public void addedToScene(sk.tuke.kpi.gamelib.Scene scene) {
        super.addedToScene(scene);
        restartSpawning();
    }

    private boolean isRipleyNear() {
        Actor ripley = getScene().getFirstActorByType(Ripley.class);
        if (ripley == null) return false;

        float distance = calculateDistance(this.getPosX(), this.getPosY(), ripley.getPosX(), ripley.getPosY());
        return distance <= 50;
    }

    private float calculateDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private void trySpawnEnemy() {
        if (remainingEnemies > 0 && isRipleyNear()) {
            spawnEnemy();
        }
    }

    private void spawnEnemy() {
        if (remainingEnemies > 0) {
            Enemy enemy = new Alien();
            enemy.setPosition(this.getPosX(), this.getPosY());
            getScene().addActor(enemy);
            remainingEnemies--;

            if (remainingEnemies == 0) {
                dispose();
            } else {
                pauseSpawning();
            }
        }
    }

    private void pauseSpawning() {
        if (spawningAction != null) {
            spawningAction.dispose();
            spawningAction = null;

            new Wait<>(3).scheduleFor(this);
        }
    }

    private void restartSpawning() {
        spawningAction = new Loop<>(new Invoke<>(this::trySpawnEnemy)).scheduleFor(this);
    }

    public void dispose() {
        if (spawningAction != null) {
            spawningAction.dispose();
            spawningAction = null;
        }
    }
}
