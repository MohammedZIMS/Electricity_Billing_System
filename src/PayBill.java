import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PayBill extends JFrame {
    String meterNum;
    JLabel nameLabel, monthLabel, unitLabel, totalLabel, statusLabel, transcriptionIdLabel;
    JTextField nameTextField, unitTextField, totalTextField, statusTextField, transcriptionIdTextField;
    Choice searchMonthChoice;
    JButton closeButton, payButton;

    PayBill(String meterNum) {
        this.meterNum = meterNum;

        setTitle("Pay Bill");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel heading1 = new JLabel("Pay Bill");
        heading1.setBounds(180, 10, 200, 20);
        heading1.setFont(new Font("Times New Roman", Font.BOLD, 24));
        add(heading1);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(35, 80, 200, 20);
        add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setBounds(300, 80, 200, 20);
        nameTextField.setEditable(false);
        add(nameTextField);

        monthLabel = new JLabel("Month:");
        monthLabel.setBounds(35, 120, 200, 20);
        add(monthLabel);

        searchMonthChoice = new Choice();
        searchMonthChoice.setBounds(300, 120, 200, 20);
        addMonthsToChoice();
        add(searchMonthChoice);

        unitLabel = new JLabel("Units Consumed:");
        unitLabel.setBounds(35, 160, 200, 20);
        add(unitLabel);

        unitTextField = new JTextField();
        unitTextField.setBounds(300, 160, 200, 20);
        unitTextField.setEditable(false);
        add(unitTextField);

        totalLabel = new JLabel("Total Bill:");
        totalLabel.setBounds(35, 200, 200, 20);
        add(totalLabel);

        totalTextField = new JTextField();
        totalTextField.setBounds(300, 200, 200, 20);
        totalTextField.setEditable(false);
        add(totalTextField);

        statusLabel = new JLabel("Status:");
        statusLabel.setBounds(35, 240, 200, 20);
        add(statusLabel);

        statusTextField = new JTextField();
        statusTextField.setBounds(300, 240, 200, 20);
        statusTextField.setEditable(false);
        add(statusTextField);

        transcriptionIdLabel = new JLabel("Transcription:");
        transcriptionIdLabel.setBounds(35, 280, 200, 20);
        add(transcriptionIdLabel);

        transcriptionIdTextField = new JTextField("");
        transcriptionIdTextField.setBounds(300, 280, 200, 20);
        add(transcriptionIdTextField);

        payButton = new JButton("Pay");
        payButton.setBackground(Color.BLACK);
        payButton.setForeground(Color.WHITE);
        payButton.setBounds(100, 460, 100, 25);
        payButton.addActionListener(e -> payBill());
        add(payButton);

        closeButton = new JButton("Close");
        closeButton.setBackground(Color.BLACK);
        closeButton.setForeground(Color.WHITE);
        closeButton.setBounds(230, 460, 100, 25);
        closeButton.addActionListener(e -> dispose());
        add(closeButton);

        setVisible(true);

        loadCustomerDetails(); // Load customer details like name

        // Add listener for month selection changes
        searchMonthChoice.addItemListener(e -> {
            String selectedMonth = searchMonthChoice.getSelectedItem();
            loadBillDataForMonth(selectedMonth);
        });
    }

    private void loadCustomerDetails() {
        try {
            DataBases c = new DataBases();
            
            ResultSet resultSet = c.statement.executeQuery("SELECT * FROM new_customer WHERE meterNo = '" + meterNum + "'");
            if (resultSet.next()) {
                nameTextField.setText(resultSet.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBillDataForMonth(String month) {
        try {
            DataBases c = new DataBases();
            String query = "SELECT * FROM bill WHERE meter_no = ? AND month = ?";
            PreparedStatement pstmt = c.connection.prepareStatement(query);
            pstmt.setString(1, meterNum);
            pstmt.setString(2, month);
            ResultSet resultSet = pstmt.executeQuery();
            
            if (resultSet.next()) {
                int unitsConsumed = resultSet.getInt("unit_consumed");
                int totalBill = resultSet.getInt("total_bill");
                String status = resultSet.getString("status");

                unitTextField.setText(String.valueOf(unitsConsumed));
                totalTextField.setText(String.valueOf(totalBill));
                statusTextField.setText(status);
            } else {
                unitTextField.setText("");
                totalTextField.setText("");
                statusTextField.setText("");
            }
            
            pstmt.close();
            c.connection.close(); // Close the connection when done
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void payBill() {
        try {
            DataBases c = new DataBases();
            String month = searchMonthChoice.getSelectedItem();
            c.statement.executeUpdate("UPDATE bill SET status = 'Paid' WHERE meter_no = '" + meterNum + "' AND month = '" + month + "'");
            JOptionPane.showMessageDialog(null, "Bill Paid Successfully");
            new BillDetails(meterNum); // After payment, show updated details
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    private void addMonthsToChoice() {
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        for (String month : months) {
            searchMonthChoice.add(month);
        }
    }

    public static void main(String[] args) {
        // Example usage:
        // Replace "241916" with the actual customer's meter number
        new PayBill("241916");
    }
}
