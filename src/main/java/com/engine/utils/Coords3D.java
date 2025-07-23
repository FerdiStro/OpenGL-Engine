package com.engine.utils;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coords3D {


    private float x;
    private float y;
    private float z;

    private String sign;
    private Integer triangleId;

    public Coords3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float[] getPoints() {
        return new float[]{x, y, z};
    }

    public void moveVector3D(Vector3D vector3D) {
     this.x += vector3D.x();
     this.y += vector3D.y();
     this.z += vector3D.z();
    }



}
