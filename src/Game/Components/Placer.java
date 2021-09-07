package Game.Components;

import Engine.Components.*;
import Engine.GUI.GUIRenderer;
import Engine.IO.*;
import Engine.Managers.*;
import Engine.Math.*;
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
            GameObject go = new GameObject(Vector3f.add(this.transform().position, new Vector3f(0, -1.2f, 0)), new Vector3f(), new Vector3f(1, 1, 1));
            MeshRenderer meshRenderer = new MeshRenderer(meshToPlace, material);
            meshRenderer.Attach(go);

            System.out.println(GameObjectManager.gameObjects.size());
        }

        if (Input.isButtonDown(GLFW_MOUSE_BUTTON_RIGHT))
        {
            LightManager.lights.get(0).transform().position = new Vector3f(Camera.main.transform().position.x, Camera.main.transform().position.y, Camera.main.transform().position.z);
        }
    }
}
