package Engine.Graphics;

import Engine.Math.*;
import Engine.Utils.*;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL46.*;

public class Shader
{
    private String vertexFile, fragmentFile;
    private int vertexID, fragmentID, programID;

    public Shader(String vertexPath, String fragmentPath)
    {
        vertexFile = FileUtils.LoadAsString(vertexPath);
        fragmentFile = FileUtils.LoadAsString(fragmentPath);

        System.out.println("");
    }

    private boolean created = false;
    public void Create()
    {
        if (created) return;
        created = true;
        
        programID = glCreateProgram();

        // Vertex Shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexID, vertexFile);
        glCompileShader(vertexID);

        if (glGetShaderi(vertexID, GL_COMPILE_STATUS) == GL_FALSE)
        {
            System.err.println("Vertex Shader Compile Error: " + glGetShaderInfoLog(vertexID));
            return;
        }

        // Fragment Shader
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentID, fragmentFile);
        glCompileShader(fragmentID);

        if (glGetShaderi(fragmentID, GL_COMPILE_STATUS) == GL_FALSE)
        {
            System.err.println("Fragment Shader Compile Error: " + glGetShaderInfoLog(fragmentID));
            return;
        }

        // Program setup
        glAttachShader(programID, vertexID);
        glAttachShader(programID, fragmentID);

        glLinkProgram(programID);
        if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE)
        {
            System.err.println("Program Linking Error: " + glGetProgramInfoLog(programID));
            return;
        }

        glValidateProgram(programID);
        if (glGetProgrami(programID, GL_VALIDATE_STATUS) == GL_FALSE)
        {
            System.err.println("Program Validate Error: " + glGetProgramInfoLog(programID));
            return;
        }

        glDeleteShader(vertexID);
        glDeleteShader(fragmentID);
    }

    public int GetUniformLocation(String name)
    {
        return glGetUniformLocation(programID, name);
    }

    public void SetUniform(String name, float value)
    {
        glUniform1f(GetUniformLocation(name), value);
    }

    public void SetUniform(String name, int value)
    {
        glUniform1i(GetUniformLocation(name), value);
    }

    public void SetUniform(String name, boolean value)
    {
        glUniform1i(GetUniformLocation(name), value ? 1 : 0);
    }

    public void SetUniform(String name, Vector2f value)
    {
        glUniform2f(GetUniformLocation(name), value.x, value.y);
    }

    public void SetUniform(String name, Vector3f value)
    {
        glUniform3f(GetUniformLocation(name), value.x, value.y, value.z);
    }

    public void SetUniform(String name, Vector4f value)
    {
        glUniform4f(GetUniformLocation(name), value.x, value.y, value.z, value.w);
    }

    public void SetUniform(String name, Matrix4f value)
    {
        FloatBuffer matrixBuffer = MemoryUtil.memAllocFloat(Matrix4f.SIZE * Matrix4f.SIZE);
        matrixBuffer.put(value.GetAll()).flip();

        glUniformMatrix4fv(GetUniformLocation(name), true, matrixBuffer);
    }

    public void Bind()
    {
        glUseProgram(programID);
    }

    public void Unbind()
    {
        glUseProgram(0);
    }

    public void Destroy()
    {
        glDetachShader(programID, vertexID);
        glDetachShader(programID, fragmentID);
        glDeleteProgram(programID);
    }
}
