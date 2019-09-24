package by.yegorikbaev.mrz.compressor.impl;

import by.yegorikbaev.mrz.bean.Configuration;
import by.yegorikbaev.mrz.bean.Matrix;
import by.yegorikbaev.mrz.bean.SplittedImage;
import by.yegorikbaev.mrz.compressor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

@Service
public class DefaultImageCompressor implements ImageCompressor {

    private static final Logger LOGGER = Logger.getLogger(DefaultImageCompressor.class.getName());

    private ImageSplitter imageSplitter;

    private ImageToMatrixConverter imageConverter;

    private WeightsGenerator weightsGenerator;

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
    public void setWeightsGenerator(WeightsGenerator weightsGenerator) {
        this.weightsGenerator = weightsGenerator;
    }

    @Autowired
    public void setImageRestorer(ImageRestorer imageRestorer) {
        this.imageRestorer = imageRestorer;
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
        Matrix[] resultWeights = train(firstLayerWeights, secondLayerWeights, configuration, vectors);
        long afterTraining = System.currentTimeMillis();
        convert(splittedImage, resultWeights);
        log(configuration, afterTraining - beforeTraining);
        return imageRestorer.restore(splittedImage);
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

    private Matrix[] train(Matrix firstLayer, Matrix secondLayer, Configuration configuration, Matrix[] vectors) {
        Matrix[] training = new Matrix[vectors.length];
        double summaryError;
        int iteration = 0;
        do {
            summaryError = 0.0;
            int indexOfMatrix = 0;
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
        double error = 0.0;
        for (double value : matrix.getAsArray()[0]) {
            error += value * value;
        }
        return error;
    }

    private void convert(@NotNull SplittedImage image, Matrix[] matrices) {
        int indexOfMatrix = 0;
        for (int indexOfWidth = 0; indexOfWidth < image.getRectanglesInWidth(); indexOfWidth++) {
            for (int indexOfHeight = 0; indexOfHeight < image.getRectanglesInHeight(); indexOfHeight++) {
                convertSubimage(image.getSubimages()[indexOfWidth][indexOfHeight],
                        image, matrices[indexOfMatrix++].getAsArray());
            }
        }
    }

    private void convertSubimage(BufferedImage subimage, SplittedImage image, double[][] matrix) {
        for (int indexOfWidth = 0; indexOfWidth < subimage.getWidth(); indexOfWidth++) {
            for (int indexOfHeight = 0; indexOfHeight < subimage.getHeight(); indexOfHeight++) {
                int coefficient = RGB_COEFFICIENT * (indexOfWidth + indexOfHeight * image.getHeight());
                int r = normalizeColor(matrix[0][coefficient]);
                int g = normalizeColor(matrix[0][1 + coefficient]);
                int b = normalizeColor(matrix[0][2 + coefficient]);
                subimage.setRGB(indexOfWidth, indexOfHeight, new Color(r, g, b).getRGB());
            }
        }
    }

    private static final int MAX_COLOR_VALUE = 255;

    private static final int MIN_COLOR_VALUE = 0;

    private int normalizeColor(double value) {
        int result = (int) (MAX_COLOR_VALUE * (value + 1) / 2);
        return Math.max(Math.min(result, MAX_COLOR_VALUE), MIN_COLOR_VALUE);
    }

    private void log(Configuration configuration, long time) {
        LOGGER.info(String.format("Maximal error = %f", configuration.getMaximalError()));
        LOGGER.info(String.format("Coefficient of training = %f", configuration.getCoefficientOfTraining()));
        LOGGER.info(String.format("Coefficient of compression = %f", configuration.getCoefficientOfCompression()));
        LOGGER.info(String.format("Neurons in hidden layer = %d", configuration.getNeuronsNumber()));
        LOGGER.info(String.format("Time for training = %d milliseconds", time));
    }
}