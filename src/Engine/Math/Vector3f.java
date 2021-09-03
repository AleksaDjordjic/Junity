package Engine.Math;

import java.util.Objects;

public class Vector3f
{
    public static final Vector3f Right = new Vector3f(1, 0, 0);
    public static final Vector3f Up = new Vector3f(0, 1, 0);
    public static final Vector3f Forward = new Vector3f(0, 0, 1);

    public float x, y, z;

    public Vector3f()
    {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vector3f(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float Intensity()
    {
        return (float)Math.sqrt(x * x + y * y + z * z);
    }

    public float IntensitySquared()
    {
        return x * x + y * y + z * z;
    }

    public Vector3f Normalized()
    {
        float intensity = Intensity();
        return Vector3f.divide(this, new Vector3f(intensity, intensity, intensity));
    }

    public static float Dot(Vector3f first, Vector3f second)
    {
        return first.x * second.x + first.y * second.y + first.z * second.z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3f vector3f = (Vector3f) o;
        return Float.compare(vector3f.x, x) == 0 && Float.compare(vector3f.y, y) == 0 && Float.compare(vector3f.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public static Vector3f add(Vector3f first, Vector3f second)
    {
        return new Vector3f(first.x + second.x, first.y + second.y, first.z + second.z);
    }

    public static Vector3f subtract(Vector3f first, Vector3f second)
    {
        return new Vector3f(first.x - second.x, first.y - second.y, first.z - second.z);
    }

    public static Vector3f multiply(Vector3f first, Vector3f second)
    {
        return new Vector3f(first.x * second.x, first.y * second.y, first.z * second.z);
    }

    public static Vector3f divide(Vector3f first, Vector3f second)
    {
        return new Vector3f(first.x / second.x, first.y / second.y, first.z / second.z);
    }
}
