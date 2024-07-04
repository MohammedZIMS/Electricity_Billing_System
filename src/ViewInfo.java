import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.print.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewInfo extends JFrame implements Printable {

    JButton closeButton;
    JButton printButton;
    JTable viewInfoTable;
    DefaultTableModel viewInfoTableModel;

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

        viewInfoTableModel = new DefaultTableModel(new String[]{"SI", "Notice", "Publish Date", "Due Date"}, 0);

        viewInfoTable = new JTable(viewInfoTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
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

        printButton = new JButton("Print");
        printButton.addActionListener(e -> printSelectedRow());
        buttonPanel.add(printButton);

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

                viewInfoTableModel.addRow(new Object[]{si, notice, publishDate, dueDate});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printSelectedRow() {
        int selectedRow = viewInfoTable.getSelectedRow();
        if (selectedRow != -1) {
            generatePDF(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to print.");
        }
    }

    private void generatePDF(int selectedRow) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Generate Notice");
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

        int selectedRow = viewInfoTable.getSelectedRow();
        if (selectedRow == -1) {
            return NO_SUCH_PAGE;
        }

        String siValue = viewInfoTableModel.getValueAt(selectedRow, 0).toString();
        String noticeValue = viewInfoTableModel.getValueAt(selectedRow, 1).toString();
        String publishDateValue = viewInfoTableModel.getValueAt(selectedRow, 2).toString();
        String dueDateValue = viewInfoTableModel.getValueAt(selectedRow, 3).toString();

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        g2d.setFont(new Font("Times New Roman", Font.BOLD, 18));
        g2d.drawString("Notice Information", 100, 50);
        g2d.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        g2d.drawString("SI: " + siValue, 100, 100);
        g2d.drawString("Notice: " + noticeValue, 100, 120);
        g2d.drawString("Publish Date: " + publishDateValue, 100, 140);
        g2d.drawString("Due Date: " + dueDateValue, 100, 160);

        g2d.setFont(new Font("Times New Roman", Font.PLAIN, 10));
        g2d.drawString("Generated on: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), 100, 200);

        return PAGE_EXISTS;
    }

    public static void main(String[] args) {
        new ViewInfo();
    }
}
