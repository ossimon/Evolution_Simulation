package simulation;

import java.util.Random;

public enum MapDirection {

    NORTH,
    NORTHWEST,
    NORTHEAST,
    SOUTH,
    SOUTHWEST,
    SOUTHEAST,
    WEST,
    EAST;

    public static MapDirection randomDirection() {

        Random rng = new Random();
        int dir = rng.nextInt(8);

        return switch (dir) {
            case 0 -> NORTH;
            case 1 -> NORTHEAST;
            case 2 -> NORTHWEST;
            case 3 -> SOUTH;
            case 4 -> SOUTHEAST;
            case 5 -> SOUTHWEST;
            case 6 -> EAST;
            case 7 -> WEST;
            default -> throw new IllegalStateException("Unexpected value: " + dir);
        };
    }

    public String toString(){

        return switch (this) {
            case NORTH -> "Północ";
            case NORTHEAST -> "Północny Wschód";
            case NORTHWEST -> "Północny Zachód";
            case SOUTH -> "Południe";
            case SOUTHEAST -> "Południowy Wschód";
            case SOUTHWEST -> "Południowy Zachód";
            case WEST -> "Zachód";
            case EAST -> "Wschód";
        };
    }

    public MapDirection next(){

        return switch (this){
            case NORTH -> NORTHEAST;
            case NORTHEAST -> EAST;
            case EAST -> SOUTHEAST;
            case SOUTHEAST -> SOUTH;
            case SOUTH -> SOUTHWEST;
            case SOUTHWEST -> WEST;
            case WEST -> NORTHWEST;
            case NORTHWEST -> NORTH;
        };
    }

    public MapDirection previous(){

        return switch (this){
            case NORTH -> NORTHWEST;
            case NORTHEAST -> NORTH;
            case EAST -> NORTHEAST;
            case SOUTHEAST -> EAST;
            case SOUTH -> SOUTHEAST;
            case SOUTHWEST -> SOUTH;
            case WEST -> SOUTHWEST;
            case NORTHWEST -> WEST;
        };
    }

    public Vector2d toUnitVector(){

        return switch (this) {
            case NORTH -> new Vector2d(0, -1);
            case NORTHEAST -> new Vector2d(1, -1);
            case EAST -> new Vector2d(1, 0);
            case SOUTHEAST -> new Vector2d(1, 1);
            case SOUTH -> new Vector2d(0, 1);
            case SOUTHWEST -> new Vector2d(-1, 1);
            case WEST -> new Vector2d(-1, 0);
            case NORTHWEST -> new Vector2d(-1, -1);
        };
    }
}
