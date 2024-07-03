import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.*;

public class CustomerDetails extends JFrame implements ActionListener {
    Choice searchMeterNoChoice, searchNameChoice;
    JLabel searchMeterNoLabel, searchNameLabel;
    JButton printButton, searchButton, closeButton, shCleanButton;
    JTable customerTable;
    DefaultTableModel tableModel;

    CustomerDetails() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Pariseba");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(144, 238, 144));
        searchPanel.setLayout(new GridLayout(1, 5, 10, 10));
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

        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        searchPanel.add(searchButton);

        shCleanButton = new JButton("Clear Search History");
        shCleanButton.addActionListener(e -> {
            tableModel.setRowCount(0);
            populateAllCustomers();
        });
        searchPanel.add(shCleanButton);

        tableModel = new DefaultTableModel(new String[]{"Meter No", "Name", "Address", "City", "State", "Email", "Phone Number", "Delete"}, 0);
        customerTable = new JTable(tableModel);
        customerTable.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        customerTable.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox()));
        add(new JScrollPane(customerTable), BorderLayout.CENTER);

        JPanel printPanel = new JPanel();
        printButton = new JButton("Print");
        printButton.addActionListener(this);
        printPanel.add(printButton);

        closeButton = new JButton("Close");
        closeButton.addActionListener(this);
        printPanel.add(closeButton);

        add(printPanel, BorderLayout.SOUTH);

        populateAllCustomers();

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

    private void populateAllCustomers() {
        tableModel.setRowCount(0);
        try {
            DataBases c = new DataBases();
            String query = "SELECT * FROM new_Customer";
            ResultSet resultSet = c.statement.executeQuery(query);
            while (resultSet.next()) {
                String meterNo = resultSet.getString("meterNo");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phoneNumber");

                tableModel.addRow(new Object[]{meterNo, name, address, city, state, email, phoneNumber, "Delete"});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getUsernameFromSignup(String meterNo) {
        String username = "";
        try {
            DataBases c = new DataBases();
            String query = "SELECT userName FROM Signup WHERE meterNo = '" + meterNo + "'";
            ResultSet resultSet = c.statement.executeQuery(query);
            if (resultSet.next()) {
                username = resultSet.getString("userName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            checkMeterNoAndName();
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

    private void checkMeterNoAndName() {
        String meterNo = searchMeterNoChoice.getSelectedItem();
        String name = searchNameChoice.getSelectedItem();

        try {
            DataBases c = new DataBases();
            String query = "SELECT * FROM new_Customer WHERE meterNo = '" + meterNo + "' AND name = '" + name + "'";
            ResultSet resultSet = c.statement.executeQuery(query);
            if (resultSet.next()) {
                searchCustomerDetails(meterNo, name);
            } else {
                JOptionPane.showMessageDialog(this, "Please check Meter No and Name", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchCustomerDetails(String meterNo, String name) {
        tableModel.setRowCount(0);

        try {
            DataBases c = new DataBases();
            String query = "SELECT * FROM new_Customer WHERE meterNo = '" + meterNo + "' AND name = '" + name + "'";
            ResultSet resultSet = c.statement.executeQuery(query);
            while (resultSet.next()) {
                String meterNoVal = resultSet.getString("meterNo");
                String nameVal = resultSet.getString("name");
                String userVal = getUsernameFromSignup(meterNoVal); // Get the username from the Signup table
                String addressVal = resultSet.getString("address");
                String cityVal = resultSet.getString("city");
                String stateVal = resultSet.getString("state");
                String emailVal = resultSet.getString("email");
                String phoneNumberVal = resultSet.getString("phoneNumber");

                tableModel.addRow(new Object[]{meterNoVal, nameVal, addressVal, cityVal, stateVal, emailVal, phoneNumberVal, "Delete"});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CustomerDetails();
    }

    // Custom renderer for the delete button
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Custom editor for the delete button
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                int row = customerTable.getSelectedRow();
                String meterNo = tableModel.getValueAt(row, 0).toString();
                deleteCustomer(meterNo);
                tableModel.removeRow(row);
            }
            isPushed = false;
            return new String(label);
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }

        private void deleteCustomer(String meterNo) {
            try {
                DataBases c = new DataBases();
                String query = "DELETE FROM new_Customer WHERE meterNo = '" + meterNo + "'";
                c.statement.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Customer with Meter No: " + meterNo + " deleted successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
