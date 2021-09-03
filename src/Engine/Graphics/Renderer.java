package Engine.Graphics;

import Engine.Math.*;
import Engine.Objects.*;
import Engine.IO.*;

import static org.lwjgl.opengl.GL46.*;

public class Renderer
{
    private Window window;
    private Shader shader;

    public Renderer(Window window, Shader shader)
    {
        this.window = window;
        this.shader = shader;
    }

    public void RenderMesh(GameObject object, Camera camera)
    {
        Mesh mesh = object.getMesh();

        glBindVertexArray(mesh.getVAO());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, mesh.getMaterial().textureID());
        shader.Bind();
        shader.SetUniform("model", Matrix4f.Transform(object.getPosition(), object.getRotation(), object.getScale()));
        shader.SetUniform("view", Matrix4f.View(camera.position, camera.rotation));
        shader.SetUniform("projection", window.getProjection());
        glDrawElements(GL_TRIANGLES, mesh.getIndices().length, GL_UNSIGNED_INT, 0);
        shader.Unbind();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }
}
