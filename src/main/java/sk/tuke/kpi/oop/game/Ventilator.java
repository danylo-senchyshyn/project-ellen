package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;

import java.util.Objects;

public class Ventilator extends AbstractActor implements Repairable {
    private boolean isDamage;
    public static final Topic<Ventilator> VENTILATOR_REPAIRED = Topic.create("ventilator repaired", Ventilator.class);

    public Ventilator() {
        setAnimation(new Animation("sprites/ventilator.png", 32, 32,  0.1f, Animation.PlayMode.LOOP_PINGPONG));
        damageVentilator();
    }

    private void damageVentilator() {
        getAnimation().stop();
        isDamage = true;
    }

    @Override
    public boolean repair() {
        if (isDamage) {
            getAnimation().play();
            isDamage = false;
            Objects.requireNonNull(getScene()).getMessageBus().publish(VENTILATOR_REPAIRED,this);
            return true;
        } else {
            return false;
        }
    }
}
