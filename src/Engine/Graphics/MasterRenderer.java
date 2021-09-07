package Engine.Graphics;

import Engine.Components.*;
import Engine.Managers.LightManager;
import Engine.Math.*;
import Engine.Objects.*;
import Engine.IO.*;

import java.util.*;

import static org.lwjgl.opengl.GL46.*;

public final class MasterRenderer
{
    public static List<MeshRenderer> meshRenderers = new ArrayList<>();
    private static Window window;

    public static void Setup(Window window)
    {
        MasterRenderer.window = window;
    }

    public static void Render()
    {
        for (MeshRenderer renderer : meshRenderers)
        {
            RenderMesh(renderer.transform(), renderer.mesh, renderer.material, window.camera.transform());
        }
    }
    
    private static void RenderMesh(Transform transform, Mesh mesh, Material material, Transform camera)
    {
        glBindVertexArray(mesh.getVAO());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glEnableVertexAttribArray(3);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, material.getTextureID());

        material.getShader().Bind();
        material.getShader().SetUniform("transform", Matrix4f.Transform(transform.position, transform.rotation, transform.scale));
        material.getShader().SetUniform("view", Matrix4f.View(camera.position, camera.rotation));
        material.getShader().SetUniform("projection", window.getProjection());

        if(!LightManager.lights.isEmpty())
        {
            Light light = LightManager.lights.get(0);
            material.getShader().SetUniform("lightPosition", light.transform().position);
            material.getShader().SetUniform("lightColor", light.color);
        }
        material.getShader().SetUniform("shineDamper", material.shineDamper);
        material.getShader().SetUniform("reflectivity", material.reflectivity);
        material.getShader().SetUniform("skyColor", LightManager.skyColor);

        glDrawElements(GL_TRIANGLES, mesh.getIndices().length, GL_UNSIGNED_INT, 0);
        material.getShader().Unbind();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        glEnableVertexAttribArray(3);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }
}
