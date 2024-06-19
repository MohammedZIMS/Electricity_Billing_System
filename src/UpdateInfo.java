import javax.swing.*;
import java.awt.*;


public class UpdateInfo extends JFrame {
    JLabel NoticeLabel, PublishDateLabel, DueDateLabel;
    JTextField NoticeTextField, PublishDateTextField, DueDateTextField;
    JButton SubmitButton, CloseButton;

    UpdateInfo() {
        // Set up the JFrame
        setTitle("Pariseba");
        setSize(700, 500);
        setLocation(600, 200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Heading Panel
        JPanel UpdateInfoHeadingPanel = new JPanel();
        JLabel heading1 = new JLabel("Update Information");
        heading1.setFont(new Font("Times New Roman", Font.BOLD, 24));
        UpdateInfoHeadingPanel.add(heading1);
        add(UpdateInfoHeadingPanel, BorderLayout.NORTH);

        // Body Panel for form inputs
        JPanel UpdateInfoBody = new JPanel();
        UpdateInfoBody.setLayout(new GridLayout(6, 1, 10, 10)); // Using GridLayout for simplicity

        NoticeLabel = new JLabel("Notice: ");
        NoticeTextField = new JTextField(20);

        PublishDateLabel = new JLabel("Publish Date: ");
        PublishDateTextField = new JTextField(20);

        DueDateLabel = new JLabel("Due Date: ");
        DueDateTextField = new JTextField(20);

        UpdateInfoBody.add(NoticeLabel);
        UpdateInfoBody.add(NoticeTextField);
        UpdateInfoBody.add(PublishDateLabel);
        UpdateInfoBody.add(PublishDateTextField);
        UpdateInfoBody.add(DueDateLabel);
        UpdateInfoBody.add(DueDateTextField);

        add(UpdateInfoBody, BorderLayout.CENTER);

        // Button Panel
        JPanel UpdateInfoButtonPanel = new JPanel();
        UpdateInfoButtonPanel.setLayout(new FlowLayout());

        SubmitButton = new JButton("Submit");
        SubmitButton.addActionListener(e -> {
            String notice = NoticeTextField.getText();
            String publishDate = PublishDateTextField.getText();
            String dueDate = DueDateTextField.getText();

            // Add your database update logic here
            try {
                DataBases c = new DataBases();
                String query = "INSERT INTO viewInfo (notice, publishDate, dueDate) VALUES ('" + notice + "', '" + publishDate + "', '" + dueDate + "')";
                c.statement.executeUpdate(query);
                
                JOptionPane.showMessageDialog(this, "Information Updated Successfully");
                dispose();
                new ViewInfo();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        UpdateInfoButtonPanel.add(SubmitButton);

        CloseButton = new JButton("Close");
        CloseButton.addActionListener(e -> {
            dispose();
            new ViewInfo();
        });
        UpdateInfoButtonPanel.add(CloseButton);

        add(UpdateInfoButtonPanel, BorderLayout.SOUTH);

        // Set the JFrame visibility
        setVisible(true);
    }

    public static void main(String[] args) {
        new UpdateInfo();
    }
}
