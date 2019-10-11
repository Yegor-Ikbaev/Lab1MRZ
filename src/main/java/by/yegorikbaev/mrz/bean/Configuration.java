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

    @Value("${configuration.m}")
    private int widthOfRectangle;

    @Value("${configuration.n}")
    private int heightOfRectangle;

    private int totalRectangles;

    @Value("${configuration.p}")
    private int neuronsNumber;

    @Value("${configuration.a}")
    private double coefficientOfTraining;

    @Value("${configuration.e}")
    private double maximalError;

    @Value("${configuration.format}")
    private String format;

    @Value("#{T(java.lang.System).getProperty(\"user.dir\").concat('${configuration.savePath}')}")
    private String pathToSave;

    @Value("#{T(java.lang.System).getProperty(\"user.dir\").concat('${configuration.sourcePath}')}")
    private String pathToSource;

    private double coefficientOfCompression;

    private int pixelsInRectangle;

    public void setConfiguration(Configuration configuration) {
        widthOfRectangle = configuration.widthOfRectangle;
        heightOfRectangle = configuration.heightOfRectangle;
        neuronsNumber = configuration.neuronsNumber;
        coefficientOfTraining = configuration.coefficientOfTraining;
        coefficientOfCompression = configuration.coefficientOfCompression;
        maximalError = configuration.maximalError;
        format = configuration.format;
        pathToSave = configuration.pathToSave;
        pathToSource = configuration.pathToSource;
        pixelsInRectangle = configuration.pixelsInRectangle;
    }

    public int getWidth() {
        return widthOfRectangle;
    }

    public void setWidth(int widthOfRectangle) {
        this.widthOfRectangle = widthOfRectangle;
    }

    public int getHeight() {
        return heightOfRectangle;
    }

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

    public void setNeuronsNumber(int neuronsNumber) {
        this.neuronsNumber = neuronsNumber;
    }

    public double getCoefficientOfTraining() {
        return coefficientOfTraining;
    }

    public void setCoefficientOfTraining(double coefficientOfTraining) {
        this.coefficientOfTraining = coefficientOfTraining;
    }

    public double getMaximalError() {
        return maximalError;
    }

    public void setMaximalError(double maximalError) {
        this.maximalError = maximalError;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getPathToSave() {
        return pathToSave;
    }

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