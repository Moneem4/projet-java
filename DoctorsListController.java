package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DoctorsListController {
	@FXML
	private TableView<User> table;

	@FXML
	private TableColumn<User, String> username, fullname, mdp;

	@FXML
	private JFXButton add, update, delete;

	@FXML
	private JFXHamburger returnBtn;

	private ObservableList<User> data;

	@FXML
	void addDoctor(ActionEvent event) {

	}

	@FXML
	void deleteDoctor(ActionEvent event) {

	}

	@FXML
	void updateDoctor(ActionEvent event) {

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

	@FXML
	private void initialize() {
		// data = DBConnection.selection();
		username.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
		fullname.setCellValueFactory(new PropertyValueFactory<User, String>("fullname"));
		mdp.setCellValueFactory(new PropertyValueFactory<User, String>("mdp"));
		data = FXCollections.observableArrayList(new User("Jacob", "Smith", "jacob.smith@example.com"),
				new User("Isabella", "Johnson", "isabella.johnson@example.com"),
				new User("Ethan", "Williams", "ethan.williams@example.com"),
				new User("Emma", "Jones", "emma.jones@example.com"),
				new User("Michael", "Brown", "michael.brown@example.com"));
		table.setItems(data);
	}
}
