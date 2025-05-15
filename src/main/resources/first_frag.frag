#version 330 core

out vec4 FragColor;

in vec3 ourColor;

uniform vec4 uniColor; // we set this variable in the OpenGL code.


void main() {
    //Color in Vev4 : t  g p r
//    FragColor = vec4(1, 0.0, 0.0, 1.0);
//    vec4 red = vec4(1.0, 0.0, 0.0, 1.0);



    FragColor = vec4(ourColor, 1.0) ;


//    FragColor = uniColor;



}
