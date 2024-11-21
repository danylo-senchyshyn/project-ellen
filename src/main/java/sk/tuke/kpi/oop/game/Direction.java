package sk.tuke.kpi.oop.game;

public enum Direction {
    NORTH(0, 1),
    EAST(1, 0),
    SOUTH(0, -1),
    WEST(-1, 0),
    NORTHEAST(1, 1),
    SOUTHEAST(1, -1),
    SOUTHWEST(-1, -1),
    NORTHWEST(-1, 1),
    NONE(0, 0);

    static {
        NORTH.angle = 0;
        EAST.angle = 270;
        SOUTH.angle = 180;
        WEST.angle = 90;
        NORTHEAST.angle = 315;
        SOUTHEAST.angle = 225;
        SOUTHWEST.angle = 135;
        NORTHWEST.angle = 45;
        NONE.angle = 0;
    }

    private final int dx, dy;
    private float angle;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public float getAngle() {
        return angle;
    }

    public Direction combine(Direction other) {
        int combinedDx = Math.max(-1, Math.min(1, this.dx + other.getDx()));
        int combinedDy = Math.max(-1, Math.min(1, this.dy + other.getDy()));

        for (Direction value : Direction.values()) {
            if (value.getDx() == combinedDx && value.getDy() == combinedDy) {
                return value;
            }
        }
        return NONE;
    }
}
