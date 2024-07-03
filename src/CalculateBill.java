import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;

public class CalculateBill extends JFrame implements ActionListener {
    JLabel meterNoLabel, nameLabel, nameText, addressLabel, addressText, unitConsumedLabel, monthLabel;
    JTextField unitConsumeTextField;
    Choice meterNoChoice, monthChoice;
    JButton submitButton, cancelButton;

    CalculateBill() {
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
        populateMeterNoChoice();
        meterNoChoice.setBounds(180, 60, 150, 20);
        calculateBillPanel.add(meterNoChoice);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 100, 100, 20);
        calculateBillPanel.add(nameLabel);

        nameText = new JLabel("");
        nameText.setBounds(180, 100, 150, 20);
        calculateBillPanel.add(nameText);

        addressLabel = new JLabel("Address:");
        addressLabel.setBounds(50, 140, 100, 20);
        calculateBillPanel.add(addressLabel);

        addressText = new JLabel("");
        addressText.setBounds(180, 140, 150, 20);
        calculateBillPanel.add(addressText);

        fetchCustomerDetails(meterNoChoice.getSelectedItem()); // Initial population of details

        meterNoChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    fetchCustomerDetails(meterNoChoice.getSelectedItem());
                }
            }
        });

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
        addMonthsToChoice();
        calculateBillPanel.add(monthChoice);

        submitButton = new JButton("Submit");
        submitButton.setBounds(100, 280, 100, 30);
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(this);
        calculateBillPanel.add(submitButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(220, 280, 100, 30);
        cancelButton.setBackground(Color.BLACK);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(this);
        calculateBillPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(calculateBillPanel, BorderLayout.CENTER);

        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setLayout(new BorderLayout());
        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/bill3.jpg"));
        Image image = imageIcon.getImage().getScaledInstance(250, 300, Image.SCALE_DEFAULT);
        ImageIcon scaledImageIcon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(scaledImageIcon);
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.setPreferredSize(new Dimension(250, 500));
        add(imagePanel, BorderLayout.EAST);

        setVisible(true);
    }

    private void populateMeterNoChoice() {
        try {
            DataBases c = new DataBases();
            ResultSet resultSet = c.statement.executeQuery("SELECT * FROM new_Customer");
            while (resultSet.next()) {
                meterNoChoice.add(resultSet.getString("meterNo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchCustomerDetails(String meterNo) {
        try {
            DataBases c = new DataBases();
            ResultSet resultSet = c.statement.executeQuery("SELECT * FROM new_Customer WHERE meterNo = '" + meterNo + "'");
            if (resultSet.next()) {
                nameText.setText(resultSet.getString("name"));
                addressText.setText(resultSet.getString("address"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMonthsToChoice() {
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        for (String month : months) {
            monthChoice.add(month);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String meterNo = meterNoChoice.getSelectedItem();
            String name = nameText.getText();
            String address = addressText.getText();
            String unitConsumed = unitConsumeTextField.getText();
            String month = monthChoice.getSelectedItem();

            int totalBill = calculateTotalBill(Integer.parseInt(unitConsumed));

            String query = String.format("INSERT INTO bill (meter_no, name, address, unit_consumed, month, total_bill, status) VALUES ('%s', '%s', '%s', '%s', '%s', '%d', 'Unpaid')", meterNo, name, address, unitConsumed, month, totalBill);

            try {
                DataBases db = new DataBases();
                db.statement.executeUpdate(query);

                JOptionPane.showMessageDialog(this, "Bill calculated and recorded successfully!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }

    private int calculateTotalBill(int unitsConsumed) {
        int totalBill = 0;
        String industrialMeterType = "Industrial"; // Define the industrial meter type
    
        try {
            DataBases c = new DataBases();
            ResultSet resultSet = c.statement.executeQuery("SELECT * FROM rates");
            if (resultSet.next()) {
                int costPerUnit = Integer.parseInt(resultSet.getString("cost_per_unit"));
                int meterRent = Integer.parseInt(resultSet.getString("meter_rent"));
                int serviceCharge = Integer.parseInt(resultSet.getString("service_charge"));
                int fixedTax = Integer.parseInt(resultSet.getString("fixed_tax"));
    
                // Get units consumed last month
                int unitsConsumedLastMonth = getUnitsConsumedLastMonth(meterNoChoice.getSelectedItem(), monthChoice.getSelectedItem());
    
                // Calculate total units consumed this month
                int totalUnitsConsumed = unitsConsumed - unitsConsumedLastMonth;
    
                // Calculate total bill based on total units consumed
                totalBill = totalUnitsConsumed * costPerUnit + meterRent + serviceCharge + fixedTax;
    
                // Check if it's an Industrial meter to apply additional tax
                ResultSet meterResultSet = c.statement.executeQuery("SELECT * FROM meter_info WHERE meter_no = '" + meterNoChoice.getSelectedItem() + "'");
                if (meterResultSet.next()) {
                    String meterType = meterResultSet.getString("meter_type");
                    if (meterType.equals(industrialMeterType)) {
                        // Apply 15% tax for Industrial meters
                        double additionalTax = totalBill * 0.15;
                        totalBill += additionalTax;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalBill;
    }
    
    private int getUnitsConsumedLastMonth(String meterNo, String currentMonth) {
        int unitsConsumedLastMonth = 0;
        try {
            DataBases c = new DataBases();
            String previousMonth = getPreviousMonth(currentMonth); // Get the previous month
            String query = String.format("SELECT unit_consumed FROM bill WHERE meter_no = '%s' AND month = '%s'", meterNo, previousMonth);
            ResultSet resultSet = c.statement.executeQuery(query);
            if (resultSet.next()) {
                unitsConsumedLastMonth = Integer.parseInt(resultSet.getString("unit_consumed"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unitsConsumedLastMonth;
    }
    
    private String getPreviousMonth(String currentMonth) {
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
    
        int index = -1;
        for (int i = 0; i < months.length; i++) {
            if (months[i].equalsIgnoreCase(currentMonth)) {
                index = i;
                break;
            }
        }
    
        if (index == 0) {
            return months[11]; // December is previous month of January
        } else {
            return months[index - 1];
        }
    }
    
    public static void main(String[] args) {
        new CalculateBill();
    }
}
