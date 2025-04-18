package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm {
    private int maxAmmo;
    private int currentAmmo;

    public Firearm(int initialAmmo, int maxAmmo) {
        this.maxAmmo = maxAmmo;
        this.currentAmmo = initialAmmo;
    }

    public Firearm(int initialAmmo) {
        this.currentAmmo = initialAmmo;
        this.maxAmmo = initialAmmo;
    }

    public int getAmmo() {
        return currentAmmo;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public void reload(int plusAmmo) {
        if (getAmmo() + plusAmmo < maxAmmo) {
            currentAmmo += plusAmmo;
        }
        else {
            currentAmmo = maxAmmo;
        }
    }

    public Fireable fire() {
        if (currentAmmo != 0) {
            currentAmmo--;
            return createBullet();
        } else {
            return null;
        }
    }

    protected abstract Fireable createBullet();
}
