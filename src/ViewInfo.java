import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.ResultSet;

public class ViewInfo extends JFrame {
    JButton printButton, closeButton;
    JTable viewInfoTable;
    DefaultTableModel viewInfoTableModel;

    ViewInfo() {
        setTitle("Pariseba");
        setSize(700, 500);
        setLocation(600, 200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Heading Panel
        JPanel ViewInfoHeadingPanel = new JPanel();
        JLabel heading1 = new JLabel("View Information");
        heading1.setFont(new Font("Times New Roman", Font.BOLD, 24));
        ViewInfoHeadingPanel.add(heading1);
        add(ViewInfoHeadingPanel, BorderLayout.NORTH);

        // Table Model and JTable
        viewInfoTableModel = new DefaultTableModel(new String[]{"SI", "Notice", "Publish Date", "Due Date"}, 0);
        viewInfoTable = new JTable(viewInfoTableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Adjust column widths
        TableColumnModel columnModel = viewInfoTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50); // SI column
        columnModel.getColumn(1).setPreferredWidth(300); // Notice column

        // Set Notice column to wrap text
        viewInfoTable.getColumnModel().getColumn(1);

        // Fetch data from database
        loadData();

        JScrollPane scrollPane = new JScrollPane(viewInfoTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        printButton = new JButton("Print");
        printButton.addActionListener(e -> {
            try {
                viewInfoTable.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        buttonPanel.add(printButton);

        closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Set the JFrame visibility
        setVisible(true);
    }

    private void loadData() {
        try {
            DataBases c = new DataBases();
            ResultSet resultSet = c.statement.executeQuery("SELECT * FROM viewInfo");
            while (resultSet.next()) {
                int si = resultSet.getInt("SI");
                String notice = resultSet.getString("notice");
                String publishDate = resultSet.getString("publishDate");
                String dueDate = resultSet.getString("dueDate");
                viewInfoTableModel.addRow(new Object[]{si, notice, publishDate, dueDate});
            }
            c.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ViewInfo();
    }
}
/*
 * 
 // Custom TableCellRenderer for JTextArea
 class TextAreaRenderer extends JScrollPane implements javax.swing.table.TableCellRenderer {
    JTextArea textarea;
    
    public TextAreaRenderer() {
        textarea = new JTextArea();
        textarea.setLineWrap(true);
        textarea.setWrapStyleWord(true);
        getViewport().add(textarea);
    }
    
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        textarea.setText((String) value);
        return this;
    }
}

*/