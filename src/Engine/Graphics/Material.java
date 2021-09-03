package Engine.Graphics;

import org.newdawn.slick.opengl.*;
import org.newdawn.slick.opengl.renderer.SGL;

import static org.lwjgl.opengl.GL46.*;

public class Material
{
    private String texturePath;
    private Texture texture;
    private float width, height;
    private int textureID;

    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public int textureID() {return textureID; }

    public Material(String texturePath)
    {
        this.texturePath = texturePath;
    }

    public void Create()
    {
        try
        {
            String[] pathDotSplit = texturePath.split("[.]");
            String extension = pathDotSplit[pathDotSplit.length - 1];

            texture = TextureLoader.getTexture(extension, Material.class.getResourceAsStream(texturePath), SGL.GL_NEAREST);
            width = texture.getWidth();
            height = texture.getHeight();
            textureID = texture.getTextureID();
        }
        catch (Exception exception)
        {
            System.err.println("Error Loading Texture: " + exception.toString());
        }
    }

    public void Destroy()
    {
        glDeleteTextures(textureID);
    }
}