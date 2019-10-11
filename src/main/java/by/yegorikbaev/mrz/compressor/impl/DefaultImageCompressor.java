package by.yegorikbaev.mrz.compressor.impl;

import by.yegorikbaev.mrz.bean.Configuration;
import by.yegorikbaev.mrz.bean.Matrix;
import by.yegorikbaev.mrz.bean.SplittedImage;
import by.yegorikbaev.mrz.bean.TrainingResult;
import by.yegorikbaev.mrz.compressor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

/**
 * Implementation of Compressor
 * Group 721702
 * @author  Yegor Ikbaev
 * @version 1.0
 * @since   2019-11-10
 */
@Service
public class DefaultImageCompressor implements ImageCompressor {

    private static final Logger LOGGER = Logger.getLogger(DefaultImageCompressor.class.getName());

    private ImageSplitter imageSplitter;

    private ImageToMatrixConverter imageConverter;

    private MatrixToImageConverter matrixConverter;

    private WeightsGenerator weightsGenerator;

    private Trainer trainer;

    private ImageRestorer imageRestorer;

    @Autowired
    public void setImageSplitter(ImageSplitter imageSplitter) {
        this.imageSplitter = imageSplitter;
    }

    @Autowired
    public void setImageConverter(ImageToMatrixConverter imageConverter) {
        this.imageConverter = imageConverter;
    }

    @Autowired
    public void setMatrixConverter(MatrixToImageConverter matrixConverter) {
        this.matrixConverter = matrixConverter;
    }

    @Autowired
    public void setWeightsGenerator(WeightsGenerator weightsGenerator) {
        this.weightsGenerator = weightsGenerator;
    }

    @Autowired
    public void setImageRestorer(ImageRestorer imageRestorer) {
        this.imageRestorer = imageRestorer;
    }

    @Autowired
    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    @Override
    public BufferedImage compress(@NotNull BufferedImage sourceImage, @NotNull Configuration configuration) {
        SplittedImage splittedImage =
                imageSplitter.split(sourceImage, configuration.getWidth(), configuration.getHeight());
        prepareConfiguration(splittedImage, configuration);
        Matrix[] vectors = imageConverter.convert(splittedImage);
        Matrix firstLayerWeights =
                weightsGenerator.generate(configuration.getPixelsInRectangle(), configuration.getNeuronsNumber());
        Matrix secondLayerWeights = firstLayerWeights.transpose();
        long beforeTraining = System.currentTimeMillis();
        Matrix[] resultWeights = trainer.train(firstLayerWeights, secondLayerWeights, configuration, vectors);
        long afterTraining = System.currentTimeMillis();
        log(configuration, afterTraining - beforeTraining);
        return imageRestorer.restore(matrixConverter.convert(new TrainingResult(splittedImage, resultWeights)));
    }

    private static final int RGB_COEFFICIENT = 3;

    private void prepareConfiguration(SplittedImage splittedImage, Configuration configuration) {
        configuration.setTotalRectangles(splittedImage.getTotalRectangles());
        int pixelsInRectangle = configuration.getWidth() * configuration.getHeight() * RGB_COEFFICIENT;
        configuration.setPixelsInRectangle(pixelsInRectangle);
        double coefficientOfCompression = (1.0 * pixelsInRectangle * configuration.getTotalRectangles()) /
                ((pixelsInRectangle + configuration.getTotalRectangles()) * configuration.getNeuronsNumber() + 2);
        configuration.setCoefficientOfCompression(coefficientOfCompression);
    }

    private void log(Configuration configuration, long time) {
        LOGGER.info(String.format("Maximal error = %f", configuration.getMaximalError()));
        LOGGER.info(String.format("Coefficient of training = %f", configuration.getCoefficientOfTraining()));
        LOGGER.info(String.format("Coefficient of compression = %f", configuration.getCoefficientOfCompression()));
        LOGGER.info(String.format("Neurons in hidden layer = %d", configuration.getNeuronsNumber()));
        LOGGER.info(String.format("Time for training = %d milliseconds", time));
    }
}