package demo;

import org.lwjgl.opengl.GL;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.joml.Math.sin;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class ShaderDemo {
    int shaderProgramm = 0;
    private long window;

    public static void main(String[] args) {
        new ShaderDemo().run();
    }

    public void run() {
        init();
        loop();

        glfwDestroyWindow(window);
        glfwTerminate();
    }

    private void init() {
        glfwInit();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        window = glfwCreateWindow(800, 600, "Hello Shader", NULL, NULL);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();



        int vao = glGenVertexArrays();
        int vbo = glGenBuffers();

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);



        // Triangle
//        float[] vertices = {
//                -0.5f, -0.5f, 0.0f,
//                0.5f, -0.5f, 0.0f,
//                0.0f, 0.5f, 0.0f
//        };
//        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
//        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
//        glEnableVertexAttribArray(0);



        //Square
        float[] verticesWithColor = {
                0.5f, -0.5f, 0.0f,  1.0f, 0.0f, 0.0f,   // bottom right
                -0.5f, -0.5f, 0.0f,  0.0f, 1.0f, 0.0f,   // bottom left
                0.0f,  0.5f, 0.0f,  0.0f, 0.0f, 1.0f
        };
        //first 3 values for position
        glBufferData(GL_ARRAY_BUFFER, verticesWithColor, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        //last 3 values for color
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);




        int vertexShader = loadShader("first_vert.vert", GL_VERTEX_SHADER);
        int fragmentShader = loadShader("first_frag.frag", GL_FRAGMENT_SHADER);
        int shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);


        this.shaderProgramm = shaderProgram;

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT);

            //User programm first to uniform value
            glUseProgram(this.shaderProgramm);


            float timeValue = (float) glfwGetTime();
            float greenValue = (sin(timeValue) / 2.0f) + 0.5f;

            int vertexColorLocation = glGetUniformLocation(this.shaderProgramm, "uniColor");

            if (vertexColorLocation == -1) {
//                System.out.println("Color location not found");
//                return;
            }
            glUniform4f(vertexColorLocation, 0.0f, greenValue, 0.0f, 1.0f);


            //Draw the triangle
            glDrawArrays(GL_TRIANGLES, 0, 3);

            //todo: write Shader class to load and use shader easy
//            https://learnopengl.com/Getting-started/Shaders



            // swap buffers and poll IO events
            glfwSwapBuffers(window);
            glfwPollEvents();


        }
    }

    private int loadShader(String fileName, int type) {
        String source;
        try {
            source = Files.readString(Paths.get("src/main/resources/" + fileName));
        } catch (IOException e) {
            throw new RuntimeException("Shader konnte nicht geladen werden: " + fileName, e);
        }

        int shader = glCreateShader(type);
        glShaderSource(shader, source);
        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RuntimeException("Shader Kompilierungsfehler: " + glGetShaderInfoLog(shader));
        }

        return shader;
    }
}
