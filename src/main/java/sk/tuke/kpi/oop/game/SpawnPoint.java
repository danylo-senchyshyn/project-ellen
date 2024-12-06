package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class SpawnPoint extends AbstractActor {
    private int remainingEnemies;

    public SpawnPoint(int maxEnemies) {
        remainingEnemies = maxEnemies;
        Animation birthplaceAnimation = new Animation("sprites/spawn.png", 32, 32, 0.1f);
        setAnimation(birthplaceAnimation);
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::spawnEnemy),
                new Wait<>(3)
            )
        ).scheduleFor(this);
    }

    private void spawnEnemy() {
        Scene scene = getScene();
        Actor ripley = getScene().getFirstActorByType(Ripley.class);

        if (scene == null || remainingEnemies <= 0 || !isRipleyNear(ripley)) return;

        Alien alien = new Alien(100, new RandomlyMoving());
        scene.addActor(alien, getPosX() + getWidth() / 2, getPosY() + getHeight() / 2);

        remainingEnemies--;

        //System.out.println("spawn" + LocalDateTime.now());
    }

    private boolean isRipleyNear(Actor actor) {
        Actor ripley = getScene().getFirstActorByType(Ripley.class);
        if (ripley == null) return false;

        int dx = actor.getPosX() + actor.getWidth() / 2 - getPosX() - getWidth() / 2;
        int dy = actor.getPosY() + actor.getHeight() / 2 - getPosY() - getHeight() / 2;
        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance <= 50;
    }

    public void dispose() {
        remainingEnemies = 0;
        if (getScene() != null) {
            getScene().cancelActions(this);
        }
    }
}
