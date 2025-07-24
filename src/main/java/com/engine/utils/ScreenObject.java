package com.engine.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class ScreenObject implements Render3DObject {
    private final List<Triangle> triangles;


    private boolean hasChange = true;
    private float[] cachePrimitivArray = null;

    public static ScreenObject getScreenObjectAligns(ArrayList<String> aligns, List<Coords3D> coordList) {
        if (aligns.size() % 3 != 0) {
            log.error("Aligns List need to fill with triangle data. so the sie need to be multiple of 3");
            return null;
        }
        List<Triangle> triangles = new ArrayList<>();

        Map<String, Coords3D> coords2DMap = new HashMap<>();

        coordList.forEach(cord -> coords2DMap.put(cord.getSign(), cord));


        for (int i = 0; i < aligns.size(); i++) {
            if ((i+1) % 3 == 0) {
                Triangle triangle = new Triangle(triangles.size(), coords2DMap.get(aligns.get(i - 2)), coords2DMap.get(aligns.get(i - 1)), coords2DMap.get(aligns.get(i)));
                triangles.add(triangle);
            }
        }
        return new ScreenObject(triangles);
    }

    public static ScreenObject getScreenObjectCordList(ArrayList<Coords3D> coordList) {
        List<Triangle> triangles = new ArrayList<>();


        Coords3D[] coords3DS = new Coords3D[3];
        for (int i = 0; i < coordList.size(); i++) {
            coords3DS[i % 3] = coordList.get(i);
            if ((i + 1) % 3 == 0) {
                Triangle triangle = new Triangle(coordList.get(i).getTriangleId());
                triangle.setPoints(coords3DS);
                triangles.add(triangle);
            }
        }

        return new ScreenObject(triangles);
    }

    @Override
    public float[] toPrimitiveArray() {
        if (hasChange) {
            float[] primitiveArray = new float[9 * triangles.size()];
            for (int i = 0; i < triangles.size(); i++) {
                float[] points = triangles.get(i).toPrimitiveArray();
                System.arraycopy(points, 0, primitiveArray, i * 9, 9);
            }
            this.hasChange = false;
            this.cachePrimitivArray = primitiveArray;
            return primitiveArray;
        }
        return this.cachePrimitivArray;
    }

    @Override
    public void moveVector3D(Vector3D vector3D) {
        for (Triangle triangle : triangles) {
            triangle.moveVector3D(vector3D);
        }
        this.hasChange = true;
        toPrimitiveArray();
    }


}
