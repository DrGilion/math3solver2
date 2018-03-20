package windows;

import java.util.ArrayList;
import java.util.HashMap;

import application.Application;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import windows.algorithmpanels.*;

public class MainScreen extends HBox {

	private static MainScreen main = null;
	private static Accordion accord;
	private BorderPane rightSide = null;
	private SplitPane split = null;
	private static HashMap<String,VBox> list = null;
	private static HashMap<String,ArrayList<AlgorithmPanel>> panellist = null;

	
	static{
		list = new HashMap<String,VBox>();
		panellist = new HashMap<String,ArrayList<AlgorithmPanel>>();
		addBasicPanels();
		addCustomPanels();
		accord = new Accordion();
		
		MainScreen.addBasicCategories();
		MainScreen.addCustomCategories();
		MainScreen.addBasicButtons();
		MainScreen.addCustomButtons();
	}
	
	public MainScreen() {
		super();
		main = this;
		rightSide = new BorderPane();
		split = new SplitPane();
		
		this.getChildren().add(split);
		split.setDividerPositions(0.3f);
		split.prefWidthProperty().bind(Application.getStage().widthProperty());
		split.prefHeightProperty().bind(Application.getStage().heightProperty());
		split.getItems().add(0, accord);
		split.getItems().add(1, rightSide);

	}

	public BorderPane getContentPanel() {
		return rightSide;
	}
	
	public static HashMap<String, ArrayList<AlgorithmPanel>> getList() {
		return panellist;
	}

	public static MainScreen getScreen() {
		if (main == null) {
			new MainScreen();
		}
		return main;
	}
	
	public static void addNewButton(String buttonName,String categoryname, AlgorithmPanel cls){
		Button button = new Button(buttonName);
		button.setOnAction(e -> changeAlgorithmPanel(cls));
		if(list == null) System.out.println("liste ist null");
		if(list.get(categoryname) == null) System.out.println(categoryname +" ist null");
		list.get(categoryname).getChildren().add(button);
	}
	
	public static void addBasicButtons(){
		for(String key : panellist.keySet()){
			for(AlgorithmPanel panel : panellist.get(key)){
				addNewButton(panel.getDescription(),key,panel);
			}
		}
	}
	
	public static void addCustomButtons(){
		///TODO
	}

	public static void addNewCategory(String text) {
		VBox box = new VBox();
		box.setSpacing(10);
		TitledPane pane = new TitledPane(text, box);
		accord.getPanes().add(pane);
		list.put(text, box);
	}
	
	public static void addBasicCategories(){
		MainScreen.addNewCategory("Numerik");
		MainScreen.addNewCategory("Statistik");
		MainScreen.addNewCategory("Sonstiges");
	}
	
	public static void addCustomCategories(){
		//TODO
	}

	public SplitPane getSplitPane() {
		return split;
	}


	public static void addAlgorithmPanel(AlgorithmPanel alg) {
		if(!panellist.containsKey(alg.getType())){
			panellist.put(alg.getType(), new ArrayList<AlgorithmPanel>());
		}
		panellist.get(alg.getType()).add(alg);
		
	}

	public static void changeAlgorithmPanel(AlgorithmPanel alg) {
		for (ArrayList<AlgorithmPanel> p : panellist.values()) {
			p.stream().forEach( panel ->{
				if(panel == alg){
					MainScreen.getScreen().getContentPanel().setCenter(panel);
				}
			});
		}

	}
	
	public static void addBasicPanels(){
		//Numerik
		addAlgorithmPanel(new FehlerBerechnungPanel());
		addAlgorithmPanel(new FixPunktPanel());
		addAlgorithmPanel(new VektorInformationPanel());
		addAlgorithmPanel(new MatrixInformationPanel());
		addAlgorithmPanel(new MatrixMultiplicationPanel());
		addAlgorithmPanel(new KonditionszahlenPanel());
		addAlgorithmPanel(new QuadraturPanel());
		addAlgorithmPanel(new PolynomInterpolationPanel());
		addAlgorithmPanel(new LGSSolverPanel());
		addAlgorithmPanel(new AbleitungsPanel());
		
		//Statistik
		addAlgorithmPanel(new DataCalculationPanel());
		
	}
	
	public static void addCustomPanels() {
		// TODO Auto-generated method stub
		
	}



}
