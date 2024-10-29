package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.awt.*;

public class Teleport extends AbstractActor {
    private Teleport destination;

    public Teleport(Teleport destination) {
        this.destination = destination;
        Animation teleportAnimation = new Animation("sprites/lift.png");
        setAnimation(teleportAnimation);
    }

    public Teleport getDestination() {
        return destination;
    }

    public void setDestination(Teleport destinationTeleport) {
        if (this != destinationTeleport) {
            this.destination = destinationTeleport;
        }
    }

    public void teleportPlayer(Player player) {
        if (this.destination == null || getScene() == null || player == null) return;

        Point pointPlayer = new Point(
            (player.getPosX() + player.getWidth() / 2),
            (player.getPosY() + player.getHeight() / 2)
        );

        Point pointTeleport = new Point(
            (this.getPosX() + this.getWidth() / 2),
            (this.getPosY() + this.getHeight() / 2)
        );

        if (pointTeleport.equals(pointPlayer)) {
            player.setPosition(
                this.getPosX() + (this.getWidth() / 2) - (player.getWidth() / 2),
                this.getPosY() + (this.getHeight() / 2) - (player.getHeight() / 2)
            );
        } else {
            return;
        }
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::teleportPlayer)).scheduleFor(getScene().getFirstActorByType(Player.class));
    }
}
