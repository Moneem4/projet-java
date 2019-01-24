package application;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class loginController {
	public static User user;
	public static String userType;
	@FXML
	private Label status;
	@FXML
	private JFXTextField username;
	@FXML
	private JFXPasswordField mdp;
	@FXML
	private JFXButton login, signin;
	@FXML
	private JFXRadioButton nurse, doctor, admin;

	@FXML
	void initialize() {
		final ToggleGroup group = new ToggleGroup();
		doctor.setToggleGroup(group);
		nurse.setToggleGroup(group);
		admin.setToggleGroup(group);
	}

	@FXML
	void loginFn(ActionEvent event) {
		if (!username.getText().equals("") && !mdp.getText().equals("")
				&& (doctor.isSelected() || nurse.isSelected() || admin.isSelected())) {
			user = DBConnection.requetePreparee(username.getText());
			if (user != null && user.getMdp().equals(mdp.getText())) {
				error("");
				// JOptionPane.showMessageDialog(null, "Connecté", "operation
				// reussite", 1);
				userType = (doctor.isSelected()) ? "doctor" : (nurse.isSelected()) ? "nurse" : "admin";
				if (userType.equals("admin"))
					user = DBConnection.adminLogin(username.getText());
				if (user != null)
					try {
						Parent main = FXMLLoader.load(getClass().getResource("main.fxml"));
						Scene main_scene = new Scene(main);
						Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
						app_stage.setScene(main_scene);
						app_stage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}
			} else {
				error("Enter a valid username and password!");
				username.setText("");
				mdp.setText("");
			}
		} else {
			error("All fields are required!");
		}
	}

	@FXML
	void signinFn(ActionEvent event) {
		try {
			Parent sign = FXMLLoader.load(getClass().getResource("signin.fxml"));
			Scene sign_scene = new Scene(sign);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			app_stage.setScene(sign_scene);
			app_stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void error(String msg) {
		status.setText(msg);
	}
}
