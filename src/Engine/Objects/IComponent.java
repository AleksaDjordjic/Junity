package Engine.Objects;

public interface IComponent
{
    void Awake();
    void Setup();
    void Update();
    void LateUpdate();
    void FixedUpdate();
}
