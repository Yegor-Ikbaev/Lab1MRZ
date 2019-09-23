package by.yegorikbaev.mrz.compressor.impl;

import by.yegorikbaev.mrz.bean.Matrix;
import by.yegorikbaev.mrz.compressor.WeightsGenerator;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.logging.Logger;

@Service
public class DefaultWeightsGenerator implements WeightsGenerator {

    private static final Logger LOGGER = Logger.getLogger(DefaultWeightsGenerator.class.getName());

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
        LOGGER.info(String.format("Created matrix of weights with rows = %d and columns = %d", rows, columns));
        return new Matrix(matrix);
    }

    private void validate(final int rows, final int columns) {
        if (rows < 0 || columns < 0) {
            String message = String.format("Rows(%d) number or columns(%d) number is negative", rows, columns);
            LOGGER.warning(message);
            throw new IllegalArgumentException(message);
        }
    }
}