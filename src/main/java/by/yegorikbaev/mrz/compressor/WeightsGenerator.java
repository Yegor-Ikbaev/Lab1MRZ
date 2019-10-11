package by.yegorikbaev.mrz.compressor;

import by.yegorikbaev.mrz.bean.Matrix;

/**
 * Interface for generator of weights's matrix
 * Group 721702
 * @author  Yegor Ikbaev
 * @version 1.0
 * @since   2019-11-10
 */
@FunctionalInterface
public interface WeightsGenerator {
    Matrix generate(int rows, int columns);
}