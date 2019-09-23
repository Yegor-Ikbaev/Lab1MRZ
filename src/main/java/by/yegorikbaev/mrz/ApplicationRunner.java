package by.yegorikbaev.mrz;

import by.yegorikbaev.mrz.bean.Configuration;
import by.yegorikbaev.mrz.compressor.ImageCompressor;
import by.yegorikbaev.mrz.io.ImageLoader;
import by.yegorikbaev.mrz.io.ImageSaver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;

class ApplicationRunner {

    private ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

    private ImageCompressor compressor = context.getBean("defaultImageCompressor", ImageCompressor.class);

    private ImageLoader loader = context.getBean("defaultImageLoader", ImageLoader.class);

    private ImageSaver saver = context.getBean("defaultImageSaver", ImageSaver.class);

    void run(@NotNull Configuration configuration) {
        setValues(configuration);
        BufferedImage sourceImage = loader.load(configuration.getPathToSource());
        saver.save(sourceImage, configuration.getPathToSource(), configuration.getFormat());
        BufferedImage targetImage = compressor.compress(sourceImage, configuration);
        saver.save(targetImage, configuration.getPathToSave(), configuration.getFormat());
    }

    private void setValues(Configuration targetConfiguration) {
        Configuration defaultConfiguration = context.getBean("configuration", Configuration.class);
        targetConfiguration.setConfiguration(defaultConfiguration);
    }
}