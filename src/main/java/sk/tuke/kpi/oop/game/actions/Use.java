package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.items.Usable;

public class Use<A extends Actor> extends AbstractAction<A> {
    private final Usable<A> item;

    public Use(Usable<A> item) {
        this.item = item;
    }

    @Override
    public void execute(float deltaTime) {
        A actor = getActor();

        if (actor != null) {
            System.out.println("use");
            item.useWith(actor);
        }
        setDone(true);
    }

    public Disposable scheduleForIntersectingWith(Actor mediatingActor) {
        Scene scene = mediatingActor.getScene();
        if (scene == null) return null;

        Class<A> usingActorClass = item.getUsingActorClass();  // `usable` je spominana clenska premenna
        return scene.getActors().stream()  // ziskame stream aktérov na scene
            .filter(mediatingActor::intersects)  // vyfiltrujeme akterov, ktori su v kolizii so sprostredkovatelom
            .filter(usingActorClass::isInstance) // vyfiltrujeme akterov kompatibilneho typu
            .map(usingActorClass::cast)  // vykoname pretypovanie streamu akterov
            .findFirst()  // vyberieme prveho (ak taky existuje) aktera zo streamu
            .map(this::scheduleFor)  // zavolame metodu `scheduleFor` s najdenym akterom a vratime `Disposable` objekt
            .orElse(null);  // v pripade, ze ziaden vyhovujuci akter nebol najdeny, vratime `null`
    }

    @Override
    public @NotNull Disposable scheduleFor(@NotNull A actor) {
        return super.scheduleFor(actor);
    }
}
