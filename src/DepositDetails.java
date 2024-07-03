import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class DepositDetails extends JFrame implements ActionListener {
    JLabel meterNoSearchLabel, monthSearchLabel, statusSearchLabel;
    Choice meterNoSearchChoice, monthSearchChoice, statusSearchChoice;
    JButton meterNoSearchButton, monthSearchButton, statusSearchButton, shCleanButton, printButton, closeButton;
    JTable depositTable;
    DefaultTableModel tableModel;

    DepositDetails() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Deposit Details");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(144, 238, 144));
        searchPanel.setLayout(new GridLayout(1, 6, 10, 10));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(searchPanel, BorderLayout.NORTH);

        meterNoSearchLabel = new JLabel("Search by Meter No:");
        searchPanel.add(meterNoSearchLabel);

        meterNoSearchChoice = new Choice();
        populateMeterNoChoice();
        searchPanel.add(meterNoSearchChoice);

        meterNoSearchButton = new JButton("Search");
        meterNoSearchButton.addActionListener(this);
        searchPanel.add(meterNoSearchButton);

        monthSearchLabel = new JLabel("Search by Month:");
        searchPanel.add(monthSearchLabel);

        monthSearchChoice = new Choice();
        populateMonthChoice();
        searchPanel.add(monthSearchChoice);

        monthSearchButton = new JButton("Search");
        monthSearchButton.addActionListener(this);
        searchPanel.add(monthSearchButton);

        statusSearchLabel = new JLabel("Search:");
        searchPanel.add(statusSearchLabel);

        statusSearchChoice = new Choice();
        populateStatusChoice();
        searchPanel.add(statusSearchChoice);

        statusSearchButton = new JButton("Search");
        statusSearchButton.addActionListener(this);
        searchPanel.add(statusSearchButton);

        shCleanButton = new JButton("Clear Search");
        shCleanButton.addActionListener(e -> tableModel.setRowCount(0));
        searchPanel.add(shCleanButton);

        tableModel = new DefaultTableModel(new String[]{"Meter No", "Name", "Address", "Unit Consumed", "Month", "Total Bill", "Status"}, 0);
        depositTable = new JTable(tableModel);
        add(new JScrollPane(depositTable), BorderLayout.CENTER);

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
            ResultSet resultSet = c.statement.executeQuery("SELECT DISTINCT meter_no FROM bill");
            while (resultSet.next()) {
                meterNoSearchChoice.add(resultSet.getString("meter_no"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateMonthChoice() {
        monthSearchChoice.add("January");
        monthSearchChoice.add("February");
        monthSearchChoice.add("March");
        monthSearchChoice.add("April");
        monthSearchChoice.add("May");
        monthSearchChoice.add("June");
        monthSearchChoice.add("July");
        monthSearchChoice.add("August");
        monthSearchChoice.add("September");
        monthSearchChoice.add("October");
        monthSearchChoice.add("November");
        monthSearchChoice.add("December");
    }

    private void populateStatusChoice() {
        statusSearchChoice.add("Paid");
        statusSearchChoice.add("Unpaid");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == meterNoSearchButton) {
            searchDepositDetails("meter_no", meterNoSearchChoice.getSelectedItem());
        } else if (e.getSource() == monthSearchButton) {
            searchDepositDetails("month", monthSearchChoice.getSelectedItem());
        } else if (e.getSource() == statusSearchButton) {
            searchDepositDetails("status", statusSearchChoice.getSelectedItem());
        } else if (e.getSource() == printButton) {
            try {
                depositTable.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == closeButton) {
            dispose();
        }
    }

    private void searchDepositDetails(String column, String value) {
        tableModel.setRowCount(0);

        try {
            DataBases c = new DataBases();
            String query = "SELECT * FROM bill WHERE " + column + " = '" + value + "'";
            ResultSet resultSet = c.statement.executeQuery(query);
            while (resultSet.next()) {
                String meterNo = resultSet.getString("meter_no");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String unitConsumed = resultSet.getString("unit_consumed");
                String month = resultSet.getString("month");
                String totalBill = resultSet.getString("total_bill");
                String status = resultSet.getString("status");
                tableModel.addRow(new Object[]{meterNo, name, address, unitConsumed, month, totalBill, status});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new DepositDetails();
    }
}
