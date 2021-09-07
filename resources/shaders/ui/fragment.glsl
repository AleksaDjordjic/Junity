#version 460 core

in vec2 textureCoords;

out vec4 outColor;

uniform sampler2D guiTexture;

void main()
{
    outColor = texture(guiTexture,textureCoords);
}