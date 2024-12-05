package sk.tuke.kpi.oop.game.characters;

import java.util.ArrayList;
import java.util.List;

public class Health {
    private int maxHealth;
    private int currentHealth;
    private List<FatigueEffect> effect;
    private boolean isExhausted;

    public Health(int initialHealth, int maxHealth) {
        this.currentHealth = initialHealth;
        this.maxHealth = maxHealth;
        this.effect = new ArrayList<>();
        this.isExhausted = false;
    }

    public Health(int initialHealth) {
        currentHealth = initialHealth;
        maxHealth = currentHealth;
    }

    public int getValue() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void refill(int amount) {
        currentHealth = Math.min(currentHealth + amount, maxHealth);
        if (currentHealth > 0) {
            isExhausted = false;
        }
    }

    public void restore() {
        currentHealth = maxHealth;
        isExhausted = false;
    }

    public void drain(int amount) {
        currentHealth = Math.max(currentHealth - amount, 0);
        if (currentHealth == 0) exhaust();
    }

    public void exhaust() {
        if (isExhausted) return;
        isExhausted = true;

        currentHealth = 0;
        if (effect != null) {
            effect.forEach(FatigueEffect::apply);
        }
    }

    @FunctionalInterface
    public interface FatigueEffect {
        void apply();
    }

    public void onFatigued(FatigueEffect effect) {
        if (effect != null)
            this.effect.add(effect);
    }
}
