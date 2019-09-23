package by.yegorikbaev.mrz.compressor;

import by.yegorikbaev.mrz.bean.SplittedImage;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface ImageRestorer {
    BufferedImage restore(SplittedImage image);
}