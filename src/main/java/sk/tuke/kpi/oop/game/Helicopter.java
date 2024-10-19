package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Helicopter extends AbstractActor {
    private int newPositionX = 0, newPositionY = 0;
    private Animation helicopterAnimation;

    public Helicopter() {
        helicopterAnimation = new Animation("sprites/heli.png", 64, 64, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(helicopterAnimation);
    }

    public void searchAndDestroy() {
        new Loop<>(new Invoke<>(this::searchActor)).scheduleFor(this);
    }

    private void searchActor() {
        Player refActor = getScene().getLastActorByType(Player.class);
        if (refActor == null) return;

        if (this.getPosX() != refActor.getPosX()) {
            if(this.getPosX() > refActor.getPosX()) {
                newPositionX = this.getPosX()- 1;
            } else {
                newPositionX = this.getPosX() + 1;
            }
        }

        if (this.getPosY() != refActor.getPosY()) {
            if(this.getPosY() > refActor.getPosY()) {
                newPositionY = this.getPosY()- 1;
            } else {
                newPositionY = this.getPosY() + 1;
            }
        }

        this.setPosition(newPositionX, newPositionY);

        if (intersects(refActor)) {
            refActor.setEnergy(refActor.getEnergy()-1);
        }
    }
}
