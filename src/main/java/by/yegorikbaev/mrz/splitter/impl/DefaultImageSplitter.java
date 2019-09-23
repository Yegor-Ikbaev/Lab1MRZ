package by.yegorikbaev.mrz.splitter.impl;

import by.yegorikbaev.mrz.bean.SplittedImage;
import by.yegorikbaev.mrz.splitter.ImageSplitter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;

@Service
public class DefaultImageSplitter implements ImageSplitter {
    @Override
    public SplittedImage split(@NotNull BufferedImage sourceImage, int width, int height) {
        validate(sourceImage, width, height);
        SplittedImage image = new SplittedImage();
        image.setSourceImage(sourceImage);
        image.setWidth(width);
        image.setHeight(height);
        image.setCrossed(isCrossed(sourceImage, width, height));
        calculateRectangles(image);
        split(image);
        return image;
    }

    private void validate(BufferedImage sourceImage, int width, int height) {
        if (width <= 0 || height <= 0) {
            String pattern = "Width(%d) or height(%d) are equals or less 0";
            throw new IllegalArgumentException(String.format(pattern, width, height));
        }
        int widthOfImage = sourceImage.getWidth();
        int heightOfImage = sourceImage.getHeight();
        if (width > widthOfImage || height > heightOfImage) {
            String pattern = "Width(%d) or height(%d) are greater source sizes(%d, %d)";
            throw new IllegalArgumentException(String.format(pattern, width, height, widthOfImage, heightOfImage));
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
        int rectanglesInWidth = Double.valueOf(Math.ceil(widthOfImage / image.getWidth())).intValue();
        int rectanglesInHeight = Double.valueOf(Math.ceil(heightOfImage / image.getHeight())).intValue();
        int totalRectangles = rectanglesInWidth * rectanglesInHeight;
        image.setRectanglesInWidth(rectanglesInWidth);
        image.setRectanglesInHeight(rectanglesInHeight);
        image.setTotalRectangles(totalRectangles);
    }

    private void split(SplittedImage image) {
        BufferedImage[][] subimages = new BufferedImage[image.getRectanglesInWidth()][image.getRectanglesInHeight()];
        int width = image.getWidth();
        int height = image.getHeight();
        for (int heightRectangle = 0; heightRectangle < image.getRectanglesInHeight() - 1; heightRectangle++) {
            for (int widthRectangle = 0; widthRectangle < image.getRectanglesInWidth() - 1; widthRectangle++) {
                int pointX = widthRectangle * width;
                int pointY = heightRectangle * height;
                subimages[heightRectangle][widthRectangle] = image.getSourceImage().getSubimage(pointX, pointY, width, height);
            }
        }
        for (int heightRectangle = 0; heightRectangle < image.getRectanglesInHeight() - 1; heightRectangle++) {
            int pointX = image.getSourceImage().getWidth() - width;
            int pointY = heightRectangle * height;
            subimages[heightRectangle][image.getRectanglesInWidth() - 1] = image.getSourceImage().getSubimage(pointX, pointY, width, height);
        }
        for (int widthRectangle = 0; widthRectangle < image.getRectanglesInWidth() - 1; widthRectangle++) {
            int pointX = widthRectangle * width;
            int pointY = image.getRectanglesInHeight() - height;
            subimages[image.getRectanglesInHeight() - 1][widthRectangle] = image.getSourceImage().getSubimage(pointX, pointY, width, height);
        }
        int pointX = image.getSourceImage().getWidth() - width;
        int pointY = image.getRectanglesInHeight() - height;
        subimages[image.getRectanglesInHeight() - 1][image.getRectanglesInWidth() - 1] = image.getSourceImage().getSubimage(pointX, pointY, width, height);
        image.setSubimages(subimages);
    }
}