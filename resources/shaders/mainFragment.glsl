#version 460 core

in vec4 passColor;
in vec2 passTexture;

in vec3 surfaceNormal;
in vec3 toLightVector;

out vec4 outColor;

uniform sampler2D tex;
uniform vec3 lightColor;

void main()
{
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLight = normalize(toLightVector);

    float nDotl = dot(unitNormal, unitLight);
    float brightness = max(nDotl, 0.0);
    vec3 diffuse = brightness * lightColor;

    outColor = vec4(diffuse, 0.0) * texture(tex, passTexture) * passColor;
}