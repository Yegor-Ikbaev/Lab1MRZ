package by.yegorikbaev.mrz.compressor.impl;

import by.yegorikbaev.mrz.bean.SplittedImage;
import by.yegorikbaev.mrz.compressor.ImageRestorer;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

/**
 * Implementation of restorer of images
 * Group 721702
 * @author  Yegor Ikbaev
 * @version 1.0
 * @since   2019-11-10
 */
@Service
public class DefaultImageRestorer implements ImageRestorer {

    private static final Logger LOGGER = Logger.getLogger(DefaultImageRestorer.class.getName());

    @Override
    public @NotNull BufferedImage restore(@NotNull SplittedImage image) {
        BufferedImage target = new BufferedImage(image.getSourceImage().getWidth(), image.getSourceImage().getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = target.getGraphics();
        int width = image.getWidth();
        int height = image.getHeight();
        int lastHeightRectangle = image.getRectanglesInHeight() - 1;
        int lastWidthRectangle = image.getRectanglesInWidth() - 1;
        for (int heightRectangle = 0; heightRectangle < lastHeightRectangle; heightRectangle++) {
            for (int widthRectangle = 0; widthRectangle < lastWidthRectangle; widthRectangle++) {
                int pointX = widthRectangle * width;
                int pointY = heightRectangle * height;
                BufferedImage subImage = image.getSubimages()[heightRectangle][widthRectangle];
                graphics.drawImage(subImage, pointX, pointY, width, height, null);
            }
        }
        for (int heightRectangle = 0; heightRectangle < lastHeightRectangle; heightRectangle++) {
            int pointX = image.getSourceImage().getWidth() - width;
            int pointY = heightRectangle * height;
            BufferedImage subImage = image.getSubimages()[heightRectangle][lastWidthRectangle];
            graphics.drawImage(subImage, pointX, pointY, width, height, null);
        }
        for (int widthRectangle = 0; widthRectangle < lastWidthRectangle; widthRectangle++) {
            int pointX = widthRectangle * width;
            int pointY = image.getSourceImage().getHeight() - height;
            BufferedImage subImage = image.getSubimages()[lastHeightRectangle][widthRectangle];
            graphics.drawImage(subImage, pointX, pointY, width, height, null);
        }
        int pointX = image.getSourceImage().getWidth() - width;
        int pointY = image.getSourceImage().getHeight() - height;
        BufferedImage subImage = image.getSubimages()[lastHeightRectangle][lastWidthRectangle];
        graphics.drawImage(subImage, pointX, pointY, width, height, null);
        log(target);
        return target;
    }

    private void log(BufferedImage image) {
        LOGGER.info(String.format("Restored image (%d x %d)", image.getWidth(), image.getHeight()));
    }
}