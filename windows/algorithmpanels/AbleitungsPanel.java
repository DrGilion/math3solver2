package windows.algorithmpanels;

import datastructures.Expr;
import io.InputField;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class AbleitungsPanel extends AlgorithmPanel{
	


	public AbleitungsPanel() {
		super("Numerik");
	}

	@Override
	public Pane configureInfoPane() {
		VBox box = new VBox();
        WebView browser = new WebView();
        browser.getEngine().loadContent("<math display=\"block\"><mrow><mi>x</mi><mo>=</mo><mfrac><mrow><mo>−</mo><mi>b</mi><mo>±</mo>"
        	+ "<msqrt><mrow><msup><mi>b</mi><mn>2</mn></msup><mo>−</mo><mn>4</mn><mi>a</mi><mi>c</mi></mrow></msqrt></mrow><mrow>"
        	+ "<mn>2</mn><mi>a</mi></mrow></mfrac></mrow></math>"
        );
        browser.setLayoutX(100);
        browser.setLayoutY(200);
        box.getChildren().add(browser);
		return box;
	}

	@Override
	public Pane configureInputPane() {
		GridPane pane = new GridPane();
		
		Label label = new Label("Term ableiten: ");
		InputField resultfield = new InputField();

		//TODO
		resultfield.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				if (resultfield != null && resultfield.getText() != null) {
					Expr expr = new Expr(resultfield.getText());
					resultfield.setValue(expr.value(5));
				}
			}
		});
		
		pane.add(label, 0, 0);
		pane.add(resultfield, 1, 0);
		return pane;
	}

	@Override
	public String getDescription() {
		return "Under  Construction";
	}

}
