package windows.algorithmpanels;

import datastructures.Matrix;
import functions.Algorithm;
import io.InputField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class PolynomInterpolationPanel extends AlgorithmPanel{

	Spinner<Integer> gradspinner;
	InputField[][] fields; 
	GridPane gridpane;
	
	public PolynomInterpolationPanel() {
		super("Numerik");
	}

	@Override
	public Pane configureInfoPane() {
		Button calculate = new Button("Interpolation durchführen");
		calculate.setOnAction(evt -> {
			int size = gradspinner.getValue() + 1;
			double[][] array = new double[3][size];
			for (int x = 0; x < 3; ++x) {
				for (int y = 0; y < size; ++y) {
					array[x][y] = fields[x][y].getValue();
				}
			}
			Matrix m = new Matrix(array);
			String result = "";
			result += "Lagrange-Verfahren:\n";
			for (String s : Algorithm.LagrangePolynomInterpolation(m, size - 1)) {
				result += s;
			}
			result += "Newton-Verfahren:\n";
			result += Algorithm.NewtonPolynomInterpolation(m, size - 1);
			
			this.sendResultString(result);
		});
		gradspinner = new Spinner<Integer>(1,100,3);
		gradspinner.valueProperty().addListener( (x,oldV,newV) -> buildGrid(newV));
		HBox pane = new HBox(new Label("Grad: "),gradspinner, calculate);
		pane.setSpacing(15);
		return pane;
	}

	@Override
	public Pane configureInputPane() {
		gridpane = new GridPane();
		gridpane.setHgap(15);
		gridpane.setVgap(5);
		buildGrid(3);
		return gridpane;
	}

	@Override
	public String getDescription() {
		return "Polynom-Interpolation";
	}
	
	private void buildGrid(int size){
		gridpane.getChildren().clear();
		fields = new InputField[3][size+1];
		gridpane.add(new Label("n"), 0, 0);
		gridpane.add(new Label("x"), 0, 1);
		gridpane.add(new Label("y"), 0, 2);
		for(int n = 0; n <= size ; n++){
			fields[0][n] = new InputField(n);
			gridpane.add(fields[0][n], n+1, 0);
			fields[1][n] = new InputField(0.0);
			gridpane.add(fields[1][n], n+1, 1);
			fields[2][n] = new InputField(0.0);
			gridpane.add(fields[2][n], n+1, 2);
		}
	}

}
