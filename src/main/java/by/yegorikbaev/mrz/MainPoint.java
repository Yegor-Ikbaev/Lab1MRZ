package by.yegorikbaev.mrz;

import by.yegorikbaev.mrz.bean.Configuration;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainPoint {

    public static void main(String[] args) throws IOException {
        long before = System.currentTimeMillis();
        ApplicationRunner runner = new ApplicationRunner();
        Configuration configuration = new Configuration();
        configuration.setPathToSave("E:\\Lenna2.gif");
        configuration.setCoefficientOfTraining(0.001);
        configuration.setFormat("gif");
        configuration.setMaximalError(973);
        configuration.setNeuronsNumber(16);
        configuration.setWidth(4);
        configuration.setHeight(4);
        runner.run("E:\\Lena.gif", configuration);
        long after = System.currentTimeMillis();
        System.out.println(after - before);
    }
}
