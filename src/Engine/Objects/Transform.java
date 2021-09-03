package Engine.Objects;

import Engine.Math.*;

public class Transform
{
    public Vector3f position;
    public Vector3f rotation;
    public Vector3f scale;

    public Transform()
    {
        position = new Vector3f();
        rotation = new Vector3f();
        scale = new Vector3f();
    }

    public Transform(Vector3f position, Vector3f rotation, Vector3f scale)
    {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }
}
