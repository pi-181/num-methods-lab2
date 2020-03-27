package com.demkom58.nmlab2;

import com.demkom58.divine.gui.GuiController;
import com.demkom58.divine.util.AlertUtil;
import com.demkom58.nmlab2.calculations.Answer;
import com.demkom58.nmlab2.calculations.EquationSystemSolver;
import com.demkom58.nmlab2.calculations.GaussSeidel;
import com.demkom58.nmlab2.calculations.Jacobi;
import com.demkom58.nmlab2.util.MatrixTable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.converter.NumberStringConverter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainController extends GuiController {

    @FXML
    private GridPane matrixRoot;

    @FXML
    private TextField accuracyInput;
    @FXML
    private TextField iterationLimitInput;

    @FXML
    private TextField systemSizeInput;

    private MatrixTable matrixTable;

    @Override
    public void init() {
        super.init();

        iterationLimitInput.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        systemSizeInput.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));

        matrixTable = new MatrixTable(matrixRoot, "0", true);
    }

    @FXML
    public void jacobi(MouseEvent event) {
        if (matrixTable.isEmpty()) {
            AlertUtil.showErrorMessage("Матриця пуста!");
            return;
        }

        var seidel = new Jacobi(getMatrix(), getIterationLimit(), getAccuracy());
        calculate(seidel);
    }

    @FXML
    public void gaussSeidel(MouseEvent event) {
        if (matrixTable.isEmpty()) {
            AlertUtil.showErrorMessage("Матриця пуста!");
            return;
        }

        var seidel = new GaussSeidel(getMatrix(), getIterationLimit(), getAccuracy());
        calculate(seidel);
    }

    private void calculate(@NotNull final EquationSystemSolver solver) {
        boolean diagonalDominant = solver.makeDominant();
        if (!diagonalDominant)
            AlertUtil.showErrorMessage("Система не є діагонально домінуючою: метод не може гарантувати конвергенцію.");

        matrixTable.setMatrix(solver.getMatrix());
        var solve = solver.solve();

        var addition = solve.isStoppedByLimit() ? "Зупинено оскільки досягло ліміту ітерацій.\n" : "";
        var beautified = String.join("\n", solve.getAnswer().split(" "));

        AlertUtil.showInfoMessage(solver.getName() + ": Вирішено!", addition + beautified);
    }

    @FXML
    public void build(MouseEvent event) {
        int size = getSystemSize();
        if (size == 0) {
            matrixTable.setMatrix(new int[0][0]);
            return;
        }

        matrixTable.setMatrix(new int[size][size + 1]);
    }

    public double[][] getMatrix() {
        if (matrixTable.isEmpty())
            return new double[0][0];

        return matrixTable.getDoubleMatrix();
    }

    public float getAccuracy() {
        final String text = accuracyInput.getText();
        if (text.isEmpty())
            return Float.parseFloat(accuracyInput.getPromptText());

        return Float.parseFloat(text);
    }

    public int getIterationLimit() {
        final String text = iterationLimitInput.getText();
        if (text.isEmpty())
            return Integer.parseInt(iterationLimitInput.getPromptText());

        return Integer.parseInt(text);
    }

    public int getSystemSize() {
        final String text = systemSizeInput.getText();
        if (text.isEmpty())
            return Integer.parseInt(systemSizeInput.getPromptText());

        return Integer.parseInt(text);
    }
}
