import java.awt.*;
import java.util.Random;
import javax.swing.*;

class NewCustomer extends JFrame 
{
    JLabel newCustomerLabel, MeterNoLabel, AddressLabel, CityLabel, StateLabel, EmailLabel, PhonNumberLabel;
    JTextField newCustomerTextField, MeterNoTextField, AddressTextField, CityTextField, StateTextField, EmailTextField, PhonNumberTextField;
    JButton CancelButton, NextButton;

    NewCustomer() 
    {
        JPanel newCustomerPanel = new JPanel();
        newCustomerPanel.setBackground(new Color(252, 186, 3));
        newCustomerPanel.setLayout(null);

        JLabel heading1 = new JLabel("New Customer");
        heading1.setBounds(180, 10, 200, 20);
        heading1.setFont(new Font("Times New Roman", Font.BOLD, 24));
        newCustomerPanel.add(heading1);

        newCustomerLabel = new JLabel("Customer Name: ");
        newCustomerLabel.setBounds(50, 80, 120, 20);
        newCustomerPanel.add(newCustomerLabel);

        newCustomerTextField = new JTextField();
        newCustomerTextField.setBounds(180, 80, 150, 20);
        newCustomerPanel.add(newCustomerTextField);

        MeterNoLabel = new JLabel("Meter No: ");
        MeterNoLabel.setBounds(50, 110, 120, 20);
        newCustomerPanel.add(MeterNoLabel);

        MeterNoTextField = new JTextField();
        MeterNoTextField.setBounds(180, 110, 150, 20);
        newCustomerPanel.add(MeterNoTextField);

        Random ran = new Random();
        long number = ran.nextLong() % 1000000;
        MeterNoTextField.setText("" + Math.abs(number));
        MeterNoTextField.setEditable(false);

        AddressLabel = new JLabel("Address: ");
        AddressLabel.setBounds(50, 140, 120, 20);
        newCustomerPanel.add(AddressLabel);

        AddressTextField = new JTextField();
        AddressTextField.setBounds(180, 140, 150, 20);
        newCustomerPanel.add(AddressTextField);

        CityLabel = new JLabel("City: ");
        CityLabel.setBounds(50, 180, 120, 20);
        newCustomerPanel.add(CityLabel);

        CityTextField = new JTextField();
        CityTextField.setBounds(180, 180, 150, 20);
        newCustomerPanel.add(CityTextField);

        StateLabel = new JLabel("State: ");
        StateLabel.setBounds(50, 220, 120, 20);
        newCustomerPanel.add(StateLabel);

        StateTextField = new JTextField();
        StateTextField.setBounds(180, 220, 150, 20);
        newCustomerPanel.add(StateTextField);

        EmailLabel = new JLabel("Email: ");
        EmailLabel.setBounds(50, 260, 120, 20);
        newCustomerPanel.add(EmailLabel);

        EmailTextField = new JTextField();
        EmailTextField.setBounds(180, 260, 150, 20);
        newCustomerPanel.add(EmailTextField);

        PhonNumberLabel = new JLabel("Phone Number: ");
        PhonNumberLabel.setBounds(50, 300, 120, 20);
        newCustomerPanel.add(PhonNumberLabel);

        PhonNumberTextField = new JTextField();
        PhonNumberTextField.setBounds(180, 300, 150, 20);
        newCustomerPanel.add(PhonNumberTextField);

        NextButton = new JButton("Next");
        NextButton.setBounds(100, 400, 100, 30);
        NextButton.setBackground(Color.BLACK);
        NextButton.setForeground(Color.WHITE);
        NextButton.addActionListener(e -> 
        {
            String name = newCustomerTextField.getText();
            String meterNo = MeterNoTextField.getText();
            String address = AddressTextField.getText();
            String city = CityTextField.getText();
            String state = StateTextField.getText();
            String email = EmailTextField.getText();
            String phoneNumber = PhonNumberTextField.getText();

            String queryCustomer = "insert into new_Customer values('"+name+"','"+meterNo+"','"+address+"','"+city+"','"+state+"','"+email+"','"+phoneNumber+"')";
            String querySignup = "insert into signup values('"+meterNo+"','','','"+name+"','')";

            try{
                DataBases c = new DataBases();
                c.statement.executeUpdate(queryCustomer);
                c.statement.executeUpdate(querySignup);

                JOptionPane.showMessageDialog(this, "Customer details added successfully");
                JOptionPane.showMessageDialog(this, "Customer added successfully!");
                setVisible(false);
                new Meterinfo(meterNo);
            }catch (Exception E){
                E.printStackTrace();
            }
        });
        newCustomerPanel.add(NextButton);

        CancelButton = new JButton("Cancel");
        CancelButton.setBounds(240, 400, 100, 30);
        CancelButton.setBackground(Color.BLACK);
        CancelButton.setForeground(Color.WHITE);
        CancelButton.addActionListener(e -> 
        {
            this.dispose();
        });
        newCustomerPanel.add(CancelButton);

        setSize(700, 500);
        setLocation(600, 200);
        setResizable(false);

        add(newCustomerPanel, BorderLayout.CENTER);

        ImageIcon NewCustomerIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/customer4-removebg-preview.png"));
        Image NewCustomerImg = NewCustomerIcon.getImage().getScaledInstance(230, 200, Image.SCALE_DEFAULT);
        ImageIcon NewCustomerIcon1 = new ImageIcon(NewCustomerImg);
        JLabel newCustomerLabel = new JLabel(NewCustomerIcon1);
        newCustomerLabel.setBackground(Color.WHITE);
        add(newCustomerLabel, BorderLayout.WEST);

        setTitle("Pariseba");
        setVisible(true);
    }

    public static void main(String[] args) 
    {
        new NewCustomer();
    }
}
