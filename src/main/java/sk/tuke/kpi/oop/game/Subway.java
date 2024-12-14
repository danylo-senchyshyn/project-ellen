package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Point;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Subway extends AbstractActor {
    public enum Status {first, second, third, fourth, fifth, sixth}
    private Status status;
    private Animation animation;
    //private LevelCleared levelCleared = new LevelCleared();
    public static final Topic<Subway> TELEPORTED_ON_FOURTH = Topic.create("ripley teleported on last level", Subway.class);

    public Subway(Status status) {
        this.status = status;
        setAn(status);
    }

    public void move() {
        Actor player = getScene().getLastActorByType(Ripley.class);
        if (player == null) {
            return;
        }

        Point firstPoint = null;
        Point secondPoint = null;
        Point thirdPoint = null;
        Point fourthPoint = null;
        Point fifthPoint = null;
        Point sixthPoint = null;

        for (Actor place : getScene().getActors()) {
            if (place instanceof Subway) {
                Subway subway = (Subway) place;
                if (subway.status == Status.first) {
                    firstPoint = new Point(place.getPosX(), 960 - place.getPosY() - place.getHeight());
                }
                if (subway.status == Status.second) {
                    secondPoint = new Point(place.getPosX(), 960 - place.getPosY() - place.getHeight());
                }
                if (subway.status == Status.third) {
                    thirdPoint = new Point(place.getPosX(), 960 - place.getPosY() - place.getHeight());
                }
                if (subway.status == Status.fourth) {
                    fourthPoint = new Point(place.getPosX(), 960 - place.getPosY() - place.getHeight());
                }
                if (subway.status == Status.fifth) {
                    fifthPoint = new Point(place.getPosX(), 960 - place.getPosY() - place.getHeight());
                }
                if (subway.status == Status.sixth) {
                    sixthPoint = new Point(place.getPosX(), 960 - place.getPosY() - place.getHeight());
                }
            }
        }

        if (isPlayerInBounds(player, firstPoint)) {
            animation.setFrameDuration(0.25f);
            animation.setPlayMode(Animation.PlayMode.ONCE_REVERSED);
            animation.stop();
            player.setPosition(
                secondPoint.getX() + 16,
                960 - secondPoint.getY() - 16
            );
//            getScene().addActor(levelCleared, player.getPosX() - levelCleared.getWidth() / 2, player.getPosY() - levelCleared.getHeight() / 2);
//            new ActionSequence<>(
//                new Wait<>(1),
//                new Invoke<>(() -> getScene().removeActor(levelCleared))
//            ).scheduleFor(levelCleared);
        }
        if (isPlayerInBounds(player, thirdPoint)) {
            animation.setFrameDuration(0.25f);
            animation.setPlayMode(Animation.PlayMode.ONCE);
            animation.stop();
            player.setPosition(
                fourthPoint.getX() + 16,
                960 - fourthPoint.getY() - 16
            );
            setAn(Status.fifth);
//            getScene().addActor(levelCleared, player.getPosX() - levelCleared.getWidth() / 2, player.getPosY() - levelCleared.getHeight() / 2);
//            new ActionSequence<>(
//                new Wait<>(1),
//                new Invoke<>(() -> getScene().removeActor(levelCleared))
//            ).scheduleFor(levelCleared);
        }
        if (isPlayerInBounds(player, fifthPoint)) {
            animation.setFrameDuration(0.25f);
            animation.setPlayMode(Animation.PlayMode.ONCE);
            animation.stop();
            player.setPosition(
                sixthPoint.getX() + 16,
                960 - sixthPoint.getY() - 16
            );
//            getScene().addActor(levelCleared, player.getPosX() - levelCleared.getWidth() / 2, player.getPosY() - levelCleared.getHeight() / 2);
//            new ActionSequence<>(
//                new Wait<>(1),
//                new Invoke<>(() -> getScene().removeActor(levelCleared))
//            ).scheduleFor(levelCleared);
        }
    }

    public boolean isPlayerInBounds(Actor player, Point place) {
        Point playerCentre = new Point(player.getPosX() + player.getWidth() / 2, player.getPosY() + player.getHeight() / 2);

        return playerCentre.getX() <= place.getX() + 32  && playerCentre.getX() >= place.getX() &&
               playerCentre.getY() >= 960 - place.getY() - 32 && playerCentre.getY() <= 960 - place.getY();
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        new Loop<>(
            new Invoke<>(this::move)
        ).scheduleFor(this);
    }

    public void setAn(Status status) {
        if (status == Status.third || status == Status.fifth) {
            animation = new Animation("maps/alienbreed-sprites/invisible.png");
        } else {
            animation = new Animation("maps/alienbreed-sprites/tunnel_transparent.png", 32, 32, 0.2f, Animation.PlayMode.LOOP);
        }
        setAnimation(animation);
    }
}
