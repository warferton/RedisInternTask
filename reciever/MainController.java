package application;

import java.net.URL;
import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import redis.clients.jedis.Jedis;

public class MainController implements Initializable{
	private Jedis jedis = new Jedis("localhost");

	@FXML
	public Label valueText;
	@FXML
	public ListView<String> keyList;
	@FXML
	public ListView<String> fieldList;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> insertList = FXCollections.observableArrayList(jedis.hkeys("keys"));
		try {
		keyList.setItems(insertList);
		}
		catch(Exception e) {
		}
	}
	
	//----------
	//valueText.setText(jedis.hget(keyList.getSelectionModel().getSelectedItem(), fieldList.getSelectionModel().getSelectedItem()));
	//----------
	
	//Buttons
	public void showData(ActionEvent event) {
		ObservableList<String> insertList = FXCollections.observableArrayList(jedis.hkeys(keyList.getSelectionModel().getSelectedItem()));
		try {
		fieldList.setItems(insertList);
		//boolean shows = true;
		//if(shows)
		//{
			valueText.setText(jedis.hget(keyList.getSelectionModel().getSelectedItem(), fieldList.getSelectionModel().getSelectedItem()));
		//}
		}catch(Exception e) {
		}
	}
	public void flusher() {
		jedis.flushAll();
		keyList.setItems(null);
		fieldList.setItems(null);
		valueText.setText("");
	}
	public void refresher() {
		ObservableList<String> insertList = FXCollections.observableArrayList(jedis.hkeys("keys"));
		try {
		keyList.setItems(insertList);
		valueText.setText("");
		}
		catch(Exception e) {System.out.println("Pizdec");
		}
	}
	
}
