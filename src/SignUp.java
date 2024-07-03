import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

class SignUp extends JFrame implements ActionListener {
    JLabel CreateAs, MeterNumber, EmployerId, UserName, Name, Password, confirmPasswordJLabel;
    Choice loginAsChoice;
    JTextField MeterTextField, EmployerIdTextField, UserNameTextField, NameTextField;
    JPasswordField PasswordTextField, confirmPasswordJTField;
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

        EmployerId = new JLabel("Employer Id: ");
        EmployerId.setBounds(30, 200, 125, 20);
        add(EmployerId);

        EmployerIdTextField = new JTextField();
        EmployerIdTextField.setBounds(170, 200, 150, 20);
        add(EmployerIdTextField);

        MeterNumber = new JLabel("Meter Number: ");
        MeterNumber.setBounds(30, 200, 125, 20);
        add(MeterNumber);

        MeterTextField = new JTextField();
        MeterTextField.setBounds(170, 200, 150, 20);
        add(MeterTextField);

        Name = new JLabel("Name: ");
        Name.setBounds(30, 240, 125, 20);
        add(Name);

        NameTextField = new JTextField();
        NameTextField.setBounds(170, 240, 150, 20);
        add(NameTextField);

        MeterTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    DataBases c = new DataBases();
                    String user = loginAsChoice.getSelectedItem();
                    ResultSet resultSet;
                    if (user.equals("Customer")) {
                        resultSet = c.statement.executeQuery("SELECT name FROM new_Customer WHERE meterNo = '" + MeterTextField.getText() + "'");
                        if (resultSet.next()) {
                            NameTextField.setText(resultSet.getString("name"));
                        } else {
                            JOptionPane.showMessageDialog(null, "Meter number not found.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        EmployerIdTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    DataBases c = new DataBases();
                    ResultSet resultSet = c.statement.executeQuery("SELECT name, Username FROM newEmployer WHERE EmployerId = '" + EmployerIdTextField.getText() + "'");
                    if (resultSet.next()) {
                        NameTextField.setText(resultSet.getString("name"));
                        UserNameTextField.setText(resultSet.getString("Username"));
                    } else {
                        JOptionPane.showMessageDialog(null, "Employer Id not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        UserName = new JLabel("Username: ");
        UserName.setBounds(30, 280, 125, 20);
        add(UserName);

        UserNameTextField = new JTextField();
        UserNameTextField.setBounds(170, 280, 150, 20);
        add(UserNameTextField);

        Password = new JLabel("Password: ");
        Password.setBounds(30, 320, 125, 20);
        add(Password);

        PasswordTextField = new JPasswordField();
        PasswordTextField.setBounds(170, 320, 150, 20);
        add(PasswordTextField);

        confirmPasswordJLabel = new JLabel("Confirm Password: ");
        confirmPasswordJLabel.setBounds(30, 360, 125, 20);
        add(confirmPasswordJLabel);

        confirmPasswordJTField = new JPasswordField();
        confirmPasswordJTField.setBounds(170, 360, 150, 20);
        add(confirmPasswordJTField);

        EmployerId.setVisible(false);
        EmployerIdTextField.setVisible(false);

        loginAsChoice.addItemListener(e -> {
            String user = loginAsChoice.getSelectedItem();
            if (user.equals("Admin")) {
                MeterNumber.setVisible(false);
                MeterTextField.setVisible(false);
                EmployerId.setVisible(true);
                EmployerIdTextField.setVisible(true);
                NameTextField.setText("");
                UserNameTextField.setText("");
            } else {
                MeterNumber.setVisible(true);
                MeterTextField.setVisible(true);
                EmployerId.setVisible(false);
                EmployerIdTextField.setVisible(false);
                NameTextField.setText("");
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
        setTitle("Signup");
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
            String sConfirmPassword = new String(confirmPasswordJTField.getPassword());
            String sMeterNumber = MeterTextField.getText();
            String sEmployerId = EmployerIdTextField.getText();

            if (sPassword.length() < 6) {
                JOptionPane.showMessageDialog(null, "Password must be at least 6 characters long", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!sPassword.equals(sConfirmPassword)) {
                JOptionPane.showMessageDialog(null, "Password and Confirm Password do not match", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                DataBases c = new DataBases();
                String query;
                 
                String checkUsernameQuery = "SELECT COUNT(*) FROM signup WHERE Username = '" + sUserName + "'";
                ResultSet rs = c.statement.executeQuery(checkUsernameQuery);
                if (rs.next() && rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(this, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                

                if (sLoginAs.equals("Customer")) {
                    query = "INSERT INTO Signup (meterNumber, userName, Password, Name, userType) VALUES ('" + sMeterNumber + "', '" + sUserName + "', '" + sPassword + "', '" + sName + "', '" + sLoginAs + "')";
                }
                else if (sLoginAs.equals("Admin")) 
                {
                    query = "INSERT INTO Signup (meterNumber, userName, Password, Name, userType, EmployerId) VALUES ('"+sEmployerId+"','" + sUserName + "', '" + sPassword + "', '" + sName + "', '" + sLoginAs + "', '" + sEmployerId +"')";
                }
                else
                {
                    query = "update Signup set username = '"+sUserName+"', password = '"+sPassword+"', usertype = '"+sLoginAs+"' where meter_no = '"+sMeterNumber+"'";
                }
                c.statement.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Account Created");
                setVisible(false);
                new Login();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "SQL Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else if (e.getSource() == back) {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        new SignUp();
    }
}
