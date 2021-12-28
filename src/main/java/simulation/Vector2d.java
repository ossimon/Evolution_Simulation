package simulation;

import java.util.Objects;

public class Vector2d implements Comparable {

    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;

        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }

    public Vector2d within(Vector2d other) {
        int x = this.x;
        int y = this.y;
        if (this.x < 0) {
            x = other.x;
        } else if (this.x > other.x) {
            x = 0;
        }
        if (this.y < 0) {
            y = other.y;
        } else if (this.y > other.y) {
            y = 0;
        }
        return new Vector2d(x, y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(Object other) {

        if (this.x > ((Vector2d) other).x) return 1;
        else if (this.x < ((Vector2d) other).x) return 0;
        else return -1;
    }
}