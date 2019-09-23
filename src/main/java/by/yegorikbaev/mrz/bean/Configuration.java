package by.yegorikbaev.mrz.bean;

public class Configuration {

    private int widthOfRectangle = 4;

    private int heightOfRectangle = 4;

    private int elementsNumber;

    private int neuronsNumber;

    private double coefficientOfTraining = 0.005;

    private double maximalError;

    private String format = "png";

    private String pathToSave;

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
}
