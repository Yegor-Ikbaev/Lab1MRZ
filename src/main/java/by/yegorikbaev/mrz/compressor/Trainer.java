package by.yegorikbaev.mrz.compressor;

import by.yegorikbaev.mrz.bean.Configuration;
import by.yegorikbaev.mrz.bean.Matrix;

/**
 * Interface for trainer of neural networks
 * Group 721702
 * @author  Yegor Ikbaev
 * @version 1.0
 * @since   2019-11-10
 */
@FunctionalInterface
public interface Trainer {
    Matrix[] train(Matrix firstLayer, Matrix secondLayer, Configuration configuration, Matrix[] vectors);
}