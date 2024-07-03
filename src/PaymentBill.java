import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class PaymentBill extends JFrame {
    String meterNum;
    JButton backButton, payButton;
    Choice searchMonthChoice;

    PaymentBill(String meterNum) {
        this.meterNum = meterNum;

        setTitle("Payment Gateway");
        setSize(400, 600);
        setLocationRelativeTo(null);

        JEditorPane j = new JEditorPane();
        j.setEditable(false);

        try {
            // Set the bKash pay bill URL
            String bKashPayBillURL = "https://www.bkash.com/en/products-services/pay-bill";
            
            // Open the URL in the default browser
            j.setPage("https://www.bkash.com/en/products-services/pay-bill");
            j.setBounds(400,150,800,600);
        } catch (Exception e) {
            e.printStackTrace();
            j.setContentType("text/html");
            j.setText("<html>Error! Unable to load payment gateway.</html>");
        }

        JScrollPane scrollPane = new JScrollPane(j);
        add(scrollPane);

        payButton = new JButton("Pay");
        payButton.setBackground(Color.BLACK);
        payButton.setForeground(Color.WHITE);
        payButton.setBounds(100, 20, 800, 30);
        payButton.addActionListener(e -> payBill());
        add(payButton);

        backButton = new JButton("Back");
        backButton.setBounds(100, 40, 80, 30);
        backButton.addActionListener(e -> {
            dispose();
            new PayBill(meterNum);
        });
        add(backButton);

        setVisible(true);
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

    public static void main(String[] args) {
        // Example usage:
        // Replace "241916" with the actual customer's meter number
        new PaymentBill("241916");
    }
}
