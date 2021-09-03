package Engine.Math;

import org.lwjglx.util.vector.Matrix;

import java.util.Arrays;

public class Matrix4f
{
    public static final int SIZE = 4;
    private float[] elements = new float[SIZE * SIZE];

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix4f matrix4f = (Matrix4f) o;
        return Arrays.equals(elements, matrix4f.elements);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }

    // 1 in diagonal
    public static Matrix4f Identity()
    {
        Matrix4f result = new Matrix4f();

        for (int x = 0; x < SIZE; x++)
        {
            for (int y = 0; y < SIZE; y++)
            {
                if (x == y) result.Set(x, y, 1);
                else result.Set(x, y, 0);
            }
        }

        return result;
    }

    public static Matrix4f Translate(Vector3f translate)
    {
        Matrix4f result = Matrix4f.Identity();

        result.Set(3, 0, translate.x);
        result.Set(3, 1, translate.y);
        result.Set(3, 2, translate.z);

        return result;
    }

    public static Matrix4f Rotate(float angle, Vector3f axis)
    {
        Matrix4f result = Matrix4f.Identity();

        float cos = (float)Math.cos(Math.toRadians(angle));
        float sin = (float)Math.sin(Math.toRadians(angle));
        float inverseCos = 1 - cos;

        result.Set(0, 0, cos + axis.x * axis.x * inverseCos);
        result.Set(0, 1, axis.x * axis.y * inverseCos - axis.z * sin);
        result.Set(0, 2, axis.x * axis.z * inverseCos + axis.y * sin);
        result.Set(1, 0, axis.y * axis.x * inverseCos + axis.z * sin);
        result.Set(1, 1, cos + axis.y * axis.y * inverseCos);
        result.Set(1, 2, axis.y * axis.z * inverseCos - axis.x * sin);
        result.Set(2, 0, axis.z * axis.x * inverseCos - axis.y * sin);
        result.Set(2, 1, axis.z * axis.y * inverseCos + axis.x * sin);
        result.Set(2, 2, cos + axis.z * axis.z * inverseCos);

        return result;
    }

    public static Matrix4f Scale(Vector3f scalar)
    {
        Matrix4f result = Matrix4f.Identity();

        result.Set(0, 0, scalar.x);
        result.Set(1, 1, scalar.y);
        result.Set(2, 2, scalar.z);

        return result;
    }

    public static Matrix4f multiply(Matrix4f matrix, Matrix4f other)
    {
        Matrix4f result = Matrix4f.Identity();

        for (int x = 0; x < SIZE; x++)
        {
            for (int y = 0; y < SIZE; y++)
            {
                result.Set(x, y,
                  matrix.Get(x, 0) * other.Get(0, y) +
                        matrix.Get(x, 1) * other.Get(1, y) +
                        matrix.Get(x, 2) * other.Get(2, y) +
                        matrix.Get(x, 3) * other.Get(3, y));
            }
        }

        return result;
    }

    public static Matrix4f Transform(Vector3f position, Vector3f rotation, Vector3f scale)
    {
        Matrix4f translationMatrix = Matrix4f.Translate(position);
        Matrix4f rotationXMatrix = Matrix4f.Rotate(rotation.x, Vector3f.Right);
        Matrix4f rotationYMatrix = Matrix4f.Rotate(rotation.y, Vector3f.Up);
        Matrix4f rotationZMatrix = Matrix4f.Rotate(rotation.z, Vector3f.Forward);
        Matrix4f scaleMatrix = Matrix4f.Scale(scale);

        Matrix4f rotationMatrix = Matrix4f.multiply(rotationXMatrix, Matrix4f.multiply(rotationYMatrix, rotationZMatrix));

        return Matrix4f.multiply(translationMatrix, Matrix4f.multiply(rotationMatrix, scaleMatrix));
    }

    public static Matrix4f Projection(float aspect, float fov, float nearPlane, float farPlane)
    {
        Matrix4f result = Matrix4f.Identity();

        float tanFOV = (float) Math.tan(Math.toRadians(fov / 2));
        float range = farPlane - nearPlane;

        result.Set(0, 0, 1.0f / (aspect * tanFOV));
        result.Set(1, 1, 1.0f / tanFOV);
        result.Set(2, 2, -((farPlane + nearPlane) / range));
        result.Set(2, 3, -1.0f);
        result.Set(3, 2, -((2 * farPlane * nearPlane) / range));
        result.Set(3, 3, 0.0f);

        return result;
    }

    public static Matrix4f View(Vector3f position, Vector3f rotation)
    {
        Vector3f negativePosition = new Vector3f(-position.x, -position.y, -position.z);
        Matrix4f translationMatrix = Matrix4f.Translate(negativePosition);
        Matrix4f rotationXMatrix = Matrix4f.Rotate(rotation.x, Vector3f.Right);
        Matrix4f rotationYMatrix = Matrix4f.Rotate(rotation.y, Vector3f.Up);
        Matrix4f rotationZMatrix = Matrix4f.Rotate(rotation.z, Vector3f.Forward);

        Matrix4f rotationMatrix = Matrix4f.multiply(rotationZMatrix, Matrix4f.multiply(rotationYMatrix, rotationXMatrix));

        return Matrix4f.multiply(translationMatrix, rotationMatrix);
    }

    public float Get(int x, int y)
    {
        return elements[y * SIZE + x];
    }

    public void Set(int x, int y, float value)
    {
        elements[y * SIZE + x] = value;
    }

    public float[] GetAll()
    {
        return elements;
    }
}