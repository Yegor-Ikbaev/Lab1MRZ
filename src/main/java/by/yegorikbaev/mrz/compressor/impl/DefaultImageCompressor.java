package by.yegorikbaev.mrz.compressor.impl;

import by.yegorikbaev.mrz.bean.Configuration;
import by.yegorikbaev.mrz.bean.Matrix;
import by.yegorikbaev.mrz.bean.SplittedImage;
import by.yegorikbaev.mrz.compressor.ImageCompressor;
import by.yegorikbaev.mrz.converter.ImageToMatrixConverter;
import by.yegorikbaev.mrz.restorer.Restorer;
import by.yegorikbaev.mrz.splitter.ImageSplitter;
import by.yegorikbaev.mrz.weightsgenerator.WeightsGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class DefaultImageCompressor implements ImageCompressor {

    private ImageSplitter imageSplitter;

    private ImageToMatrixConverter imageConverter;

    private WeightsGenerator weightsGenerator;

    private Restorer restorer;

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
    public void setRestorer(Restorer restorer) {
        this.restorer = restorer;
    }

    @Override
    public BufferedImage compress(@NotNull BufferedImage sourceImage, @NotNull Configuration configuration) {
        SplittedImage splittedImage = imageSplitter.split(sourceImage, configuration.getWidth(), configuration.getHeight());
        prepareConfiguration(splittedImage, configuration);
        Matrix[] vectors = imageConverter.convert(splittedImage);
        Matrix firstLayer = weightsGenerator.generate(configuration.getWidth() * configuration.getHeight() * 3, configuration.getNeuronsNumber());
        Matrix secondLayer = firstLayer.transpose();
        Matrix[] training = train(firstLayer, secondLayer, configuration, vectors);
        convert(splittedImage, training);
        return restorer.restore(splittedImage);
    }

    private void prepareConfiguration(SplittedImage splittedImage, Configuration configuration) {
        configuration.setElementsNumber(splittedImage.getTotalRectangles());
    }

    private Matrix[] train(Matrix firstLayer, Matrix secondLayer, Configuration configuration, Matrix[] vectors) {
        Matrix[] training = new Matrix[vectors.length];
        double E;
        int index;
        int iteration = 0;
        do {
            E = 0.0;
            index = 0;
            for (Matrix vector : vectors) {
                Matrix Y = vector.multiply(firstLayer);
                Matrix X_ = Y.multiply(secondLayer);
                Matrix dX = X_.minus(vector);
                firstLayer.update(firstLayer.minus(vector.transpose().multiply(configuration.getCoefficientOfTraining()).multiply(dX).multiply(secondLayer.transpose())));
                secondLayer.update(secondLayer.minus(Y.transpose().multiply(configuration.getCoefficientOfTraining()).multiply(dX)));
            }
            for (Matrix vector : vectors) {
                Matrix Y = vector.multiply(firstLayer);
                Matrix X_ = Y.multiply(secondLayer);
                training[index++] = X_;
                Matrix dX = X_.minus(vector);
                E += dX.multiply(dX.transpose()).sum();
            }
            iteration++;
        } while (E > configuration.getMaximalError());
        System.out.println(iteration);
        return training;
    }

    private void convert(@NotNull SplittedImage image, Matrix[] matrices) {
        int index = 0;
        for (int indexInWidth = 0; indexInWidth < image.getRectanglesInWidth(); indexInWidth++) {
            for (int indexInHeight = 0; indexInHeight < image.getRectanglesInHeight(); indexInHeight++) {
                convertSubimage(image.getSubimages()[indexInWidth][indexInHeight], image, matrices[index++].getAsArray());
            }
        }
    }

    private void convertSubimage(BufferedImage subimage, SplittedImage image, double[][] matrix) {
        for (int i = 0; i < subimage.getWidth(); i++) {
            for (int j = 0; j < subimage.getHeight(); j++) {
                int coefficient = 3 * (i + j * image.getHeight());
                int r = normalizeColor(matrix[0][coefficient]);
                int g = normalizeColor(matrix[0][1 + coefficient]);
                int b = normalizeColor(matrix[0][2 + coefficient]);
                Color color = new Color(r, g ,b);
                subimage.setRGB(i, j, color.getRGB());
            }
        }
    }

    private static final int MAX_COLOR_VALUE = 255;

    private int normalizeColor(double value) {
        int result = Double.valueOf(MAX_COLOR_VALUE * (value + 1) / 2).intValue();
        result = Math.min(result, 255);
        return Math.max(result, 0);
    }
}
