package io;

import datastructures.Expr;
import javafx.scene.control.TextField;

public class InputField extends TextField{
	
	
	public InputField(){
		super();
	}
	
	public InputField(String string) {
		super(string);
	}
	
	public InputField(double d){
		super(""+d);
	}

	
	public void setValue(double d){
		this.setText(""+d);
	}
	
	public double getValue(){
		String result = this.getText();
		if(result == null || result.equals("")){
			return 0.0;
		}else{
			return new Expr(result).value(1);
		}
	}
	
}
