package Engine.Objects;

import Engine.GUI.*;
import Engine.Managers.*;
import Engine.Math.*;

import java.util.*;

public class GameObject
{
    public Transform transform;
    public RectTransform rectTransform;
    public ArrayList<IComponent> components = new ArrayList<>();

    public GameObject()
    {
        GameObjectManager.gameObjects.add(this);
        this.transform = new Transform();
    }

    public GameObject(Vector2f position, Vector2f scale)
    {
        GameObjectManager.gameObjects.add(this);
        this.rectTransform = new RectTransform(position, scale);
    }

    public GameObject(Vector3f position, Vector3f rotation, Vector3f scale)
    {
        GameObjectManager.gameObjects.add(this);
        this.transform = new Transform(position, rotation, scale);
    }
}