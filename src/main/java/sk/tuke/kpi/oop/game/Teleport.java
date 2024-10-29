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
    private boolean playerTeleported;

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

    /*
    public void teleportPlayer(Player player) {
        if (this.destination == null || getScene() == null) {
            return;
        }

        Point pointPlayer = new Point(  (player.getPosX() + player.getWidth() / 2), (player.getPosY() + player.getHeight() / 2) );
        Point pointTeleport = new Point((this.getPosX() + this.getWidth() / 2),     (this.getPosY() + this.getHeight() / 2) );

        if (pointTeleport.equals(pointPlayer)) {
            player.setPosition(this.getPosX() + (this.getWidth() / 2) - (player.getWidth() / 2), this.getPosY() + (this.getHeight() / 2) - (player.getHeight() / 2));
        } else return;
    }
     */

    private boolean isPlayerInZone(Player player) {
        Rectangle teleportBounds = new Rectangle(
            this.getPosX(),
            this.getPosY(),
            getAnimation().getWidth(),
            getAnimation().getHeight()
        );

        Rectangle playerBounds = new Rectangle(
            player.getPosX(),
            player.getPosY(),
            player.getWidth(),
            player.getHeight()
        );

        return teleportBounds.intersects(playerBounds);
    }

    public void teleportPlayer(Player player) {
        if (this.destination == null || getScene() == null || player == null) {
            return;
        }

        if (isPlayerInZone(player) && !playerTeleported) {
            int targetX = this.destination.getPosX() + (this.destination.getWidth() - player.getWidth()) / 2;
            int targetY = this.destination.getPosY() + (this.destination.getHeight() - player.getHeight()) / 2;
            player.setPosition(targetX, targetY);

            this.playerTeleported = true;
            this.destination.playerTeleported = true;
        } else if (!isPlayerInZone(player)) {
            playerTeleported = false;
        }
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::teleportPlayer)).scheduleFor(getScene().getFirstActorByType(Player.class));
    }
}
