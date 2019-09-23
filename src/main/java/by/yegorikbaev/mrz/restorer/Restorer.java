package by.yegorikbaev.mrz.restorer;

import by.yegorikbaev.mrz.bean.SplittedImage;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface Restorer {
    BufferedImage restore(SplittedImage image);
}