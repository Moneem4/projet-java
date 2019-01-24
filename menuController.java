package application;

import java.io.IOException;

import com.jfoenix.controls.JFXHamburger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class menuController {
	@FXML
	private Pane p1, p2, p3, p4, p5, p6, p7, p8, p9;
	@FXML
	private ImageView home, docs, search, messages, newDoc, logout, doctors,addAdmin, icon,adChef,icon1;
	@FXML
	private JFXHamburger returnBtn;
	@FXML
	private Label label_admin,addChefL;
	private String nextColor = "#4059a9;", style = "-fx-background-radius: 50; -fx-background-color: ";
	@FXML
    private Pane glass;
	
	@FXML
	void initialize(){
		icon.setVisible(!loginController.userType.equals("admin"));
		icon1.setVisible(!loginController.userType.equals("admin"));
    	addAdmin.setVisible(loginController.userType.equals("admin"));
    	label_admin.setVisible(loginController.userType.equals("admin"));
    	adChef.setVisible(loginController.userType.equals("admin"));
    	addChefL.setVisible(loginController.userType.equals("admin"));
    	p2.setDisable(loginController.userType.equals("nurse"));

	}
	
	@FXML
    void addChefFn(MouseEvent event) {

    }
	
	@FXML
	void changeBG(MouseEvent event) {
		((Pane) ((ImageView) event.getSource()).getParent()).setStyle(style + "#25F893;");
	}

	@FXML
	void hoverBG(MouseEvent event) {
		((Pane) ((ImageView) event.getSource()).getParent()).setStyle(style + nextColor);
		nextColor = (nextColor.equals("#4059a9;") == true) ? "#cd017f;" : "#4059a9;";
	}

	@FXML
	void exitBG(MouseEvent event) {
		((Pane) ((ImageView) event.getSource()).getParent()).setStyle("-fx-background-color: none;");
	}

	@FXML
	void logoutFn(MouseEvent event) {
		switchTo("login.fxml", (Stage) ((Node) event.getSource()).getScene().getWindow());
	}

	@FXML
	void NewFileFn(MouseEvent event) {

	}

	@FXML
	void searchFn(MouseEvent event) {

	}
	
	@FXML
	void addAdminFn(MouseEvent event){
		switchTo("addAdmin.fxml", (Stage) ((Node) event.getSource()).getScene().getWindow());
	}

	@FXML
	void showDoctorsList(MouseEvent event) {
		switchTo("DoctorsList.fxml", (Stage) ((Node) event.getSource()).getScene().getWindow());
	}

	@FXML
	void showMedicalFilesFn(MouseEvent event) {
		switchTo("medicalFiles.fxml", (Stage) ((Node) event.getSource()).getScene().getWindow());
	}

	@FXML
	void showMessagesFn(MouseEvent event) {
		switchTo("../messaging/messages.fxml", (Stage) ((Node) event.getSource()).getScene().getWindow());
	}

	@FXML
	void showMainFn(MouseEvent event) {
		switchTo("main.fxml", (Stage) ((Node) event.getSource()).getScene().getWindow());
	}

	public void switchTo(String scene, Stage stage) {
		try {
			Parent main = FXMLLoader.load(getClass().getResource(scene));
			Scene sign_scene = new Scene(main);
			stage.setScene(sign_scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
