package by.yegorikbaev.mrz.loader.impl;

import by.yegorikbaev.mrz.loader.ImageLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class DefaultImageLoader implements ImageLoader {
    @Override
    public BufferedImage load(String path) {
        if (StringUtils.isEmpty(path)) {
            throw  new IllegalArgumentException("Path is null or empty");
        }
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.err.println("Can't load image: " + path);
        }
        return image;
    }
}