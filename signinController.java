package application;

import java.io.IOException;
import javax.swing.JOptionPane;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class signinController {
	@FXML
	private JFXTextField username, fullname, immat, speciality;
	@FXML
	private JFXPasswordField mdp;
	@FXML
	private JFXButton signin, returnBtn;
	@FXML
	private JFXComboBox<?> departements;
	@FXML
	private JFXRadioButton doctor, Nurse;

	@FXML
	void initialize() {
		immat.setDisable(true);
		final ToggleGroup group = new ToggleGroup();
		doctor.setToggleGroup(group);
		Nurse.setToggleGroup(group);
		ObservableList depts = DBConnection.getDepartements();
		departements.setItems(depts);
	}

	@FXML
	void showImmat(MouseEvent event) {
		immat.setDisable(false);
	}

	@FXML
	void hideImmat(MouseEvent event) {
		immat.setDisable(true);
	}

	@FXML
	void returnFn(ActionEvent event) {
		try {
			Parent login = FXMLLoader.load(getClass().getResource("login.fxml"));
			Scene login_scene = new Scene(login);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			app_stage.setScene(login_scene);
			app_stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void signinFn(ActionEvent event) {
		if (!username.getText().equals("") && !fullname.getText().equals("") && !mdp.getText().equals("")
				&& !departements.getSelectionModel().isEmpty() && !speciality.getText().equals("")
				&& !(doctor.isSelected() && immat.getText().equals("")) && departements.getSelectionModel().getSelectedIndex()!=0) {
			int id = DBConnection.ajout(new User(username.getText(), fullname.getText(), mdp.getText()),
					departements.getSelectionModel().getSelectedIndex());
			if (doctor.isSelected())
				DBConnection.addDoctor(id, immat.getText(), speciality.getText());
			else
				DBConnection.addNurse(id, speciality.getText());
			try {
				Parent sign = FXMLLoader.load(getClass().getResource("login.fxml"));
				Scene login_scene = new Scene(sign);
				Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				app_stage.setScene(login_scene);
				app_stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
