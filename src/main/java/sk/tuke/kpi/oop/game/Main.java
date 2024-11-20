package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl2.Lwjgl2Backend;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.gamelib.inspector.InspectableScene;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.scenarios.FirstSteps;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);
        Game game = new GameApplication(windowSetup, new Lwjgl2Backend());

        Scene scene = new World("world");
        FirstSteps firstSteps = new FirstSteps();

        scene.addListener(firstSteps);
        game.addScene(scene);
        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
        game.start();
    }
}
