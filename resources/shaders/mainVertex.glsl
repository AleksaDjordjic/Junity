#version 460 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec4 color;
layout(location = 2) in vec2 texture;
layout(location = 3) in vec3 normal;

out vec4 passColor;
out vec2 passTexture;
out vec3 surfaceNormal;
out vec3 toLightVector;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;
uniform vec3 lightPosition;

void main()
{
    vec4 worldPosition = model * vec4(position, 1.0);

    gl_Position = projection * view * worldPosition;

    passColor = color;
    passTexture = texture;
    surfaceNormal = (model * vec4(normal, 0.0)).xyz;
    toLightVector = lightPosition - worldPosition.xyz;
}