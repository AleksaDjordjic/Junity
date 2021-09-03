package Engine.Graphics;

import Engine.Components.*;
import Engine.Math.*;
import Engine.Objects.*;
import Engine.IO.*;

import java.util.*;

import static org.lwjgl.opengl.GL46.*;

public class Renderer
{
    public static List<MeshRenderer> meshRenderers = new ArrayList<>();
    private static Window window;

    public static void Setup(Window window)
    {
        Renderer.window = window;
    }

    public static void Render()
    {
        for (var renderer : meshRenderers) {
            RenderMesh(renderer.transform(), renderer.mesh, renderer.material, window.camera.transform());
        }
    }
    
    private static void RenderMesh(Transform transform, Mesh mesh, Material material, Transform camera)
    {
        glBindVertexArray(mesh.getVAO());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, material.textureID());
        material.getShader().Bind();
        material.getShader().SetUniform("model", Matrix4f.Transform(transform.position, transform.rotation, transform.scale));
        material.getShader().SetUniform("view", Matrix4f.View(camera.position, camera.rotation));
        material.getShader().SetUniform("projection", window.getProjection());
        glDrawElements(GL_TRIANGLES, mesh.getIndices().length, GL_UNSIGNED_INT, 0);
        material.getShader().Unbind();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }
}
