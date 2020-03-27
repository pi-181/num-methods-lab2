package com.demkom58.nmlab2.calculations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Jacobi implements EquationSystemSolver
{

    private final int maxIterations;
    private final double epsilon;

    private double[][] matrix;

    public Jacobi(double[][] matrix, int maxIterations, double epsilon) {
        this.matrix = matrix;
        this.maxIterations = maxIterations;
        this.epsilon = epsilon;
    }

    public boolean transformToDominant(int r, boolean[] visited, int[] rows) {
        int matrixLength = matrix.length;

        if (r == matrix.length) {
            double[][] newMatrix = new double[matrixLength][matrixLength + 1];
            for (int i = 0; i < rows.length; i++) {
                if (matrixLength + 1 >= 0)
                    System.arraycopy(matrix[rows[i]], 0, newMatrix[i], 0, matrixLength + 1);
            }

            matrix = newMatrix;
            return true;
        }

        for (int y = 0; y < matrixLength; y++) {
            if (visited[y])
                continue;

            double sum = 0;

            for (int x = 0; x < matrixLength - 1; x++)
                if (x != r)
                    sum += Math.abs(matrix[y][x]);

            if (Math.abs(matrix[y][r]) > sum) { // diagonally dominant?
                visited[y] = true;
                rows[r] = y;

                if (transformToDominant(r + 1, visited, rows))
                    return true;

                visited[y] = false;
            }
        }

        return false;
    }


    /**
     * Returns true if is possible to transform M(data member) to a diagonally
     * dominant matrix, false otherwise.
     */
    @Override
    public boolean makeDominant() {
        boolean[] visited = new boolean[matrix.length];
        int[] rows = new int[matrix.length];

        Arrays.fill(visited, false);

        return transformToDominant(0, visited, rows);
    }

    @Override
    public Answer solve() {
        int iterations = 0;
        int n = matrix.length;

        double[] X = new double[n];
        double[] P = new double[n];

        Arrays.fill(X, 0);
        Arrays.fill(P, 0);

        final Answer answer;
        final List<String> iterationNodes = new ArrayList<>();

        while (true) {
            for (int i = 0; i < n; i++) {
                double sum = matrix[i][n];

                for (int j = 0; j < n; j++)
                    if (j != i)
                        sum -= matrix[i][j] * P[j];

                X[i] = 1 / matrix[i][i] * sum;
            }

            final StringBuilder builder = new StringBuilder("Ітерація " + iterations + ": ");
            for (int i = 0; i < n; i++)
                builder.append(X[i]).append("; ");
            iterationNodes.add(builder.toString());

            iterations++;
            if (iterations == 1)
                continue;

            boolean stop = true;
            for (int i = 0; i < n; i++)
                if (Math.abs(X[i] - P[i]) > epsilon) {
                    stop = false;
                    break;
                }

            if (stop || (iterations == maxIterations)) {
                answer = new Answer(iterationNodes, !stop);
                break;
            }

            P = X.clone();
        }

        return answer;
    }

    @Override
    public double[][] getMatrix() {
        return matrix;
    }

    @Override
    public String getName() {
        return "Метод простих ітерацій";
    }

}
