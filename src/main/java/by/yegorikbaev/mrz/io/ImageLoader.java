package by.yegorikbaev.mrz.io;

import java.awt.image.BufferedImage;

/**
 * Interface for loader of source images
 * Group 721702
 * @author  Yegor Ikbaev
 * @version 1.0
 * @since   2019-11-10
 */
@FunctionalInterface
public interface ImageLoader {
    BufferedImage load(String path);
}