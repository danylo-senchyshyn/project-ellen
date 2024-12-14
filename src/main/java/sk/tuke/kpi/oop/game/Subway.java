package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Point;
import sk.tuke.kpi.oop.game.Logo.LevelCleared;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class Subway extends AbstractActor {
    public enum Status {first, second, third, fourth, fifth, sixth}
    private final Status status;
    private Animation animation;
    private final Map<Status, Point> subwayPositions = new EnumMap<>(Status.class);

    public Subway(Status status) {
        this.status = status;
        setAnimationBasedOnStatus(status);
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        initializeSubwayPositions();

        new Loop<>(new Invoke<>(this::checkAndMovePlayer)).scheduleFor(this);
    }

    private void initializeSubwayPositions() {
        for (Actor actor : Objects.requireNonNull(getScene()).getActors()) {
            if (actor instanceof Subway) {
                Subway subway = (Subway) actor;
                subwayPositions.put(subway.status, new Point(subway.getPosX(), subway.getPosY()));
            }
        }
    }

    private void checkAndMovePlayer() {
        Actor player = getScene().getLastActorByType(Ripley.class);
        if (player == null) return;

        if (isPlayerInBounds(player, subwayPositions.get(Status.first))) {
            teleportPlayer(player, subwayPositions.get(Status.second));
        } else if (isPlayerInBounds(player, subwayPositions.get(Status.third))) {
            teleportPlayer(player, subwayPositions.get(Status.fourth));
            setAnimationBasedOnStatus(Status.fifth);
        } else if (isPlayerInBounds(player, subwayPositions.get(Status.fifth))) {
            teleportPlayer(player, subwayPositions.get(Status.sixth));
        }
    }

    private void teleportPlayer(Actor player, Point target) {
        if (target == null) return;

        player.setPosition(target.getX() + 16, target.getY() - 16);
    }

    private boolean isPlayerInBounds(Actor player, Point position) {
        if (position == null) return false;

        Point playerCenter = new Point(player.getPosX() + player.getWidth() / 2, player.getPosY() + player.getHeight() / 2);

        return playerCenter.getX() >= position.getX() &&
            playerCenter.getX() <= position.getX() + 32 &&
            playerCenter.getY() >= position.getY() &&
            playerCenter.getY() <= position.getY() + 32;
    }

    public void setAnimationBasedOnStatus(Status status) {
        if (status == Status.third || status == Status.fifth) {
            animation = new Animation("maps/alienbreed-sprites/invisible.png");
        } else {
            animation = new Animation("maps/alienbreed-sprites/tunnel_black.png", 32, 32, 0.2f, Animation.PlayMode.LOOP);
        }
        setAnimation(animation);
    }
}
