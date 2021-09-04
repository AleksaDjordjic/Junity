package Engine.Components;

import Engine.Managers.*;
import Engine.Math.*;
import Engine.Objects.*;

public class Light extends Component
{
    public float intensity;
    public Vector3f color;

    public Light()
    {
        intensity = 1f;
        color = new Vector3f(1, 1, 1);
    }

    public Light(float intensity, Vector3f color)
    {
        this.intensity = intensity;
        this.color = color;
    }

    @Override
    public void Awake() {
        super.Awake();
        LightManager.lights.add(this);
    }
}
