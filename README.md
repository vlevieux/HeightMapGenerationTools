# HeightMapGenerationTools

This is a Java Tool for generating random heightmaps with GUI.

This offers few tools to generate heightmaps. The main idea is that algorithm can be easily added to this software. The GUI is realised with JavaFX.

```
|-- src/
|   |-- models
|   |   |-- AlgorithmModel.java <-- Abstract class that allows parallel processing
|   |   |-- Hill.java
|   |   |-- Random.java
|   |   |-- SquareDiamond.java
|   |-- Map.java
|   |...
```

# Included Algorithms

- Hill (krarius, iteration)
- Square Diamond (variance, topleft, topright, bottomleft, bottomright)
- Random (min, max)

# Features

- Embedded database to store statistics and results
- Log in real time
- Parallel processing for alogithms

# Next Features

- Outsource algorithms for easier importation
