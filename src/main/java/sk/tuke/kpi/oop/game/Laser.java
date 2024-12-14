package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Laser extends AbstractActor {
    private Disposable laserDamage = null;

    public Laser() {
        Animation animation = new Animation("maps/alienbreed-sprites/laser.png", 16, 48, 0.1f, Animation.PlayMode.LOOP);
        setAnimation(animation);
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        new Loop<>(new Invoke<>(this::damage)).scheduleFor(this);
    }

    public void damage() {
        Ripley ripley = getScene().getFirstActorByType(Ripley.class);

        if (isIntersects(ripley, this) && ripley.getHealth().getValue() > 0) {
            ripley.getHealth().drain(0.3f);
            new ActionSequence<>(
                new Invoke<>(this::stopDamage),
                new Wait<>(1),
                new Invoke<>(this::damage)
            ).scheduleFor(this);
        }
    }

    public boolean isIntersects(Actor player, Actor place) {
        int playerLeft = player.getPosX();
        int playerRight = player.getPosX() + player.getWidth();
        int playerTop = player.getPosY();
        int playerBottom = player.getPosY() + player.getHeight();

        int placeLeft = place.getPosX();
        int placeRight = place.getPosX() + place.getWidth();
        int placeTop = place.getPosY();
        int placeBottom = place.getPosY() + place.getHeight();

        boolean isInside = playerLeft >= placeLeft && playerRight <= placeRight &&
            playerTop >= placeTop && playerBottom <= placeBottom;

        boolean touchesLeftEdge = playerRight == placeLeft && playerBottom > placeTop && playerTop < placeBottom;
        boolean touchesRightEdge = playerLeft == placeRight && playerBottom > placeTop && playerTop < placeBottom;
        boolean touchesTopEdge = playerBottom == placeTop && playerRight > placeLeft && playerLeft < placeRight;
        boolean touchesBottomEdge = playerTop == placeBottom && playerRight > placeLeft && playerLeft < placeRight;

        boolean isTouching = touchesLeftEdge || touchesRightEdge || touchesTopEdge || touchesBottomEdge;

        return isInside || isTouching;
    }

    public void stopDamage() {
        if (laserDamage != null) {
            laserDamage.dispose();
            laserDamage = null;
        }
    }
}
