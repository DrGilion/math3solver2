package windows.algorithmpanels;

import datastructures.Expr;
import functions.Algorithm;
import io.InputField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class QuadraturPanel extends AlgorithmPanel{
	
	public QuadraturPanel() {
		super("Numerik");
	}

	TextField formelField;
	TextField formel2Field;
	TextField formel4Field;
	InputField lowField;
	InputField highField;
	InputField iterField;
	
	

	@Override
	public Pane configureInfoPane() {
		VBox box = new VBox();
		Button button = new Button("Quadraturformeln anwenden");
		button.setOnAction( evt ->{
			Expr func = new Expr(formelField.getText());
			Expr func2 = new Expr(formel2Field.getText());
			Expr func4 = new Expr(formel4Field.getText());
			int iterations = (int) iterField.getValue();
			double low = lowField.getValue();
			double high = highField.getValue();
			
			
			String result = "";
			result += "\nRechteckregel: " + Algorithm.IntegralRechteckregel(func, iterations, low, high);
			result += "\nFehler der Rechteckregel: " + Algorithm.RechteckregelFehler(func2, iterations, low, high);
			result += "\nTrapezregel: " + Algorithm.IntegralTrapezregel(func, iterations, low, high);
			result += "\nFehler der Rechteckregel: " + Algorithm.TrapezregelFehler(func2, iterations, low, high);
			result += "\nSimpsonregel: " + Algorithm.IntegralSimpsonregel(func, iterations, low, high);
			result += "\nFehler der Rechteckregel: " + Algorithm.SimpsonregelFehler(func4, iterations, low, high);

			this.sendResultString(result);
		});
		
		box.getChildren().add(button);
		return box;
	}

	@Override
	public Pane configureInputPane() {
		VBox base = new VBox();
		
		formelField = new TextField();
		formel2Field = new TextField();
		formel4Field = new TextField();
		lowField = new InputField();
		highField = new InputField();
		iterField = new InputField(); 
		
		
		base.getChildren().addAll(new Label("Funktion: "),formelField,new Label("2.Ableitung der Funktion(fuer Rechteckregel und Trapezregel): "),formel2Field,
				new Label("4.Ableitung der Funktion(fuer Simpsonregel): "),formel4Field,new Label("Untere Grenze: "),lowField,
				new Label("Obere Grenze: "),highField,new Label("Iterationen: "),iterField);
		return base;
	}

	@Override
	public String getDescription() {
		return "Quadraturen";
	}

}
