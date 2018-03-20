package windows.algorithmpanels;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public abstract class AlgorithmPanel extends BorderPane{
	
	private String type;
	public AlgorithmPanel(String type){
		super();
		this.type = type;
		this.setTop(new ScrollPane(configureInfoPane()));
		this.setCenter(new ScrollPane(configureInputPane()));
		this.setBottom(new ResultPane());
	}

	public class ResultPane extends TextArea{
		
		public ResultPane(){
			this.setEditable(false);
		}
	}
	
	/**
	 * use this to update the TextPane at the Bottom with your text
	 * @param text
	 */
	public final void sendResultString(String text){
		((ResultPane) this.getBottom()).setText(text);
	}
	
	public abstract Pane configureInfoPane();
	
	public abstract Pane configureInputPane();
	
	public abstract String getDescription();
	
	public String getType(){
		return type;
	}
}
