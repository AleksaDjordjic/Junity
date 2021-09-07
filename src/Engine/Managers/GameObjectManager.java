package Engine.Managers;

import Engine.Objects.*;

import java.util.*;

public final class GameObjectManager
{
    private static boolean setup = false;
    public static boolean isSetup() { return setup; }

    public static ArrayList<GameObject> gameObjects = new ArrayList<>();

    public static void Awake()
    {
        setup = true;

        for (GameObject go : (ArrayList<GameObject>)gameObjects.clone())
        {
            for (IComponent component : (ArrayList<IComponent>)go.components.clone())
            {
                component.Awake();
            }
        }
    }

    public static void Setup()
    {
        for (GameObject go : (ArrayList<GameObject>)gameObjects.clone())
        {
            for (IComponent component : (ArrayList<IComponent>)go.components.clone())
            {
                component.Setup();
            }
        }
    }

    public static void Update()
    {
        for (GameObject go : (ArrayList<GameObject>)gameObjects.clone())
        {
            for (IComponent component : (ArrayList<IComponent>)go.components.clone())
            {
                component.Update();
            }
        }
    }

    public static void LateUpdate()
    {
        for (GameObject go : (ArrayList<GameObject>)gameObjects.clone())
        {
            for (IComponent component : (ArrayList<IComponent>)go.components.clone())
            {
                component.LateUpdate();
            }
        }
    }

    public static void FixedUpdate()
    {
        for (GameObject go : (ArrayList<GameObject>)gameObjects.clone())
        {
            for (IComponent component : (ArrayList<IComponent>)go.components.clone())
            {
                component.FixedUpdate();
            }
        }
    }
}
