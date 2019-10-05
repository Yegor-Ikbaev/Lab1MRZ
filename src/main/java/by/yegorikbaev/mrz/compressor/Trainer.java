package by.yegorikbaev.mrz.compressor;

import by.yegorikbaev.mrz.bean.Configuration;
import by.yegorikbaev.mrz.bean.Matrix;

@FunctionalInterface
public interface Trainer {
    Matrix[] train(Matrix firstLayer, Matrix secondLayer, Configuration configuration, Matrix[] vectors);
}