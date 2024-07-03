import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class UpdateProfile extends JFrame {
    String meterNum;
    String Accounttype; 
    JLabel nameLabel, meterNoLabel, meterNoTextLabel, designationLabel, emailLabel, phoneNumberLabel, addressLabel;
    JTextField nameTextField, designationTextField, emailTextField, phoneNumberTextField, addressTextField;
    JButton backButton, submitProfileButton;

    UpdateProfile(String Accounttype, String meterNum) { 
        this.meterNum = meterNum;
        this.Accounttype = Accounttype; 

        setTitle("Update Profile");
        setLayout(new BorderLayout());

        JPanel profilePanel = new JPanel(new BorderLayout());

        JLabel profileHeading = new JLabel("Update Profile", JLabel.CENTER);
        profileHeading.setFont(new Font("Times New Roman", Font.BOLD, 24));
        profilePanel.add(profileHeading, BorderLayout.NORTH);

        JPanel profileImagePanel = new JPanel();
        ImageIcon profileIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/Profail.png"));
        Image profileImage = profileIcon.getImage().getScaledInstance(350, 400, Image.SCALE_DEFAULT);
        JLabel profileLabel = new JLabel(new ImageIcon(profileImage));
        profileImagePanel.add(profileLabel);
        profilePanel.add(profileImagePanel, BorderLayout.WEST);

        JPanel profileBodyPanel = new JPanel();
        profileBodyPanel.setLayout(null);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(40, 100, 100, 20);
        profileBodyPanel.add(nameLabel);

        nameTextField = new JTextField("");
        nameTextField.setBounds(40, 120, 150, 20);
        profileBodyPanel.add(nameTextField);

        meterNoLabel = new JLabel("Meter No:");
        meterNoLabel.setBounds(40, 160, 100, 20);
        profileBodyPanel.add(meterNoLabel);

        meterNoTextLabel = new JLabel(meterNum);  
        meterNoTextLabel.setBounds(140, 160, 150, 20);
        profileBodyPanel.add(meterNoTextLabel);

        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(40, 200, 100, 20);
        profileBodyPanel.add(emailLabel);
        
        emailTextField = new JTextField("");
        emailTextField.setBounds(40, 220, 150, 20);
        profileBodyPanel.add(emailTextField);
        
        phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberLabel.setBounds(40, 250, 100, 20);
        profileBodyPanel.add(phoneNumberLabel);
        
        phoneNumberTextField = new JTextField("");
        phoneNumberTextField.setBounds(40, 270, 150, 20);
        profileBodyPanel.add(phoneNumberTextField);
        
        addressLabel = new JLabel("Address:");
        addressLabel.setBounds(40, 300, 100, 20);
        profileBodyPanel.add(addressLabel);
        
        addressTextField = new JTextField("");
        addressTextField.setBounds(40, 320, 150, 20);
        profileBodyPanel.add(addressTextField);
        
        designationLabel = new JLabel("Designation:");
        designationLabel.setBounds(40, 350, 100, 20);
        profileBodyPanel.add(designationLabel);

        designationTextField = new JTextField("");
        designationTextField.setBounds(40, 370, 150, 20);
        profileBodyPanel.add(designationTextField);

        profilePanel.add(profileBodyPanel, BorderLayout.CENTER);

        try {
            DataBases c = new DataBases();
            if (Accounttype.equals("Customer")) {
                ResultSet resultSet = c.statement.executeQuery("SELECT * FROM new_Customer WHERE meterNo = '" + meterNum + "'");
                if (resultSet.next()) {
                    nameTextField.setText(resultSet.getString("name"));
                    emailTextField.setText(resultSet.getString("email"));
                    phoneNumberTextField.setText(resultSet.getString("phoneNumber"));
                    addressTextField.setText(resultSet.getString("address"));
                    designationLabel.setVisible(false); 
                    designationTextField.setVisible(false); 
                }
            } else if (Accounttype.equals("Admin")) {
                ResultSet resultSet = c.statement.executeQuery("SELECT * FROM newEmployer WHERE EmployerId = '" + meterNum + "'");
                if (resultSet.next()) {
                    nameTextField.setText(resultSet.getString("name"));
                    emailTextField.setText(resultSet.getString("email"));
                    phoneNumberTextField.setText(resultSet.getString("phoneNumber"));
                    addressTextField.setText(resultSet.getString("address"));
                    designationTextField.setText(resultSet.getString("designation")); 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel profileButtonPanel = new JPanel();
        backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose());
        profileButtonPanel.add(backButton);

        submitProfileButton = new JButton("Submit");
        submitProfileButton.addActionListener(e -> {
            String sname = nameTextField.getText();
            String semail = emailTextField.getText();
            String sphone = phoneNumberTextField.getText();
            String saddress = addressTextField.getText();
            String sdesignation = designationTextField.getText(); 

            try {
                DataBases c = new DataBases();
                if (Accounttype.equals("Customer")) {
                    c.statement.executeUpdate("UPDATE new_Customer SET name = '" + sname + "', email = '" + semail + "', phoneNumber = '" + sphone + "', address = '" + saddress + "' WHERE meterNo = '" + meterNum + "'");
                } else if (Accounttype.equals("Admin")) {
                    String EmployerId = meterNoTextLabel.getText();
                    c.statement.executeUpdate("UPDATE newEmployer SET name = '" + sname + "', email = '" + semail + "', phoneNumber = '" + sphone + "', address = '" + saddress + "', designation = '" + sdesignation + "' WHERE EmployerId = '" + EmployerId + "'");
                }
                JOptionPane.showMessageDialog(null, "User Information Updated Successfully");
                setVisible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        profileButtonPanel.add(submitProfileButton);

        profilePanel.add(profileButtonPanel, BorderLayout.SOUTH);

        add(profilePanel);

        setSize(700, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UpdateProfile("Admin", "372899")); 
    }
}
