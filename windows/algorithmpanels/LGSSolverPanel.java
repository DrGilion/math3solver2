package windows.algorithmpanels;

import datastructures.Matrix;
import datastructures.Vektor;
import functions.Algorithm;
import io.InputField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import windows.MainScreen;

public class LGSSolverPanel extends AlgorithmPanel{

	
	private GridPane matrixpanel;
	private Spinner<Integer> spinX;
	private Spinner<Integer> spinY;
	private InputField iterfield;
	private Label[][] labels;
	private InputField[][] fields;
	private InputField[] vectors;
	private InputField[] startvectors;
	
	public LGSSolverPanel() {
		super("Numerik");
	}

	@Override
	public Pane configureInfoPane() {
		Button calculate = new Button("LGS nähern");
		calculate.setOnAction( evt -> {
			int iter = (int) iterfield.getValue();
			Matrix m = this.getMatrix();
			Vektor v = this.getVector();
			Vektor startv = this.getStartVector();
			double[][] jacobiresult = Algorithm.JacobiVerfahren(m, v, startv, iter);
			String result = "";
			result += "\nJacobi-Verfahren: \nKonvergenz gegeben?: " + Algorithm.KonvergenzJacobi(m);
			for(int i = 0 ; i < jacobiresult.length ; ++i){
				result += "\n"+(i+1) + ".Iteration :\n";
				for(int x = 0 ; x < jacobiresult[i].length ; ++x){
					result += "x" + (x+1) +"= " + jacobiresult[i][x] + "\n";
				}
				result += "\n";
			}
			startv = this.getStartVector();
			double[][] gaussresult = Algorithm.GaussSeidelVerfahren(m, v, startv, iter);
			result += "\nGauss-Seidel-Verfahren: \nKonvergenz gegeben?: " + Algorithm.KonvergenzGaussSeidel(m);
			for(int i = 0 ; i < gaussresult.length ; ++i){
				result += "\n"+(i+1) + ".Iteration :\n";
				for(int x = 0 ; x < gaussresult[i].length ; ++x){
					result += "x" + (x+1) +"= " + gaussresult[i][x] + "\n";
				}
				result += "\n";
			}
			this.sendResultString(result);
		});
		HBox pane = new HBox(calculate);
		pane.setSpacing(15);
		return pane;
	}

	@Override
	public Pane configureInputPane() {
		VBox resultpane = new VBox();
		resultpane.minWidthProperty().bind(MainScreen.getScreen().getContentPanel().widthProperty());
		matrixpanel = new GridPane();
		resultpane.setSpacing(10);
		labels = new Label[1][0];
		spinX = new Spinner<Integer>();
		spinY = new Spinner<Integer>();
		spinX.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
		spinX.getValueFactory().setValue(3);
		spinX.setEditable(true);
		spinX.valueProperty().addListener( (spin,old,fresh) -> setupMatrixSize(fresh,spinY.getValue()));
		spinX.setOnScroll(event -> {
			if (event.getDeltaY() < 0) {
				spinX.decrement();
			} else if (event.getDeltaY() > 0) {
				spinX.increment();
			}
		});
		spinY.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
		spinY.getValueFactory().setValue(3);
		spinY.setEditable(true);
		spinY.valueProperty().addListener( (spin,old,fresh) -> setupMatrixSize(spinX.getValue(),fresh));
		spinY.setOnScroll(event -> {
			if (event.getDeltaY() < 0) {
				spinY.decrement();
			} else if (event.getDeltaY() > 0) {
				spinY.increment();
			}
		});
		iterfield = new InputField(3);
		iterfield.setPrefColumnCount(5);
		VBox adjustpane = new VBox();
		adjustpane.setSpacing(5);
		adjustpane.getChildren().addAll(new Label("1. Dimension der matrix festlegen:"),spinX);
		adjustpane.getChildren().addAll(new Label("2. Dimension der matrix festlegen:"),spinY);
		adjustpane.getChildren().addAll(new Label("Iterationen"),iterfield);
		resultpane.getChildren().add(adjustpane);
		resultpane.getChildren().add(matrixpanel);
		setupMatrixSize(3,3);
		return resultpane;
	}
	
	private void setupMatrixSize(int sizeX, int sizeY){
		//saving up old values for convenience
		int oldX = labels.length;
		int oldY = labels[0].length;
		double[][] oldvalues = new double[1][1];
		if(oldX>0 && oldY>0){
			oldvalues = new double[oldX][oldY];
			for(int x =0 ; x<oldX; ++x){
				for(int y =0 ; y<oldY; ++y){
					oldvalues[x][y] = fields[x][y].getValue();
				}
			}
		}
		//new guis
		matrixpanel.getChildren().clear();
		labels = new Label[sizeX][sizeY];
		fields = new InputField[sizeX][sizeY];
		vectors = new InputField[sizeX];
		startvectors = new InputField[sizeX];
		
		for(int x = 0; x<sizeX ; ++x){
			HBox tmppane = new HBox();
			tmppane.setSpacing(10);
			for(int y = 0; y<sizeY ; ++y){
				labels[x][y] = new Label("["+x+"]["+y+"]: ");
				fields[x][y] = new InputField(""+1.0);
				fields[x][y].setPrefColumnCount(5);
				if(x<oldX && oldX >0 && y<oldY && oldY >0){
					fields[x][y].setValue(oldvalues[x][y]);
				}
				tmppane.getChildren().addAll(labels[x][y],fields[x][y]);
			}
			vectors[x] = new InputField(1.0);
			startvectors[x] = new InputField(0.0);
			tmppane.getChildren().addAll(new Label(" = "),vectors[x],new Label("\t Startvektor x"+(x+1) + ": "),startvectors[x]);
			matrixpanel.add(new ScrollPane(tmppane),0,x);
		}
	}
	
	public Matrix getMatrix(){
		double[][] input = new double[fields.length][fields[0].length];
		for(int x =0 ; x<fields.length; ++x){
			for(int y =0 ; y<fields[0].length; ++y){
				input[x][y] = fields[x][y].getValue();
			}
		}
		Matrix m = new Matrix(input);
		return m;
	}
	
	public Vektor getVector(){
		double[] input = new double[vectors.length];
		for(int x =0 ; x<vectors.length; ++x){
			input[x] = vectors[x].getValue();
		}
		Vektor v = new Vektor(input);
		return v;
	}
	
	public Vektor getStartVector(){
		double[] input = new double[startvectors.length];
		for(int x =0 ; x<startvectors.length; ++x){
			input[x] = startvectors[x].getValue();
		}
		Vektor v = new Vektor(input);
		return v;
	}

	@Override
	public String getDescription() {
		return "Iterationsverfahren für LGS";
	}

}
