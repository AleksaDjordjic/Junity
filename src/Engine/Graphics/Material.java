package Engine.Graphics;

import org.newdawn.slick.opengl.*;
import org.newdawn.slick.opengl.renderer.SGL;

import static org.lwjgl.opengl.GL46.*;

public class Material
{
    private Shader shader;
    private String texturePath;
    private Texture texture;
    private float width, height;
    private int textureID;

    public float reflectivity = 0;
     public float shineDamper = 1;

    public Shader getShader() { return shader; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public int getTextureID() {return textureID; }

    public Material(Shader shader, String texturePath)
    {
        this.shader = shader;
        this.texturePath = texturePath;
    }

    private boolean created = false;
    public void Create()
    {
        if (created) return;
        created = true;

        shader.Create();

        try
        {
            String[] pathDotSplit = texturePath.split("[.]");
            String extension = pathDotSplit[pathDotSplit.length - 1];

            texture = TextureLoader.getTexture(extension, Material.class.getResourceAsStream(texturePath));
            glGenerateMipmap(GL_TEXTURE_2D);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_LOD_BIAS, -1);

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