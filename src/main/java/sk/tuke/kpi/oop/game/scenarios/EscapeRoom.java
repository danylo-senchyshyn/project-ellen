package sk.tuke.kpi.oop.game.scenarios;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.SpawnPoint;
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

public class EscapeRoom implements SceneListener {

    public static class Factory implements ActorFactory {
        @Override
        public Actor create(String type, String name) {
            assert name != null;

            if (name.contains("door") && type.equals("vertical")) {
                return new Door(name, Door.Orientation.VERTICAL);
            } else if (name.contains("door") && type.equals("horizontal")) {
                return new Door(name, Door.Orientation.HORIZONTAL);
            }

            switch (name) {
                case "ellen":
                    return new Ripley();
                case "energy":
                    return new Energy();
                case "access card":
                    return new AccessCard();
                //case "locker":
                    //return new Locker();
                case "ventilator":
                    return new Ventilator();
                case "alien":
                    return new Alien(100, new RandomlyMoving());
                case "alien mother":
                    return new MotherAlien(new RandomlyMoving());
                case "ammo":
                    return new Ammo();
                    case "spawn point":
                        return new SpawnPoint(10);
                default:
                    return null;
            }
        }
    }

    @Override
    public void sceneCreated(Scene scene) {
    }

    @Override
    public void sceneInitialized(Scene scene) {
        Ripley ellen = scene.getFirstActorByType(Ripley.class);
        scene.follow(ellen);

        Disposable movableCon = scene.getInput().registerListener(new MovableController(ellen));
        Disposable keeperCon = scene.getInput().registerListener(new KeeperController(ellen));
        Disposable shooterCon = scene.getInput().registerListener(new ShooterController(ellen));

        SpawnPoint spawnPoint = new SpawnPoint(10);
        scene.addActor(spawnPoint, 400, 350);

        FireExtinguisher fireExtinguisher = new FireExtinguisher();
        AccessCard accessCard = new AccessCard();
        Hammer hammer = new Hammer();

        ellen.getBackpack().add(accessCard);
        ellen.getBackpack().add(fireExtinguisher);
        ellen.getBackpack().add(hammer);
        scene.getGame().pushActorContainer(ellen.getBackpack());

        scene.getMessageBus().subscribe(Door.DOOR_OPENED, (Ripley) -> ellen.decreaseEnergy());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> movableCon.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> keeperCon.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> shooterCon.dispose());
        scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED, (Ripley) -> ellen.stopDecreasingEnergy().dispose());
    }

    @Override
    public void sceneUpdating(Scene scene) {
        Ripley ellen = scene.getFirstActorByType(Ripley.class);
        assert ellen != null;
        ellen.showRipleyState();
    }
}
