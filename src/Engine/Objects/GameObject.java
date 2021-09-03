package Engine.Objects;

import Engine.Managers.*;
import Engine.Math.*;

import java.util.*;

public class GameObject
{
    public Transform transform;
    public ArrayList<Component> components = new ArrayList<>();

    public GameObject()
    {
        GameObjectManager.gameObjects.add(this);
        this.transform = new Transform();
    }

    public GameObject(Vector3f position, Vector3f rotation, Vector3f scale)
    {
        GameObjectManager.gameObjects.add(this);
        this.transform = new Transform(position, rotation, scale);
    }
}