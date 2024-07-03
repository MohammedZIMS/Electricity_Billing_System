import java.awt.*;
import java.sql.*;
import java.util.Random;
import javax.swing.*;

public class NewEmployer extends JFrame {

    private JLabel EmployerLabel, EmployerIdLabel, EmployerUsernameLabel, DesignationLabel, AddressLabel, CityLabel, StateLabel, EmailLabel, PhoneNumberLabel;
    private JTextField EmployerTextField, EmployerIdTextField, EmployerUsernameTextField, AddressTextField, CityTextField, StateTextField, EmailTextField, PhoneNumberTextField;
    private JButton CancelButton, NextButton;
    private Choice DesignationChoice;

    public NewEmployer() {
        JPanel newEmployerPanel = new JPanel();
        newEmployerPanel.setBackground(new Color(252, 186, 3));
        newEmployerPanel.setLayout(null);

        JLabel heading1 = new JLabel("New Employer");
        heading1.setBounds(180, 10, 200, 20);
        heading1.setFont(new Font("Times New Roman", Font.BOLD, 24));
        newEmployerPanel.add(heading1);

        EmployerLabel = new JLabel("Employer Name: ");
        EmployerLabel.setBounds(50, 80, 120, 20);
        newEmployerPanel.add(EmployerLabel);

        EmployerTextField = new JTextField();
        EmployerTextField.setBounds(180, 80, 150, 20);
        newEmployerPanel.add(EmployerTextField);

        EmployerIdLabel = new JLabel("Employer ID: ");
        EmployerIdLabel.setBounds(50, 110, 120, 20);
        newEmployerPanel.add(EmployerIdLabel);

        EmployerIdTextField = new JTextField();
        EmployerIdTextField.setBounds(180, 110, 150, 20);
        EmployerIdTextField.setEditable(false); // Employer ID should not be editable
        newEmployerPanel.add(EmployerIdTextField);

        generateEmployerId(); // Automatically generate Employer ID

        EmployerUsernameLabel = new JLabel("Username: ");
        EmployerUsernameLabel.setBounds(50, 140, 120, 20);
        newEmployerPanel.add(EmployerUsernameLabel);

        EmployerUsernameTextField = new JTextField();
        EmployerUsernameTextField.setBounds(180, 140, 150, 20);
        newEmployerPanel.add(EmployerUsernameTextField);

        DesignationLabel = new JLabel("Designation: ");
        DesignationLabel.setBounds(50, 180, 120, 20);
        newEmployerPanel.add(DesignationLabel);

        DesignationChoice = new Choice();
        DesignationChoice.add("Senior Secretary");
        DesignationChoice.add("Joint Secretary");
        DesignationChoice.add("Deputy Secretary");
        DesignationChoice.add("System Analyst");
        DesignationChoice.add("Protocol Officer (attached to BPDB)");
        DesignationChoice.add("Manager");
        DesignationChoice.add("General Manager");
        DesignationChoice.add("Employee");
        DesignationChoice.setBounds(180, 180, 150, 20);
        newEmployerPanel.add(DesignationChoice);

        AddressLabel = new JLabel("Address: ");
        AddressLabel.setBounds(50, 220, 120, 20);
        newEmployerPanel.add(AddressLabel);

        AddressTextField = new JTextField();
        AddressTextField.setBounds(180, 220, 150, 20);
        newEmployerPanel.add(AddressTextField);

        CityLabel = new JLabel("City: ");
        CityLabel.setBounds(50, 260, 120, 20);
        newEmployerPanel.add(CityLabel);

        CityTextField = new JTextField();
        CityTextField.setBounds(180, 260, 150, 20);
        newEmployerPanel.add(CityTextField);

        StateLabel = new JLabel("State: ");
        StateLabel.setBounds(50, 300, 120, 20);
        newEmployerPanel.add(StateLabel);

        StateTextField = new JTextField();
        StateTextField.setBounds(180, 300, 150, 20);
        newEmployerPanel.add(StateTextField);

        EmailLabel = new JLabel("Email: ");
        EmailLabel.setBounds(50, 340, 120, 20);
        newEmployerPanel.add(EmailLabel);

        EmailTextField = new JTextField();
        EmailTextField.setBounds(180, 340, 150, 20);
        newEmployerPanel.add(EmailTextField);

        PhoneNumberLabel = new JLabel("Phone Number: ");
        PhoneNumberLabel.setBounds(50, 380, 120, 20);
        newEmployerPanel.add(PhoneNumberLabel);

        PhoneNumberTextField = new JTextField();
        PhoneNumberTextField.setBounds(180, 380, 150, 20);
        newEmployerPanel.add(PhoneNumberTextField);

        NextButton = new JButton("Next");
        NextButton.setBounds(100, 420, 100, 30);
        NextButton.setBackground(Color.BLACK);
        NextButton.setForeground(Color.WHITE);
        NextButton.addActionListener(e -> {
            saveEmployerDetails();
        });
        newEmployerPanel.add(NextButton);

        CancelButton = new JButton("Cancel");
        CancelButton.setBounds(240, 420, 100, 30);
        CancelButton.setBackground(Color.BLACK);
        CancelButton.setForeground(Color.WHITE);
        CancelButton.addActionListener(e -> 
        {
            dispose();
            new EmployerDetails();
        });
        newEmployerPanel.add(CancelButton);

        setSize(700, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        add(newEmployerPanel, BorderLayout.CENTER);

        ImageIcon employerIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/customer4-removebg-preview.png"));
        Image employerImg = employerIcon.getImage().getScaledInstance(230, 200, Image.SCALE_DEFAULT);
        ImageIcon employerIconResized = new ImageIcon(employerImg);
        JLabel employerImageLabel = new JLabel(employerIconResized);
        employerImageLabel.setBackground(Color.WHITE);
        add(employerImageLabel, BorderLayout.WEST);

        setTitle("Pariseba");
        setVisible(true);
    }

    private void generateEmployerId() {
        long number = Math.abs(new Random().nextLong() % 1000000);
        EmployerIdTextField.setText(String.valueOf(number));
    }

    private void saveEmployerDetails() {
        String name = EmployerTextField.getText();
        String employerId = EmployerIdTextField.getText();
        String username = EmployerUsernameTextField.getText();
        String address = AddressTextField.getText();
        String city = CityTextField.getText();
        String state = StateTextField.getText();
        String email = EmailTextField.getText();
        String phoneNumber = PhoneNumberTextField.getText();

        // Validate email
        if (!email.contains("@") || !email.endsWith(".com")) {
            JOptionPane.showMessageDialog(this, "Invalid email format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate phone number
        if (phoneNumber.length() != 11 || !phoneNumber.startsWith("01") || "356789".indexOf(phoneNumber.charAt(2)) == -1) {
            JOptionPane.showMessageDialog(this, "Invalid phone number format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            DataBases c = new DataBases();
            
            // Check for unique username
            String checkUsernameQuery = "SELECT COUNT(*) FROM newEmployer WHERE Username = '" + username + "'";
            ResultSet rs = c.statement.executeQuery(checkUsernameQuery);
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String queryEmployer = "INSERT INTO newEmployer (name, EmployerId, Username, address, city, state, email, phoneNumber) VALUES('" 
                                   + name + "','" + employerId + "','" + username + "','" + address + "','" + city + "','" 
                                   + state + "','" + email + "','" + phoneNumber + "')";
            String querySignup = "INSERT INTO signup (meterNumber, userName, password, name, userType) VALUES('" 
                                 + employerId + "','','','" + name + "','')";

            c.statement.executeUpdate(queryEmployer);
            c.statement.executeUpdate(querySignup);

            JOptionPane.showMessageDialog(this, "Employer details added successfully");
            setVisible(false);
            new EmployerDetails(); 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NewEmployer::new);
    }
}
