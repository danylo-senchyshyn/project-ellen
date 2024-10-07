package sk.tuke.kpi.oop.game;

public class Mjolnir extends Hammer {
    private int remainingUses;

    // Конструктор
    public Mjolnir() {
        super();
        this.remainingUses = 4;
    }

    public void repairWith(Reactor reactor) {
        if (remainingUses > 0 && reactor.getDamage() > 0) {
            reactor.repairWith(50);
            remainingUses--;

            if (remainingUses == 0) {
                breakHammer();
            }
        }
    }

    private void breakHammer() {
        System.out.println("Mjolnir is broken!");
    }

    // Метод для получения оставшихся использований
    public int getRemainingUses() {
        return remainingUses;
    }
}
