import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CustomerDetails extends JFrame implements ActionListener {
    Choice searchMeterNoChoice, searchMonthChoice, searchNameChoice;
    JLabel searchMeterNoLabel, searchMonthLabel, searchNameLabel;
    JButton printButton, searchButton, closeButton, SHCleanButton;
    JTable customerTable;
    DefaultTableModel tableModel;

    CustomerDetails() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Pariseba");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(144,238,144));
        searchPanel.setLayout(new GridLayout(4, 2, 10, 10));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(searchPanel, BorderLayout.NORTH);

        searchMeterNoLabel = new JLabel("Search by Meter No:");
        searchPanel.add(searchMeterNoLabel);

        searchMeterNoChoice = new Choice();
        populateMeterNoChoice();
        searchPanel.add(searchMeterNoChoice);

        searchNameLabel = new JLabel("Search by Name:");
        searchPanel.add(searchNameLabel);

        searchNameChoice = new Choice();
        populateNameChoice();
        searchPanel.add(searchNameChoice);

        searchMonthLabel = new JLabel("Search by Month:");
        searchPanel.add(searchMonthLabel);

        searchMonthChoice = new Choice();
        addMonthsToChoice();
        searchPanel.add(searchMonthChoice);

        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        searchPanel.add(searchButton);

        SHCleanButton = new JButton("Search History Clean");
        SHCleanButton.addActionListener(e ->
        {
            tableModel.setRowCount(0);
        }
        );
        searchPanel.add(SHCleanButton);

        tableModel = new DefaultTableModel(new String[]{"Meter No", "Name", "Address", "Unit Consumed", "Month", "Total Bill", "Status"}, 0);
        customerTable = new JTable(tableModel);
        add(new JScrollPane(customerTable), BorderLayout.CENTER);

        JPanel printPanel = new JPanel();
        printButton = new JButton("Print");
        printButton.addActionListener(this);
        printPanel.add(printButton);
        
        closeButton = new JButton("Close");
        closeButton.addActionListener(this);
        printPanel.add(closeButton);

        add(printPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void populateMeterNoChoice() {
        try {
            DataBases c = new DataBases();
            ResultSet resultSet = c.statement.executeQuery("SELECT meterNo FROM new_Customer");
            while (resultSet.next()) {
                searchMeterNoChoice.add(resultSet.getString("meterNo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateNameChoice() {
        try {
            DataBases c = new DataBases();
            ResultSet resultSet = c.statement.executeQuery("SELECT name FROM new_Customer");
            while (resultSet.next()) {
                searchNameChoice.add(resultSet.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMonthsToChoice() {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        for (String month : months) {
            searchMonthChoice.add(month);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            searchCustomerDetails();
        } else if (e.getSource() == closeButton) {
            dispose();
        } else if (e.getSource() == printButton) {
            try {
                customerTable.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void searchCustomerDetails() {
        String meterNo = searchMeterNoChoice.getSelectedItem();
        String name = searchNameChoice.getSelectedItem();
        String month = searchMonthChoice.getSelectedItem();
        
        tableModel.setRowCount(0); // Clear the table before adding new data

        try {
            DataBases c = new DataBases();
            String query = "SELECT * FROM bill WHERE meter_no = '" + meterNo + "' OR name = '" + name + "' OR month = '" + month + "'";
            ResultSet resultSet = c.statement.executeQuery(query);
            while (resultSet.next()) {
                String meterNoVal = resultSet.getString("meter_no");
                String nameVal = resultSet.getString("name");
                String addressVal = resultSet.getString("address");
                String unitConsumedVal = resultSet.getString("unit_consumed");
                String monthVal = resultSet.getString("month");
                String totalBillVal = resultSet.getString("total_bill");
                String statusVal = resultSet.getString("status");
                tableModel.addRow(new Object[]{meterNoVal, nameVal, addressVal, unitConsumedVal, monthVal, totalBillVal, statusVal});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CustomerDetails();
    }
}
