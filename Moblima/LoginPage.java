package Moblima;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPage implements ActionListener{
    JFrame frame = new JFrame();
    JButton loginButton = new JButton("Login");
    JButton resetButton = new JButton("Reset");
    JTextField userIDField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();
    JLabel userIDLabel = new JLabel("userID: ");
    JLabel userPasswordLabel = new JLabel("password: ");
    JLabel messageLabel = new JLabel("Admin Login");

    LoginObserver loginObservers[] = new LoginObserver[10];
    HashMap<String,String> logininfo = new HashMap<String,String>();
    BookMyShow bookMyShow = new BookMyShow();

    LoginPage(HashMap<String,String> loginInfoOriginal){

        logininfo = loginInfoOriginal;

        userIDLabel.setBounds(50,100,75,25);
        userPasswordLabel.setBounds(50,150,75,25);

        messageLabel.setBounds(125,50,250,35);
        messageLabel.setFont(new Font(null,Font.ITALIC,25));

        userIDField.setBounds(125,100,200,25);
        userPasswordField.setBounds(125,150,200,25);

        loginButton.setBounds(125,200,100,25);
        loginButton.addActionListener(this);

        resetButton.setBounds(225,200,100,25);
        resetButton.addActionListener(this);

        frame.add(userIDLabel);
        frame.add(userPasswordLabel);
        frame.add(messageLabel);
        frame.add(userIDField);
        frame.add(userPasswordField);
        frame.add(loginButton);
        frame.add(resetButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420,420);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==resetButton){
            userIDField.setText("");
            userPasswordField.setText("");
        }
        
        if(e.getSource()==loginButton){
            String userID = userIDField.getText();
            String password = String.valueOf(userPasswordField.getPassword());

            if(logininfo.containsKey(userID)){
                // Login success
                if(logininfo.get(userID).equals(password)){

                    JFrame popUpFrame = new JFrame();
                    JOptionPane.showMessageDialog(popUpFrame, "Login Successful!");
                    frame.dispose();


                    notifyLoginObservers();
                }
                else{
                    messageLabel.setForeground(Color.red);
                    messageLabel.setText("Wrong password");
                }
            }
            else{
                messageLabel.setForeground(Color.red);
                messageLabel.setText("Username not found");
            }
        }
    }

    public void addLoginObserver(LoginObserver loginObserver){
        for(int i=0;i<loginObservers.length;i++){
            if(loginObservers[i]==null){
                loginObservers[i] = loginObserver;
                break;
            }
        }
    }

    public void removeLoginObserver(LoginObserver loginObserver){
        for(int i=0;i<loginObservers.length;i++){
            if(loginObservers[i]==loginObserver){
                loginObservers[i] = null;
                break;
            }
        }
    }

    public void notifyLoginObservers(){
        for(int i=0;i<loginObservers.length;i++){
            if(loginObservers[i]!=null){
                loginObservers[i].loginSuccess();
            }
        }
    }
}
