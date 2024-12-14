package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl2.Lwjgl2Backend;
import sk.tuke.kpi.oop.game.scenarios.EscapeRoom;
import sk.tuke.kpi.oop.game.scenarios.MyScenario;

public class Main {
    public static void main(String[] args) {

        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);
        Game game = new GameApplication(windowSetup, new Lwjgl2Backend());

        Scene scene = new World("My World", "maps/map.tmx", new MyScenario.Factory());
        //Scene scene = new World("My World", "maps/escape-room.tmx", new EscapeRoom.Factory());
        MyScenario myScenario = new MyScenario();
        //EscapeRoom escapeRoom = new EscapeRoom();

        game.addScene(scene);
        //scene.addListener(escapeRoom);
        scene.addListener(myScenario);
        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
        game.start();
    }
}
