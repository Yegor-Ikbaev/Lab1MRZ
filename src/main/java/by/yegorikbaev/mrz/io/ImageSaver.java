package by.yegorikbaev.mrz.io;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface ImageSaver {
    void save(BufferedImage image, String path, String format);
}