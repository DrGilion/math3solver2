package io;

import org.jfree.fx.FXGraphics2D;
import org.scilab.forge.jlatexmath.Box;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javafx.scene.canvas.Canvas;

public class FormulaCanvas extends Canvas {

	private FXGraphics2D g2;

	private Box box;

	public FormulaCanvas(TeXFormula formula) {
		 this.g2 = new FXGraphics2D(getGraphicsContext2D());
         this.g2.scale(20, 20);

         // create a formula
         TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);

         // the 'Box' seems to be the thing we can draw directly to Graphics2D
         this.box = icon.getBox();

         // Redraw canvas when size changes. 
         widthProperty().addListener(evt -> draw()); 
         heightProperty().addListener(evt -> draw()); 
	}

	private void draw() {
		double width = getWidth(); 
        double height = getHeight();
        getGraphicsContext2D().clearRect(0, 0, width, height);
        this.box.draw(g2, 1, 5);
	}

	@Override
	public boolean isResizable() {
		return true;
	}

	@Override
	public double prefWidth(double height) {
		return 200;
	}

	@Override
	public double prefHeight(double width) {
		return 200;
	}
}
