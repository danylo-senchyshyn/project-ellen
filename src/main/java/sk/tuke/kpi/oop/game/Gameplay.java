package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.gamelib.map.MapMarker;
import sk.tuke.kpi.oop.game.tools.Hammer;

import java.util.Map;

public class Gameplay extends Scenario {

    @Override
    public void setupPlay(Scene scene) {
        Map<String, MapMarker> markers = scene.getMap().getMarkers();
        MapMarker reactorArea1 = markers.get("reactor-area-1");
        MapMarker coolerArea1 = markers.get("cooler-area-1");
        MapMarker computerArea = markers.get("computer-area");

        Reactor reactor = new Reactor();
        scene.addActor(reactor, reactorArea1.getPosX(), reactorArea1.getPosY());
        reactor.turnOn();

        Cooler cooler = new Cooler(reactor);
        scene.addActor(cooler, coolerArea1.getPosX(), coolerArea1.getPosY());
        cooler.turnOff();

        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(cooler::turnOn)
        ).scheduleFor(cooler);

        Hammer hammer = new Hammer();
        scene.addActor(hammer, 200, 45);

        new When<>(
            () -> reactor.getTemperature() >= 3000,
            new Invoke<>(() -> hammer.useWith(reactor))
        ).scheduleFor(reactor);

        // Computer
        Computer computer = new Computer();
        scene.addActor(computer, computerArea.getPosX(), computerArea.getPosY());

        // Teleports
        Teleport teleport3 = new Teleport(null);
        scene.addActor(teleport3, 22, 350);

        Teleport teleport2 = new Teleport(teleport3);
        scene.addActor(teleport2, 22, 200);

        Teleport teleport1 = new Teleport(teleport2);
        scene.addActor(teleport1, 22, 30);
    }
}
