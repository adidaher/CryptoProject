package application;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

import Algo.ECDH;
import Algo.Point;
import Algo.digitalSignature;

public class person {
	String username, email,pwd;
	int inboxFlag=0;
	ArrayList<String> text ;
	ECDH deffie_hellman = new ECDH();
	String hex_shared_Key;
	
	
	Point Public_key;  //ka
	int random_Key;    //a
	Point shared_key;   //kab

	Point s , KeyS;
	int q;
	int a;
	int ya;
	public int getYa() {
		return ya;
	}

	public void setYa(int ya) {
		this.ya = ya;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getQ() {
		return q;
	}

	public void setQ(int q) {
		this.q = q;
	}

	public Point getS() {
		return s;
	}

	public void setS(Point s) {
		this.s = s;
	}

	public person(String username, String email, String pwd) {
		super();
		this.username = username;
		this.email = email;
		this.pwd = pwd;
		this.text = new ArrayList<String>();
	    
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public void addText(String textString  ) {
		
		text.add(textString);		
	}
	
	
	public ArrayList<String> getText() {
		return text;
	}

	

	public String gethex_shared_Key() {
		return hex_shared_Key;
	}

	public void sethex_shared_Key(String hex_shared_Key) {
		this.hex_shared_Key = hex_shared_Key;
	}

	public int getInboxFlag() {
		return inboxFlag;
	}

	public void setInboxFlag(int inboxFlag) {
		this.inboxFlag = inboxFlag;
	}

	public void Create_Public_key() { //return rand num 1 -  p-1
		Random random = new Random();
		this.random_Key = random.nextInt((10000-1)+1)+1;
		this.Public_key= deffie_hellman.calculate_Key(new BigDecimal(String.valueOf(random_Key)), deffie_hellman.g);
		
	}

	public ArrayList<Point> getPublic_key(person sender) { //xa or xb 
		digitalSignature ds = new digitalSignature();
		// Point signing(String M, int a, int Xa, int q)
		q = 19;
		a = 10;
		Random random = new Random();
	    int xa = random.nextInt(((q-2) - 2) + 1) + 2;	
	    Point S = ds.signing(Public_key.getX().toBigInteger().toString(16), a, xa, q);
	    sender.setS(S);
	    sender.setQ(q);
	    sender.setA(a);
	    sender.setYa((int) (Math.pow(a, xa) % q));
	    ArrayList<Point> arr = new ArrayList<>();
		arr.add(S);
		arr.add(Public_key);
		return arr;
	}

	public void setPublic_key(Point private_key) {
		this.Public_key = private_key;
	}
	
	
	public void calculate_shared_key(Point received_key){
		
		shared_key = deffie_hellman.calculate_Key(new BigDecimal(String.valueOf(random_Key)) , received_key);
		String key = shared_key.getX().toBigInteger().toString(16);
		hex_shared_Key = key.substring(key.length()-16);

	}



}
