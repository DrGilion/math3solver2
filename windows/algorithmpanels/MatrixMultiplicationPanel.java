package windows.algorithmpanels;

import datastructures.Matrix;
import io.MatrixInputPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MatrixMultiplicationPanel extends AlgorithmPanel {
	
	public MatrixMultiplicationPanel() {
		super("Numerik");
	}

	private HBox basepane;

	private MatrixInputPanel panelA;
	private MatrixInputPanel panelB;

	EventHandler<ActionEvent> listener;
	private Button calc;

	@Override
	public Pane configureInfoPane() {
		listener = evt -> {
			
			Matrix m1 = panelA.getMatrix();
			Matrix m2 = panelB.getMatrix();
			Matrix m3 = m1.Produkt(m2);
			String result = "Ergebnis der Multiplikation:\n" + m3;
			this.sendResultString(result);
		};
		calc = new Button("Matrix-Multiplikation durchführen");
		calc.setOnAction(listener);

		return new VBox(calc);
	}

	@Override
	public Pane configureInputPane() {
		basepane = new HBox();
		panelA = new MatrixInputPanel();
		panelB = new MatrixInputPanel();

		basepane.getChildren().addAll(panelA,panelB);
		return basepane;
	}

	@Override
	public String getDescription() {
		return "Matrix-Multiplikation durchfuehren";
	}
}