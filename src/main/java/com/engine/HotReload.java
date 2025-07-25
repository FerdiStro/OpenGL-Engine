package com.engine;

import com.engine.utils.JsonData;
import com.engine.utils.ScreenObject;
import com.engine.utils.Vector3D;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.joml.Math;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.NULL;


@Slf4j
public class HotReload {

    private static final String vertexShaderURL = "/home/ferdistro/Projects/Private/OpenGL-Engine/src/main/java/com/engine/shaders/test_vertex.vert";
    private static final String fragmentShaderURL = "/home/ferdistro/Projects/Private/OpenGL-Engine/src/main/java/com/engine/shaders/test_fragment.frag";
    private static final String verticaslURL = "/home/ferdistro/Projects/Private/OpenGL-Engine/src/main/java/com/engine/shaders/3DVerticals";
    private static final String jsonCoordinateList = "src/main/java/com/engine/shaders/Cords.json";
    private final Gson gson = new Gson();
    float moveX = 0;
    private float angle = 0f;
    private long window;
    private int shaderProgram, vao;
    private HotReloadUpdate hotReloadInterface;

    public static void main(String[] args) {
        new HotReload().init();
    }

    private String loadFile(String url) throws IOException {
        File file = new File(url);

        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append("\n");
        }

        reader.close();

        return stringBuilder.toString();


    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) {

            glClearColor(0f, 0f, 0f, 1f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glUseProgram(shaderProgram);

            glDisable(GL_CULL_FACE);
            glEnable(GL_DEPTH_TEST);


            //not effective but works
            hotReloadInterface.reload();

            //Move Object
//            moveX = moveX + 0.1f;
//            if (moveX > 5) {
//                moveX = -5.0f;
//            }


            // Update Transformation Matrices
            angle += 0.01f;

            //Camera distance
            Matrix4f view = new Matrix4f().translate(0, 0, -10);
            Matrix4f projection = new Matrix4f().perspective(Math.toRadians(45.0f), 800f / 600f, 0.1f, 100f);
            Matrix4f model = new Matrix4f().rotateY(angle).rotateX(angle);

            int modelLoc = glGetUniformLocation(shaderProgram, "model");
            int viewLoc = glGetUniformLocation(shaderProgram, "view");
            int projLoc = glGetUniformLocation(shaderProgram, "projection");

            FloatBuffer modelFb = BufferUtils.createFloatBuffer(16);
            FloatBuffer viewFb = BufferUtils.createFloatBuffer(16);
            FloatBuffer projFb = BufferUtils.createFloatBuffer(16);


            glUniformMatrix4fv(modelLoc, false, model.get(modelFb));
            glUniformMatrix4fv(viewLoc, false, view.get(viewFb));
            glUniformMatrix4fv(projLoc, false, projection.get(projFb));

            glBindVertexArray(vao);
            glDrawArrays(GL_TRIANGLES, 0, 9);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    private void initWindow() {
        glfwInit();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);


        window = glfwCreateWindow(800, 600, "Rotating Cube", NULL, NULL);


        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);

        GL.createCapabilities();


        try {

            this.hotReloadInterface = () -> {
                try {
                    loadShaderProgram();
                    loadVerticalsObject();
                } catch (Exception e) {
                    //ignore
                }

            };


            loadShaderProgram();
            loadVerticalsObject();


        } catch (Exception e) {
            e.printStackTrace();
            //ignore

        }


    }

    private void loadVerticalsObject() throws IOException {
        String json = loadFile(jsonCoordinateList);
        JsonData jsonData = new Gson().fromJson(json, TypeToken.getParameterized(JsonData.class).getType());

        ScreenObject screenObject = null;
        if (jsonData.aligns() == null) {
            if (jsonData.cords() == null) {
                log.error("CordList out of json is null");
                return;
            }
            screenObject = ScreenObject.getScreenObjectCordList(jsonData.cords());
        } else {
            screenObject = ScreenObject.getScreenObjectAligns(jsonData.aligns(), jsonData.cords());
        }

        if (screenObject == null) {
            log.error("ScreenObject is null");
            return;
        }

        screenObject.moveVector3D(new Vector3D(moveX, 0, 0));

        float[] primitiveArray = screenObject.toPrimitiveArray();

        vao = glGenVertexArrays();
        int vbo = glGenBuffers();

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, primitiveArray, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);


    }


    private void loadShaderProgram() throws IOException {
        String vertex = loadFile(vertexShaderURL);

        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertex);
        glCompileShader(vertexShader);
        checkCompileErrors(vertexShader, "VERTEX");

        String fragment = loadFile(fragmentShaderURL);
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fragment);
        glCompileShader(fragmentShader);
        checkCompileErrors(fragmentShader, "FRAGMENT");


        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    private void init() {
        initWindow();

        loop();

        glfwDestroyWindow(window);
        glfwTerminate();

    }

    private void checkCompileErrors(int shader, String type) {
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RuntimeException(type + " SHADER: " + glGetShaderInfoLog(shader));
        }
    }

}

