package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class medicalFilesController {
	@FXML
	private TableView<User> table = new TableView<User>();

	@FXML
	private TableColumn id, patName, age, docName, date, daignosis, treatment;

	@FXML
	private JFXButton add, update, delete;

	@FXML
	void addMedicalFile(ActionEvent event) {

	}

	@FXML
	void deleteMedicalFile(ActionEvent event) {

	}

	@FXML
	void updateMedicalFile(ActionEvent event) {

	}

	@FXML
	void showMainFn(MouseEvent event) {
		try {
			Parent main = FXMLLoader.load(getClass().getResource("menu3.fxml"));
			Scene main_scene = new Scene(main);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			app_stage.setScene(main_scene);
			app_stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
