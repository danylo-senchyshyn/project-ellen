package sk.tuke.kpi.oop.game.scenarios;

import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.*;

public class FirstSteps implements SceneListener {
    private Ripley ripley;
    private Energy energy;
    private MovableController movableController;
    private Ammo ammo;
    Hammer hammer = new Hammer();
    Wrench wrench = new Wrench();
    FireExtinguisher fireExtinguisher = new FireExtinguisher();
    private KeeperController keeperController;

    @Override
    public void sceneInitialized(Scene scene) {
        ripley = new Ripley();
        scene.addActor(ripley, 0, 0);
//        ripley.setEnergy(50);
//        ripley.setAmmo(200);


        movableController = new MovableController(ripley);
        scene.getInput().registerListener(movableController);


        energy = new Energy();
        scene.addActor(energy, -100, 50);
        new When<>(
            () -> ripley.intersects(energy),
            new Invoke<>(() -> energy.useWith(ripley))
        ).scheduleFor(ripley);


        ammo = new Ammo();
        scene.addActor(ammo, -200, 50);
        new When<>(
            () -> ripley.intersects(ammo),
            new Invoke<>(() -> ammo.useWith(ripley))
        ).scheduleFor(ripley);


//        scene.addActor(hammer, 100, -50);
//        scene.addActor(fireExtinguisher, 120, 40);
//        scene.addActor(wrench, -150, 200);


//        ripley.getBackpack().add(hammer);
//        ripley.getBackpack().add(fireExtinguisher);
//        ripley.getBackpack().add(wrench);


//        scene.getGame().pushActorContainer(ripley.getBackpack());
//        ripley.getBackpack().shift();


        keeperController = new KeeperController(ripley);
        scene.getInput().registerListener(keeperController);
    }

    @Override
    public void sceneUpdating(Scene scene) {
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        scene.getGame().getOverlay().drawText("Energy: " + ripley.getEnergy(), 120, yTextPos);
        scene.getGame().getOverlay().drawText("Ammo: " + ripley.getAmmo(), 320, yTextPos);
    }
}
