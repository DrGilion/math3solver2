package application;

import static javafx.application.Application.launch;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.jfree.fx.FXGraphics2D;

import org.scilab.forge.jlatexmath.Box;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import datastructures.Matrix;

/**
 * http://stackoverflow.com/questions/25027060/running-swing-application-in-javafx
 */
public class Test extends Application {

    static class MyCanvas extends Canvas { 

        private FXGraphics2D g2;

        private Box box;

        public MyCanvas() {
            this.g2 = new FXGraphics2D(getGraphicsContext2D());
            this.g2.scale(20, 20);

            // create a formula
            TeXFormula formula = new TeXFormula("x=\\frac{-b \\pm \\sqrt {b^2-4ac}}{2a}");
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
        public double prefWidth(double height) { return getWidth(); }  

        @Override 
        public double prefHeight(double width) { return getHeight(); } 
    } 


    @Override 
    public void start(Stage stage) throws Exception {
       MyCanvas canvas = new MyCanvas();
        StackPane stackPane = new StackPane(); 
        stackPane.getChildren().add(canvas);  
        // Bind canvas size to stack pane size. 
        canvas.widthProperty().bind( stackPane.widthProperty()); 
        canvas.heightProperty().bind( stackPane.heightProperty());  
        stage.setScene(new Scene(stackPane)); 
        stage.setTitle("FXGraphics2DDemo3.java"); 
        stage.setWidth(700);
        stage.setHeight(390);
        stage.show(); 

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //launch(args);
    	Matrix a = new Matrix(new double[][]{
    		{8,1,5},{-5,-5,-7},{5,-5,-7}
    	});
    	Matrix b = new Matrix(new double[][]{
    		{-1,1},{4,-2},{-7,4}
    	});
    	Matrix c = a.Produkt(b);
    	System.out.println(a+"\n"+b+"\n"+c);
    }

}