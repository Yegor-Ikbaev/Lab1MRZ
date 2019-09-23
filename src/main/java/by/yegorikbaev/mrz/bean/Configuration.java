package by.yegorikbaev.mrz.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@PropertySource("classpath:configuration.properties")
@Component
@Scope("prototype")
public class Configuration {

    @Value("${configuration.m}")
    private int widthOfRectangle;

    @Value("${configuration.n}")
    private int heightOfRectangle;

    private int elementsNumber;

    @Value("${configuration.p}")
    private int neuronsNumber;

    @Value("${configuration.a}")
    private double coefficientOfTraining;

    @Value("${configuration.e}")
    private double maximalError;

    @Value("${configuration.format}")
    private String format;

    @Value("${configuration.savePath}")
    private String pathToSave;

    private double coefficientOfCompression;

    public void setConfiguration(Configuration configuration) {
        widthOfRectangle = configuration.widthOfRectangle;
        heightOfRectangle = configuration.heightOfRectangle;
        elementsNumber = configuration.elementsNumber;
        neuronsNumber = configuration.neuronsNumber;
        coefficientOfTraining = configuration.coefficientOfTraining;
        coefficientOfCompression = configuration.coefficientOfCompression;
        maximalError = configuration.maximalError;
        format = configuration.format;
        pathToSave = configuration.pathToSave;
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

    public int getElementsNumber() {
        return elementsNumber;
    }

    public void setElementsNumber(int elementsNumber) {
        this.elementsNumber = elementsNumber;
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
}
