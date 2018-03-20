package windows.algorithmpanels;

import datastructures.Expr;
import functions.Algorithm;
import io.InputField;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class FixPunktPanel extends AlgorithmPanel{

	public FixPunktPanel() {
		super("Numerik");
	}

	private TextField funcfield;
	private InputField left;
	private InputField right;
	private InputField start;
	private InputField iterationfield;
	
	private InputField lipschitzfield;
	private InputField firstIter;

	@Override
	public Pane configureInfoPane() {
		Button calcNormal = new Button("Fixpunkt berechnen");
		calcNormal.setOnAction(evt ->{
			Expr ex = new Expr(funcfield.getText());
			double startvalue = start.getValue();
			int iter = (int) iterationfield.getValue();
			String result = "";
			for(int i = 0; i<iter; i++){
				result += "\n"+(i)+".Iteration:  "+Algorithm.Fixpunkt(startvalue,i, ex);
			}
			this.sendResultString(result);
		});
		Button calcBanach = new Button("Fixpunkt berechnen");
		calcBanach.setOnAction(evt ->{
			double startvalue = start.getValue();
			int iter = (int) iterationfield.getValue();
			String result = "Abschätzung mit dem Banachschen Fixpunktsatz: "
			+Algorithm.BanachFix(lipschitzfield.getValue(), startvalue, firstIter.getValue(), iter);
			this.sendResultString(result);
		});
		return new HBox(calcNormal,calcBanach);
	}

	@Override
	public Pane configureInputPane() {
		VBox pane = new VBox();
		pane.setSpacing(25);
		Text funclabel = new Text("Funktion: ");
		funcfield = new TextField();
		Text intervalltext = new Text("im Intervall [");
		left = new InputField();
		left.setPrefColumnCount(2);
		right = new InputField();
		right.setPrefColumnCount(2);
		start = new InputField(); 
		iterationfield = new InputField();
		lipschitzfield = new InputField();
		firstIter = new InputField();
		pane.getChildren().add(new FlowPane(funclabel,funcfield));
		pane.getChildren().add(new FlowPane(intervalltext,left,new Text(","),right,new Text("]")));
		pane.getChildren().add(new FlowPane(new Text("Startwert: "),start));
		pane.getChildren().add(new FlowPane(new Text("Anzahl an Iterationen: "),iterationfield));
		pane.getChildren().add(new Separator());
		pane.getChildren().add(new FlowPane(new Text("Lipschitz-Konstante: \n(fuer banachschen Fixpunktsatz)"),lipschitzfield));
		pane.getChildren().add(new FlowPane(new Text("Ergebnis der ersten Iteration: \n(fuer banachschen Fixpunktsatz)"),firstIter));

		
		return pane;
	}

	@Override
	public String getDescription() {
		return "Fixpunkt berechnen";
	}

}
