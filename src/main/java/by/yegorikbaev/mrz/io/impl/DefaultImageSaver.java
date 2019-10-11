package by.yegorikbaev.mrz.io.impl;

import by.yegorikbaev.mrz.io.ImageSaver;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Implementation of saver for compressed images
 * Group 721702
 * @author  Yegor Ikbaev
 * @version 1.0
 * @since   2019-11-10
 */
@Service
public class DefaultImageSaver implements ImageSaver {

    private static final Logger LOGGER = Logger.getLogger(DefaultImageSaver.class.getName());

    @Override
    public void save(@NotNull BufferedImage image, @NotNull String path, @NotNull String format) {
        if (StringUtils.isEmpty(path) || StringUtils.isEmpty(format)) {
            LOGGER.warning("Path or format is empty");
            throw  new IllegalArgumentException("Path or format is empty");
        }
        try {
            ImageIO.write(image, format, new File(path));
            LOGGER.info("Saved image: " + path);
        } catch (IOException e) {
            LOGGER.warning("Can't save image: " + path);
            throw new IllegalArgumentException("Can't save image: " + path);
        }
    }
}