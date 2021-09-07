package Engine.Objects;

import Engine.GUI.RectTransform;
import Engine.Managers.GameObjectManager;

public class UIComponent implements IComponent
{
    private GameObject attachedGameObject;
    private RectTransform rectTransform;

    public GameObject gameObject() { return attachedGameObject; }
    public RectTransform rectTransform() { return rectTransform; }

    public UIComponent() { }

    public void Attach(GameObject gameObject)
    {
        attachedGameObject = gameObject;
        gameObject.components.add(this);
        rectTransform = gameObject.rectTransform;

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
