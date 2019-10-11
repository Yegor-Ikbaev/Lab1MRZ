package by.yegorikbaev.mrz.io;

import java.awt.image.BufferedImage;

/**
 * Interface for saving of compressed images
 * Group 721702
 * @author  Yegor Ikbaev
 * @version 1.0
 * @since   2019-11-10
 */
@FunctionalInterface
public interface ImageSaver {
    void save(BufferedImage image, String path, String format);
}