package windows.algorithmpanels;

import datastructures.Vektor;
import functions.Algorithm;
import io.VektorInputPanel;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class VektorInformationPanel extends AlgorithmPanel {
	
	public VektorInformationPanel() {
		super("Numerik");
	}

	private VektorInputPanel panel;
	
	
	@Override
	public Pane configureInfoPane() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pane configureInputPane() {
		panel = new VektorInputPanel();
		EventHandler<KeyEvent> listener = evt ->{
			Vektor vec = new Vektor(panel.getVektor());
			String result = "";
			result += "Betrag des Vektors: "+Algorithm.VektorBetrag(vec);
			double[] normen = Algorithm.Vektornormen(vec);
			result += "\n\n1.Euklidnorm: "+normen[0];
			result += "\n2.Summennorm: "+normen[1];
			result += "\n3.Maximumsnorm: "+normen[2];
			result += "\n\nnormierter Vektor:\n"+ Algorithm.normierterVektor(vec).toString();
			this.sendResultString(result);
		};
		panel.addKeyListener(listener);
		return panel;
	}

	@Override
	public String getDescription() {
		return "Vektor-Kalkulationen durchfuehren";
	}

}
