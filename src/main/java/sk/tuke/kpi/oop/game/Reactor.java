package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Reactor extends AbstractActor {
    private int temperature;
    private int damage;
    private boolean running;
    private Animation normalAnimation;
    private Animation hotAnimation;
    private Animation brokenAnimation;
    private Animation offAnimation;
    private Light connectedLight;

    // Конструктор Reactor
    public Reactor() {
        this.temperature = 0;
        this.damage = 0;
        this.running = false; // По умолчанию реактор выключен

        normalAnimation = new Animation ("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        hotAnimation = new Animation    ("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        brokenAnimation = new Animation ("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        offAnimation = new Animation    ("sprites/reactor.png", 80, 80, 0.1f);

        setAnimation(offAnimation);
    }

    // Метод для включения реактора
    public void turnOn() {
        if (!isBroken()) {
            this.running = true;
            updateAnimation();
        }
    }

    // Метод для выключения реактора
    public void turnOff() {
        this.running = false;
        setAnimation(offAnimation);
    }

    // Метод для проверки, включен ли реактор
    public boolean isRunning() {
        return running;
    }

    // Метод для увеличения температуры
    public void increaseTemperature(int increment) {
        if (!running || damage >= 100 || increment < 0) { return; } // Реактор должен быть включен

        this.temperature += increment;

        if (temperature > 2000) {
            if (temperature <= 4000) {
                damage = Math.max(damage, (int) Math.floor((temperature - 2000) / 50.0));
            } else if (temperature > 4000 && temperature < 6000) {
                damage = Math.max(damage, (int) Math.floor((temperature - 2000) / 25.0));
            } else if (temperature >= 6000) {
                damage = 100;
                turnOff();
            }
        }

        updateAnimation();
    }

    // Метод для уменьшения температуры
    public void decreaseTemperature(int decrement) {
        if (!running || damage >= 100 || decrement < 0) { return; }

        int effectiveDecrement = decrement;

        if (damage >= 50) {
            effectiveDecrement = decrement / 2;
        }

        this.temperature = Math.max(this.temperature - effectiveDecrement, 0);

        updateAnimation();
    }

    // Метод для ремонта реактора с помощью молотка
    public void repairWith(int damageToRepair) {
        int newDamage = Math.max(damage - damageToRepair, 0);

        int auxiliaryDamage = Math.abs(newDamage - damageToRepair);

        int newTemperature = (int) ((6000 * newDamage) / 100); // линейная зависимость

        if (newTemperature < temperature) {
            temperature = newTemperature;
            damage = newDamage;
        }

        updateElectricityFlow(); // обновляем поток электричества для света
        updateAnimation(); // обновляем анимацию в зависимости от повреждений
    }

    // Обновление анимации реактора в зависимости от состояния
    private void updateAnimation() {
        if (!running) {
            setAnimation(offAnimation);
            return;
        }

        if (damage < 33) {
            setAnimation(normalAnimation);
        } else if (damage < 66) {
            setAnimation(hotAnimation);
        } else {
            setAnimation(brokenAnimation);
        }
    }

    // Метод для проверки, сломан ли реактор
    public boolean isBroken() {
        return damage >= 100;
    }

    // Метод добавления света
    public void addLight(Light light) {
        this.connectedLight = light;
        updateElectricityFlow();
    }

    // Метод удаления света
    public void removeLight() {
        this.connectedLight = null;
        updateElectricityFlow();
    }

    private void updateElectricityFlow() {
        if (connectedLight != null) {
            connectedLight.setElectricityFlow(running && damage < 50);
        }
    }

    // Геттеры и сеттеры
    public int getTemperature() {
        return temperature;
    }
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
}
