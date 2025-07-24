# Different Render Objects

2D-Shapes: https://www.twinkl.de/teaching-wiki/2d-shapes <br>
3D-Shapes: https://www.twinkl.de/teaching-wiki/3d-shapes

### Triangle

```
A\
|  \
|    C
|  /
B/
```

Java-Code:

```java
Triangle triangle_1 = new Triangle(1, new Coords2D(0.0f, 1.0f), new Coords2D(0.0f, 0.0f), new Coords2D(1.0f, 1.0f));
```

example json:

```json
[
  { "sign": "A", "triangleId": 1, "x": 0.0, "y": 1.0, "z": 0.0},
  { "sign": "B", "triangleId": 1, "x": 0.0, "y": 0.0, "z": 0.0},
  { "sign": "C", "triangleId": 1, "x": 0.5, "y": 0.5, "z": 0.0}
]
```

### 2 Triangle Facing

```
A     D
|\   /|
| \ / |
|  C  |
| / \ |
|/   \|
B     E
```

example json:

```json
[
  { "sign": "A", "triangleId": 1, "x": 0.0, "y": 1.0, "z": 0.0},
  { "sign": "B", "triangleId": 1, "x": 0.0, "y": 0.0, "z": 0.0},
  { "sign": "C", "triangleId": 1, "x": 0.5, "y": 0.5, "z": 0.0},
  { "sign": "C", "triangleId": 2, "x": 0.5, "y": 0.5, "z": 0.0},
  { "sign": "E", "triangleId": 2, "x": 1.0, "y": 0.0, "z": 0.0},
  { "sign": "D", "triangleId": 2, "x": 1.0, "y": 1.0, "z": 0.0}
]
```

### Square

```
A--B
| /|
|/ |
C--D
```

example json:

```json

[
  { "sign": "A", "triangleId": 1, "x": 0.0, "y": 1.0, "z": 0.0},
  { "sign": "B", "triangleId": 1, "x": 1.0, "y": 1.0, "z": 0.0},
  { "sign": "C", "triangleId": 1, "x": 0.0, "y": 0.0, "z": 0.0}, 
  
  { "sign": "C", "triangleId": 2, "x": 0.0, "y": 0.0, "z": 0.0},
  { "sign": "B", "triangleId": 2, "x": 1.0, "y": 1.0, "z": 0.0},
  { "sign": "D", "triangleId": 2, "x": 1.0, "y": 0.0, "z": 0.0}
]
```

### Cube

```
     E--------F
    /|       /|
   / |      / |
  A--------B  |
  |  |     |  |
  |  H-----|--G
  | /      | /
  |/       |/
  D--------C
```
example json:
```json
{
  "aligns": [
    "A", "B", "D", "D", "B", "C",
    "B", "C", "G", "A", "F", "G",
    "D", "A", "H", "A", "F", "G"
  ],
  "cords": [
    { "sign": "A", "x": 0.0, "y": 1.0, "z": 0.0 },
    { "sign": "B", "x": 1.0, "y": 1.0, "z": 0.0 },
    { "sign": "C", "x": 1.0, "y": 0.0, "z": 0.0 },
    { "sign": "D", "x": 0.0, "y": 0.0, "z": 0.0 },
    { "sign": "E", "x": 0.0, "y": 1.0, "z": -1.0 },
    { "sign": "F", "x": 1.0, "y": 1.0, "z": -1.0 },
    { "sign": "G", "x": 1.0, "y": 0.0, "z": -1.0 },
    { "sign": "H", "x": 0.0, "y": 0.0, "z": -1.0 }
  ]
}
```
```json
[
  { "sign": "A", "triangleId": 1, "x": -1.0, "y": 1.0, "z": 1.0 },
  { "sign": "B", "triangleId": 1, "x": 1.0,  "y": 1.0, "z": 1.0 },
  { "sign": "C", "triangleId": 1, "x": 1.0,  "y": -1.0, "z": 1.0 },

  { "sign": "A", "triangleId": 2, "x": -1.0, "y": 1.0, "z": 1.0 },
  { "sign": "C", "triangleId": 2, "x": 1.0,  "y": -1.0, "z": 1.0 },
  { "sign": "D", "triangleId": 2, "x": -1.0, "y": -1.0, "z": 1.0 },

  { "sign": "E", "triangleId": 3, "x": -1.0, "y": 1.0, "z": -1.0 },
  { "sign": "F", "triangleId": 3, "x": 1.0,  "y": 1.0, "z": -1.0 },
  { "sign": "B", "triangleId": 3, "x": 1.0,  "y": 1.0, "z": 1.0 },

  { "sign": "E", "triangleId": 4, "x": -1.0, "y": 1.0, "z": -1.0 },
  { "sign": "B", "triangleId": 4, "x": 1.0,  "y": 1.0, "z": 1.0 },
  { "sign": "A", "triangleId": 4, "x": -1.0, "y": 1.0, "z": 1.0 },

  { "sign": "F", "triangleId": 5, "x": 1.0,  "y": 1.0, "z": -1.0 },
  { "sign": "G", "triangleId": 5, "x": 1.0,  "y": -1.0, "z": -1.0 },
  { "sign": "C", "triangleId": 5, "x": 1.0,  "y": -1.0, "z": 1.0 },

  { "sign": "F", "triangleId": 6, "x": 1.0,  "y": 1.0, "z": -1.0 },
  { "sign": "C", "triangleId": 6, "x": 1.0,  "y": -1.0, "z": 1.0 },
  { "sign": "B", "triangleId": 6, "x": 1.0,  "y": 1.0, "z": 1.0 },

  { "sign": "G", "triangleId": 7, "x": 1.0,  "y": -1.0, "z": -1.0 },
  { "sign": "H", "triangleId": 7, "x": -1.0, "y": -1.0, "z": -1.0 },
  { "sign": "D", "triangleId": 7, "x": -1.0, "y": -1.0, "z": 1.0 },

  { "sign": "G", "triangleId": 8, "x": 1.0,  "y": -1.0, "z": -1.0 },
  { "sign": "D", "triangleId": 8, "x": -1.0, "y": -1.0, "z": 1.0 },
  { "sign": "C", "triangleId": 8, "x": 1.0,  "y": -1.0, "z": 1.0 },

  { "sign": "H", "triangleId": 9, "x": -1.0, "y": -1.0, "z": -1.0 },
  { "sign": "E", "triangleId": 9, "x": -1.0, "y": 1.0, "z": -1.0 },
  { "sign": "A", "triangleId": 9, "x": -1.0, "y": 1.0, "z": 1.0 },

  { "sign": "H", "triangleId": 10, "x": -1.0, "y": -1.0, "z": -1.0 },
  { "sign": "A", "triangleId": 10, "x": -1.0, "y": 1.0, "z": 1.0 },
  { "sign": "D", "triangleId": 10, "x": -1.0, "y": -1.0, "z": 1.0 },

  { "sign": "E", "triangleId": 11, "x": -1.0, "y": 1.0, "z": -1.0 },
  { "sign": "F", "triangleId": 11, "x": 1.0,  "y": 1.0, "z": -1.0 },
  { "sign": "G", "triangleId": 11, "x": 1.0,  "y": -1.0, "z": -1.0 },

  { "sign": "E", "triangleId": 12, "x": -1.0, "y": 1.0, "z": -1.0 },
  { "sign": "G", "triangleId": 12, "x": 1.0,  "y": -1.0, "z": -1.0 },
  { "sign": "H", "triangleId": 12, "x": -1.0, "y": -1.0, "z": -1.0 }
]
```