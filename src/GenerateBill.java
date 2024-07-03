import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GenerateBill extends JFrame implements Printable {
    JLabel nameLabel, monthLabel, unitLabel, totalLabel, statusLabel, electricityChargeLabel, demandChargeLabel, meterCostLabel, formerCostLabel, ppcChargeLabel;
    JTextField nameTextField, unitTextField, totalTextField, statusTextField, electricityChargeField, demandChargeField, meterCostField, formerCostField, ppcChargeField;
    JButton generatePDFButton;
    Choice searchMonthChoice;

    String meterNum;

    public GenerateBill(String meterNum) {
        this.meterNum = meterNum;
        // Set up the JFrame
        setTitle("Generate Bill");
        setSize(800, 600);
        setLocation(400, 100);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Create Labels and TextFields for bill details
        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 80, 200, 20);
        add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setBounds(300, 80, 200, 20);
        nameTextField.setEditable(false);
        add(nameTextField);

        monthLabel = new JLabel("Month:");
        monthLabel.setBounds(50, 120, 200, 20);
        add(monthLabel);

        searchMonthChoice = new Choice();
        searchMonthChoice.setBounds(300, 120, 200, 20);
        addMonthsToChoice();
        add(searchMonthChoice);

        unitLabel = new JLabel("Units Consumed:");
        unitLabel.setBounds(50, 160, 200, 20);
        add(unitLabel);

        unitTextField = new JTextField();
        unitTextField.setBounds(300, 160, 200, 20);
        unitTextField.setEditable(false);
        add(unitTextField);

        totalLabel = new JLabel("Total Bill:");
        totalLabel.setBounds(50, 200, 200, 20);
        add(totalLabel);

        totalTextField = new JTextField();
        totalTextField.setBounds(300, 200, 200, 20);
        totalTextField.setEditable(false);
        add(totalTextField);

        statusLabel = new JLabel("Status:");
        statusLabel.setBounds(50, 240, 200, 20);
        add(statusLabel);

        statusTextField = new JTextField();
        statusTextField.setBounds(300, 240, 200, 20);
        statusTextField.setEditable(false);
        add(statusTextField);


        electricityChargeLabel = new JLabel("Electricity Charge:");
        electricityChargeLabel.setBounds(50, 280, 200, 20);
        add(electricityChargeLabel);

        electricityChargeField = new JTextField("0.00");
        electricityChargeField.setBounds(300, 280, 200, 20);
        electricityChargeField.setEditable(false);
        add(electricityChargeField);

        demandChargeLabel = new JLabel("Demand Charge:");
        demandChargeLabel.setBounds(50, 320, 200, 20);
        add(demandChargeLabel);

        demandChargeField = new JTextField("0.00");
        demandChargeField.setBounds(300, 320, 200, 20);
        demandChargeField.setEditable(false);
        add(demandChargeField);

        meterCostLabel = new JLabel("Meter Cost:");
        meterCostLabel.setBounds(50, 360, 200, 20);
        add(meterCostLabel);

        meterCostField = new JTextField("45");
        meterCostField.setBounds(300, 360, 200, 20);
        meterCostField.setEditable(false);
        add(meterCostField);

        formerCostLabel = new JLabel("Former Cost:");
        formerCostLabel.setBounds(50, 400, 200, 30);
        add(formerCostLabel);

        formerCostField = new JTextField("0.00");
        formerCostField.setBounds(300, 400, 200, 30);
        formerCostField.setEditable(false);
        add(formerCostField);

        ppcChargeLabel = new JLabel("PPC Charge:");
        ppcChargeLabel.setBounds(50, 440, 200, 30);
        add(ppcChargeLabel);

        ppcChargeField = new JTextField("0.00");
        ppcChargeField.setBounds(300,440, 200, 30);
        ppcChargeField.setEditable(false);
        add(ppcChargeField);

        generatePDFButton = new JButton("Generate PDF");
        generatePDFButton.setBounds(300, 500, 150, 30);
        generatePDFButton.addActionListener(e -> generatePDF());
        add(generatePDFButton);

        setVisible(true);

        loadCustomerDetails();

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

    private void addMonthsToChoice() {
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        for (String month : months) {
            searchMonthChoice.add(month);
        }
    }


    // Method to generate PDF
    private void generatePDF() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Generate Bill");
        job.setPrintable(this);

        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
            } catch (PrinterException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error creating PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        g2d.setFont(new Font("Times New Roman", Font.BOLD, 18));
        g2d.drawString("Electricity Bill", 100, 50);
        g2d.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        g2d.drawString("Name: " + nameTextField.getText(), 100, 100);
        g2d.drawString("Month: " + searchMonthChoice.getItem(pageIndex), 100, 120);
        g2d.drawString("Unit Comsum: " + unitTextField.getText(), 100, 140);
        g2d.drawString("Total Bill: " +  totalTextField.getText(), 100, 160);
        g2d.drawString("Electricity Charge: " + electricityChargeField.getText(), 100, 180);
        g2d.drawString("Demand Charge: " + demandChargeField.getText(), 100, 200);
        g2d.drawString("Meter Cost: " + meterCostField.getText(), 100, 220);
        g2d.drawString("Former Cost: " + formerCostField.getText(), 100, 240);
        g2d.drawString("PPC Charge: " + ppcChargeField.getText(), 100, 260);

        g2d.setFont(new Font("Times New Roman", Font.PLAIN, 10));
        g2d.drawString("Generated on: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), 100, 300);

        return PAGE_EXISTS;
    }

    public static void main(String[] args) {
        new GenerateBill("241916");
    }
}
