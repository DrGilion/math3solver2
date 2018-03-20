package application;

import java.awt.Toolkit;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import windows.MainScreen;


public class Application extends javafx.application.Application{
	
	private static Stage stage;
	private static Scene startScene;
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Platform.runLater(() -> {
			stage = primaryStage;
			primaryStage.setOnCloseRequest(evt -> CLOSE());
			primaryStage.setTitle("Micha\'s Matherechner");
			
			int screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
			int screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
			
			primaryStage.setWidth(screenWidth/2);
			primaryStage.setMinWidth(screenWidth/5);
			primaryStage.setHeight(screenHeight/2);
			primaryStage.setMinHeight(screenHeight/5);
			
			Scene startscene = new Scene(new MainScreen());
			
			startScene = startscene;
		
			

			primaryStage.setScene(startScene);
			primaryStage.show();
			
			for (Node divider : MainScreen.getScreen().getSplitPane().lookupAll(".split-pane-divider")) {
				divider.setMouseTransparent(true);
			}
			
		});
		
	}
	
	public static Stage getStage(){
		return stage;
	}
	
	/*public static void changeScene(Scene scene){
		stage.setScene(scene);
		stage.show();
		getStage().sizeToScene();
	}*/
	
	private static void CLOSE(){
		//speichern und co. TODO
	}
	
	/*public static void chooseCloseOperation(WindowEvent evt){
		if(startScene == getStage().getScene()){
			System.exit(0);
		}else{
			changeScene(startScene);
			evt.consume();
		}
	}*/
}
