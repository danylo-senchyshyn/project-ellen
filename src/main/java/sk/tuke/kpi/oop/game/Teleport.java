package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Teleport extends AbstractActor {
    private Teleport destination;
    private boolean canTeleport = true;

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
        if (player != null) {
            player.setPosition(
                this.getPosX() + (this.getWidth() / 2) - (player.getWidth() / 2),
                this.getPosY() + (this.getHeight() / 2) - (player.getHeight() / 2)
            );
            canTeleport = false;
        }
    }

    public void resetTeleportFlag() {
        Player player = getScene().getLastActorByType(Player.class);
        if (!canTeleport && isPlayerOutside(player)) {
            canTeleport = true;
        }
    }

    private boolean isPlayerOutside(Player player) {
        return (player.getPosX() + player.getWidth() < this.getPosX()) ||
            (player.getPosX() > this.getPosX() + this.getWidth()) ||
            (player.getPosY() + player.getHeight() < this.getPosY()) ||
            (player.getPosY() > this.getPosY() + this.getHeight());
    }

    public void attemptTeleport() {
        Player player = getScene().getLastActorByType(Player.class);
        int playerCenterX = player.getPosX() + player.getWidth() / 2;
        int playerCenterY = player.getPosY() + player.getHeight() / 2;

        boolean isPlayerInBounds =
            (playerCenterX >= this.getPosX()) &&
                (playerCenterX <= this.getPosX() + this.getWidth()) &&
                (playerCenterY >= this.getPosY()) &&
                (playerCenterY <= this.getPosY() + this.getHeight());

        if (canTeleport && !isPlayerOutside(player) && this.destination != null && isPlayerInBounds) {
            destination.teleportPlayer(player);
        }
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        Player player = getScene().getLastActorByType(Player.class);

        new Loop<>(new Invoke<>(this::attemptTeleport)).scheduleFor(player);
        new Loop<>(new Invoke<>(this::resetTeleportFlag)).scheduleFor(player);
    }
}
