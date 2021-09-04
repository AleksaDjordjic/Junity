package Engine.IO;

import Engine.Graphics.*;
import Engine.Managers.*;
import Engine.Math.*;
import Engine.Components.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import static org.lwjgl.opengl.GL46.*;

public class Window
{
    private String title;
    private int width;
    private int height;

    private long window;
    private GLFWWindowSizeCallback sizeCallback;
    private boolean isResized;
    private boolean isFullscreen;
    private int[] winPosX = new int[1], winPosY = new int[1];

    private Matrix4f projection;

    public Camera camera;
    public Input input;
    private boolean mouseLocked = false;

    private long updateEveryMillis = 1000;
    private long lastFpsUpdate = 0;
    private float fps = 0.0f;
    private long time = 0;

    public Window(String title, int width, int height, Camera camera)
    {
        this.title = title;
        this.width = width;
        this.height = height;
        this.projection = Matrix4f.Projection((float)width / (float)height, 90f, 0.001f, 1000f);
        this.camera = camera;
    }

    public String getTitle() { return title; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public long getWindow() { return window; }
    public boolean isFullscreen() { return isFullscreen; }
    public void setFullscreen(boolean fullscreen)
    {
        isFullscreen = fullscreen;
        isResized = true;

        if(isFullscreen)
        {
            glfwGetWindowPos(window, winPosX, winPosY);
            glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(), 0, 0, width, height, 0);
        }
        else glfwSetWindowMonitor(window, 0, winPosX[0],winPosY[0], width, height, 0);
    }
    public Matrix4f getProjection() { return projection; }

    private void SwapBuffers()
    {
        glfwSwapBuffers(window);
    }
    public boolean ShouldClose()
    {
        return glfwWindowShouldClose(window);
    }
    public void LockMouse(boolean lock)  {glfwSetInputMode(window, GLFW_CURSOR, lock ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL); }

    private void CalculateFPS() {
        long currentTime = System.currentTimeMillis();
        if (time - lastFpsUpdate > updateEveryMillis)
        {
            fps = 1000.0f / (currentTime - time);
            glfwSetWindowTitle(window, title + " - FPS: " + fps);
            lastFpsUpdate = currentTime;
        }
        time = currentTime;
    }

    private void CreateCallbacks()
    {
        sizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int newWidth, int newHeight) {
                width = newWidth;
                height = newHeight;
                isResized = true;
            }
        };

        // Input Callbacks
        glfwSetKeyCallback(window, input.getKeyboardCallback());
        glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
        glfwSetMouseButtonCallback(window, input.getMouseButtonCallback());
        glfwSetScrollCallback(window, input.getScrollCallback());
    }

    private void Create()
    {
        if (!glfwInit())
        {
            System.err.println("GLFW could not be initialized!");
            return;
        }

        input = new Input();

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        window = glfwCreateWindow(width, height, title, isFullscreen ? glfwGetPrimaryMonitor() : NULL, NULL);
        if (window == 0)
        {
            System.err.println("Window wasn't created!");
            return;
        }

        // Center Window
        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        winPosX[0] = (videoMode.width() - width) / 2;
        winPosY[0] = (videoMode.height() - height) / 2;
        glfwSetWindowPos(window, winPosX[0], winPosY[0]);
        glfwMakeContextCurrent(window);

        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glEnable(GL_BACK);

        CreateCallbacks();

        glfwShowWindow(window);

        // Vsync on
        glfwSwapInterval(1);

        time = System.currentTimeMillis();
        lastFpsUpdate = time;
    }

    public void Run()
    {
        Init();
        Setup();
        while (!ShouldClose() && !Input.isKeyDown(GLFW_KEY_ESCAPE))
        {
            Update();
            Render();
            LateUpdate();

            if (Input.isKeyDown(GLFW_KEY_F11)) setFullscreen(!isFullscreen);
        }
        Destroy();
    }

    private void Init()
    {
        System.out.println("Initializing Window...");
        Renderer.Setup(this);
        Create();
    }

    private void Setup()
    {
        GameObjectManager.Awake();
        GameObjectManager.Setup();
    }

    private void Update()
    {
        GameObjectManager.Update();

        // Clear Screen
        if (isResized) { glViewport(0, 0, width, height); isResized = false; }
        if (Input.isKeyDown(GLFW_KEY_F9)) { mouseLocked = !mouseLocked; LockMouse(mouseLocked); }
        glClearColor(.529f, .808f, .992f, 1f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glfwPollEvents();
        CalculateFPS();
    }

    private void LateUpdate()
    {
        GameObjectManager.LateUpdate();
    }

    private void FixedUpdate()
    {
        GameObjectManager.FixedUpdate();
    }

    private void Render()
    {
        Renderer.Render();
        SwapBuffers();
    }

    public void Close()
    {
        glfwSetWindowShouldClose(window, true);
    }

    public void Destroy()
    {
        input.Destroy();
        sizeCallback.free();
        glfwWindowShouldClose(window);
        glfwDestroyWindow(window);
        glfwTerminate();
    }
}
