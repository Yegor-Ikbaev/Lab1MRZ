package by.yegorikbaev.mrz.bean;

import javax.validation.constraints.NotNull;

/**
 * Contains result of training
 * Group 721702
 * @author  Yegor Ikbaev
 * @version 1.0
 * @since   2019-11-10
 */
public class TrainingResult {

    private SplittedImage image;

    private Matrix[] weights;

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