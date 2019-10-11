package by.yegorikbaev.mrz.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Configuration for neural network
 * Group 721702
 * @author  Yegor Ikbaev
 * @version 1.0
 * @since   2019-11-10
 */
@PropertySource("classpath:application.properties")
@Component
@Scope("prototype")
public class Configuration {

    private int widthOfRectangle;

    private int heightOfRectangle;

    private int totalRectangles;

    private int neuronsNumber;

    private double coefficientOfTraining;

    private double maximalError;

    private String format;

    private String pathToSave;

    private String pathToSource;

    private double coefficientOfCompression;

    private int pixelsInRectangle;

    public int getWidth() {
        return widthOfRectangle;
    }

    @Value("${configuration.m}")
    public void setWidth(int widthOfRectangle) {
        this.widthOfRectangle = widthOfRectangle;
    }

    public int getHeight() {
        return heightOfRectangle;
    }

    @Value("${configuration.n}")
    public void setHeight(int heightOfRectangle) {
        this.heightOfRectangle = heightOfRectangle;
    }

    public int getTotalRectangles() {
        return totalRectangles;
    }

    public void setTotalRectangles(int totalRectangles) {
        this.totalRectangles = totalRectangles;
    }

    public int getNeuronsNumber() {
        return neuronsNumber;
    }

    @Value("${configuration.p}")
    public void setNeuronsNumber(int neuronsNumber) {
        this.neuronsNumber = neuronsNumber;
    }

    public double getCoefficientOfTraining() {
        return coefficientOfTraining;
    }

    @Value("${configuration.a}")
    public void setCoefficientOfTraining(double coefficientOfTraining) {
        this.coefficientOfTraining = coefficientOfTraining;
    }

    public double getMaximalError() {
        return maximalError;
    }

    @Value("${configuration.e}")
    public void setMaximalError(double maximalError) {
        this.maximalError = maximalError;
    }

    public String getFormat() {
        return format;
    }

    @Value("${configuration.format}")
    public void setFormat(String format) {
        this.format = format;
    }

    public String getPathToSave() {
        return pathToSave;
    }

    @Value("#{T(java.lang.System).getProperty(\"user.dir\").concat('${configuration.savePath}')}")
    public void setPathToSave(String pathToSave) {
        this.pathToSave = pathToSave;
    }

    public double getCoefficientOfCompression() {
        return coefficientOfCompression;
    }

    public void setCoefficientOfCompression(double coefficientOfCompression) {
        this.coefficientOfCompression = coefficientOfCompression;
    }

    public String getPathToSource() {
        return pathToSource;
    }

    @Value("#{T(java.lang.System).getProperty(\"user.dir\").concat('${configuration.sourcePath}')}")
    public void setPathToSource(String pathToSource) {
        this.pathToSource = pathToSource;
    }

    public int getPixelsInRectangle() {
        return pixelsInRectangle;
    }

    public void setPixelsInRectangle(int pixelsInRectangle) {
        this.pixelsInRectangle = pixelsInRectangle;
    }
}