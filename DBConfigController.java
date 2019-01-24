package application;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import perhapsFiles.common.SwitchScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DBConfigController {
	@FXML
	private JFXTextField host, port, db, username;
	@FXML
	private JFXPasswordField mdp;
	@FXML
	private JFXButton connect, reset;

	@FXML
	void connectFn(ActionEvent event) {
		boolean connected = DBConnection.getConnexion(host.getText(), port.getText(), db.getText(), username.getText(),"");
		if (connected) {
			connected = DBConnection.creation();
			if (connected) {
				try {
					Parent login = FXMLLoader.load(getClass().getResource("login.fxml"));
					Scene login_scene = new Scene(login);
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					app_stage.setScene(login_scene);
					app_stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Serveur Error! Check your configuration again please.",
						"Serveur Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@FXML
	void resetFn(ActionEvent event) {
		host.setText("");
		port.setText("");
		db.setText("");
		username.setText("");
		mdp.setText("");
	}
}
