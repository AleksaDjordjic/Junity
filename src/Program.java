import Engine.IO.*;
import Engine.Graphics.*;
import Engine.Math.*;
import Engine.Objects.*;

import static org.lwjgl.glfw.GLFW.*;

public class Program implements Runnable
{
    public Mesh mesh;
    public Shader shader;
    public GameObject gameObject;
    public Camera camera;

    public final String TITLE = "Main Window";
    public final int WIDTH = 1280, HEIGHT = 720;

    public Thread main;
    public Window window;
    public Renderer renderer;

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
        Init();
        while (!window.ShouldClose() && !Input.isKeyDown(GLFW_KEY_ESCAPE))
        {
            Update();
            Render();

            if (Input.isKeyDown(GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
        }
        Close();
    }

    public void Init()
    {
        System.out.println("Initializing...");
        mesh = ModelLoader.LoadModel("resources/models/test.fbx", "/textures/oldpfp.png");
        /*new Mesh(new Vertex[] {
            new Vertex(-0.5f, 0.5f, 0f, 1f, 1f, 1f, 1f, 0f, 0f),
            new Vertex(-0.5f, -0.5f, 0f, 1f, 1f, 1f, 1f, 0f, 1f),
            new Vertex(0.5f, -0.5f, 0f, 1f, 1f, 1f, 1f, 1f, 1f),
            new Vertex(0.5f, 0.5f, 0f, 1f, 1f, 1f, 1f, 1f, 0f),
        }, new int[] {
            0, 1, 2,
            0, 3, 2
        }, new Material("/textures/oldpfp.png"));*/
        gameObject = new GameObject(new Vector3f(0, 0, 0), new Vector3f(), new Vector3f(1, 1, 1), mesh);
        camera = new Camera(new Vector3f(0, 0, 1), new Vector3f());
        window = new Window(TITLE, WIDTH, HEIGHT);
        shader = new Shader("/shaders/mainVertex.glsl", "/shaders/mainFragment.glsl");
        renderer = new Renderer(window, shader);

        camera.isFirstPerson = true;
        camera.lookAt = gameObject;

        window.Create();
        mesh.Create();
        shader.Create();
    }

    public void Update()
    {
        camera.Update();
        window.Update();
    }

    public void Render()
    {
        renderer.RenderMesh(gameObject, camera);
        window.SwapBuffers();
    }

    public void Close()
    {
        window.Close();
        mesh.Destroy();
    }
}
