package application;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;
import javafx.stage.Stage;


public class MainController implements Initializable {
	
	private Jedis jedis = new Jedis("localhost");
	private String keysMap = "keys";
	private String key;
	protected static String field;
	protected static String value;
	
	//Text fields
	
	@FXML
	protected TextField keyText;
	@FXML
	protected TextField fieldText;
	@FXML
	protected TextField valueText;
	@FXML
	private Label statusLabel;
	//Error Labels
	@FXML
	public Label keyErrorLabel;
	@FXML
	public Label fieldErrorLabel;
	@FXML
	public Label valueErrorLabel;
	
	
	//Key Holders
	@FXML
	public ComboBox<String> keyBox;
	private ObservableList<String> keyList;
		
	//Field Holders
	@FXML
	public ComboBox<String> fieldBox;
	private ObservableList<String> fieldList;
		
	//errorcheck
	protected static boolean err = false;
	
	//data insertion
	public void Declaredata(ActionEvent event) throws IOException, TwoKeysException, NoDataException{
		//two or no keys entered
			try  {
				// ||!(keyBox.getValue().trim().isEmpty()))
				if(keyBox.getValue()==null && keyText.getText().trim().isEmpty())
				{
					throw new NoDataException();
				}
				if((!(keyBox.getValue() == null) && !keyText.getText().trim().isEmpty()))
				{
					throw new TwoKeysException();
				}
				}
				catch(NoDataException e)
				{
					keyErrorLabel.setText("Choose an existing key or create a new one.");

				}
				catch(TwoKeysException e) {
					keyErrorLabel.setText("You can not choose two keys simultaniously!");
					keyText.setText("");
				}
				catch(NullPointerException e) {
					e.printStackTrace();
				}
			try {
				if(keyBox.getValue() == null && !(keyText.getText().trim().isEmpty()))
					key = keyText.getText();
				else 
					key = keyBox.getValue();
				
				if(fieldBox.getValue() == null && fieldText.getText()!=null)
					field = fieldText.getText();
				else
					field = fieldBox.getValue();
				
				value  = valueText.getText();
				
				Map<String, String> kvMap = new HashMap<>();
				kvMap.put(field, value);
				
				jedis.hset(key, kvMap);
				
				//key holder map 
				Map<String, String> holder= new HashMap<>();
				holder.put(key, "");
				jedis.hset(keysMap,holder);
				statusLabel.setText("set");
				valueText.setText("");
					
		}catch(JedisDataException e) {
			popUpWarning();
		}
		catch(NullPointerException e) {
			popUpWarning();
		}
		}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			
				keyList = FXCollections.observableArrayList(jedis.hkeys(keysMap));
				keyBox.setItems(keyList);
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
	}
	
//key selection
	public void keyChosen(ActionEvent event) {
		key = keyBox.getValue();

		fieldList = FXCollections.observableArrayList(jedis.hkeys(key));
		fieldBox.setItems(fieldList);
	}
	//field selection 
	public void fieldChosen(ActionEvent event) {
		field = fieldBox.getValue();
	}
	
	//reset
	public void reseter() {
			keyList = FXCollections.observableArrayList(jedis.hkeys(keysMap));
			keyBox.setItems(keyList);
			fieldBox.setItems(null);
			keyBox.setValue("");
			fieldBox.setValue("");
			keyText.setText("");
			fieldText.setText("");
			valueText.setText("");
			keyErrorLabel.setText("");
			statusLabel.setText("");
	}
	
	protected void popUpWarning() {
		try {
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("Warning.fxml"));
			Scene scene = new Scene(root,350,150);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		}
		catch(IOException e) {e.printStackTrace();}
	}
}
