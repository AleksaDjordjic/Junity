package Engine.GUI;

import Engine.Math.*;

public class RectTransform
{
    public Vector2f position;
    public Vector2f scale;

    public RectTransform()
    {
        this.position = new Vector2f();
        this.scale = new Vector2f(1, 1);
    }

    public RectTransform(Vector2f position)
    {
        this.position = position;
        this.scale = new Vector2f(1, 1);
    }

    public RectTransform(Vector2f position, Vector2f scale)
    {
        this.position = position;
        this.scale = scale;
    }
}
