package sk.tuke.kpi.oop.game.scenarios;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.items.FireExtinguisher;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class MissionImpossible implements SceneListener {

    public static class Factory implements ActorFactory {
        @Override
        public Actor create(String type, String name) {
            assert name != null;

            switch (name) {
                case "access card": return new AccessCard();
                case "door": return new LockedDoor();
                case "ellen": return new Ripley();
                case "energy": return new Energy();
                case "locker": return new Locker();
                case "ventilator": return new Ventilator();
                default: return null;
            }
        }
    }

    @Override
    public void sceneInitialized(Scene scene) {
        Ripley ellen = scene.getFirstActorByType(Ripley.class);
        scene.follow(ellen);

        Disposable movableCon = scene.getInput().registerListener(new MovableController(ellen));
        Disposable keeperCon = scene.getInput().registerListener(new KeeperController(ellen));

        FireExtinguisher fireExtinguisher= new FireExtinguisher();
        ellen.getBackpack().add(fireExtinguisher);
        scene.getGame().pushActorContainer(ellen.getBackpack());

        AccessCard accessCard = new AccessCard();
        Hammer hammer = new Hammer();
        ellen.getBackpack().add(accessCard);
        ellen.getBackpack().add(hammer);

        scene.getMessageBus().subscribe(Door.DOOR_OPENED, (Ripley) -> ellen.decreaseEnergy());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> movableCon.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> keeperCon.dispose());
        scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED, (Ripley) -> ellen.stopDecresingEnergy().dispose());
    }

    @Override
    public void sceneUpdating(Scene scene) {
        Ripley ellen = scene.getFirstActorByType(Ripley.class);
        assert ellen != null;
        ellen.showRipleyState();
    }
}
