package com.demkom58.nmlab2.calculations;

public interface EquationSystemSolver {

    String getName();

    Answer solve();

    boolean makeDominant();

    double[][] getMatrix();

}
