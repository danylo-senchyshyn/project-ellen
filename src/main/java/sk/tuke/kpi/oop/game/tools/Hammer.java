package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Hammer extends BreakableTool {
    private int remainingUses;
    private final Animation hammerAnimation;

    public Hammer() {
        super(1);
        hammerAnimation = new Animation("sprites/hammer.png");
        setAnimation(hammerAnimation);
    }

    public int getRemainingUses() {
        return remainingUses;
    }

    @Override
    public void use() {
        super.use();
    }
}
