package by.yegorikbaev.mrz.io.impl;

import by.yegorikbaev.mrz.io.ImageLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Implementation of loader of source images
 * Group 721702
 * @author  Yegor Ikbaev
 * @version 1.0
 * @since   2019-11-10
 */
@Service
public class DefaultImageLoader implements ImageLoader {

    private static final Logger LOGGER = Logger.getLogger(DefaultImageLoader.class.getName());

    @Override
    public BufferedImage load(String path) {
        if (StringUtils.isEmpty(path)) {
            LOGGER.warning("Path is null or empty");
            throw  new IllegalArgumentException("Path is null or empty");
        }
        BufferedImage image;
        try {
            image = ImageIO.read(new File(path));
            LOGGER.info("Loaded image: " + path);
        } catch (IOException e) {
            LOGGER.warning("Can't load image: " + path);
            throw new IllegalArgumentException("Can't load image: " + path);
        }
        return image;
    }
}