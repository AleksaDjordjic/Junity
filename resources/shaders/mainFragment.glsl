#version 460 core

in vec4 passColor;
in vec2 passTexture;

out vec4 outColor;

uniform sampler2D tex;

void main()
{
    outColor = texture(tex, passTexture) * passColor;
}