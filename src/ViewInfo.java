import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;

public class ViewInfo extends JFrame {

    JButton closeButton;
    JTable viewInfoTable;
    DefaultTableModel viewInfoTableModel;
    JButton printButton;

    ViewInfo() {
        setTitle("Pariseba");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel viewInfoHeadingPanel = new JPanel();
        JLabel heading1 = new JLabel("View Information");
        heading1.setFont(new Font("Times New Roman", Font.BOLD, 24));
        viewInfoHeadingPanel.add(heading1);
        add(viewInfoHeadingPanel, BorderLayout.NORTH);

        viewInfoTableModel = new DefaultTableModel(new String[]{"SI", "Notice", "Publish Date", "Due Date", "Print"}, 0);

        viewInfoTable = new JTable(viewInfoTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; 
            }
        };

        TableColumnModel columnModel = viewInfoTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50); 
        columnModel.getColumn(1).setPreferredWidth(300);

        loadData();

        JScrollPane scrollPane = new JScrollPane(viewInfoTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

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

                printButton = new JButton("Print");
                printButton.addActionListener(e -> {
                    int selectedRow = viewInfoTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String siValue = (String) viewInfoTableModel.getValueAt(selectedRow, 0);
                        String noticeValue = (String) viewInfoTableModel.getValueAt(selectedRow, 1);
                        String publishDateValue = (String) viewInfoTableModel.getValueAt(selectedRow, 2);
                        String dueDateValue = (String) viewInfoTableModel.getValueAt(selectedRow, 3);

                        System.out.println("Printing row data:");
                        System.out.println("  SI:          " + siValue);
                        System.out.println("  Notice:     " + noticeValue);
                        System.out.println("  Publish Date:" + publishDateValue);
                        System.out.println("  Due Date:    " + dueDateValue);
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a row to print.");
                    }
                });

                viewInfoTableModel.addRow(new Object[]{si, notice, publishDate, dueDate, printButton});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) 
    {
        new ViewInfo();
    }
}
