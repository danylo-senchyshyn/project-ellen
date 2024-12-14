package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;


public class DoorStrong extends Door {
    public static final Topic<DoorStrong> DOOR_STRONG_OPENED = Topic.create("door strong opened", DoorStrong.class);
    public static final Topic<DoorStrong> DOOR_STRONG_CLOSED = Topic.create("door strong closed", DoorStrong.class);
    public enum DoorStrongNumber {first, second, third}
    private DoorStrongNumber number;
    private Animation animation;

    public DoorStrong() {
        this(DoorStrongNumber.first);
    }
    public DoorStrong(DoorStrongNumber number) {
        super();
        this.number = number;
        animation = new Animation("maps/alienbreed-sprites/vdoor_strong.png", 16, 32);
        setAnimation(animation);
        helpAnimation();
    }

    private void helpAnimation() {
        if (this.number == DoorStrongNumber.first) {
            animation.setFrameDuration(0.1f);
        } else if (this.number == DoorStrongNumber.second) {
            animation.setFrameDuration(0.2f);
        } else if (this.number == DoorStrongNumber.third) {
            animation.setFrameDuration(0.3f);
        }
        animation.stop();
    }

    @Override
    public void open() {
        super.open();
        getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16 + 1).setType(MapTile.Type.CLEAR);

        animation.setPlayMode(Animation.PlayMode.ONCE);
        animation.play();
        setAnimation(animation);
        getScene().getMessageBus().publish(DOOR_STRONG_OPENED, this);


    }

    @Override
    public void close() {
        super.close();
        getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16 + 1).setType(MapTile.Type.WALL);

        animation.setPlayMode(Animation.PlayMode.ONCE_REVERSED);
        animation.play();
        setAnimation(animation);
        getScene().getMessageBus().publish(DOOR_STRONG_CLOSED, this);
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        getScene().getMessageBus().publish(DOOR_STRONG_CLOSED, this);
        getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
        getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16 + 1).setType(MapTile.Type.WALL);
    }
}
