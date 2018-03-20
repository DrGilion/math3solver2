package io;

import datastructures.Matrix;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import windows.MainScreen;

public class MatrixInputPanel extends VBox{
	
	private GridPane matrixpanel;
	private Spinner<Integer> spinX;
	private Spinner<Integer> spinY;
	private Label[][] labels;
	private InputField[][] fields;
	private EventHandler<KeyEvent> listener = null;

	public MatrixInputPanel(){
		super();
		this.minWidthProperty().bind(MainScreen.getScreen().getContentPanel().widthProperty());
		matrixpanel = new GridPane();
		this.setSpacing(10);
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
		VBox adjustpane = new VBox();
		adjustpane.setSpacing(5);
		adjustpane.setSpacing(5);
		adjustpane.getChildren().addAll(new Label("1. Dimension der matrix festlegen:"),spinX);
		adjustpane.getChildren().addAll(new Label("2. Dimension der matrix festlegen:"),spinY);
		this.getChildren().add(adjustpane);
		this.getChildren().add(matrixpanel);
		setupMatrixSize(3,3);
	}
	
	private void setupMatrixSize(int sizeX, int sizeY){
		//saving up old values for convenience
		//TODO buggy
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
		
		for(int x = 0; x<sizeX ; ++x){
			HBox tmppane = new HBox();
			tmppane.setSpacing(10);
			for(int y = 0; y<sizeY ; ++y){
				labels[x][y] = new Label("["+x+"]["+y+"]: ");
				fields[x][y] = new InputField(""+1.0);
				fields[x][y].setPrefColumnCount(5);
				if(listener!=null) fields[x][y].setOnKeyPressed(listener);
				if(x<oldX && oldX >0 && y<oldY && oldY >0){
					fields[x][y].setValue(oldvalues[x][y]);
				}
				tmppane.getChildren().addAll(labels[x][y],fields[x][y]);
			}
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
	
	public void addKeyListener(EventHandler<KeyEvent> listener){
		this.listener = listener;
		int sizeX = labels.length;
		int sizeY = labels[0].length;
		for(int x = 0; x<sizeX ; ++x){
			for(int y = 0; y<sizeY ; ++y){
				fields[x][y].setOnKeyPressed(listener);
			}
		}
		
	}
}
