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

        for (var go: gameObjects)
        {
            for (var component : go.components)
            {
                component.Awake();
            }
        }
    }

    public static void Setup()
    {
        for (var go: gameObjects)
        {
            for (var component : go.components)
            {
                component.Setup();
            }
        }
    }

    public static void Update()
    {
        for (var go : (ArrayList<GameObject>)gameObjects.clone())
        {
            for (var component : (ArrayList<Component>)go.components.clone())
            {
                component.Update();
            }
        }
    }

    public static void LateUpdate()
    {
        for (var go: gameObjects)
        {
            for (var component : go.components)
            {
                component.LateUpdate();
            }
        }
    }

    public static void FixedUpdate()
    {
        for (var go: gameObjects)
        {
            for (var component : go.components)
            {
                component.FixedUpdate();
            }
        }
    }
}
