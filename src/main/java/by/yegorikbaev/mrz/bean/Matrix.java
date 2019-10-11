package by.yegorikbaev.mrz.bean;

import org.ojalgo.function.aggregator.Aggregator;
import org.ojalgo.matrix.PrimitiveMatrix;

import javax.validation.constraints.NotNull;

/**
 * Representation of matrix
 * Group 721702
 * @author  Yegor Ikbaev
 * @version 1.0
 * @since   2019-11-10
 */
public class Matrix {

    private PrimitiveMatrix source;

    public Matrix(@NotNull double[][] matrix) {
        this.source = PrimitiveMatrix.FACTORY.rows(matrix);
    }

    private Matrix(@NotNull PrimitiveMatrix matrix) {
        this.source = matrix;
    }

    public Matrix multiply(@NotNull Matrix arg) {
        return new Matrix(source.multiply(arg.source));
    }

    public Matrix multiply(double arg) {
        return new Matrix(source.multiply(arg));
    }

    public Matrix transpose() {
        return new Matrix(source.transpose());
    }

    public Matrix minus(@NotNull Matrix arg) {
        return new Matrix(source.subtract(arg.source));
    }

    public double sum() {
        return source.aggregateAll(Aggregator.SUM);
    }

    public @NotNull double[][] getAsArray() {
        return source.toRawCopy2D();
    }

    public Matrix update(@NotNull Matrix matrix) {
        source = matrix.source;
        return this;
    }
}