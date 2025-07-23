package com.engine.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class Triangle implements Render3DObject {
    private final Integer triangleId;
    @Getter
    private final Coords3D[] coords = new Coords3D[3];
    private final float[] cachePrimitivArray = new float[9];
    private boolean hasChange = true;


    public Triangle(Integer triangleId, Coords2D cordA, Coords2D cordB, Coords2D cordC) {
        this.triangleId = triangleId;
        coords[0] = cordA;
        coords[1] = cordB;
        coords[2] = cordC;
        this.toPrimitiveArray();
    }

    public Triangle(Integer triangleId, Coords3D cordA, Coords3D cordB, Coords3D cordC) {
        this.triangleId = triangleId;

        coords[0] = cordA;
        coords[1] = cordB;
        coords[2] = cordC;
        this.toPrimitiveArray();
    }

    @Override
    public float[] toPrimitiveArray() {
        if (hasChange) {
            System.arraycopy(coords[0].getPoints(), 0, cachePrimitivArray, 0, 3);
            System.arraycopy(coords[1].getPoints(), 0, cachePrimitivArray, 3, 3);
            System.arraycopy(coords[2].getPoints(), 0, cachePrimitivArray, 6, 3);
            this.hasChange = false;
        }
        return this.cachePrimitivArray;
    }

    @Override
    public void moveVector3D(Vector3D vector3D) {
        for (int i = 0; i < 3; i++) {
            coords[i].moveVector3D(vector3D);
        }
    }

    public void setPoints(Coords3D[] coords) {
        if (coords.length != 3) {
            log.error("Triangle only can have 3 points, so Cords3D array must have 3 elements. Not: {}", coords.length);
        }
        for (int i = 0; i < 3; i++) {
            float[] cordPoints = coords[i].getPoints();
            this.coords[i] = coords[i];
            System.arraycopy(cordPoints, 0, cachePrimitivArray, i * 3, 3);
        }
    }

}
