package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;

public class LockedDoor extends Door {
    private boolean isLocked;

    public LockedDoor() {
        super();
        isLocked = true;
    }

    public LockedDoor(String name, Orientation orientation){
        super(name,orientation);
        isLocked = true;
    }

    public void lock() {
        isLocked = true;
        this.close();
    }

    public void unlock() {
        isLocked = false;
        this.open();
    }

    public boolean isLocked() {
        return isLocked;
    }

    @Override
    public void useWith(Actor actor) {
        if (!isLocked) {
            super.useWith(actor);
        }
    }
}
