package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Reactor;

public class PerpetualReactorHeating extends AbstractAction<Reactor> {
    private int heatingAmount;

    public PerpetualReactorHeating(int heatingAmount) {
        this.heatingAmount = heatingAmount;
    }

    @Override
    public void execute (float deltaTime) {
        Reactor reactor = getActor();
        if (reactor!=null && reactor.isLaunched()) {
            reactor.increaseTemperature(heatingAmount);
        }
        reactor.updateAnimation();
    }
}
