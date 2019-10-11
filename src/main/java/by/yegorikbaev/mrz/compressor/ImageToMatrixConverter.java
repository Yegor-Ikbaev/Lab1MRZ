package by.yegorikbaev.mrz.compressor;

import by.yegorikbaev.mrz.bean.Matrix;
import by.yegorikbaev.mrz.bean.SplittedImage;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Converts source image to matrix of colors
 * Group 721702
 * @author  Yegor Ikbaev
 * @version 1.0
 * @since   2019-11-10
 */
@Component
public class ImageToMatrixConverter implements Converter<SplittedImage, Matrix[]> {

    private static final int MAX_COLOR_VALUE = 255;

    @Override
    public Matrix[] convert(@NotNull SplittedImage image) {
        Matrix[] matrices = new Matrix[image.getTotalRectangles()];
        int index = 0;
        for (int indexInWidth = 0; indexInWidth < image.getRectanglesInWidth(); indexInWidth++) {
            for (int indexInHeight = 0; indexInHeight < image.getRectanglesInHeight(); indexInHeight++) {
                matrices[index++] = convertSubimage(image.getSubimages()[indexInHeight][indexInWidth], image);
            }
        }
        return matrices;
    }

    private Matrix convertSubimage(BufferedImage subimage, SplittedImage image) {
        int size = subimage.getWidth() * subimage.getHeight() * 3;
        double[][] matrix = new double[1][size];
        for (int i = 0; i < subimage.getWidth(); i++) {
            for (int j = 0; j < subimage.getHeight(); j++) {
                Color color = new Color(subimage.getRGB(i, j));
                int coefficient = 3 * (j + i * image.getHeight());
                matrix[0][coefficient] = normalizeColor(color.getRed());
                matrix[0][1 + coefficient] = normalizeColor(color.getGreen());
                matrix[0][2 + coefficient] = normalizeColor(color.getBlue());
            }
        }
        return new Matrix(matrix);
    }

    private double normalizeColor(double value) {
        return (2 * value / MAX_COLOR_VALUE) - 1;
    }
}