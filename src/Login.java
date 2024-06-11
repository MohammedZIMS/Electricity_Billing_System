import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

class Login extends JFrame implements ActionListener {
    JTextField userText;
    JPasswordField passwordText;
    Choice loginChoice;

    JButton loginButton, closeButton, signupButton;

    Login() {
        super("Login");
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel userName = new JLabel("Username: ");
        userName.setBounds(300, 60, 100, 20);
        add(userName);

        userText = new JTextField();
        userText.setBounds(380, 60, 180, 20);
        add(userText);

        JLabel password = new JLabel("Password: ");
        password.setBounds(300, 100, 100, 20);
        add(password);

        passwordText = new JPasswordField();
        passwordText.setBounds(380, 100, 180, 20);
        add(passwordText);

        JLabel loginAs = new JLabel("Login As:");
        loginAs.setBounds(300, 140, 80, 20);
        add(loginAs);

        loginChoice = new Choice();
        loginChoice.add("Customer");
        loginChoice.add("Admin");
        loginChoice.setBounds(380, 140, 180, 18);
        add(loginChoice);

        loginButton = new JButton("Login");
        loginButton.setBounds(330, 180, 100, 20);
        loginButton.addActionListener(this);
        add(loginButton);

        closeButton = new JButton("Close");
        closeButton.setBounds(460, 180, 100, 20);
        closeButton.addActionListener(this);
        add(closeButton);

        signupButton = new JButton("Signup");
        signupButton.setBounds(400, 210, 100, 20);
        signupButton.addActionListener(this);
        add(signupButton);

        ImageIcon profile1 = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/profile0-r.png"));
        Image profile2 = profile1.getImage().getScaledInstance(400, 250, Image.SCALE_DEFAULT);
        ImageIcon fProfile = new ImageIcon(profile2);
        JLabel profileLabel = new JLabel(fProfile);
        profileLabel.setBounds(10, 10, 300, 250);
        add(profileLabel);

        setSize(640, 300);
        setLocation(400, 200);
        setTitle("Pariseba");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String usernameString = userText.getText();
            String passwordString = new String(passwordText.getPassword());
            String usertypeString = loginChoice.getSelectedItem();

            try {
                DataBases c = new DataBases();
                String query = "SELECT * FROM signup WHERE userName ='" + usernameString + "' AND password ='" + passwordString + "' AND userType ='" + usertypeString + "'";
                ResultSet resultSet = c.statement.executeQuery(query);

                if (resultSet.next()) {
                    // String meter = resultSet.getString("meter_no");
                    setVisible(false);
                    new Main();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Login");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == signupButton) {
            setVisible(false);
            new SignUp();
        } else if (e.getSource() == closeButton) {
            setVisible(false);
            System.exit(0);
        }
    }

    public static void main(String[] args) 
    {
        new Login();
    }
}
