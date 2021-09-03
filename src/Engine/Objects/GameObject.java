package Engine.Objects;

import Engine.Math.*;
import Engine.Graphics.*;

public class GameObject
{
    private Vector3f position, rotation, scale;
    private Mesh mesh;

    public Vector3f getPosition() { return position; }
    public Vector3f getRotation() { return rotation; }
    public Vector3f getScale() { return scale; }
    public Mesh getMesh() { return mesh; }

    public GameObject(Vector3f position, Vector3f rotation, Vector3f scale, Mesh mesh)
    {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.mesh = mesh;
    }
}