package Engine.Components;

import Engine.GUI.*;
import Engine.Graphics.*;
import Engine.Objects.*;

public class GUIElement extends UIComponent
{
    public Material material;

    public GUIElement() {  }

    public GUIElement(Material material)
    {
        this.material = material;
    }

    @Override
    public void Setup() {
        super.Setup();
        material.Create();
        GUIRenderer.guiElements.add(this);
    }
}
