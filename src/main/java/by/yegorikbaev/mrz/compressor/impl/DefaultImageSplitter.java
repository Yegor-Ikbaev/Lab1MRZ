package by.yegorikbaev.mrz.compressor.impl;

import by.yegorikbaev.mrz.bean.SplittedImage;
import by.yegorikbaev.mrz.compressor.ImageSplitter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

@Service
public class DefaultImageSplitter implements ImageSplitter {

    private static final Logger LOGGER = Logger.getLogger(DefaultImageSplitter.class.getName());

    @Override
    public @NotNull SplittedImage split(@NotNull BufferedImage sourceImage, int width, int height) {
        validate(sourceImage, width, height);
        SplittedImage image = new SplittedImage();
        image.setSourceImage(sourceImage);
        image.setWidth(width);
        image.setHeight(height);
        image.setCrossed(isCrossed(sourceImage, width, height));
        calculateRectangles(image);
        split(image);
        log(image);
        return image;
    }

    private void validate(BufferedImage sourceImage, int width, int height) {
        if (width <= 0 || height <= 0) {
            String message = String.format("Width(%d) or height(%d) are equals or less 0", width, height);
            LOGGER.warning(message);
            throw new IllegalArgumentException(message);
        }
        int widthOfImage = sourceImage.getWidth();
        int heightOfImage = sourceImage.getHeight();
        if (width > widthOfImage || height > heightOfImage) {
            String message = String.format("Width(%d) or height(%d) are greater source sizes(%d, %d)",
                    width, height, widthOfImage, heightOfImage);
            LOGGER.warning(message);
            throw new IllegalArgumentException(message);
        }
    }

    private boolean isCrossed(BufferedImage sourceImage, int width, int height) {
        int widthOfImage = sourceImage.getWidth();
        int heightOfImage = sourceImage.getHeight();
        return (widthOfImage % width != 0) || (heightOfImage % height != 0);
    }

    private void calculateRectangles(SplittedImage image) {
        float widthOfImage = image.getSourceImage().getWidth();
        float heightOfImage = image.getSourceImage().getHeight();
        int rectanglesInWidth = (int) Math.ceil(widthOfImage / image.getWidth());
        int rectanglesInHeight = (int) Math.ceil(heightOfImage / image.getHeight());
        int totalRectangles = rectanglesInWidth * rectanglesInHeight;
        image.setRectanglesInWidth(rectanglesInWidth);
        image.setRectanglesInHeight(rectanglesInHeight);
        image.setTotalRectangles(totalRectangles);
    }

    private void split(SplittedImage image) {
        BufferedImage[][] subimages = new BufferedImage[image.getRectanglesInHeight()][image.getRectanglesInWidth()];
        int width = image.getWidth();
        int height = image.getHeight();
        for (int heightRectangle = 0; heightRectangle < image.getRectanglesInHeight() - 1; heightRectangle++) {
            for (int widthRectangle = 0; widthRectangle < image.getRectanglesInWidth() - 1; widthRectangle++) {
                int pointX = widthRectangle * width;
                int pointY = heightRectangle * height;
                subimages[heightRectangle][widthRectangle] =
                        image.getSourceImage().getSubimage(pointX, pointY, width, height);
            }
        }
        for (int heightRectangle = 0; heightRectangle < image.getRectanglesInHeight() - 1; heightRectangle++) {
            int pointX = image.getSourceImage().getWidth() - width;
            int pointY = heightRectangle * height;
            subimages[heightRectangle][image.getRectanglesInWidth() - 1] =
                    image.getSourceImage().getSubimage(pointX, pointY, width, height);
        }
        for (int widthRectangle = 0; widthRectangle < image.getRectanglesInWidth() - 1; widthRectangle++) {
            int pointX = widthRectangle * width;
            int pointY = image.getSourceImage().getHeight() - height;
            subimages[image.getRectanglesInHeight() - 1][widthRectangle] =
                    image.getSourceImage().getSubimage(pointX, pointY, width, height);
        }
        int pointX = image.getSourceImage().getWidth() - width;
        int pointY = image.getSourceImage().getHeight() - height;
        subimages[image.getRectanglesInHeight() - 1][image.getRectanglesInWidth() - 1] =
                image.getSourceImage().getSubimage(pointX, pointY, width, height);
        image.setSubimages(subimages);
    }

    private void log(@NotNull SplittedImage image) {
        LOGGER.info(String.format("Splitting source image by rectangles %d x %d", image.getWidth(), image.getHeight()));
        LOGGER.info(String.format("Rectangles in width = %d, Rectangles in height = %d",
                image.getRectanglesInWidth(), image.getRectanglesInHeight()));
        LOGGER.info(String.format("Total rectangles = %d", image.getTotalRectangles()));
        LOGGER.info(String.format("Sizes of source image: %d x %d",
                image.getSourceImage().getWidth(), image.getSourceImage().getHeight()));
        LOGGER.info(String.format("Matrix of subimages: %d x %d",
                image.getSubimages().length, image.getSubimages()[0].length));
    }
}