package by.yegorikbaev.mrz.compressor;

import by.yegorikbaev.mrz.bean.SplittedImage;

import java.awt.image.BufferedImage;

/**
 * Interface for splitter of images
 * Group 721702
 * @author  Yegor Ikbaev
 * @version 1.0
 * @since   2019-11-10
 */
@FunctionalInterface
public interface ImageSplitter {
    SplittedImage split(BufferedImage sourceImage, int width, int height);
}