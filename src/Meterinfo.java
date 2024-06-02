import java.awt.*;
import javax.swing.*;

class Meterinfo extends JFrame {
    JLabel MeterNo, meterInfoLabel, meterLocationLabel, meterTypeLabel, installationDateLabel, meterReadingLabel;
    JTextField installationDateTextField;
    Choice meterTypeChoice, billTypeChoice, meterLocationChoice;
    JButton backButton, submitButton;

    Meterinfo(String meterNo) {
        JPanel meterInfoPanel = new JPanel();
        meterInfoPanel.setBackground(new Color(252, 186, 3));
        meterInfoPanel.setLayout(null);

        JLabel heading1 = new JLabel("Meter Information");
        heading1.setBounds(180, 10, 250, 20);
        heading1.setFont(new Font("Times New Roman", Font.BOLD, 24));
        meterInfoPanel.add(heading1);

        meterInfoLabel = new JLabel("Meter No: ");
        meterInfoLabel.setBounds(50, 80, 200, 20);
        meterInfoPanel.add(meterInfoLabel);

        MeterNo = new JLabel(meterNo);
        MeterNo.setBounds(180, 80, 200, 20);
        meterInfoPanel.add(MeterNo);

        meterLocationLabel = new JLabel("Meter Location: ");
        meterLocationLabel.setBounds(50, 110, 120, 20);
        meterInfoPanel.add(meterLocationLabel);

        meterLocationChoice = new Choice();
        meterLocationChoice.add("Outside");
        meterLocationChoice.add("Inside");
        meterLocationChoice.setBounds(180, 110, 150, 20);
        meterInfoPanel.add(meterLocationChoice);

        meterTypeLabel = new JLabel("Meter Type: ");
        meterTypeLabel.setBounds(50, 140, 100, 20);
        meterInfoPanel.add(meterTypeLabel);

        meterTypeChoice = new Choice();
        meterTypeChoice.add("Electric Meter");
        meterTypeChoice.add("Solar Meter");
        meterTypeChoice.add("Smart Meter");
        meterTypeChoice.setBounds(180, 140, 150, 20);
        meterInfoPanel.add(meterTypeChoice);

        installationDateLabel = new JLabel("Installation Date: ");
        installationDateLabel.setBounds(50, 180, 120, 20);
        meterInfoPanel.add(installationDateLabel);

        installationDateTextField = new JTextField();
        installationDateTextField.setBounds(180, 180, 150, 20);
        meterInfoPanel.add(installationDateTextField);

        JLabel billtyp = new JLabel("Bill Type: ");
        billtyp.setBounds(50, 220, 100, 20);
        meterInfoPanel.add(billtyp);

        billTypeChoice = new Choice();
        billTypeChoice.add("Normal");
        billTypeChoice.add("Industrial");
        billTypeChoice.setBounds(180, 220, 150, 20);
        meterInfoPanel.add(billTypeChoice);

        submitButton = new JButton("Submit");
        submitButton.setBounds(120, 280, 100, 30);
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Meter information submitted");
        });
        meterInfoPanel.add(submitButton);

        backButton = new JButton("Back");
        backButton.setBounds(240, 280, 100, 30);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            new NewCustomer();
            dispose();
        });
        meterInfoPanel.add(backButton);

        setSize(700, 500);
        setLocation(600, 200);
        setResizable(false);

        add(meterInfoPanel, BorderLayout.CENTER);

        ImageIcon meterInfoIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/meterInfo.png"));
        Image meterInfoImg = meterInfoIcon.getImage().getScaledInstance(230, 200, Image.SCALE_DEFAULT);
        ImageIcon meterInfoIcon1 = new ImageIcon(meterInfoImg);
        JLabel meterInfoImageLabel = new JLabel(meterInfoIcon1);
        meterInfoImageLabel.setBackground(Color.WHITE);
        add(meterInfoImageLabel, BorderLayout.EAST);

        setTitle("Pariseba");
        setVisible(true);
    }

    public static void main(String[] args) {
        new Meterinfo("123456");
    }
}
