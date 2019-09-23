package by.yegorikbaev.mrz.bean;

import org.ojalgo.function.aggregator.Aggregator;
import org.ojalgo.matrix.PrimitiveMatrix;

import javax.validation.constraints.NotNull;

public class Matrix {

    private PrimitiveMatrix source;

    public Matrix(@NotNull double[][] matrix) {
        this.source = PrimitiveMatrix.FACTORY.rows(matrix);
    }

    private Matrix(PrimitiveMatrix matrix) {
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

    public Matrix minus(Matrix arg) {
        return new Matrix(source.subtract(arg.source));
    }

    public double sum() {
        return source.aggregateAll(Aggregator.SUM);
    }

    public double[][] getAsArray() {
        return source.toRawCopy2D();
    }

    public Matrix update(Matrix matrix) {
        source = matrix.source;
        return this;
    }
}