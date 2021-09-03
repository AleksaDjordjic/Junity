package Engine.IO;

import Engine.Math.*;
import Engine.Graphics.*;
import org.lwjgl.assimp.*;

import static org.lwjgl.assimp.Assimp.*;

public class ModelLoader
{
    public static Mesh LoadModel(String modelPath, String texturePath)
    {
        AIScene scene = aiImportFile(modelPath, aiProcess_JoinIdenticalVertices | aiProcess_Triangulate);

        if (scene == null)
        {
            System.err.println("Couldn't Load Model @ " + modelPath);
            return null;
        }

        AIMesh mesh = AIMesh.create(scene.mMeshes().get(0));
        int vertexCount = mesh.mNumVertices();

        AIVector3D.Buffer vertices = mesh.mVertices();
        AIVector3D.Buffer normals = mesh.mNormals();

        Vertex[] finalVertices = new Vertex[vertexCount];

        for (int i = 0; i < vertexCount; i++)
        {
            AIVector3D vertex = vertices.get(i);
            Vector3f meshVertex = new Vector3f(vertex.x(), vertex.y(), vertex.z());

            AIVector3D normal = normals.get(i);
            Vector3f meshNormal = new Vector3f(normal.x(), normal.y(), normal.z());

            Vector2f meshTexture = new Vector2f(0, 0);
            if (mesh.mNumUVComponents().get(0) != 0)
            {
                AIVector3D texture = mesh.mTextureCoords(0).get(i);
                meshTexture.x = texture.x();
                meshTexture.y = texture.y();
            }

            finalVertices[i] = new Vertex(meshVertex, meshTexture, meshNormal);
        }

        int faceCount = mesh.mNumFaces();
        AIFace.Buffer indices = mesh.mFaces();
        int[] finalIndices = new int[faceCount * 3];

        for (int i = 0; i < faceCount; i++) {
            AIFace face = indices.get(i);
            finalIndices[i * 3] = face.mIndices().get(0);
            finalIndices[i * 3 + 1] = face.mIndices().get(1);
            finalIndices[i * 3 + 2] = face.mIndices().get(2);
        }

        return new Mesh(finalVertices, finalIndices);
    }
}
