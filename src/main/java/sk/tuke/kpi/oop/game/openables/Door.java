package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

public class Door extends AbstractActor implements Openable, Usable<Actor> {
    private Animation doorAnimation;
    boolean isOpened;
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);

    public Door() {
        doorAnimation = new Animation("sprites/vdoor.png", 16, 32, 0.1f);
        doorAnimation.stop();
        setAnimation(doorAnimation);
        isOpened = false;
    }

    public void open() {
        if (isOpened) return;
        isOpened = true;

        getScene().getMap().getTile(getPosX() / 16, getPosY() / 16).setType(MapTile.Type.CLEAR);
        getScene().getMap().getTile(getPosX() / 16, getPosY() / 16 + 1).setType(MapTile.Type.CLEAR);
        getAnimation().setPlayMode(Animation.PlayMode.ONCE);
        getAnimation().play();
        setAnimation(doorAnimation);

        getScene().getMessageBus().publish(DOOR_OPENED, this);
    }

    public void close() {
        if (!isOpened) return;
        isOpened = false;

        getScene().getMap().getTile(getPosX() / 16, getPosY() / 16).setType(MapTile.Type.WALL);
        getScene().getMap().getTile(getPosX() / 16, getPosY() / 16 + 1).setType(MapTile.Type.WALL);
        getAnimation().setPlayMode(Animation.PlayMode.ONCE_REVERSED);
        getAnimation().play();
        setAnimation(doorAnimation);

        getScene().getMessageBus().publish(DOOR_CLOSED, this);
    }

    public boolean isOpen(){
        return isOpened;
    }

    @Override
    public void useWith(Actor actor) {
        if (isOpened)
            close();
        else
            open();
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        getScene().getMap().getTile(this.getPosX() /16, this.getPosY() / 16).setType(MapTile.Type.WALL);
        getScene().getMap().getTile(this.getPosX() /16, this.getPosY() / 16+1 ).setType(MapTile.Type.WALL);
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }
}
