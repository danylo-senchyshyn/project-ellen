package sk.tuke.kpi.oop.game.openables;


import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Objects;

public class Door extends AbstractActor implements Openable, Usable<Actor> {
    private Animation doorAnimation;
    private boolean isOpened;
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);
    public enum Orientation {HORIZONTAL, VERTICAL}
    private Orientation orientation;

    public Door() {
        doorAnimation = new Animation("sprites/vdoor.png", 16, 32, 0.1f, Animation.PlayMode.ONCE);
        setAnimation(doorAnimation);
        getAnimation().stop();
        isOpened = false;
    }

    public Door(String name, Orientation orientation) {
        super(name);
        setHelpAnimation(orientation);
        this.orientation = orientation;
        setAnimation(doorAnimation);
        doorAnimation.stop();
        isOpened = false;
    }

    @Override
    public void useWith(Actor actor) {
        if (isOpened)
            close();
        else
            open();
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

    @Override
    public void open() {
        isOpened = true;

        Objects.requireNonNull(getScene()).getMap().getTile(getPosX() / 16, getPosY() / 16).setType(MapTile.Type.CLEAR);
        getScene().getMap().getTile(getPosX() / 16, getPosY() / 16 + 1).setType(MapTile.Type.CLEAR);

        doorAnimation.setPlayMode(Animation.PlayMode.ONCE_REVERSED);
        doorAnimation.play();
        doorAnimation.stop();

        getScene().getMessageBus().publish(DOOR_OPENED, this);
    }

    @Override
    public void close() {
        isOpened = false;

        Objects.requireNonNull(getScene()).getMap().getTile(getPosX() / 16, getPosY() / 16).setType(MapTile.Type.WALL);
        getScene().getMap().getTile(getPosX() / 16, getPosY() / 16 + 1).setType(MapTile.Type.WALL);
        doorAnimation.setPlayMode(Animation.PlayMode.ONCE);
        doorAnimation.play();
        doorAnimation.stop();
        getScene().getMessageBus().publish(DOOR_CLOSED, this);
    }

    @Override
    public boolean isOpen() {
        return isOpened;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if (getScene() != null) {
            getScene().getMessageBus().publish(DOOR_CLOSED, this);
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
            getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16 + 1).setType(MapTile.Type.WALL);
        }
    }

    private void setHelpAnimation(Orientation orientation) {
        if (orientation == Orientation.VERTICAL) {
            doorAnimation = new Animation("sprites/vdoor.png", 16, 32, 0.2f);
        } else {
            doorAnimation = new Animation("sprites/hdoor.png", 32, 16, 0.2f);
        }
    }
}
