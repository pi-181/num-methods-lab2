package com.demkom58.nmlab2.calculations;

import java.util.List;

public class Answer {
    private final List<Iteration> iterations;
    private final boolean stoppedByLimit;

    public Answer(List<Iteration> iterations, boolean stoppedByLimit) {
        this.iterations = iterations;
        this.stoppedByLimit = stoppedByLimit;
    }

    public Iteration getAnswer() {
        return iterations.get(iterations.size() - 1);
    }

    public boolean isStoppedByLimit() {
        return stoppedByLimit;
    }

    public List<Iteration> getIterations() {
        return iterations;
    }

    public static class Iteration {
        private final int number;
        private final double[] values;

        public Iteration(int number, double[] values) {
            this.number = number;
            this.values = values;
        }

        public int getNumber() {
            return number;
        }

        public double[] getValues() {
            return values;
        }
    }

}
