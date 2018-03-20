package windows.algorithmpanels;

import datastructures.Matrix;
import io.MatrixInputPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class KonditionszahlenPanel extends AlgorithmPanel{

	public KonditionszahlenPanel() {
		super("Numerik");
	}

	private MatrixInputPanel panel;
	EventHandler<ActionEvent> listener;
	private Button calc;

	@Override
	public Pane configureInfoPane() {
		listener = evt -> {
			Matrix m1 = panel.getMatrix();
			Matrix invers = m1.inverse();
			String result = "Konditionszahlen des Terms:\n\n";
			if(invers !=null){
				result += "\nKondition für Zeilennorm: "+(m1.zeilensummenNorm()*invers.zeilensummenNorm());
				result += "\nKondition für Spaltennorm: "+(m1.spaltensummenNorm()*invers.spaltensummenNorm());
				result += "\nKondition für Frobeniusnorm: "+(m1.frobeniusNorm()*invers.frobeniusNorm());
			}else{
				result += "\nKonditionszahlen nicht berechenbar";
			}
			this.sendResultString(result);
			
		};
		calc = new Button("Matrix-Multiplikation durchführen");
		calc.setOnAction(listener);
		TextFlow text = new TextFlow(new Text("Fuer die Berechnung der Konditionszahlen sind die Ergebnisse\nder Gleichung auf der rechten Seite nicht wichtig.\n"));
		return new FlowPane(text,calc);
	}

	@Override
	public Pane configureInputPane() {
		panel = new MatrixInputPanel();
		return panel;
	}

	@Override
	public String getDescription() {
		return "Konditionszahlen berechnen";
	}
}
