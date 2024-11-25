package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Usable;

public class KeeperController implements KeyboardListener {
    private Keeper keeper;

    public KeeperController(Keeper keeper) {
        this.keeper = keeper;
    }

    @Override
    public void keyPressed(Input.Key key) {
        if (key == Input.Key.ENTER)
            new Take<>(keeper).scheduleFor(keeper);

        if (key == Input.Key.BACKSPACE)
            new Drop<>(keeper).scheduleFor(keeper);

        if (key == Input.Key.S)
            new Shift<>().scheduleFor(keeper);

        if (key == Input.Key.U)
            useAction();

        if (key == Input.Key.B)
            useAction2();
    }

    private void useAction() {
        Usable<?> usable = null;
        for (Actor actor : keeper.getScene().getActors()) {
            if (Usable.class.isInstance(actor) && keeper.intersects(actor)) {
                usable = Usable.class.cast(actor);
                break;
            }
        }

        if (usable != null) {
            new Use<>(usable).scheduleForIntersectingWith(keeper);
        }
    }

    private void useAction2() {
        if (Usable.class.isInstance(keeper.getBackpack().peek())) {
            Use<?> use = new Use<>( (Usable<?>) keeper.getBackpack().peek());
            use.scheduleForIntersectingWith(keeper);
        }
    }
}
