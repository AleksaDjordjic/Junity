package Engine.GUI;

import Engine.Components.*;
import Engine.Graphics.*;
import Engine.IO.*;
import Engine.Managers.*;
import Engine.Math.*;
import java.util.*;

import static org.lwjgl.opengl.GL46.*;

public final class GUIRenderer
{
    public static List<GUIElement> guiElements = new ArrayList<>();
    private static Window window;
    private static Mesh rectMesh;

    public static Mesh getRectMesh() { return rectMesh; }

    public static void Setup(Window window)
    {
        GUIRenderer.window = window;

        rectMesh = new Mesh(new Vertex[] {
            new Vertex(new Vector3f(-.5f, -.5f, 0)),
            new Vertex(new Vector3f(.5f, -.5f, 0)),
            new Vertex(new Vector3f(.5f, .5f, 0)),
            new Vertex(new Vector3f(-.5f, .5f, 0)),
        }, new int[] {
            0, 1, 3,
            3, 1, 2,
        });

        rectMesh.CreateSimple();
    }

    public static void Render()
    {
        BeforeRender();

        for (GUIElement element : guiElements)
        {
            Material material = element.material;
            glBindTexture(GL_TEXTURE_2D, material.getTextureID());

            material.getShader().Bind();
            material.getShader().SetUniform("transform", Matrix4f.Transform(element.rectTransform().position, element.rectTransform().scale));

            glDrawElements(GL_TRIANGLES, rectMesh.getIndices().length, GL_UNSIGNED_INT, 0);
            material.getShader().Unbind();
        }

        AfterRender();
    }

    private static void BeforeRender()
    {
        glBindVertexArray(rectMesh.getVAO());
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, rectMesh.getIBO());
        glActiveTexture(GL_TEXTURE0);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_DEPTH_TEST);
    }

    private static void AfterRender()
    {
        glEnable(GL_DEPTH_TEST);
        glDisable(GL_BLEND);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }
}
