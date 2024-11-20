package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Backpack implements ActorContainer<Collectible> {
    private final String name;
    private final int capacity;
    private List<Collectible> itemsInBackpack = new ArrayList<>();

    public Backpack(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<Collectible> getContent() {
        return List.copyOf(itemsInBackpack);
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return itemsInBackpack.size();
    }

    @Override
    public void add(Collectible actor) {
        if (itemsInBackpack.size() < capacity) {
            itemsInBackpack.add(actor);
        } else {
            throw new IllegalStateException(getName() + " is full");
        }
    }

    @Override
    public void remove(Collectible actor) {
        if (itemsInBackpack != null) {
            itemsInBackpack.remove(actor);
        }
    }

    @NotNull
    @Override
    public Iterator<Collectible> iterator() {
        return itemsInBackpack.iterator();
    }

    @Override
    public Collectible peek() {
        if (getSize() > 0) {
            return itemsInBackpack.get(getSize() - 1);
        } else {
            return null;
        }
    }

    @Override
    public void shift() {
        Collections.rotate(itemsInBackpack, 1);
    }
}
