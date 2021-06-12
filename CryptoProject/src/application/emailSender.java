package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import Algo.Client;
import Algo.Des;
import Algo.Point;
import Algo.digitalSignature;
import Algo.server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class emailSender implements Initializable {

	@FXML
	private TextArea EmailTextButtom;

	@FXML
	private TextArea FromLabel;

	@FXML
	private TextArea Tolabel;

	@FXML
	private Button OpenButton;

	@FXML
	private Button sendButtom;

	@FXML
	private Label InboxLabel;

	@FXML
	private Label usernameOnline;

	private person person;
	person reciever, sender;
	static int q;
	static int a;
	static int xa;// private
	static Point S;
    int flag=1;
	public void initialize(URL location, ResourceBundle resources) {
		try {

			usernameOnline.setText("Welcome  " + Client.onlinePerson.getUsername());
			FromLabel.setText(Client.onlinePerson.getEmail());

			if (Client.onlinePerson.getUsername().equals(server.Alice.getUsername()))
				Tolabel.setText(server.Bob.getEmail());
			else
				Tolabel.setText(server.Alice.getEmail());

			if (Client.onlinePerson.getInboxFlag() == 1) {
				InboxLabel.setText("you have one message");
				OpenButton.setVisible(true);
			} else {
				InboxLabel.setVisible(false);
				OpenButton.setVisible(false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void start(Stage primaryStage) {
		try {

			Parent root = FXMLLoader.load(getClass().getResource("EmailGui.fxml"));
			Scene scene1 = new Scene(root);
			primaryStage.setTitle("Email");
			primaryStage.setScene(scene1);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void openBtn(ActionEvent event) {

		receivedCC receive = new receivedCC();
		if (server.Bob.getEmail().equals(FromLabel.getText())) {
			receive.recevier = server.Bob;
		} else {
			receive.recevier = server.Alice;
		}
		receive.start(new Stage());

	}

	@FXML
	void sendBtn(ActionEvent event) {

		String temp;
		int i = 0, j = 0;
		String sendto = Tolabel.getText();
		String message = EmailTextButtom.getText();
		System.out.println(sendto);

		if (server.Bob.getEmail().equals(sendto)) {
			reciever = server.Bob;
			sender = server.Alice;
		} else {
			sender = server.Bob;
			reciever = server.Alice;
		}
		
		
		digitalSignature ds = new digitalSignature();
		q = 19;
		a = 10;
		Random random = new Random();
	    xa = random.nextInt(((q-2) - 2) + 1) + 2;			
		String M_without_Spaces = EmailTextButtom.getText().replaceAll("\\s+","");
		
		System.out.println("now signing..");
		S = ds.signing(M_without_Spaces, a, xa, q);
		receiveInit(reciever);

        while(flag==1) {
        sender.Create_Public_key(); // create ka and a
		reciever.Create_Public_key(); // create kb and b
		
		//singing the kb (bob singing the kb and send s and tha kb to alice)
	    ArrayList<Point> arr = reciever.getPublic_key(sender);
	    
	    //alice get the Kb and verificate
	    if (ds.verification(arr.get(1).getX().toBigInteger().toString(16), sender.getYa(), arr.get(0), sender.getA(),sender.getQ())) {
		              sender.calculate_shared_key(arr.get(1)); // send the kb to the sender
		              if(!Des.weakey.contains(sender.hex_shared_Key))
		            	  flag=0;
	    } else {
	    	EmailTextButtom.setText("you are under attack");
	    	flag=0;
	    	return;
	    }
	}
	    
		reciever.calculate_shared_key(sender.Public_key); // send the public key to receiver

		//now encrypt the email and send it to the receiver
		String lines[] = message.split("\\r?\\n");
		for (String line : lines) {
			temp = Des.Encryption(line, sender.hex_shared_Key);
			reciever.addText(temp);
		}
		    reciever.setInboxFlag(1);
		    updater();

	}


	public static void receiveInit(person recevier) {
		recevier.setS(S);
		recevier.setQ(q);
		recevier.setA(a);
		recevier.setYa((int) (Math.pow(a, xa) % q));
	}
	
	
	public void updater() {
		person nowOnilne;
		  Thread updater = new Thread(() -> {
		        while (true) {
		            
		        	if (server.Bob.getEmail().equals(FromLabel.getText())) {
		        		sender = server.Bob;
		    		} else {
		    			sender = server.Alice;
		    		}
		           if(sender.getInboxFlag() == 1 ) {
		        	   InboxLabel.setVisible(true);
					   OpenButton.setVisible(true);
		           }else {
		        	   InboxLabel.setVisible(false);
						OpenButton.setVisible(false);
		           }
		        }
		    });   updater.start();
	}
	
	

}
