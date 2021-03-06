package controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
import application.smtp;
import java.util.Properties;
import javax.mail.Message;
//import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//import javafx.scene.control.ProgressIndicator;
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
import javafx.scene.layout.GridPane;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.Pane;
//import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
//import javafx.stage.Modality;
import javafx.stage.Stage;
//import javafx.stage.StageStyle;

public class MainController {
	



private Main main;
public void setMain(Main mainIn)
{
	main = mainIn;
}

public static int mem_logid;
public int code_mem;
public static String mem_email;
public static String mem_username;
public static String mem_timestamp;
String emailregex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.(?:[A-Z]{2,}|com|org|edu|net))+$";
//email connection


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
@FXML private Button forgot_next;
//Forgot Next Input
@FXML private TextField fn_email;
@FXML private TextField fn_code;
@FXML private PasswordField fn_password;
@FXML private PasswordField fn_rpassword;
@FXML private Label fn_status;
@FXML private Button fn_resetB;
@FXML private Button Fn_Goback;
//Account
@FXML private Button UA_UUB;
@FXML private Button UA_UE;
@FXML private Button UA_UPB;
@FXML private Button UA_DAB;
@FXML private TextField UA_username;
@FXML private TextField UA_email;
@FXML private PasswordField UA_password;
@FXML private PasswordField UA_rpassword;
@FXML private Label UA_status;
//Main
@FXML private Label m_welcome;
@FXML private Button Main_Account_Button;
@FXML private Button Main_logout_Button ;
//Delete
@FXML private PasswordField D_password;
@FXML private Button D_button;
@FXML private Button D_GB;
@FXML private Label D_status;
Stage stage;
Scene scene;
Scene scene2;
Parent root;
public void ClickLogOut(ActionEvent event) throws Exception{
	stage = (Stage)((Button) event.getSource()).getScene().getWindow();
	root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
	scene = new Scene(root);
	stage.setScene(scene);
	mem_logid=0;
	stage.setTitle("Logout!");
}
	public void ClickAccountUpdate(ActionEvent event) throws Exception{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Account.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		
		stage.setScene(new Scene(root));
		stage.show();
		
	}

public void ClickCA(ActionEvent event) throws Exception{
	

stage = (Stage)((Button) event.getSource()).getScene().getWindow();
root = FXMLLoader.load(getClass().getResource("/view/CreateAccount.fxml"));
scene = new Scene(root);
stage.setScene(scene);
}
public void ClickCAGoBack(ActionEvent event) throws Exception{
	

stage = (Stage)((Button) event.getSource()).getScene().getWindow();
root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
scene = new Scene(root);
stage.setScene(scene);
}
public void ClickForgotButton(ActionEvent event) throws Exception{
	

stage = (Stage)((Button) event.getSource()).getScene().getWindow();
root = FXMLLoader.load(getClass().getResource("/view/ForgotView.fxml"));
scene = new Scene(root);
stage.setScene(scene);

}
public void ClickCASubmit(ActionEvent event) throws Exception{
	CASubmit();
}
public void ClickLoginButton (ActionEvent event) throws Exception{
	authenticate();
	if (mem_logid != 0){
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
public void ClickDGB (ActionEvent event) throws Exception{
	stage = (Stage)((Button) event.getSource()).getScene().getWindow();
	root = FXMLLoader.load(getClass().getResource("/view/Account.fxml"));
	scene = new Scene(root);
	stage.setScene(scene);
}
public void ClickForgotCode (ActionEvent event) throws Exception{
	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/forgotInput.fxml"));
	Parent root = (Parent) fxmlLoader.load();
	Stage stage = new Stage();
	//stage = (Stage)((Button) event.getSource()).getScene().getWindow();
	//root = FXMLLoader.load(getClass().getResource("/view/forgotInput.fxml"));
	//stage.initModality(Modality.APPLICATION_MODAL);
	//stage.initStyle(StageStyle.UNDECORATED);
	//scene2 = new Scene(root);
	//stage.setScene(scene2);
	stage.setScene(new Scene(root));
	stage.show();
}
public void ClickFnButton (ActionEvent event) throws Exception{
	reset();
}
public void ClickUUB (ActionEvent event) throws Exception{
	String query = "update login set username = ? where loginid = ? ";
	try (Connection conn = DbConfig.getConnection();
			PreparedStatement insertprofile = conn.prepareStatement(query);){
		String username;int loginid;
		 loginid = mem_logid;
		username = UA_username.getText();
		
		project change = new project();
		change.setUserId(loginid);
		change.setUsername(username);
		insertprofile.setString(1, change.getUsername());
		insertprofile.setInt(2, change.getUserId());
		if (username == null || username == "" || username.trim().isEmpty()) {
			throw new Exception("Need Username");
		}
		int result = insertprofile.executeUpdate();
		
		
		if (result ==1){
			
			
			UA_status.setTextFill(Color.GREEN);}else{
			throw new Exception("Can not proceed");}
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
				
					return new PasswordAuthentication("fasttax3300@gmail.com","u'EE7K.:)2e6");
				}
			});
			Message message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress("fasttax3300@gmail.com"));
		
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mem_email));
			message.setSubject("Update Alert");
			
			message.setText("Hello," +
					"\n\n This is a friendly reminder that your username has been updated to " + change.getUsername() +
					"\n \n Thank you, FastTax");
		
			Transport.send(message);

			System.out.println("Done");
			UA_status.setText("Username reset");
			UA_username.setText(null);
		
	}catch (Exception e){
		UA_status.setTextFill(Color.RED);
		UA_status.setText(e.getMessage());
		System.out.println(mem_email);
	}
	
}
public void ClickUAUE (ActionEvent event) throws Exception{
	
		String query = "update login set email = ? where loginid = ? ";
		try (Connection conn = DbConfig.getConnection();
				PreparedStatement insertprofile = conn.prepareStatement(query);){
			String email;int loginid;
			 loginid = mem_logid;
			email = UA_email.getText();
			
			project change = new project();
			change.setUserId(loginid);
			change.setEmail(email);
			insertprofile.setString(1, change.getEmail());
			insertprofile.setInt(2, change.getUserId());
			Boolean emailResult = email.matches(emailregex);
			if (email == null || email == "" || emailResult == false) {
				throw new Exception("Invalid Email.");
			}
			int result = insertprofile.executeUpdate();
			
			
			if (result ==1){
				
				
				UA_status.setTextFill(Color.GREEN);}else{
				throw new Exception("Can not Proceed");}
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
					
						return new PasswordAuthentication("fasttax3300@gmail.com","u'EE7K.:)2e6");
					}
				});
				Message message = new MimeMessage(session);
				
				message.setFrom(new InternetAddress("fasttax3300@gmail.com"));
			
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(mem_email));
				message.setSubject("Update Alert");
				
				message.setText("Hello," +
						"\n\n This is a friendly reminder that your email has been updated to " + change.getEmail() + " for know on all notifications will be sent to this email."+
						"\n \n Thank you, FastTax");
			
				Transport.send(message);

				System.out.println("Done");
				UA_status.setText("Email reset");
				UA_email.setText(null);
			
		}catch (Exception e){
			UA_status.setTextFill(Color.RED);
			UA_status.setText(e.getMessage());
			System.out.println(mem_email);
		}
		
	
}
public void ClickUAUP(ActionEvent event) throws Exception{
	String query = "update login set password= aes_encrypt(?,?) where loginid = ? ";
	try (Connection conn = DbConfig.getConnection();
			PreparedStatement insertprofile = conn.prepareStatement(query);){
		String password,rpassword;int loginid;
		 loginid = mem_logid;
		password = UA_password.getText();
		rpassword = UA_rpassword.getText();
		
		project change = new project();
		change.setUserId(loginid);
		change.setPassword(password);
		insertprofile.setString(1, change.getPassword());
		insertprofile.setString(2, change.getPassword());
		insertprofile.setInt(3, change.getUserId());
		if (password == null || password == "" || password.trim().isEmpty()) {
			throw new Exception("Need Password.");
		}
		if (password.length() < 8){

			throw new Exception ("Password is to short! ");
		}
		if (rpassword == null || rpassword == "" || rpassword.trim().isEmpty()) {
			throw new Exception("Need Password.");
		}
		if (password.equals (rpassword)) {
			System.out.println("password good");
		}else {
			throw new Exception ("Passwords do not match");
		}
		int result = insertprofile.executeUpdate();
		
		
		if (result ==1){
			
			
			UA_status.setTextFill(Color.GREEN);}else{
			throw new Exception("Can not Proceed");}
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
				
					return new PasswordAuthentication("fasttax3300@gmail.com","u'EE7K.:)2e6");
				}
			});
			Message message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress("fasttax3300@gmail.com"));
		
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mem_email));
			message.setSubject("Update Alert");
			
			message.setText("Hello," +
					"\n\n This is a friendly reminder that your password has been updated!" +
					"\n \n Thank you, FastTax");
		
			Transport.send(message);

			System.out.println("Done");
			UA_status.setText("Password reset");
			UA_rpassword.setText(null);
			UA_password.setText(null);
		
	}catch (Exception e){
		UA_status.setTextFill(Color.RED);
		UA_status.setText(e.getMessage());
		System.out.println(mem_email);
	}
	
}
public void ClickUADA (ActionEvent event) throws Exception{
	stage = (Stage)((Button) event.getSource()).getScene().getWindow();
	root = FXMLLoader.load(getClass().getResource("/view/Delete.fxml"));
	scene = new Scene(root);
	stage.setScene(scene);
}
public void ClickDB (ActionEvent event) throws Exception{
	String query = "DELETE FROM `login` WHERE password = aes_encrypt(?,?) and loginid = ?  ";
	try (Connection conn = DbConfig.getConnection();
			PreparedStatement insertprofile = conn.prepareStatement(query);){
		String password;int loginid;
		 loginid = mem_logid;
		password =D_password.getText();
		
		project change = new project();
		change.setUserId(loginid);
		change.setPassword(password);
		insertprofile.setString(1, change.getPassword());
		insertprofile.setString(2, change.getPassword());
		insertprofile.setInt(3, change.getUserId());
		if (password == null || password == "" || password.trim().isEmpty()) {
			throw new Exception("Need Password");
		}
		int result = insertprofile.executeUpdate();
		
		
		
		if (result ==1){
			
			
			D_status.setTextFill(Color.GREEN);}else{
			throw new Exception("Password unknown");}
		stage = (Stage)((Button) event.getSource()).getScene().getWindow();
		stage.close();
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
				
					return new PasswordAuthentication("fasttax3300@gmail.com","u'EE7K.:)2e6");
				}
			});
			Message message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress("fasttax3300@gmail.com"));
		
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mem_email));
			message.setSubject("Update Alert");
			
			message.setText("Hello," +
					"\n\n We notice that you deleted your account and we want to let you know it was done Successfully!" +
					"\n \n Thank you for using FastTax");
		
			Transport.send(message);

			System.out.println("Done");
		
	
		
	}catch (Exception e){
		D_status.setTextFill(Color.RED);
		D_status.setText(e.getMessage());
		
	}
	
}


private void reset() {
	String query = "update login set password= aes_encrypt(?,?), acode = NULL where acode= ? and email= ?";
	try (Connection conn = DbConfig.getConnection();
			PreparedStatement updatereset = conn.prepareStatement(query);){
		String password,email, rpassword;
		String code;
		code= fn_code.getText();
		email = fn_email.getText();
		password = fn_password.getText();
		rpassword = fn_rpassword.getText();
		project reset= new project();
		Boolean emailResult = email.matches(emailregex);
		if (email == null || email == "" || emailResult == false) {
			throw new Exception("Invalid Email.");
		
		}
		if (code == null || code == "" || code == "0"){
			throw new Exception("Invalid Code");
		}
		if (password.equals (rpassword)){
			System.out.println("good password");
		}else{
			throw new Exception("Passwords do not match");
		}
		
		// need password security validator
		//
		//
		//
		reset.setAcode(code);
		reset.setEmail(email);
		reset.setPassword(rpassword);
		updatereset.setString(1, reset.getPassword());
		updatereset.setString(2, reset.getPassword());
		updatereset.setString(3, reset.getAcode());
		updatereset.setString(4, reset.getEmail());
		int result = updatereset.executeUpdate();
		
if (result ==1){
			
			fn_status.setTextFill(Color.GREEN);
			fn_email.setText(null);
			fn_password.setText(null);
			fn_rpassword.setText(null);
			fn_code.setText(null);
			fn_status.setText("Thank you, Your password is reset!");
			
			
		}else{
			throw new Exception("Invalid Email and Code");
			
		}


		
	}catch(Exception e){
		fn_status.setText(e.getMessage());
		fn_status.setTextFill(Color.RED);
	}
	
	
}
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
			
				return new PasswordAuthentication("fasttax3300@gmail.com","u'EE7K.:)2e6");
			}
		});

	

		Message message = new MimeMessage(session);
		
		message.setFrom(new InternetAddress("fasttax3300@gmail.com"));
	
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(forgot_email.getText()));
		message.setSubject("Password Reset");
		
		message.setText("Hello," +
				"\n\n Your password reset code is:  "+ code_mem + " \n Thank you!" );
	
		Transport.send(message);

		System.out.println("Done");
		code_mem= 0;
		
		forgot_status.setText("Send! Please check your email");
		forgot_email.setText(null);

	} catch (Exception e) {
		forgot_status.setText(e.getMessage());
	
		System.out.println(e);
	}
	
}


private void welcome() throws Exception{


	String query = "Select firstname, lastname, email from login where loginid = ?";
	try(Connection conn= DbConfig.getConnection()){
		PreparedStatement welcome = conn.prepareStatement(query); 
		welcome.setInt(1, mem_logid);
	ResultSet result1 = welcome.executeQuery();
		if (result1.next()){
			
			String firstname = result1.getString("firstname");
			String lastname = result1.getString("lastname");
			mem_email = result1.getString("email");
			 
			
			stage.setTitle(" Welcome " + firstname + " " + lastname+ "! ");
			//m_welcome.setText("System Error: missing logid ");
			
			
		}else{
			m_welcome.setText("System Error: missing logid ");
		}
		//m_welcome.setText("System Error: missing logid ");

	}catch(Exception e){
		System.out.println(" " +e.getMessage());
		
	}
	
}
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
public void CASubmit() throws Exception {
	String query = "insert into Login " + "(LoginId, email, Username, password, firstname, lastname, acode)"
			+ "values(?,?,?,aes_encrypt(?,?),?,?, NULL)";
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
		if (password.length() < 8){

			throw new Exception ("Password is to short! ");
		}
		if (rpassword == null || rpassword == "" || rpassword.trim().isEmpty()) {
			throw new Exception("Need Password.");
		}
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
Properties props = new Properties();
			
			

		

			CA_FirstName.setText(null);
			CA_LastName.setText(null);
			CA_email.setText(null);
			CA_UserName.setText(null);
			CA_Password.setText(null);
			CA_Rpassword.setText(null);
			CA_status.setText("Your Submittion has been completed check your email!");
		
			//email sent
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			
			props.put("mail.smtp.port", "465");

			Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
					
						return new PasswordAuthentication("fasttax3300@gmail.com","u'EE7K.:)2e6");
					}
				});

			

				Message message = new MimeMessage(session);
				
				message.setFrom(new InternetAddress("fasttax3300@gmail.com"));
			
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(login.getEmail()));
				message.setSubject("Account Created");
				
				message.setText("Hello " + login.getFirstName() + " " + login.getLastName() +" Username: (" + login.getUsername() +") "+
						"\n\n We just like to inform you that your account has been successfully created."+
						"\n We just want to let you know that our email system well inform you of any  password reset, deletion or changes to your account information. "+
						 " \n We well not send any personal information such as any personal tax data that you have input in your system." +
						"\n Thank you for choosing FastTax and we hope you get a big refund this tax season" +
						 "\n Sincerely,"+ 
						" \n\n The FastTax Team");
			
				Transport.send(message);

				System.out.println("Done");
			
	
			
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
