package sk.tuke.kpi.oop.game.characters;

import java.util.ArrayList;
import java.util.List;

public class Health {
    private float maxHealth;
    private float currentHealth;
    private List<FatigueEffect> effect;
    private boolean isExhausted;

    public Health(float initialHealth, float maxHealth) {
        this.currentHealth = initialHealth;
        this.maxHealth = maxHealth;
        this.effect = new ArrayList<>();
        this.isExhausted = false;
    }

    public Health(float initialHealth) {
        currentHealth = initialHealth;
        maxHealth = currentHealth;
        this.effect = new ArrayList<>();
        this.isExhausted = false;
    }

    public float getValue() {
        return currentHealth;
    }

    public void setCurrentHealth(float currentHealth) {
        this.currentHealth = currentHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void refill(float amount) {
        currentHealth = Math.min(currentHealth + amount, maxHealth);
        if (currentHealth > 0) {
            isExhausted = false;
        }
    }

    public void restore() {
        currentHealth = maxHealth;
        isExhausted = false;
    }

    public void drain(float amount) {
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
