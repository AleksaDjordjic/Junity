package Engine.Math;

import java.util.Objects;

public class Vector2f
{
    public float x, y;

    public Vector2f()
    {
        this.x = 0;
        this.y = 0;
    }

    public Vector2f(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public float Intensity()
    {
        return (float)Math.sqrt(x * x + y * y);
    }

    public float IntensitySquared()
    {
        return x * x + y * y;
    }

    public Vector2f Normalized()
    {
        float intensity = Intensity();
        return Vector2f.divide(this, new Vector2f(intensity, intensity));
    }

    public static float Dot(Vector2f first, Vector2f second)
    {
        return first.x * second.x + first.y * second.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2f vector2f = (Vector2f) o;
        return Float.compare(vector2f.x, x) == 0 && Float.compare(vector2f.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static Vector2f add(Vector2f first, Vector2f second)
    {
        return new Vector2f(first.x + second.x, first.y + second.y);
    }

    public static Vector2f subtract(Vector2f first, Vector2f second)
    {
        return new Vector2f(first.x - second.x, first.y - second.y);
    }

    public static Vector2f multiply(Vector2f first, Vector2f second)
    {
        return new Vector2f(first.x * second.x, first.y * second.y);
    }

    public static Vector2f divide(Vector2f first, Vector2f second)
    {
        return new Vector2f(first.x / second.x, first.y / second.y);
    }
}
