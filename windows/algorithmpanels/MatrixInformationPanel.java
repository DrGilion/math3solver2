package windows.algorithmpanels;

import datastructures.Matrix;
import functions.Algorithm;
import io.MatrixInputPanel;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class MatrixInformationPanel extends AlgorithmPanel{
	

	public MatrixInformationPanel() {
		super("Numerik");
	}

	private MatrixInputPanel panel;
	
	
	@Override
	public Pane configureInfoPane() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pane configureInputPane() {
		panel = new MatrixInputPanel();
		EventHandler<KeyEvent> listener = evt -> {
			Matrix m = new Matrix(panel.getMatrix());
			String result = "";
			double[] normen = Algorithm.Matrixnormen(m);
			result += "\n\n1.Zeilensummennorm: "+normen[0];
			result += "\n2.Spaltensummennorm: "+normen[1];
			result += "\n3.Frobeniusnorm: "+normen[2];
			
			result +="\nRang der Matrix: "+m.rank();
			result +="\nInverse der Matrix:\n"+m.inverse();
			this.sendResultString(result);
		};
		panel.addKeyListener(listener);
		return panel;
	}

	@Override
	public String getDescription() {
		return "Informationen zu einer Matrix abrufen";
	}


}
