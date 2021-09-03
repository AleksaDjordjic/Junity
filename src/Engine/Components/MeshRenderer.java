package Engine.Components;

import Engine.Graphics.*;
import Engine.Objects.*;

public class MeshRenderer extends Component
{
    public Mesh mesh;
    public Material material;

    public MeshRenderer() {  }

    public MeshRenderer(Mesh mesh, Material material)
    {
        this.mesh = mesh;
        this.material = material;
    }

    @Override
    public void Setup()
    {
        mesh.Create();
        material.Create();
        Renderer.meshRenderers.add(this);
    }
}
