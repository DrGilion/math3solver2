package windows.algorithmpanels;

import functions.Algorithm;
import functions.Formula;
import io.FormulaCanvas;
import io.InputField;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class FehlerBerechnungPanel extends AlgorithmPanel {

	public FehlerBerechnungPanel() {
		super("Numerik");
	}

	private InputField exactField;
	private InputField estimateField;

	@Override
	public Pane configureInfoPane() {
		BorderPane pane = new BorderPane();
		FormulaCanvas canvasrel = new FormulaCanvas(Formula.RelativerFehler());
		FormulaCanvas canvasabs = new FormulaCanvas(Formula.AbsoluterFehler());
		Insets in = new Insets(5,5,5,5);
		StackPane s1 = new StackPane(canvasrel);
		StackPane s2 = new StackPane(canvasabs);
		s1.setPadding(in);
		s2.setPadding(in);
		canvasrel.widthProperty().bind(s1.widthProperty());
		canvasabs.widthProperty().bind(s2.widthProperty());
		canvasrel.heightProperty().bind(s1.heightProperty());
		canvasabs.heightProperty().bind(s2.heightProperty());
		
		Button calc = new Button("Fehler abschaetzen");
		calc.setOnAction(e -> {
			double p = exactField.getValue();
			double pnah = estimateField.getValue();
			double result1 = Algorithm.calculateAbsoluteError(p, pnah);
			double result2 = Algorithm.calculateRelativeError(p, pnah);
			String result = "Absoluter Fehler: "+result1;
			result += "\nRelativer Fehler: "+ result2 + " = "+ result2*100 + "% Abweichung";
			this.sendResultString(result);
		});
		pane.setLeft(s1);
		pane.setRight(s2);
		pane.setBottom(new TextFlow(new Text("Bitte nur Fliesskommazahlen benutzen")));
		pane.setTop(calc);
		return pane;
	}

	@Override
	public Pane configureInputPane() {
		GridPane pane = new GridPane();

		Label exactLabel = new Label("p: ");
		Label estimateLabel = new Label("geschätztes p: ");

		exactField = new InputField();
		estimateField = new InputField();

		pane.add(exactLabel, 0, 0);
		pane.add(exactField, 1, 0);

		pane.add(estimateLabel, 0, 1);
		pane.add(estimateField, 1, 1);
		return pane;
	}

	@Override
	public String getDescription() {
		return "Fehlerberechnung";
	}
}
