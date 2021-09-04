package Engine.Graphics;

import Engine.Math.*;

public class Vertex
{
    private Vector3f position;
    private Vector4f color;
    private Vector2f texture;
    private Vector3f normal;

    public Vector3f getPosition() { return position; }
    public Vector4f getColor() { return color; }
    public Vector2f getTexture() { return texture; }
    public Vector3f getNormal() { return normal; }

    public Vertex()
    {
        position = new Vector3f();
        color = new Vector4f(1, 1, 1, 1);
        texture = new Vector2f();
        normal = Vector3f.Up;
    }

    public Vertex(Vector3f position)
    {
        this.position = position;
        this.color = new Vector4f(1, 1, 1, 1);
        this.texture = new Vector2f();
        this.normal = Vector3f.Up;
    }

    public Vertex(Vector3f position, Vector2f texture)
    {
        this.position = position;
        this.color = new Vector4f(1, 1, 1, 1);
        this.texture = texture;
        this.normal = Vector3f.Up;
    }

    public Vertex(Vector3f position, Vector2f texture, Vector3f normal)
    {
        this.position = position;
        this.color = new Vector4f(1, 1, 1, 1);
        this.texture = texture;
        this.normal = normal;
    }

    public Vertex(Vector3f position, Vector4f color, Vector2f texture)
    {
        this.position = position;
        this.color = color;
        this.texture = texture;
        this.normal = Vector3f.Up;
    }

    public Vertex(Vector3f position, Vector4f color, Vector2f texture, Vector3f normal)
    {
        this.position = position;
        this.color = color;
        this.texture = texture;
        this.normal = normal;
    }

    public Vertex(float x, float y, float z)
    {
        position = new Vector3f(x, y, z);
        color = new Vector4f(1, 1, 1, 1);
        texture = new Vector2f();
        normal = Vector3f.Up;
    }

    public Vertex(float x, float y, float z, float textureX, float textureY)
    {
        position = new Vector3f(x, y, z);
        color = new Vector4f(1, 1, 1, 1);
        texture = new Vector2f(textureX, textureY);
        normal = Vector3f.Up;
    }

    public Vertex(float x, float y, float z, float textureX, float textureY, float normalX, float normalY, float normalZ)
    {
        position = new Vector3f(x, y, z);
        color = new Vector4f(1, 1, 1, 1);
        texture = new Vector2f(textureX, textureY);
        normal = new Vector3f(normalX, normalY, normalZ);
    }

    public Vertex(float x, float y, float z, float r, float g, float b, float a)
    {
        position = new Vector3f(x, y, z);
        color = new Vector4f(r, g, b, a);
        texture = new Vector2f();
        normal = Vector3f.Up;
    }

    public Vertex(float x, float y, float z, float r, float g, float b, float a, float textureX, float textureY)
    {
        position = new Vector3f(x, y, z);
        color = new Vector4f(r, g, b, a);
        texture = new Vector2f(textureX, textureY);
        normal = Vector3f.Up;
    }

    public Vertex(float x, float y, float z, float r, float g, float b, float a, float textureX, float textureY, float normalX, float normalY, float normalZ)
    {
        position = new Vector3f(x, y, z);
        color = new Vector4f(r, g, b, a);
        texture = new Vector2f(textureX, textureY);
        normal = new Vector3f(normalX, normalY, normalZ);
    }
}