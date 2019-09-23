package by.yegorikbaev.mrz.bean;

import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;

public class SplittedImage {

    private BufferedImage sourceImage;

    private BufferedImage[][] subimages;

    private int width;

    private int height;

    private boolean isCrossed;

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

    public boolean isCrossed() {
        return isCrossed;
    }

    public void setCrossed(boolean crossed) {
        isCrossed = crossed;
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