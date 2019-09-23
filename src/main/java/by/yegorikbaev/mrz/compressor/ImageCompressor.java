package by.yegorikbaev.mrz.compressor;

import by.yegorikbaev.mrz.bean.Configuration;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface ImageCompressor {
    BufferedImage compress(BufferedImage source, Configuration configuration);
}