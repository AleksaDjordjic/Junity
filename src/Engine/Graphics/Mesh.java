package Engine.Graphics;

import Engine.Math.*;
import org.lwjgl.system.MemoryUtil;

import java.nio.*;

import static org.lwjgl.opengl.GL46.*;

public class Mesh
{
    private Vertex[] vertices;
    private int[] indices;

    private int vao, pbo, ibo, cbo, tbo, nbo;

    public Vertex[] getVertices() { return vertices; }
    public int[] getIndices() { return indices; }
    public int getVAO() { return vao; }
    public int getPBO() { return pbo; }
    public int getIBO() { return ibo; }
    public int getCBO() { return cbo; }
    public int getTBO() { return tbo; }
    public int getNBO() { return nbo; }

    public Mesh(Vertex[] vertices, int[] indices)
    {
        this.vertices = vertices;
        this.indices = indices;
    }

    private boolean created = false;
    public void Create()
    {
        if (created) return;
        created = true;

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        // Positions
        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        float[] positionData = new float[vertices.length * 3];
        for (int i = 0; i < vertices.length; i++) {
            Vector3f position =  vertices[i].getPosition();
            positionData[i * 3] = position.x;
            positionData[i * 3 + 1] = position.y;
            positionData[i * 3 + 2] = position.z;
        }
        positionBuffer.put(positionData).flip();
        pbo = StoreData(positionBuffer, 0, 3);

        // Color
        FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(vertices.length * 4);
        float[] colorData = new float[vertices.length * 4];
        for (int i = 0; i < vertices.length; i++) {
            Vector4f color =  vertices[i].getColor();
            colorData[i * 4] = color.x;
            colorData[i * 4 + 1] = color.y;
            colorData[i * 4 + 2] = color.z;
            colorData[i * 4 + 3] = color.w;
        }
        colorBuffer.put(colorData).flip();
        cbo = StoreData(colorBuffer, 1, 4);

        // Texture
        FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(vertices.length * 2);
        float[] textureData = new float[vertices.length * 2];
        for (int i = 0; i < vertices.length; i++) {
            Vector2f texture =  vertices[i].getTexture();
            textureData[i * 2] = texture.x;
            textureData[i * 2 + 1] = texture.y;
        }
        textureBuffer.put(textureData).flip();
        tbo = StoreData(textureBuffer, 2, 2);

        // Indices
        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        indicesBuffer.put(indices).flip();

        // Normals
        FloatBuffer normalsBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        float[] normalsData = new float[vertices.length * 3];
        for (int i = 0; i < vertices.length; i++) {
            Vector3f normal =  vertices[i].getNormal();
            normalsData[i * 3] = normal.x;
            normalsData[i * 3 + 1] = normal.y;
            normalsData[i * 3 + 2] = normal.z;
        }
        normalsBuffer.put(normalsData).flip();
        nbo = StoreData(normalsBuffer, 3, 3);

        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    private int StoreData(Buffer buffer, int index, int size) {
        int bufferID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, bufferID);

        if (buffer instanceof FloatBuffer)
        {
            glBufferData(GL_ARRAY_BUFFER, (FloatBuffer) buffer, GL_STATIC_DRAW);
            glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
        }
        else if (buffer instanceof IntBuffer)
        {
            glBufferData(GL_ARRAY_BUFFER, (IntBuffer) buffer, GL_STATIC_DRAW);
            glVertexAttribPointer(index, size, GL_INT, false, 0, 0);
        }

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        return bufferID;
    }

    public void Destroy()
    {
        glDeleteBuffers(pbo);
        glDeleteProgram(cbo);
        glDeleteBuffers(ibo);
        glDeleteBuffers(tbo);
        glDeleteBuffers(nbo);

        glDeleteVertexArrays(vao);
    }
}