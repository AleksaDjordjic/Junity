import Engine.Components.*;
import Engine.IO.*;
import Engine.Graphics.*;
import Engine.Math.*;
import Engine.Objects.*;
import Game.Components.Placer;

public class Program implements Runnable
{
    public final String TITLE = "Main Window";
    public final int WIDTH = 1280, HEIGHT = 720;

    public Thread main;
    public Window window;

    public GameObject cameraGO;
    public GameObject cube;
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
        Mesh mesh = ModelLoader.LoadModel("resources/models/test.fbx", "/textures/oldpfp.png");

        cube = new GameObject(new Vector3f(0, 0, 0), new Vector3f(), new Vector3f(1, 1, 1));
        MeshRenderer cubeMeshRenderer = new MeshRenderer(mesh, defaultMaterial);
        cubeMeshRenderer.Attach(cube);

        cameraGO = new GameObject(new Vector3f(0, 0, 1), new Vector3f(), new Vector3f());
        camera = new Camera();
        camera.Attach(cameraGO);

        Placer placer = new Placer(mesh, defaultMaterial);
        placer.Attach(cameraGO);

        window = new Window(TITLE, WIDTH, HEIGHT, camera);
        window.Run();
    }
}
