package Game.Components;

import Engine.Components.MeshRenderer;
import Engine.IO.Input;
import Engine.Math.Vector3f;
import Engine.Objects.*;
import Engine.Graphics.*;

import static org.lwjgl.glfw.GLFW.*;

public class Placer extends Component
{
    private Mesh meshToPlace;
    private Material material;

    public Placer(Mesh meshToPlace, Material material)
    {
        this.meshToPlace = meshToPlace;
        this.material = material;
    }

    @Override
    public void Update() {
        super.Update();

        if (Input.isButtonDown(GLFW_MOUSE_BUTTON_LEFT))
        {
            var go = new GameObject(Vector3f.add(this.transform().position, new Vector3f(0, -1.2f, 0)), new Vector3f(), new Vector3f(1, 1, 1));
            var meshRenderer = new MeshRenderer(meshToPlace, material);
            meshRenderer.Attach(go);
        }
    }
}
