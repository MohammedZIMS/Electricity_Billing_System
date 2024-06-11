import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class SignUp extends JFrame implements ActionListener {
    JLabel CreateAs, MeterNumber, EmployerId, UserName, Name, Password;
    Choice loginAsChoice;
    JTextField MeterTextField, EmployerIdTextField, UserNameTextField, NameTextField;
    JPasswordField PasswordTextField;
    JButton create, back;

    SignUp() {
        ImageIcon CreateAsIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/sign-up.png"));
        Image CreateAsImg = CreateAsIcon.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
        ImageIcon CreateAsIcon1 = new ImageIcon(CreateAsImg);
        JLabel CreateAsLabel = new JLabel(CreateAsIcon1);
        CreateAsLabel.setBounds(120, 10, 150, 150);
        add(CreateAsLabel);

        CreateAs = new JLabel("Create As: ");
        CreateAs.setBounds(30, 160, 125, 20);
        add(CreateAs);

        loginAsChoice = new Choice();
        loginAsChoice.add("Customer");
        loginAsChoice.add("Admin");
        loginAsChoice.setBounds(170, 160, 150, 20);
        add(loginAsChoice);

        Name = new JLabel("Name: ");
        Name.setBounds(30, 200, 125, 20);
        add(Name);

        NameTextField = new JTextField();
        NameTextField.setBounds(170, 200, 150, 20);
        add(NameTextField);

        UserName = new JLabel("Username: ");
        UserName.setBounds(30, 240, 125, 20);
        add(UserName);

        UserNameTextField = new JTextField();
        UserNameTextField.setBounds(170, 240, 150, 20);
        add(UserNameTextField);

        MeterNumber = new JLabel("Meter Number: ");
        MeterNumber.setBounds(30, 280, 125, 20);
        add(MeterNumber);

        MeterTextField = new JTextField();
        MeterTextField.setBounds(170, 280, 150, 20);
        add(MeterTextField);

        EmployerId = new JLabel("Employer Id: ");
        EmployerId.setBounds(30, 320, 125, 20);
        add(EmployerId);

        EmployerIdTextField = new JTextField();
        EmployerIdTextField.setBounds(170, 320, 150, 20);
        add(EmployerIdTextField);

        Password = new JLabel("Password: ");
        Password.setBounds(30, 360, 125, 20);
        add(Password);

        PasswordTextField = new JPasswordField();
        PasswordTextField.setBounds(170, 360, 150, 20);
        add(PasswordTextField);

        EmployerId.setVisible(false);
        EmployerIdTextField.setVisible(false);

        loginAsChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String user = loginAsChoice.getSelectedItem();
                if (user.equals("Admin")) {
                    MeterNumber.setVisible(false);
                    MeterTextField.setVisible(false);
                    EmployerId.setVisible(true);
                    EmployerIdTextField.setVisible(true);
                } else {
                    MeterNumber.setVisible(true);
                    MeterTextField.setVisible(true);
                    EmployerId.setVisible(false);
                    EmployerIdTextField.setVisible(false);
                }
            }
        });

        create = new JButton("Create");
        create.setBounds(30, 400, 100, 30);
        create.setBackground(Color.YELLOW);
        create.addActionListener(this);
        add(create);

        back = new JButton("Back");
        back.setBounds(170, 400, 100, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        setSize(400, 500);
        setResizable(false);
        setLocation(500, 180);
        setTitle("Sign Up");
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == create) {
            String sLoginAs = loginAsChoice.getSelectedItem();
            String sName = NameTextField.getText();
            String sUserName = UserNameTextField.getText();
            String sPassword = new String(PasswordTextField.getPassword());
            String sMeterNumber = MeterTextField.getText();
            String sEmployerId = EmployerIdTextField.getText();

            try {
                DataBases c = new DataBases();
                String query = null;
                query = "INSERT INTO Signup VALUES ('" + sMeterNumber + "', '" + sUserName + "', '" + sPassword + "', '" + sName + "', '" + sLoginAs + "')";

                c.statement.executeUpdate(query);

                JOptionPane.showMessageDialog(null, "Account Created");
                setVisible(false);
                new Login();

            } 
            catch (Exception ex) 
            {
                ex.printStackTrace();
            }
        } 
        else if (e.getSource() == back) 
        {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args)
    {
        new SignUp();
    }
}
