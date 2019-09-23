package by.yegorikbaev.mrz.compressor.impl;

import by.yegorikbaev.mrz.bean.Matrix;
import by.yegorikbaev.mrz.compressor.WeightsGenerator;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class DefaultWeightsGenerator implements WeightsGenerator {

    @Override
    public Matrix generate(final int rows, final int columns) {
        validate(rows, columns);
        SecureRandom random = new SecureRandom();
        double[][] matrix = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = random.nextDouble() * 2 - 1;
            }
        }
        return new Matrix(matrix);
    }

    private void validate(final int rows, final int columns) {
        if (rows < 0 || columns < 0) {
            throw new IllegalArgumentException("Rows number or columns number is negative");
        }
    }
}