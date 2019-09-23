package by.yegorikbaev.mrz.io.impl;

import by.yegorikbaev.mrz.io.ImageSaver;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class DefaultImageSaver implements ImageSaver {
    @Override
    public void save(@NotNull BufferedImage image, @NotNull String path, @NotNull String format) {
        try {
            ImageIO.write(image, format, new File(path));
        } catch (IOException e) {
            System.err.println("Can't save image: " + path);
        }
    }
}