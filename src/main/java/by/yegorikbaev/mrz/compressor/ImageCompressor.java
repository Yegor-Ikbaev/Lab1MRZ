package by.yegorikbaev.mrz.compressor;

import by.yegorikbaev.mrz.bean.Configuration;

import java.awt.image.BufferedImage;

/**
 * Interface for compressor of images
 * Group 721702
 * @author  Yegor Ikbaev
 * @version 1.0
 * @since   2019-11-10
 */
@FunctionalInterface
public interface ImageCompressor {
    BufferedImage compress(BufferedImage source, Configuration configuration);
}