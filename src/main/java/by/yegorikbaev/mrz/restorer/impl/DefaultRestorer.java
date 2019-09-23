package by.yegorikbaev.mrz.restorer.impl;

import by.yegorikbaev.mrz.bean.SplittedImage;
import by.yegorikbaev.mrz.restorer.Restorer;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class DefaultRestorer implements Restorer {

    @Override
    public BufferedImage restore(SplittedImage image) {
        BufferedImage target = new BufferedImage(image.getSourceImage().getWidth(), image.getSourceImage().getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = target.getGraphics();
        int width = image.getWidth();
        int height = image.getHeight();
        for (int heightRectangle = 0; heightRectangle < image.getRectanglesInHeight() - 1; heightRectangle++) {
            for (int widthRectangle = 0; widthRectangle < image.getRectanglesInWidth() - 1; widthRectangle++) {
                int pointX = widthRectangle * width;
                int pointY = heightRectangle * height;
                BufferedImage subImage = image.getSubimages()[heightRectangle][widthRectangle];
                graphics.drawImage(subImage, pointX, pointY, width, height, null);
            }
        }
        for (int heightRectangle = 0; heightRectangle < image.getRectanglesInHeight() - 1; heightRectangle++) {
            int pointX = image.getSourceImage().getWidth() - width;
            int pointY = heightRectangle * height;
            BufferedImage subImage = image.getSubimages()[heightRectangle][image.getRectanglesInWidth() - 1];
            graphics.drawImage(subImage, pointX, pointY, width, height, null);
        }
        for (int widthRectangle = 0; widthRectangle < image.getRectanglesInWidth() - 1; widthRectangle++) {
            int pointX = widthRectangle * width;
            int pointY = image.getSourceImage().getHeight() - height;
            BufferedImage subImage = image.getSubimages()[image.getRectanglesInHeight() - 1][widthRectangle];
            graphics.drawImage(subImage, pointX, pointY, width, height, null);
        }
        int pointX = image.getSourceImage().getWidth() - width;
        int pointY = image.getSourceImage().getHeight() - height;
        BufferedImage subImage = image.getSubimages()[image.getRectanglesInHeight() - 1][image.getRectanglesInWidth() - 1];
        graphics.drawImage(subImage, pointX, pointY, width, height, null);
        return target;
    }
}