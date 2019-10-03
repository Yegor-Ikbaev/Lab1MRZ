package by.yegorikbaev.mrz.bean;

import javax.validation.constraints.NotNull;

public class TrainingResult {

    private SplittedImage image;

    private Matrix[] weights;

    public TrainingResult() {
    }

    public TrainingResult(@NotNull SplittedImage image, @NotNull Matrix[] weights) {
        this.image = image;
        this.weights = weights;
    }

    public SplittedImage getImage() {
        return image;
    }

    public void setImage(SplittedImage image) {
        this.image = image;
    }

    public Matrix[] getWeights() {
        return weights;
    }

    public void setWeights(Matrix[] weights) {
        this.weights = weights;
    }
}