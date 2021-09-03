package Engine.Objects;

import Engine.IO.Input;
import Engine.Math.*;

import static org.lwjgl.glfw.GLFW.*;

public class Camera
{
    public Vector3f position;
    public Vector3f rotation;
    public GameObject lookAt;
    public boolean isFirstPerson = true;

    public float moveSpeed = 0.02f;
    public float sensitivity = 0.2f;
    private double oldMouseX = 0, oldMouseY = 0;
    private double newMouseX = 0, newMouseY = 0;

    // Third Person Vars
    private float distance = 2.0f;
    private float horizontalAngle = 0, verticalAngle = 0;

    public Camera()
    {
        this.position = new Vector3f();
        this.rotation = new Vector3f();
    }

    public void Update()
    {
        newMouseX = Input.getMouseX();
        newMouseY = Input.getMouseY();
        float mouseDeltaX = (float)(newMouseX - oldMouseX);
        float mouseDeltaY = (float)(newMouseY - oldMouseY);

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

            position.x = lookAt.getPosition().x + xOffset;
            position.y = lookAt.getPosition().y + verticalDistance;
            position.z = lookAt.getPosition().z + zOffset;

            rotation.x = -verticalAngle;
            rotation.y = -horizontalAngle;
        }
        // First Person
        else
        {
            float xMove = (float)Math.sin(Math.toRadians(rotation.y)) * moveSpeed;
            float zMove = (float)Math.cos(Math.toRadians(rotation.y)) * moveSpeed;

            if (Input.isKeyDown(GLFW_KEY_A)) { position.x -= zMove; position.z += xMove; }
            if (Input.isKeyDown(GLFW_KEY_D)) { position.x += zMove; position.z -= xMove; }
            if (Input.isKeyDown(GLFW_KEY_W)) { position.x -= xMove; position.z -= zMove; }
            if (Input.isKeyDown(GLFW_KEY_S)) { position.x += xMove; position.z += zMove; }
            if (Input.isKeyDown(GLFW_KEY_SPACE)) position.y += moveSpeed;
            if (Input.isKeyDown(GLFW_KEY_LEFT_SHIFT)) position.y -= moveSpeed;

            rotation.x -= mouseDeltaY * sensitivity;
            rotation.y -= mouseDeltaX * sensitivity;
        }

        oldMouseX = newMouseX;
        oldMouseY = newMouseY;
    }

    public Camera(Vector3f position, Vector3f rotation)
    {
        this.position = position;
        this.rotation = rotation;
    }
}
