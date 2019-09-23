package by.yegorikbaev.mrz;

import by.yegorikbaev.mrz.bean.Configuration;
import by.yegorikbaev.mrz.compressor.ImageCompressor;
import by.yegorikbaev.mrz.io.ImageLoader;
import by.yegorikbaev.mrz.io.ImageSaver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.awt.image.BufferedImage;

class ApplicationRunner {

    private ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

    private ImageCompressor compressor = context.getBean("defaultImageCompressor", ImageCompressor.class);

    private ImageLoader loader = context.getBean("defaultImageLoader", ImageLoader.class);

    private ImageSaver saver = context.getBean("defaultImageSaver", ImageSaver.class);

    public void run(String path, Configuration configuration) {
        BufferedImage sourceImage = loader.load(path);
        saver.save(sourceImage, path, configuration.getFormat());
        BufferedImage targetImage = compressor.compress(sourceImage, configuration);
        saver.save(targetImage, configuration.getPathToSave(), configuration.getFormat());
    }
}