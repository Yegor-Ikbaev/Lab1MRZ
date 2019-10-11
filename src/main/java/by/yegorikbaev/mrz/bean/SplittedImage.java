package by.yegorikbaev.mrz.bean;

import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;

/**
 * Representation of image
 * Group 721702
 * @author  Yegor Ikbaev
 * @version 1.0
 * @since   2019-11-10
 */
public class SplittedImage {

    private BufferedImage sourceImage;

    private BufferedImage[][] subimages;

    private int width;

    private int height;

    private int totalRectangles;

    private int rectanglesInWidth;

    private int rectanglesInHeight;

    public @NotNull BufferedImage getSourceImage() {
        return sourceImage;
    }

    public void setSourceImage(@NotNull BufferedImage sourceImage) {
        this.sourceImage = sourceImage;
    }

    public @NotNull BufferedImage[][] getSubimages() {
        return subimages;
    }

    public void setSubimages(@NotNull BufferedImage[][] subimages) {
        this.subimages = subimages;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTotalRectangles() {
        return totalRectangles;
    }

    public void setTotalRectangles(int totalRectangles) {
        this.totalRectangles = totalRectangles;
    }

    public int getRectanglesInWidth() {
        return rectanglesInWidth;
    }

    public void setRectanglesInWidth(int rectanglesInWidth) {
        this.rectanglesInWidth = rectanglesInWidth;
    }

    public int getRectanglesInHeight() {
        return rectanglesInHeight;
    }

    public void setRectanglesInHeight(int rectanglesInHeight) {
        this.rectanglesInHeight = rectanglesInHeight;
    }
}