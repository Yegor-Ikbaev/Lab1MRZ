package by.yegorikbaev.mrz.compressor;

import by.yegorikbaev.mrz.bean.SplittedImage;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface Restorer {
    BufferedImage restore(SplittedImage image);
}