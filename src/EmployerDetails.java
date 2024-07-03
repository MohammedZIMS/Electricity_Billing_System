import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class EmployerDetails extends JFrame {

    private Choice searchEmployerIDChoice, searchNameChoice;
    private JLabel searchEmployerIDLabel, searchNameLabel;
    private JButton printButton, searchButton, shCleanButton, closeButton;
    private JTable employerTable;
    private DefaultTableModel tableModel;

    EmployerDetails() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Pariseba");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(144, 238, 144));
        searchPanel.setLayout(new GridLayout(1, 5, 10, 10));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(searchPanel, BorderLayout.NORTH);

        searchEmployerIDLabel = new JLabel("Search by Employer ID:");
        searchPanel.add(searchEmployerIDLabel);

        searchEmployerIDChoice = new Choice();
        populateEmployerIDChoice();
        searchPanel.add(searchEmployerIDChoice);

        searchNameLabel = new JLabel("Search by Name:");
        searchPanel.add(searchNameLabel);

        searchNameChoice = new Choice();
        populateNameChoice();
        searchPanel.add(searchNameChoice);

        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchEmployer());
        searchPanel.add(searchButton);

        shCleanButton = new JButton("Clear Search History");
        shCleanButton.addActionListener(e -> populateAllEmployers());
        searchPanel.add(shCleanButton);

        tableModel = new DefaultTableModel(new String[]{"Employer ID", "Name", "Username", "Designation", "Address", "City", "State", "Email", "Phone Number", "Delete"}, 0);
        employerTable = new JTable(tableModel);
        employerTable.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        employerTable.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox()));
        add(new JScrollPane(employerTable), BorderLayout.CENTER);

        JPanel printPanel = new JPanel();
        printButton = new JButton("Print");
        printButton.addActionListener(e -> {
            try {
                employerTable.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        printPanel.add(printButton);

        closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        printPanel.add(closeButton);

        add(printPanel, BorderLayout.SOUTH);

        populateAllEmployers();

        setVisible(true);
    }

    private void populateEmployerIDChoice() {
        try {
            DataBases c = new DataBases();
            ResultSet resultSet = c.statement.executeQuery("SELECT EmployerId FROM newEmployer");
            while (resultSet.next()) {
                searchEmployerIDChoice.add(resultSet.getString("EmployerId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateNameChoice() {
        try {
            DataBases c = new DataBases();
            ResultSet resultSet = c.statement.executeQuery("SELECT name FROM newEmployer");
            while (resultSet.next()) {
                searchNameChoice.add(resultSet.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateAllEmployers() {
        tableModel.setRowCount(0);
        try {
            DataBases c = new DataBases();
            String query = "SELECT * FROM newEmployer";
            ResultSet resultSet = c.statement.executeQuery(query);
            while (resultSet.next()) {
                String employerId = resultSet.getString("EmployerId");
                String name = resultSet.getString("name");
                String username = getUsernameFromSignup(employerId); // Assuming you want to get the username from another table
                String Designation = resultSet.getString("Designation");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phoneNumber");

                tableModel.addRow(new Object[]{employerId, name, username, Designation, address, city, state, email, phoneNumber, "Delete"});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getUsernameFromSignup(String employerId) {
        String username = "";
        try {
            DataBases c = new DataBases();
            String query = "SELECT userName FROM Signup WHERE employerId = '" + employerId + "'";
            ResultSet resultSet = c.statement.executeQuery(query);
            if (resultSet.next()) {
                username = resultSet.getString("userName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    private void searchEmployer() {
        String employerId = searchEmployerIDChoice.getSelectedItem();
        String name = searchNameChoice.getSelectedItem();

        try {
            DataBases c = new DataBases();
            String query = "SELECT * FROM newEmployer WHERE EmployerId = '" + employerId + "' OR name = '" + name + "'";
            ResultSet resultSet = c.statement.executeQuery(query);
            if (resultSet.next()) {
                displayEmployerDetails(resultSet);
            } else {
                JOptionPane.showMessageDialog(this, "No records found", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayEmployerDetails(ResultSet resultSet) {
        tableModel.setRowCount(0);
        try {
            do {
                String employerId = resultSet.getString("EmployerId");
                String name = resultSet.getString("name");
                String username = getUsernameFromSignup(employerId); // Assuming you want to get the username from another table
                String designation = resultSet.getString("Designation");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phoneNumber");

                tableModel.addRow(new Object[]{employerId, name, username, designation, address, city, state, email, phoneNumber, "Delete"});
            } while (resultSet.next());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployerDetails());
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

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
                int row = employerTable.getSelectedRow();
                String employerId = tableModel.getValueAt(row, 0).toString();
                deleteEmployer(employerId);
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

        private void deleteEmployer(String employerId) {
            try {
                DataBases c = new DataBases();
                String query = "DELETE FROM newEmployer WHERE EmployerId = '" + employerId + "'";
                c.statement.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Employer with ID: " + employerId + " deleted successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
