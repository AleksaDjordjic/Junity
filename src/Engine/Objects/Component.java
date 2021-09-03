package Engine.Objects;

import Engine.Managers.GameObjectManager;

public class Component
{
    private GameObject attachedGameObject;
    private Transform transform;

    public GameObject gameObject() { return attachedGameObject; }
    public Transform transform() { return transform; }

    public Component() { }

    public void Attach(GameObject gameObject)
    {
        attachedGameObject = gameObject;
        gameObject.components.add(this);
        transform = gameObject.transform;

        if (GameObjectManager.isSetup())
        {
            Awake();
            Setup();
        }
    }

    public void Awake() { }
    public void Setup() { }
    public void Update() { }
    public void LateUpdate() { }
    public void FixedUpdate() { }
}
