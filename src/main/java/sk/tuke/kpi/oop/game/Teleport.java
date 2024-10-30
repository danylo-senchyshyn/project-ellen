package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Point;

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
        if (this.destination == null || getScene() == null || player == null)
            return;

        Point pointPlayerCentre = new Point(
            (player.getPosX() + player.getWidth() / 2),
            (player.getPosY() + player.getHeight() / 2)
        );

        Point pointTeleportCentre = new Point(
            (this.getPosX() + this.getWidth() / 2),
            (this.getPosY() + this.getHeight() / 2)
        );

        if (pointTeleportCentre.equals(pointPlayerCentre)) {
            player.setPosition(
                destination.getPosX() + (destination.getWidth() / 2)  - (player.getWidth()  / 2),
                destination.getPosY() + (destination.getHeight() / 2) - (player.getHeight() / 2)
            );
            destination.teleportPlayer(player);
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        Player player = getScene().getLastActorByType(Player.class);
        new Loop<>(new Invoke<>(this::teleportPlayer)).scheduleFor(player);
    }
}
