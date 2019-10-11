package by.yegorikbaev.mrz.compressor.impl;

import by.yegorikbaev.mrz.bean.Configuration;
import by.yegorikbaev.mrz.bean.Matrix;
import by.yegorikbaev.mrz.compressor.Trainer;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Implementation of trainer of neural network
 * Group 721702
 * @author  Yegor Ikbaev
 * @version 1.0
 * @since   2019-11-10
 */
@Service
public class DefaultTrainer implements Trainer {

    private static final Logger LOGGER = Logger.getLogger(DefaultTrainer.class.getName());

    @Override
    public Matrix[] train(@NotNull Matrix firstLayer, @NotNull Matrix secondLayer,
                          @NotNull Configuration configuration, @NotNull Matrix[] vectors) {
        Matrix[] training = new Matrix[vectors.length];
        double summaryError;
        int iteration = 0;
        do {
            summaryError = 0.0;
            for (Matrix vector : vectors) {
                Matrix tempVector = vector.multiply(firstLayer);
                Matrix resultVector = tempVector.multiply(secondLayer);
                Matrix delta = resultVector.minus(vector);
                firstLayer.update(firstLayer.minus(vector
                        .transpose()
                        .multiply(configuration.getCoefficientOfTraining())
                        .multiply(delta)
                        .multiply(secondLayer.transpose())));
                secondLayer.update(secondLayer.minus(tempVector
                        .transpose()
                        .multiply(configuration.getCoefficientOfTraining())
                        .multiply(delta)));
            }
            int indexOfMatrix = 0;
            for (Matrix vector : vectors) {
                Matrix resultVector = vector.multiply(firstLayer).multiply(secondLayer);
                training[indexOfMatrix++] = resultVector;
                Matrix delta = resultVector.minus(vector);
                summaryError += calculateError(delta);
            }
            iteration++;
            LOGGER.info(String.format("Summary error = %f in iteration = %d", summaryError, iteration));
        } while (summaryError > configuration.getMaximalError());
        LOGGER.info(String.format("Final summary error = %f in iteration = %d", summaryError, iteration));
        return training;
    }

    private double calculateError(Matrix matrix) {
        return Arrays.stream(matrix.getAsArray()[0]).map(this::multiplicity).sum();
    }

    private double multiplicity(double value) {
        return value * value;
    }
}