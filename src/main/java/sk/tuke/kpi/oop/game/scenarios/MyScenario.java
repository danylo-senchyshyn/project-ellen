package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.*;
import sk.tuke.kpi.oop.game.Logo.GameOver;
import sk.tuke.kpi.oop.game.behaviours.FollowActor;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.*;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.*;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.DoorStrong;

import java.util.Objects;

import static sk.tuke.kpi.oop.game.characters.Ripley.RIPLEY_DIED;

public class MyScenario implements SceneListener {
    public static class Factory implements ActorFactory {
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            assert name != null;
            assert type != null;

            if (name.equals("door") && type.equals("vertical")) {
                return new Door(Door.Orientation.VERTICAL);
            } else if (name.equals("door") && type.equals("horizontal")) {
                return new Door(Door.Orientation.HORIZONTAL);
            } else if (name.equals("door strong") && type.equals("first")) {
                return new DoorStrong(DoorStrong.DoorStrongNumber.first);
            } else if (name.equals("door strong") && type.equals("second")) {
                return new DoorStrong(DoorStrong.DoorStrongNumber.second);
            } else if (name.equals("door strong") && type.equals("third")) {
                return new DoorStrong(DoorStrong.DoorStrongNumber.third);
            } else if (name.equals("ellen")) {
                return new Ripley();
            } else if (name.equals("invise")) {
                return new Invise();
            } else if (name.equals("energy")) {
                return new Energy();
            } else if (name.equals("access card")) {
                return new AccessCard();
            } else if (name.equals("ventilator")) {
                return new Ventilator();
            } /* else if (name.equals("alien")) {
                return new Alien(100, new FollowActor());
            } else if (name.equals("mother alien")) {
                return new MotherAlien(new FollowActor());
            } */ else if (name.equals("ammo")) {
                return new Ammo();
            } else if (name.equals("spawn point") && type.equals("first")) {
                return new SpawnPoint(0);
            } else if (name.equals("spawn point") && type.equals("second")) {
                return new SpawnPoint(0);
            } else if (name.equals("subway") && type.equals("first")) {
                return new Subway(Subway.Status.first);
            } else if (name.equals("subway") && type.equals("second")) {
                return new Subway(Subway.Status.second);
            } else if (name.equals("subway") && type.equals("third")) {
                return new Subway(Subway.Status.third);
            } else if (name.equals("subway") && type.equals("fourth")) {
                return new Subway(Subway.Status.fourth);
            } else if (name.equals("subway") && type.equals("fifth")) {
                return new Subway(Subway.Status.fifth);
            } else if (name.equals("subway") && type.equals("sixth")) {
                return new Subway(Subway.Status.sixth);
            } else if (name.equals("laser")) {
                return new Laser();
            } else if (name.equals("reactor")) {
                return new Reactor();
            } else if (name.equals("light")) {
                return new Light();
            } else if (name.equals("monster boss")) {
                return new MonsterBoss(100, new FollowActor());
            } else if (name.equals("engine")) {
                return new Engine();
            } else {
                return null;
            }
        }
    }

    @Override
    public void sceneCreated(Scene scene) {
    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        Reactor reactor = scene.getFirstActorByType(Reactor.class);
        reactor.turnOn();
        reactor.increaseTemperature(4000);

        Ripley ellen = scene.getFirstActorByType(Ripley.class);
        scene.follow(ellen);

        Disposable movableCon = scene.getInput().registerListener(new MovableController(ellen));
        Disposable keeperCon = scene.getInput().registerListener(new KeeperController(ellen));
        Disposable shooterCon = scene.getInput().registerListener(new ShooterController(ellen));

        scene.getGame().pushActorContainer(ellen.getBackpack());

        Locker lockerMjolnir = new Locker(Locker.Item.mjolnir);
        Helicopter helicopter = new Helicopter();
        GameOver gameOver = new GameOver();

        scene.getMessageBus().subscribe(RIPLEY_DIED, (Ripley) -> {
            movableCon.dispose();
            keeperCon.dispose();
            shooterCon.dispose();
            //scene.addActor(gameOver, ellen.getPosX() - gameOver.getWidth() / 2, ellen.getPosY() - gameOver.getHeight() / 2);
        });

        scene.getMessageBus().subscribe(DoorStrong.DOOR_STRONG_OPENED, (event) -> {
            for (Actor actor : scene.getActors()) {
                if (actor instanceof DoorStrong && !((DoorStrong) actor).isOpen()) {
                    ((DoorStrong) actor).open();
                }
            }
        });

        scene.getMessageBus().subscribe(Alien.ALIEN_DEAD, (point) -> {
            Body body = new Body();
            scene.addActor(body, point.getX(), point.getY());
        });
        scene.getMessageBus().subscribe(MotherAlien.MOTHER_ALIEN_DEAD, (event) -> {
            scene.addActor(lockerMjolnir, 224, 960 - 928 - 16);
        });
        scene.getMessageBus().subscribe(MonsterBoss.MONSTER_BOSS_DEAD, (event) -> {
            new ActionSequence<>(
                new Wait<>(1),
                new Invoke<>(() -> scene.addActor(helicopter, 162, 960 - 112 - 64))
            ).scheduleFor(ellen);
        });

        scene.getMessageBus().subscribe(Reactor.REACTOR_FIXED, (event) -> {
            for (Actor actor : scene.getActors()) {
                if (actor instanceof Subway) {
                    ((Subway) actor).setAn(Subway.Status.second);
                }
            }
        });
        scene.getMessageBus().subscribe(Reactor.REACTOR_EXPLODED, (event) -> {
            ellen.getHealth().drain(100);
        });

        scene.getMessageBus().subscribe(Helicopter.HELICOPTER_EXPLODED, (event) -> {
            for (Actor actor : scene.getActors()) {
                if (actor instanceof Subway) {
                    ((Subway) actor).setAn(Subway.Status.second);
                }
            }
        });
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        Ripley ellen = scene.getFirstActorByType(Ripley.class);
        assert ellen != null;
        ellen.showRipleyState();
    }
}
