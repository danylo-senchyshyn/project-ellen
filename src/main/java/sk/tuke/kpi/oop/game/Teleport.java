package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.awt.geom.Rectangle2D;

public class Teleport extends AbstractActor {
    private boolean isPlayerInside;
    private final Animation teleportAnimation;
    private Teleport destinationTeleport;

    public Teleport() {
        isPlayerInside = false;
        teleportAnimation = new Animation("sprites/lift.png");
        setAnimation(teleportAnimation);
    }

    public Teleport getDestination() {
        return destinationTeleport;
    }

    public void setDestination(Teleport destinationTeleport) {
        this.destinationTeleport = destinationTeleport;
    }

    public void teleportPlayer(Player player) {
        if (destinationTeleport != null && destinationTeleport != this) {
            float centerX = destinationTeleport.getPosX() + destinationTeleport.getWidth() / 2;
            float centerY = destinationTeleport.getPosY() + destinationTeleport.getHeight() / 2;

            player.setPosition((int) (centerX - player.getWidth() / 2), (int) (centerY - player.getHeight() / 2));
        }
    }

    public void act() {
        if (isPlayerInside()) {
            if (isPlayerInside) {
                return;
            }

            isPlayerInside = true;

            Player player = getScene().getFirstActorByType(Player.class);
            if (player != null) {
                teleportPlayer(player);
            }
        } else {
            isPlayerInside = false;
        }
    }

    private boolean isPlayerInside() {
        Player player = getScene().getFirstActorByType(Player.class);
        if (player == null) return false;

        Rectangle2D.Float playerBounds = new Rectangle2D.Float(
            player.getPosX(),
            player.getPosY(),
            player.getWidth(),
            player.getHeight()
        );

        Rectangle2D.Float teleportBounds = new Rectangle2D.Float(
            this.getPosX(),
            this.getPosY(),
            this.getWidth(),
            this.getHeight()
        );

        return teleportBounds.intersects(playerBounds);
    }
}
