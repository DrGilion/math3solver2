package io;

import datastructures.Vektor;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class VektorInputPanel extends VBox{

	private Spinner<Integer> spin;
	private Label[] labels;
	private InputField[] fields;
	private EventHandler<KeyEvent> listener = null;
	
	public VektorInputPanel(){
		super();
		this.setSpacing(5);
		spin = new Spinner<Integer>();
		spin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
		spin.getValueFactory().setValue(3);
		spin.setEditable(true);
		spin.valueProperty().addListener( (spin,old,fresh) -> setupGUISize(fresh));
		spin.setOnScroll(event -> {
			if (event.getDeltaY() < 0) {
				spin.decrement();
			} else if (event.getDeltaY() > 0) {
				spin.increment();
			}
		}); 
		setupGUISize(3);
	}
	
	private void setupGUISize(int size){
		//saving up old values for convenience
		int oldsize = this.getChildren().size()-2;
		double[] oldvalues = new double[1];
		if(oldsize>0){
			oldvalues = new double[oldsize];
			for(int i =0 ; i<oldsize; ++i){
				oldvalues[i] = fields[i].getValue();
			}
		}
		//new guis
		this.getChildren().clear();
		labels = new Label[size];
		fields = new InputField[size];
		this.getChildren().addAll(new Label("Vektorgroesse festlegen:"),spin);
		for(int i = 0; i<size ; ++i){
			labels[i] = new Label(i+1 + ".Element: ");
			fields[i] = new InputField(""+1.0);
			if(listener!= null) fields[i].setOnKeyPressed(listener);
			if(i<oldsize && oldsize >0){
				fields[i].setText(""+oldvalues[i]);
			}
			this.getChildren().add(new FlowPane(labels[i],fields[i]));
		}
	}
	
	public Vektor getVektor(){
		double[] input = new double[fields.length];
		for(int i =0 ; i<fields.length; ++i){
			input[i] = fields[i].getValue();
		}
		Vektor vec = new Vektor(input);
		return vec;
	}
	
	public void addKeyListener(EventHandler<KeyEvent> listener){
		this.listener = listener;
		for(int i = 0; i<fields.length ; ++i){
			fields[i].setOnKeyPressed(listener);
		}

	}
}
