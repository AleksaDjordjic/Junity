#version 460 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec4 color;
layout(location = 2) in vec2 texture;
layout(location = 3) in vec3 normal;

out vec4 passColor;
out vec2 passTexture;
out vec3 surfaceNormal;
out vec3 toLightVector;
out vec3 toCameraVector;
out float visibility;

uniform mat4 transform;
uniform mat4 view;
uniform mat4 projection;
uniform vec3 lightPosition;

const float density = 0.007;
const float gradient = 1.5;

void main()
{
    vec4 worldPosition = transform * vec4(position, 1.0);
    vec4 positionRelativeToCamera = view * worldPosition;
    gl_Position = projection * positionRelativeToCamera;

    passColor = color;
    passTexture = texture;
    surfaceNormal = (transform * vec4(normal, 0.0)).xyz;
    toLightVector = lightPosition - worldPosition.xyz;
    toCameraVector = (inverse(view) * vec4(0, 0, 0, 1.0)).xyz - worldPosition.xyz;

    float distance = length(positionRelativeToCamera.xyz);
    visibility = exp(-pow((distance * density), gradient));
    visibility = clamp(visibility, 0.0, 1.0);
}