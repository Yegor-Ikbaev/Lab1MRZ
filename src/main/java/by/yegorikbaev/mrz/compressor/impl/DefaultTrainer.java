package by.yegorikbaev.mrz.compressor.impl;

import by.yegorikbaev.mrz.bean.Configuration;
import by.yegorikbaev.mrz.bean.Matrix;
import by.yegorikbaev.mrz.compressor.Trainer;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Implementation of trainer of neural network
 * Group 721702
 *
 * @author Yegor Ikbaev
 * @version 1.0
 * @since 2019-11-10
 */
@Service
public class DefaultTrainer implements Trainer {

    private static final Logger LOGGER = Logger.getLogger(DefaultTrainer.class.getName());

    @Override
    public List<Matrix> train(@NotNull Matrix firstLayer, @NotNull Matrix secondLayer,
                              @NotNull Configuration configuration, @NotNull List<Matrix> vectors) {
        List<Matrix> result = new ArrayList<>();
        double summaryError;
        int epoch = 0;
        do {
            trainEpoch(firstLayer, secondLayer, configuration, vectors);
            summaryError = calculateErrorOfEpoch(firstLayer, secondLayer, vectors, result);
            epoch++;
            LOGGER.info(String.format("Summary error = %f in epoch = %d", summaryError, epoch));
        } while (summaryError > configuration.getMaximalError());
        LOGGER.info(String.format("Final summary error = %f in epoch = %d", summaryError, epoch));
        return result;
    }

    private void trainEpoch(Matrix firstLayer, Matrix secondLayer,
                            Configuration configuration, List<Matrix> vectors) {
        for (Matrix vector : vectors) {
            Matrix hiddenLayerNeurons = vector.multiply(firstLayer);
            Matrix outputLayerNeurons = hiddenLayerNeurons.multiply(secondLayer);
            Matrix delta = outputLayerNeurons.minus(vector);
            firstLayer.update(firstLayer.minus(vector
                    .transpose()
                    .multiply(configuration.getCoefficientOfTraining())
                    .multiply(delta)
                    .multiply(secondLayer.transpose())));
            secondLayer.update(secondLayer.minus(hiddenLayerNeurons
                    .transpose()
                    .multiply(configuration.getCoefficientOfTraining())
                    .multiply(delta)));
        }
    }

    private double calculateErrorOfEpoch(Matrix firstLayer, Matrix secondLayer,
                                         List<Matrix> vectors, List<Matrix> result) {
        result.clear();
        double summaryError = 0.0;
        for (Matrix vector : vectors) {
            Matrix outputLayerNeurons = vector.multiply(firstLayer).multiply(secondLayer);
            result.add(outputLayerNeurons);
            Matrix delta = outputLayerNeurons.minus(vector);
            summaryError += calculateError(delta);
        }
        return summaryError;
    }

    private double calculateError(Matrix matrix) {
        return Arrays.stream(matrix.getAsArray()[0]).map(this::multiplicity).sum();
    }

    private double multiplicity(double value) {
        return value * value;
    }
}