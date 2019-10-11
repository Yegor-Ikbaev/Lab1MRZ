package by.yegorikbaev.mrz;

import by.yegorikbaev.mrz.bean.Configuration;
import by.yegorikbaev.mrz.compressor.ImageCompressor;
import by.yegorikbaev.mrz.io.ImageLoader;
import by.yegorikbaev.mrz.io.ImageSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

/**
 * Bean for starting compression
 * Group 721702
 * @author  Yegor Ikbaev
 * @version 1.0
 * @since   2019-11-10
 */
@Component
class ApplicationRunner {

    private ImageCompressor compressor;

    private ImageLoader loader;

    private ImageSaver saver;

    public ApplicationRunner(ImageCompressor compressor, ImageLoader loader, ImageSaver saver) {
        this.compressor = compressor;
        this.loader = loader;
        this.saver = saver;
    }

    private Configuration configuration;

    @Autowired
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    void run() {
        BufferedImage sourceImage = loader.load(configuration.getPathToSource());
        saver.save(sourceImage, configuration.getPathToSource(), configuration.getFormat());
        BufferedImage targetImage = compressor.compress(sourceImage, configuration);
        saver.save(targetImage, configuration.getPathToSave(), configuration.getFormat());
    }
}