package by.yegorikbaev.mrz.io;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface ImageLoader {
    BufferedImage load(String path);
}