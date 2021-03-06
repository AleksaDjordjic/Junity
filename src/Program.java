import Engine.Components.*;
import Engine.IO.*;
import Engine.Graphics.*;
import Engine.Math.*;
import Engine.Objects.*;
import Game.Components.*;

public class Program implements Runnable
{
    public final String TITLE = "Main Window";
    public final int WIDTH = 1280, HEIGHT = 720;

    public Thread main;
    public Window window;

    public GameObject lightGO;
    public GameObject cameraGO;
    public GameObject cube;
    public Light light;
    public Camera camera;

    public static void main(String[] args)
    {
        new Program().Start();
    }

    public void Start()
    {
        main = new Thread(this, "main");
        main.start();
    }

    public void run()
    {
        Shader defaultShader = new Shader("/shaders/mainVertex.glsl", "/shaders/mainFragment.glsl");
        Material defaultMaterial = new Material(defaultShader, "/textures/oldpfp.png");
        defaultMaterial.reflectivity = 0.5f;
        Mesh mesh = ModelLoader.LoadModel("resources/models/test.fbx", "/textures/oldpfp.png");

        cube = new GameObject(new Vector3f(0, 0, 0), new Vector3f(), new Vector3f(1, 1, 1));
        MeshRenderer cubeMeshRenderer = new MeshRenderer(mesh, defaultMaterial);
        cubeMeshRenderer.Attach(cube);

        cameraGO = new GameObject(new Vector3f(0, 0, 1), new Vector3f(), new Vector3f());
        camera = new Camera();
        camera.Attach(cameraGO);

        lightGO = new GameObject(new Vector3f(), new Vector3f(), new Vector3f());
        light = new Light(1, new Vector3f(1, 1, 1));
        light.Attach(lightGO);

        Placer placer = new Placer(mesh, defaultMaterial);
        placer.Attach(cameraGO);

        // GUI
        Shader guiShader = new Shader("/shaders/ui/vertex.glsl", "/shaders/ui/fragment.glsl");
        Material guiMaterial = new Material(guiShader, "/textures/oldpfp.png");

        GameObject guiElementGO = new GameObject(new Vector2f(0, 0), new Vector2f(1, 1));
        GUIElement guiElement = new GUIElement(guiMaterial);
        guiElement.Attach(guiElementGO);

        window = new Window(TITLE, WIDTH, HEIGHT, camera);
        window.Run();
    }
}
