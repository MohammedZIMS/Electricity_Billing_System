import javax.swing.*;
import java.awt.*;

public class UpdataProfile extends JFrame {
    JLabel nameLabel, meterNoLabel, meterNoTextLabel, emailLabel, phoneNumberLabel, addressLabel;
    JTextField nameTextField, emailTextField, phoneNumberTextField, addressTextField;
    JButton backButton, submitProfileButton;

    UpdataProfile() {
        setTitle("Profile");
        setLayout(new BorderLayout());

        JPanel profilePanel = new JPanel(new BorderLayout());

        JLabel profileHeading = new JLabel("Profile", JLabel.CENTER);
        profileHeading.setFont(new Font("Times New Roman", Font.BOLD, 24));
        profilePanel.add(profileHeading, BorderLayout.NORTH);

        JPanel profileImagePanel = new JPanel();
        // profileImagePanel.setBackground(Color.WHITE);
        ImageIcon profileIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/Profail.png"));
        Image profileImage = profileIcon.getImage().getScaledInstance(350, 400, Image.SCALE_DEFAULT);
        JLabel profileLabel = new JLabel(new ImageIcon(profileImage));
        profileLabel.setBounds(10, 200, 400, 450);
        profileImagePanel.add(profileLabel);
        profilePanel.add(profileImagePanel, BorderLayout.WEST);

        JPanel profileBodyPanel = new JPanel();
        profileBodyPanel.setLayout(null);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(40, 100, 100, 20);
        profileBodyPanel.add(nameLabel);
        
        nameTextField = new JTextField("User Name");
        nameTextField.setBounds(40, 120, 150, 20);
        profileBodyPanel.add(nameTextField);

        meterNoLabel = new JLabel("ID No:");
        meterNoLabel.setBounds(40, 160, 100, 20);
        profileBodyPanel.add(meterNoLabel);
        
        meterNoTextLabel = new JLabel("");
        meterNoTextLabel.setBounds(140, 160, 150, 20);
        profileBodyPanel.add(meterNoTextLabel);

        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(40, 200, 100, 20);
        profileBodyPanel.add(emailLabel);
        
        emailTextField = new JTextField("email@example.com");
        emailTextField.setBounds(40, 220, 150, 20);
        profileBodyPanel.add(emailTextField);

        phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberLabel.setBounds(40, 250, 100, 20);
        profileBodyPanel.add(phoneNumberLabel);
        
        phoneNumberTextField = new JTextField("01223-456-7890");
        phoneNumberTextField.setBounds(40, 270, 150, 20);
        profileBodyPanel.add(phoneNumberTextField);

        addressLabel = new JLabel("Address:");
        addressLabel.setBounds(40, 300, 100, 20);
        profileBodyPanel.add(addressLabel);
        
        addressTextField = new JTextField("123 Main St");
        addressTextField.setBounds(40, 320, 150, 20);
        profileBodyPanel.add(addressTextField);

        profilePanel.add(profileBodyPanel, BorderLayout.CENTER);

        JPanel profileButtonPanel = new JPanel();
        backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose());
        profileButtonPanel.add(backButton);

        submitProfileButton = new JButton("Submite");
        profileButtonPanel.add(submitProfileButton);

        profilePanel.add(profileButtonPanel, BorderLayout.SOUTH);

        add(profilePanel);

        setSize(700, 500);
        setLocation(600, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new UpdataProfile();
    }
}
