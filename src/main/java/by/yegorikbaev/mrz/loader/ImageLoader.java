package by.yegorikbaev.mrz.loader;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface ImageLoader {
    BufferedImage load(String path);
}