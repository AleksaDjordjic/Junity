package Engine.IO;

import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.*;

public class Input
{
    private static boolean[] keys = new boolean[GLFW_KEY_LAST];
    private static boolean[] buttons = new boolean[GLFW_MOUSE_BUTTON_LAST];
    private static double mouseX, mouseY;
    private static double scrollX, scrollY;

    public static boolean isKeyDown(int key) { return keys[key]; }
    public static boolean isButtonDown(int button) { return buttons[button]; }
    public static double getMouseX() { return mouseX; }
    public static double getMouseY() { return mouseY; }
    public static double getScrollX() { return scrollX; }
    public static double getScrollY() { return scrollY; }

    private GLFWKeyCallback keyboard;
    private GLFWCursorPosCallback mouseMove;
    private GLFWMouseButtonCallback mouseButton;
    private GLFWScrollCallback scroll;

    public GLFWKeyCallback getKeyboardCallback() { return keyboard; }
    public GLFWCursorPosCallback getMouseMoveCallback() { return mouseMove; }
    public GLFWMouseButtonCallback getMouseButtonCallback() { return mouseButton; }
    public GLFWScrollCallback getScrollCallback() { return scroll; }

    public Input()
    {
        keyboard = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keys[key] = (action != GLFW_RELEASE);
            }
        };

        mouseMove = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mouseX = xpos;
                mouseY = ypos;
            }
        };

        mouseButton = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                buttons[button] = (action != GLFW_RELEASE);
            }
        };

        scroll = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                scrollX += xoffset;
                scrollY += yoffset;
            }
        };
    }

    public void Destroy()
    {
        keyboard.free();
        mouseMove.free();
        mouseButton.free();
        scroll.free();
    }
}
