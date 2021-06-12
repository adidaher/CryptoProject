package application;
	


import java.util.HashMap;

import Algo.Client;
import Algo.server;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;



import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Main extends Application {


	
    @FXML
    private Button signInbtn;

    @FXML
    private TextField username;

    @FXML
    private TextField password;
    
    @FXML
    private Label errormsg;
    
    public void start(Stage primaryStage) {
	
		try {

		    Parent root = FXMLLoader.load(getClass().getResource("signInGui.fxml"));
	        Scene scene1 = new Scene(root);
	        primaryStage.setTitle("Log-In");
	        primaryStage.setScene(scene1);
	        primaryStage.show();
    	} catch (Exception e) {
		    e.printStackTrace();
	   }

	}

    
    @FXML
    void SignIn(ActionEvent event) {

    	errormsg.setVisible(false);
    	if(Client.checkUser(username.getText() ,password.getText())) {
    		if(server.Bob.getUsername().equals(username.getText()))
    		{
    			Client.onlinePerson=server.Bob;
    		}
    		else if(server.Alice.getUsername().equals(username.getText()))
    			Client.onlinePerson = server.Alice;

    		emailSender e = new emailSender();
    		e.start(new Stage());
    	} else {
    		
    		errormsg.setVisible(true);
    	}
    	
    }
    
   
	public static void main(String[] args) {
		launch(args);
	}
}



