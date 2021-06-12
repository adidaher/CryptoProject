package application;
import java.net.URL;
import java.util.ResourceBundle;
import Algo.Des;
import Algo.digitalSignature;
import Algo.server;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class receivedCC implements Initializable {

	@FXML
	private TextArea RecievedText;

	@FXML
	private TextArea RecieveFromLabel;

	static person recevier;

	public void start(Stage primaryStage) {
		try {

			Parent root = FXMLLoader.load(getClass().getResource("recievedMail.fxml"));
			Scene scene1 = new Scene(root);
			primaryStage.setTitle("Inbox");
			primaryStage.setScene(scene1);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {// TODO Auto-generated method stub

			int i = 0;
			String email_String = "", cipher_textString = "";

			if (recevier.getUsername().equals(server.Alice.getUsername()))
				RecieveFromLabel.setText(server.Bob.getEmail());
			else
				RecieveFromLabel.setText(server.Alice.getEmail());

			while (recevier.getText().size() > 0) {
				cipher_textString = recevier.getText().get(0);
				recevier.getText().remove(0);
				System.out.println("Bob cipher msg " + cipher_textString);
				email_String += Des.Decryption(cipher_textString, recevier.hex_shared_Key);
				// if(i!=0)
				email_String += "\n";
				i++;
			}
			
			recevier.getText().clear();
			recevier.setInboxFlag(0);
			emailSender.receiveInit(recevier);
			digitalSignature dsBob = new digitalSignature();
			String M_without_Spaces = email_String.replaceAll("\\s+","");
			

			if (dsBob.verification(M_without_Spaces, recevier.getYa(), recevier.getS(), recevier.getA(),
					recevier.getQ()))
				RecievedText.setText(email_String);
			else
				RecievedText.setText("");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
