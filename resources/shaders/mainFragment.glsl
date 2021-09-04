#version 460 core

in vec4 passColor;
in vec2 passTexture;

in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;

out vec4 outColor;

uniform sampler2D tex;
uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;

void main()
{
    // Diffuse Lighting
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLight = normalize(toLightVector);
    float nDotl = dot(unitNormal, unitLight);
    float brightness = max(nDotl, 0.2);
    vec3 diffuse = brightness * lightColor;

    // Specular Lighting
    vec3 unitToCamera = normalize(toCameraVector);
    vec3 lightDirection = -unitLight;
    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
    float specularFactor = dot(reflectedLightDirection, unitToCamera);
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow(specularFactor, shineDamper);
    vec3 finalSpecular = dampedFactor * lightColor * reflectivity;

    outColor = vec4(diffuse, 0.0) * texture(tex, passTexture) * passColor + vec4(finalSpecular, 1.0);
}