package controller;

/*	Imports	*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javafx.scene.control.ProgressIndicator;
import model.project;
import application.Main;
import application.DbConfig;
import javafx.event.ActionEvent;
//import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
//import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.Pane;
//import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainController {
private Main main;
public void setMain(Main mainIn)
{
	main = mainIn;
}
public int mem_logid = 0;
public int code_mem = 0;
//Login inputs
@FXML private TextField Login_Username;
@FXML private PasswordField Login_Password;
@FXML private Button Login_Create;
@FXML private Button Login_button;
@FXML private Button Login_Forgot;
@FXML private Label Login_status;
//CA inputs
@FXML private TextField CA_FirstName;
@FXML private TextField CA_LastName;
@FXML private TextField CA_email;
@FXML private TextField CA_UserName;
@FXML private PasswordField CA_Password;
@FXML private PasswordField CA_Rpassword;
@FXML private Button CA_Submit_button;
@FXML private Button CA_GoBack;
@FXML private Label CA_status;
//Forgot Input
@FXML private TextField forgot_email;
@FXML private Button Forgot_Submit;
@FXML private Label forgot_status;
@FXML private Button forgot_goback_Button;
@FXML private Button forgot_continue_Button;
@FXML private Label forgot_status1;

//Main
@FXML private Label M_welcome;
@FXML private Button Main_Account_Button;
@FXML private Button Main_logout_Button ;

Stage stage;
Scene scene;
Parent root;

/*	Section A	*/

/*	On Click Events	*/
public void ClickLogOut(ActionEvent event) throws Exception
{
	stage = (Stage)((Button) event.getSource()).getScene().getWindow();
	root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
	scene = new Scene(root);
	stage.setScene(scene);
	mem_logid=0;
	stage.setTitle("Logout!");
}

public void ClickAccountUpdate(ActionEvent event) throws Exception
{
	Account();
}

public void ClickCA(ActionEvent event) throws Exception
{
	stage = (Stage)((Button) event.getSource()).getScene().getWindow();
	root = FXMLLoader.load(getClass().getResource("/view/CreateAccount.fxml"));
	scene = new Scene(root);
	stage.setScene(scene);
}

public void ClickCAGoBack(ActionEvent event) throws Exception
{
	stage = (Stage)((Button) event.getSource()).getScene().getWindow();
	root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
	scene = new Scene(root);
	stage.setScene(scene);
}

public void ClickForgotButton(ActionEvent event) throws Exception
{
	stage = (Stage)((Button) event.getSource()).getScene().getWindow();
	root = FXMLLoader.load(getClass().getResource("/view/ForgotView.fxml"));
	scene = new Scene(root);
	stage.setScene(scene);
}

public void ClickCASubmit(ActionEvent event) throws Exception{
	CASubmit();
}
public void ClickLoginButton (ActionEvent event) throws Exception
{
	authenticate();
	if (mem_logid != 0)
	{
		stage = (Stage)((Button) event.getSource()).getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);
		welcome();
	}
}

public void ClickForgotSubmit (ActionEvent event) throws Exception{
	
	forgot();
	
}

public void ClickForgotC (ActionEvent event) throws Exception
{

}

/*	Section B	*/

/*	Forgot Username / Password 	- Emails user their password 	*/
private void forgot() {	
	String query = "update login set Acode = ? where email = ? ";
	try (Connection conn = DbConfig.getConnection();
			PreparedStatement insertprofile = conn.prepareStatement(query);){
		int code;
		String email;
		code =  (int) (Math.random() * 1000000000+1);
		email = forgot_email.getText();
		code_mem = code;
		project forgot = new project();
		String emailregex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.(?:[A-Z]{2,}|com|org|net|edu))+$";	//Emails Allowed: .com, .org, .net, .edu
		Boolean emailResult = email.matches(emailregex);
		if (email == null || email == "" || emailResult == false) {
			throw new Exception("Invalid Email.");
		}
		forgot.setCode(code);
		
		forgot.setEmail(email);
		insertprofile.setInt(1, forgot.getCode());
		insertprofile.setString(2, forgot.getEmail());
		int result = insertprofile.executeUpdate();
		if (result ==1){
			
			forgot_status.setText("Email is valid. Sending email please wait!");
			forgot_status.setTextFill(Color.GREEN);
			
			
			
		}else{
			throw new Exception("Invalid Email!");
			
		}
		
	Properties props = new Properties();
	
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.socketFactory.port", "465");
	props.put("mail.smtp.socketFactory.class",
			"javax.net.ssl.SSLSocketFactory");
	props.put("mail.smtp.auth", "true");
	
	props.put("mail.smtp.port", "465");

	Session session = Session.getDefaultInstance(props,
		new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
			
				return new PasswordAuthentication("","");
			}
		});

	

		Message message = new MimeMessage(session);
		
		message.setFrom(new InternetAddress("jmccaron@gmail.com"));
	
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(forgot_email.getText()));
		
		
		message.setText("Hello," +
				"\n\n Your password reset code is:  "+ code_mem + " \n Thank you!" );
	
		Transport.send(message);

		System.out.println("Done");
		code_mem= 0;
		
		forgot_status.setText("Send! Please check your email");
		

	} catch (Exception e) {
		forgot_status.setText(e.getMessage());
	
		System.out.println(e);
	}
	
}

/*	Account 	*/
public void Account() throws Exception{
	
}

/*	Welcome Screen	*/
private void welcome() throws Exception{
	System.out.println(mem_logid);

	String query = "Select firstname, lastname from login where loginid = ?";
	try(Connection conn= DbConfig.getConnection()){
		PreparedStatement welcome = conn.prepareStatement(query); 
		welcome.setInt(1, mem_logid);
	ResultSet result1 = welcome.executeQuery();
		if (result1.next()){
			
			String firstname = result1.getString("firstname");
			String lastname = result1.getString("lastname");
			stage.setTitle(" Welcome " + firstname + " " + lastname+ "! ");
			M_welcome.setText("System Error: missing logid ");
			
			
		}else{
			M_welcome.setText("System Error: missing logid ");
		}
	}catch(Exception e){
		System.out.println(" " +e.getMessage());
		
	}
	
}

/*	Authentication 	*/
public void authenticate() throws Exception {
	String query = "Select LoginId from Login Where username = ? AND password = aes_encrypt(?,?) ";
	try(Connection conn= DbConfig.getConnection()){
		PreparedStatement authenticate = conn.prepareStatement(query); 
		authenticate.setString(1,Login_Username.getText());
		authenticate.setString(2, Login_Password.getText());
		authenticate.setString(3, Login_Password.getText());
		ResultSet result = authenticate.executeQuery();
		if (result.next()){
			
			Login_status.setText("Success!!");
			Login_status.setTextFill(Color.GREEN);
			mem_logid = result.getInt("LoginId");
			System.out.println(mem_logid);
			
			
		}else{
			
			Login_status.setText("Incorrect Login credentials! Try Again");
			Login_status.setTextFill(Color.RED);
			
		}
			
		
	}catch (Exception e) {
			Login_status.setText(" " + e.getMessage());
		
	}
}

/*	Section C 	*/
/*	Create Account - Submittion	to Database*/
public void CASubmit() throws Exception {
	String query = "insert into Login " + "(LoginId, email, Username, password, firstname, lastname)"
			+ "values(?,?,?,aes_encrypt(?,?),?,?)";
	//String query = "insert into Login " + "(LoginId, email, Username, password, firstname, lastname)"
		//	+ "values(1234,4343,2323,aes_encrypt(2323,2323),2323,2323)";
	

	try (Connection conn = DbConfig.getConnection();
			PreparedStatement insertprofile = conn.prepareStatement(query);){
		int UserId;
		String email, username, password,firstname,lastname, rpassword;
		UserId =  (int) (Math.random() * 100000000+1);
		firstname = CA_FirstName.getText();
		lastname = CA_LastName.getText();
		email = CA_email.getText();
		username = CA_UserName.getText();
		password = CA_Password.getText();
		rpassword = CA_Rpassword.getText();
		String emailregex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.(?:[A-Z]{2,}|com|org|net|edu))+$";	//Emails Allowed: .com, .org, .net, .edu
		String passregex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
		Boolean passResult = password.matches(passregex);
		Boolean emailResult = email.matches(emailregex);



		// value validation
		if (firstname == null || firstname == "" || firstname.trim().isEmpty()) {
			throw new Exception("Need first name.");
		}

		if (lastname == null || lastname == "" || lastname.trim().isEmpty()) {
			throw new Exception("need last name.");
		}
		if (email == null || email == "" || emailResult == false) {
			throw new Exception("Invalid Email.");
		}
		if (username == null || username == "" || username.trim().isEmpty()) {
			throw new Exception("Need Username");
		}
		if (password == null || password == "" || password.trim().isEmpty()) {
			throw new Exception("Need Password.");
		}
		if (password.length() <= 7){

			throw new Exception ("Password is to short! ");
		}
		if (rpassword == null || rpassword == "" || rpassword.trim().isEmpty()) {
			throw new Exception("Need Password.");
		}
		if (passResult == false)
			throw new Exception("Invalid Password." + "\b 1 Upper, 1 lower, 1 Special, min 8 characters.");

		if (password.equals (rpassword)) {
			System.out.println("password good");
		}else {
			throw new Exception ("Passwords do not match");
		}

		//
		project login = new project();
		login.setUserId(UserId);
		login.setFirstName(firstname);
		login.setLastName(lastname);
		login.setEmail(email);
		login.setUsername(username);
		login.setPassword(password);
		
		

		insertprofile.setInt(1, login.getUserId());
		insertprofile.setString(2, login.getEmail());
		insertprofile.setString(3, login.getUsername());
		insertprofile.setString(4, login.getPassword());
		insertprofile.setString(5, login.getPassword());
		insertprofile.setString(6, login.getFirstName());
		insertprofile.setString(7, login.getLastName());

		
		int affectedRow = insertprofile.executeUpdate();

		if (affectedRow == 1) {
			

		

			CA_FirstName.setText(null);
			CA_LastName.setText(null);
			CA_email.setText(null);
			CA_UserName.setText(null);
			CA_Password.setText(null);
			CA_Rpassword.setText(null);
			CA_status.setText("Your Submittion has been completed " + login.getFirstName() + " " + login.getLastName() +
					"an email will be sent to you (" + login.getEmail() + ") shortly");
		}

	} catch (Exception e) {
		CA_status.setText(" " + e.getMessage() + "please try again ");
		e.getMessage();
	//} finally {
		//if (keys != null) {
			//keys.close();
	//	}
	}
	

	}

	

}
