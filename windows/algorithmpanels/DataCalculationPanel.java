package windows.algorithmpanels;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import functions.Algorithm;
import io.Utility;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class DataCalculationPanel extends AlgorithmPanel {

	private Button load;
	private TextField valuefield;

	
	public DataCalculationPanel() {
		super("Statistik");
	}
	
	@Override
	public Pane configureInfoPane() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("resource")
	@Override
	public Pane configureInputPane() {
		VBox pane = new VBox();
		load = new Button("Werte extern laden");
		valuefield = new TextField();
		pane.getChildren().addAll(new Text("Werte hier eintippen(mit Komma trennen)"), valuefield, new Text("oder"),
				load);
		valuefield.setOnKeyPressed(evt -> {
			if (evt.getCode() == KeyCode.ENTER) {
				action(valuefield.getText());
			}
		});
		load.setOnAction(evt -> {
			FileChooser choose = new FileChooser();
			File f = choose.showOpenDialog(null);
			if (f != null) {
				try {
					action(new Scanner(f).useDelimiter("\\Z").next());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		return pane;
	}

	@Override
	public String getDescription() {
		return "Kenngroessen für Datensatz berechnen";
	}

	private void action(String zahlen) {
		ArrayList<Double> data = Utility.StringToArray(zahlen);
		String result = "";
		result += "Arithmetisches Mittel: " + Algorithm.ArithmetischesMittel(data);
		result += "\nGeometrisches Mittel: "+ Algorithm.GeometrischesMittel(data);
		result += "\nMedian: "+ Algorithm.Median(data);
		result += "\nOberes Quartil(75%): " + Algorithm.p_quantil(data, 0.75);
		result += "\nUnteres Quartil(25%): " + Algorithm.p_quantil(data, 0.25);
		result += "\nModalwert: " + Algorithm.Modalwert(data);
		result += "\nSpannweite: " + Algorithm.Spannweite(data);
		result += "\nEmpirische Varianz: " + Algorithm.EmpirischeVarianz(data);
		result += "\nEmpirische Stadardabweichung: " +Algorithm.EmpirischeStandardAbweichung(data);
		result += "\nVariationskoeffizientz: " + Algorithm.Variationskoeffizient(data);
		result += "\nInterquartilsabstand: " +Algorithm.InterquartilAbstand(data);
		
		this.sendResultString(result);
	}

}
