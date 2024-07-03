import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class BillDetails extends JFrame {
    JTable billTable;
    JScrollPane scrollPane;
    String currentUserMeterNo; // To store the current user's meter number

    BillDetails(String meterNo) {
        this.currentUserMeterNo = meterNo;

        setTitle("Bill Details");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        // Table initialization
        billTable = new JTable();
        scrollPane = new JScrollPane(billTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Fetch and display bill details for current user's meter number
        fetchBillDetails();

        add(panel);
        setVisible(true);
    }

    private void fetchBillDetails() {
        // Define table columns
        String[] columns = {"Meter Number", "Name", "Address", "Total Units Consumed", "Month", "Total Bill", "Status"};

        // Create a DefaultTableModel
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);

        try {
            DataBases c = new DataBases();
            String query = String.format("SELECT * FROM bill WHERE meter_no = '%s'", currentUserMeterNo);
            ResultSet resultSet = c.statement.executeQuery(query);

            while (resultSet.next()) {
                String meterNo = resultSet.getString("meter_no");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                int unitsConsumed = resultSet.getInt("unit_consumed");
                String month = resultSet.getString("month");
                int totalBill = resultSet.getInt("total_bill");
                String status = resultSet.getString("status");

                // Calculate Total Units Consumed (unitsConsumed - unitsConsumedLastMonth)
                int unitsConsumedLastMonth = getUnitsConsumedLastMonth(meterNo, month);
                int totalUnitsConsumed = unitsConsumed - unitsConsumedLastMonth;

                // Add data to the table model
                model.addRow(new Object[]{meterNo, name, address, totalUnitsConsumed, month, totalBill, status});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set the model to the JTable
        billTable.setModel(model);
    }

    private int getUnitsConsumedLastMonth(String meterNo, String currentMonth) {
        int unitsConsumedLastMonth = 0;
        try {
            DataBases c = new DataBases();
            String previousMonth = getPreviousMonth(currentMonth); // Get the previous month
            String query = String.format("SELECT unit_consumed FROM bill WHERE meter_no = '%s' AND month = '%s'", meterNo, previousMonth);
            ResultSet resultSet = c.statement.executeQuery(query);
            if (resultSet.next()) {
                unitsConsumedLastMonth = resultSet.getInt("unit_consumed");
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
        // Example usage:
        // Replace "currentUserMeterNo" with the actual current user's meter number
        new BillDetails("241916");
    }
}
