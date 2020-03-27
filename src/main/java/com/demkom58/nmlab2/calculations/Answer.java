package com.demkom58.nmlab2.calculations;

import java.util.List;

public class Answer {
    private final List<String> iterations;
    private final boolean stoppedByLimit;

    public Answer(List<String> iterations, boolean stoppedByLimit) {
        this.iterations = iterations;
        this.stoppedByLimit = stoppedByLimit;
    }

    public String getAnswer() {
        return iterations.get(iterations.size() - 1);
    }

    public boolean isStoppedByLimit() {
        return stoppedByLimit;
    }

    public List<String> getIterations() {
        return iterations;
    }

}
