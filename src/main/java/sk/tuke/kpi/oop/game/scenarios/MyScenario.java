package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.SpawnPoint;
import sk.tuke.kpi.oop.game.Teleport;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.MotherAlien;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.*;
import sk.tuke.kpi.oop.game.openables.Door;

public class MyScenario implements SceneListener {
    public static class Factory implements ActorFactory {
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            assert name != null;
            assert type != null;

            Teleport teleport = new Teleport(null);
            Teleport teleport1 = new Teleport(teleport);
            teleport.setDestination(teleport1);

            if (name.contains("door") && type.equals("vertical")) {
                return new Door(Door.Orientation.VERTICAL);
            } else if (name.contains("door") && type.equals("horizontal")) {
                return new Door(Door.Orientation.HORIZONTAL);
            } else if (name.equals("ellen")) {
                return new Ripley();
            } else if (name.equals("energy")) {
                return new Energy();
            } else if (name.equals("access card")) {
                return new AccessCard();
            } else if (name.equals("locker")) {
                return new Locker();
            } else if (name.equals("ventilator")) {
                return new Ventilator();
            } else if (name.equals("alien")) {
                return new Alien(100, new RandomlyMoving());
            } else if (name.equals("alien mother")) {
                return new MotherAlien(new RandomlyMoving());
            } else if (name.equals("ammo")) {
                return new Ammo();
            } else if (name.equals("spawn point")) {
                return new SpawnPoint(1);
            } else if (name.equals("teleport")) {
                return teleport;
            } else if (name.equals("teleport1")) {
                return teleport1;
            }

            else {
                return null;
            }
        }
    }

    @Override
    public void sceneCreated(Scene scene) {
    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        Ripley ellen = scene.getFirstActorByType(Ripley.class);
        scene.follow(ellen);

        Disposable movableCon = scene.getInput().registerListener(new MovableController(ellen));
        Disposable keeperCon = scene.getInput().registerListener(new KeeperController(ellen));
        Disposable shooterCon = scene.getInput().registerListener(new ShooterController(ellen));

        scene.getGame().pushActorContainer(ellen.getBackpack());

        scene.getMessageBus().subscribe(Door.DOOR_OPENED, (Ripley) -> ellen.decreaseEnergy());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> movableCon.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> keeperCon.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> shooterCon.dispose());
        scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED, (Ripley) -> ellen.stopDecreasingEnergy().dispose());
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        Ripley ellen = scene.getFirstActorByType(Ripley.class);
        assert ellen != null;
        ellen.showRipleyState();
    }
}
