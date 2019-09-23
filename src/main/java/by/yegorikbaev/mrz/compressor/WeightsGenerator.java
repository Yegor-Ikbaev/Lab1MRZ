package by.yegorikbaev.mrz.compressor;

import by.yegorikbaev.mrz.bean.Matrix;

@FunctionalInterface
public interface WeightsGenerator {
    Matrix generate(int rows, int columns);
}