package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.actions.Fire;
import sk.tuke.kpi.oop.game.characters.Armed;

public class ShooterController implements KeyboardListener {
    private Armed armed;

    public ShooterController(Armed armed) {
        this.armed = armed;
    }

    @Override
    public void keyPressed(Input.Key key) {
        if (armed == null || armed.getFirearm() == null) {
            return;
        }

        if (key == Input.Key.SPACE) {
            Fire<Armed> fire = new Fire<>();
            fire.setActor(armed);
            fire.scheduleFor(armed);
        }
    }
}

