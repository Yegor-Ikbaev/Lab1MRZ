package by.yegorikbaev.mrz.compressor;

import by.yegorikbaev.mrz.bean.SplittedImage;
import by.yegorikbaev.mrz.bean.TrainingResult;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Converts matrix of colors to  source image
 * Group 721702
 * @author  Yegor Ikbaev
 * @version 1.0
 * @since   2019-11-10
 */
@Component
public class MatrixToImageConverter implements Converter<TrainingResult, SplittedImage> {

    private static final int RGB_COEFFICIENT = 3;

    private static final int MAX_COLOR_VALUE = 255;

    private static final int MIN_COLOR_VALUE = 0;

    @Override
    public SplittedImage convert(@NotNull TrainingResult result) {
        int indexOfMatrix = 0;
        for (int indexOfWidth = 0; indexOfWidth < result.getImage().getRectanglesInWidth(); indexOfWidth++) {
            for (int indexOfHeight = 0; indexOfHeight < result.getImage().getRectanglesInHeight(); indexOfHeight++) {
                convertSubimage(result.getImage().getSubimages()[indexOfHeight][indexOfWidth],
                        result.getImage(), result.getWeights()[indexOfMatrix++].getAsArray());
            }
        }
        return result.getImage();
    }

    private void convertSubimage(BufferedImage subimage, SplittedImage image, double[][] matrix) {
        for (int indexOfWidth = 0; indexOfWidth < subimage.getWidth(); indexOfWidth++) {
            for (int indexOfHeight = 0; indexOfHeight < subimage.getHeight(); indexOfHeight++) {
                int coefficient = RGB_COEFFICIENT * (indexOfHeight + indexOfWidth * image.getHeight());
                int r = normalizeColor(matrix[0][coefficient]);
                int g = normalizeColor(matrix[0][1 + coefficient]);
                int b = normalizeColor(matrix[0][2 + coefficient]);
                subimage.setRGB(indexOfWidth, indexOfHeight, new Color(r, g, b).getRGB());
            }
        }
    }

    private int normalizeColor(double value) {
        int result = (int) (MAX_COLOR_VALUE * (value + 1) / 2);
        return Math.max(Math.min(result, MAX_COLOR_VALUE), MIN_COLOR_VALUE);
    }
}