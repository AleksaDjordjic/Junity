package Engine.Components;

import Engine.IO.Input;
import Engine.Objects.*;

import static org.lwjgl.glfw.GLFW.*;

public class Camera extends Component
{
    public static Camera main;

    public GameObject lookAt;
    public boolean isFirstPerson = true;

    public float moveSpeed = 0.02f;
    public float sensitivity = 0.2f;
    private double oldMouseX = 0, oldMouseY = 0;

    // Third Person Vars
    private float distance = 2.0f;
    private float horizontalAngle = 0, verticalAngle = 0;

    public Camera() { }

    public Camera(boolean main)
    {
        if (main) Camera.main = this;
    }

    public void Update()
    {
        var newMouseX = Input.getMouseX();
        var newMouseY = Input.getMouseY();
        var mouseDeltaX = (float)(newMouseX - oldMouseX);
        var mouseDeltaY = (float)(newMouseY - oldMouseY);

        // Third Person
        if (!isFirstPerson && lookAt != null)
        {
            if (Input.isButtonDown(GLFW_MOUSE_BUTTON_LEFT))
            {
                verticalAngle += mouseDeltaY * sensitivity;
                horizontalAngle += mouseDeltaX * sensitivity;
            }
            if (Input.isButtonDown(GLFW_MOUSE_BUTTON_RIGHT))
            {
                if (distance >= .1f)
                {
                    distance += mouseDeltaY * sensitivity * .1f;
                    distance = Math.max(distance, .1f);
                }
                else
                {
                    distance = .1f;
                }
            }

            float horizontalDistance = distance * (float)Math.cos(Math.toRadians(verticalAngle));
            float verticalDistance = distance * (float)Math.sin(Math.toRadians(verticalAngle));

            float xOffset = horizontalDistance * (float)Math.sin(Math.toRadians(-horizontalAngle));
            float zOffset = horizontalDistance * (float)Math.cos(Math.toRadians(-horizontalAngle));

            this.transform().position.x = lookAt.transform.position.x + xOffset;
            this.transform().position.y = lookAt.transform.position.y + verticalDistance;
            this.transform().position.z = lookAt.transform.position.z + zOffset;

            this.transform().rotation.x = -verticalAngle;
            this.transform().rotation.y = -horizontalAngle;
        }
        // First Person
        else
        {
            float xMove = (float)Math.sin(Math.toRadians(this.transform().rotation.y)) * moveSpeed;
            float zMove = (float)Math.cos(Math.toRadians(this.transform().rotation.y)) * moveSpeed;

            if (Input.isKeyDown(GLFW_KEY_A)) { this.transform().position.x -= zMove; this.transform().position.z += xMove; }
            if (Input.isKeyDown(GLFW_KEY_D)) { this.transform().position.x += zMove; this.transform().position.z -= xMove; }
            if (Input.isKeyDown(GLFW_KEY_W)) { this.transform().position.x -= xMove; this.transform().position.z -= zMove; }
            if (Input.isKeyDown(GLFW_KEY_S)) { this.transform().position.x += xMove; this.transform().position.z += zMove; }
            if (Input.isKeyDown(GLFW_KEY_SPACE)) this.transform().position.y += moveSpeed;
            if (Input.isKeyDown(GLFW_KEY_LEFT_SHIFT)) this.transform().position.y -= moveSpeed;

            this.transform().rotation.x -= mouseDeltaY * sensitivity;
            this.transform().rotation.y -= mouseDeltaX * sensitivity;
        }

        oldMouseX = newMouseX;
        oldMouseY = newMouseY;
    }
}
