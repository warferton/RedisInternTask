package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PupupController extends MainController{
	@FXML
	Button close = new Button();
	@FXML
	void okBtn()
	{
		Stage stage = (Stage) close.getScene().getWindow();
	    stage.close();
	}
	@FXML
	void setDefault()
	{
		MainController.field = "DefultField";
		MainController.value = "DefaultValue";
		Stage stage = (Stage) close.getScene().getWindow();
	    stage.close();
	}

}
