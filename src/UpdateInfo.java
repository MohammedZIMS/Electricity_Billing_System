import javax.swing.*;
import java.awt.*;

public class UpdateInfo extends JFrame {
    JLabel NoticeLabel, PublishDateLabel, DueDateLabel;
    JTextField PublishDateTextField, DueDateTextField;
    JButton SubmitButton, CloseButton;
    JTextArea NoticeTextArea;

    UpdateInfo() {
        // Set up the JFrame
        setTitle("Pariseba");
        setSize(700, 500);
        setLocation(600, 200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null); // Using null layout for absolute positioning

        // Heading Panel
        JPanel UpdateInfoHeadingPanel = new JPanel();
        UpdateInfoHeadingPanel.setBackground(new Color(252, 186, 3));
        UpdateInfoHeadingPanel.setBounds(0, 0, 700, 60); // Setting bounds for heading panel
        UpdateInfoHeadingPanel.setLayout(null); // No layout manager

        JLabel heading1 = new JLabel("Update Information");
        heading1.setFont(new Font("Times New Roman", Font.BOLD, 24));
        heading1.setBounds(250, 10, 300, 40); // Center heading within the heading panel
        UpdateInfoHeadingPanel.add(heading1);
        add(UpdateInfoHeadingPanel);

        // Body Panel for form inputs
        JPanel UpdateInfoBodyPanel = new JPanel();
        UpdateInfoBodyPanel.setLayout(null); // No layout manager
        UpdateInfoBodyPanel.setBounds(0, 60, 500, 300); // Set bounds for the body panel

        NoticeLabel = new JLabel("Notice: ");
        NoticeLabel.setBounds(50, 30, 100, 25);
        UpdateInfoBodyPanel.add(NoticeLabel);

        NoticeTextArea = new JTextArea(5, 20); // Use JTextArea for multi-line input
        NoticeTextArea.setLineWrap(true);
        NoticeTextArea.setWrapStyleWord(true);
        JScrollPane noticeScrollPane = new JScrollPane(NoticeTextArea);
        noticeScrollPane.setBounds(180, 30, 250, 100); // Adjust bounds for JScrollPane
        UpdateInfoBodyPanel.add(noticeScrollPane);

        PublishDateLabel = new JLabel("Publish Date: ");
        PublishDateLabel.setBounds(50, 150, 100, 25);
        UpdateInfoBodyPanel.add(PublishDateLabel);

        PublishDateTextField = new JTextField();
        PublishDateTextField.setBounds(180, 150, 250, 25);
        UpdateInfoBodyPanel.add(PublishDateTextField);

        DueDateLabel = new JLabel("Due Date: ");
        DueDateLabel.setBounds(50, 190, 100, 25);
        UpdateInfoBodyPanel.add(DueDateLabel);

        DueDateTextField = new JTextField();
        DueDateTextField.setBounds(180, 190, 250, 25);
        UpdateInfoBodyPanel.add(DueDateTextField);

        add(UpdateInfoBodyPanel);

        // Button Panel
        JPanel UpdateInfoButtonPanel = new JPanel();
        UpdateInfoButtonPanel.setLayout(null); 
        UpdateInfoButtonPanel.setBounds(0, 360, 700, 100);

        SubmitButton = new JButton("Submit");
        SubmitButton.setBounds(200, 30, 100, 30); 
        SubmitButton.addActionListener(e -> {
            String notice = NoticeTextArea.getText();
            String publishDate = PublishDateTextField.getText();
            String dueDate = DueDateTextField.getText();

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
        CloseButton.setBounds(350, 30, 100, 30); 
        CloseButton.addActionListener(e -> {
            dispose();
            new ViewInfo();
        });
        UpdateInfoButtonPanel.add(CloseButton);

        add(UpdateInfoButtonPanel);

        // Image Panel
        JPanel EastImagePanel = new JPanel();
        EastImagePanel.setLayout(null);
        EastImagePanel.setBounds(500, 60, 200, 300); 

        ImageIcon upInfoImgIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/refresh.png"));
        Image upInfoImg = upInfoImgIcon.getImage().getScaledInstance(230, 200, Image.SCALE_DEFAULT);
        ImageIcon upInfoImgIcon1 = new ImageIcon(upInfoImg);
        JLabel imageLabel = new JLabel(upInfoImgIcon1);
        imageLabel.setBounds(0, 0, 230, 200); // Adjust image bounds
        EastImagePanel.add(imageLabel);

        add(EastImagePanel);

        // Set the JFrame visibility
        setVisible(true);
    }

    public static void main(String[] args) {
        new UpdateInfo();
    }
}
