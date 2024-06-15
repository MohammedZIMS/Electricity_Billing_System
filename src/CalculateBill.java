import java.awt.*;
import javax.swing.*;

class CalculateBill extends JFrame 
{
    JLabel meterNoLabel, nameLabel, addressLabel, unitConsumedLabel, monthLabel;
    JTextField unitConsumeTextField, nameTextField, addressTextField;
    Choice meterNoChoice, monthChoice;
    JButton submitButton, cancelButton;

    CalculateBill() 
    {
        setTitle("Pariseba");
        setSize(700, 500);
        setLocation(600, 200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel calculateBillPanel = new JPanel();
        calculateBillPanel.setLayout(null);
        calculateBillPanel.setBackground(new Color(252, 186, 3));
        add(calculateBillPanel);

        JLabel heading = new JLabel("Calculate Electricity Bill");
        heading.setFont(new Font("Times New Roman", Font.BOLD, 24));
        heading.setBounds(70, 10, 300, 30);
        calculateBillPanel.add(heading);

        meterNoLabel = new JLabel("Meter No:");
        meterNoLabel.setBounds(50, 60, 100, 20);
        calculateBillPanel.add(meterNoLabel);

        meterNoChoice = new Choice();
        meterNoChoice.setBounds(180, 60, 150, 20);
        meterNoChoice.add("1001"); 
        meterNoChoice.add("1002");
        calculateBillPanel.add(meterNoChoice);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 100, 100, 20);
        calculateBillPanel.add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setBounds(180, 100, 150, 20);
        calculateBillPanel.add(nameTextField);

        addressLabel = new JLabel("Address:");
        addressLabel.setBounds(50, 140, 100, 20);
        calculateBillPanel.add(addressLabel);

        addressTextField = new JTextField();
        addressTextField.setBounds(180, 140, 150, 20);
        calculateBillPanel.add(addressTextField);

        unitConsumedLabel = new JLabel("Unit Consumed:");
        unitConsumedLabel.setBounds(50, 180, 100, 20);
        calculateBillPanel.add(unitConsumedLabel);

        unitConsumeTextField = new JTextField();
        unitConsumeTextField.setBounds(180, 180, 150, 20);
        calculateBillPanel.add(unitConsumeTextField);

        monthLabel = new JLabel("Month:");
        monthLabel.setBounds(50, 220, 100, 20);
        calculateBillPanel.add(monthLabel);

        monthChoice = new Choice();
        monthChoice.setBounds(180, 220, 150, 20);
        monthChoice.add("January");
        monthChoice.add("February");
        monthChoice.add("March");
        monthChoice.add("April");
        monthChoice.add("May");
        monthChoice.add("June");
        monthChoice.add("July");
        monthChoice.add("August");
        monthChoice.add("September");
        monthChoice.add("October");
        monthChoice.add("November");
        monthChoice.add("December");
        calculateBillPanel.add(monthChoice);

        submitButton = new JButton("Submit");
        submitButton.setBounds(100, 280, 100, 30);
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(e -> handleSubmit());
        calculateBillPanel.add(submitButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(220, 280, 100, 30);
        cancelButton.setBackground(Color.BLACK);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(e -> handleCancel());
        calculateBillPanel.add(cancelButton);

        add(calculateBillPanel, BorderLayout.CENTER);

        JLabel calculateBillImageLabel = new JLabel(createImageIcon("ImagePariseba/bill3.jpg", 230, 200));
        calculateBillImageLabel.setBounds(400, 80, 230, 200);
        calculateBillImageLabel.setOpaque(true);
        calculateBillImageLabel.setBackground(Color.WHITE);
        add(calculateBillImageLabel, BorderLayout.EAST);

        setVisible(true);
    }

    private ImageIcon createImageIcon(String path, int width, int height) 
    {
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(path));
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return new ImageIcon(image);
    }

    private void handleSubmit() 
    {
        String meterNo = meterNoChoice.getSelectedItem();
        String name = nameTextField.getText();
        String address = addressTextField.getText();
        String unitConsumed = unitConsumeTextField.getText();
        String month = monthChoice.getSelectedItem();

        String query = String.format("INSERT INTO bill (meter_no, name, address, unit_consumed, month) VALUES ('%s', '%s', '%s', '%s', '%s')",
                meterNo, name, address, unitConsumed, month);

        try 
        {
            DataBases db = new DataBases();
            db.statement.executeUpdate(query);

            JOptionPane.showMessageDialog(this, "Bill calculated and recorded successfully!");
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    private void handleCancel() 
    {
        dispose();
    }

    public static void main(String[] args) 
    {
        new CalculateBill();
    }
}
