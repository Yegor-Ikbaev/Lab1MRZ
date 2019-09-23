package by.yegorikbaev.mrz.compressor.impl;

import by.yegorikbaev.mrz.bean.SplittedImage;
import by.yegorikbaev.mrz.compressor.Restorer;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class DefaultRestorer implements Restorer {

    @Override
    public BufferedImage restore(@NotNull SplittedImage image) {
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
        return target;
    }
}