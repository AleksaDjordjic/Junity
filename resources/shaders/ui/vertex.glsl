#version 460 core

in vec3 position;

out vec2 textureCoords;

uniform mat4 transform;

void main()
{
    gl_Position = transform * vec4(position, 1.0);
    textureCoords = vec2((position.x + 1.0) / 2.0, 1 - (position.y + 1.0) / 2.0);
}