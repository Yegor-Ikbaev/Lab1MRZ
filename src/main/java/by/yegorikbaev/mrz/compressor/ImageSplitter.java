package by.yegorikbaev.mrz.compressor;

import by.yegorikbaev.mrz.bean.SplittedImage;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface ImageSplitter {
    SplittedImage split(BufferedImage sourceImage, int width, int height);
}