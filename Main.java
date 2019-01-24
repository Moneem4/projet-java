package application;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	StackPane pane;
	AnchorPane root, sign;
	Stage stage;
	private boolean welcomeIsLoaded;

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		if (!welcomeIsLoaded)
			loadSplashScreen();
		try {
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("DBConfig.fxml"));
			Scene scene = new Scene(root, 1000, 650);
			if (!welcomeIsLoaded)
				root.getChildren().setAll(pane);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("img/icon4.png")));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void loadSplashScreen() {
		try {
			pane = FXMLLoader.load(getClass().getResource("Splash.fxml"));
			FadeTransition fadin = new FadeTransition(Duration.seconds(3), pane);
			fadin.setFromValue(0);
			fadin.setToValue(1);
			fadin.setCycleCount(1);

			FadeTransition fadout = new FadeTransition(Duration.seconds(3), pane);
			fadout.setFromValue(1);
			fadout.setToValue(0);
			fadout.setCycleCount(1);

			fadin.play();

			fadin.setOnFinished((e) -> {
				fadout.play();
			});

			fadout.setOnFinished((e) -> {
				try {
					welcomeIsLoaded = true;
					this.start(stage);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
