import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

class Main extends JFrame {
    String Accounttype;
    String meterNum;
    JLabel nameLabel, nameTextLabel, meterNoLabel, meterNoTextLabel, emailLabel, emailTextLabel, phoneNumberLabel, phoneNumberTextLabel, addressLabel, addressTextLabel;
    JButton backButton, updateProfileButton;
    JPanel profilePanel;

    Main(String Accounttype, String meterNum) 
    {
        this.Accounttype = Accounttype;
        this.meterNum = meterNum;
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        ImageIcon EbsIco = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/ebs0.jpg"));
        Image EbsImg = EbsIco.getImage().getScaledInstance(1500, 830, Image.SCALE_DEFAULT);
        ImageIcon EbsIco1 = new ImageIcon(EbsImg);
        JLabel EbsLabel = new JLabel(EbsIco1);
        EbsLabel.setBounds(0, 0, 1500, 830);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1500, 830));
        layeredPane.add(EbsLabel, JLayeredPane.DEFAULT_LAYER);

        JButton newCustomerButton = new JButton("New Customer");
        newCustomerButton.setBounds(70, 200, 150, 30);
        newCustomerButton.addActionListener(e -> new NewCustomer());
        layeredPane.add(newCustomerButton, JLayeredPane.PALETTE_LAYER);

        JButton customerDetailsButton = new JButton("Customer Details");
        customerDetailsButton.setBounds(250, 200, 150, 30);
        customerDetailsButton.addActionListener(e -> new CustomerDetails());
        layeredPane.add(customerDetailsButton, JLayeredPane.PALETTE_LAYER);

        JButton depositDetailsButton = new JButton("Deposit Details");
        depositDetailsButton.setBounds(430, 150, 150, 30);
        depositDetailsButton.addActionListener(e -> new DepositDetails());
        layeredPane.add(depositDetailsButton, JLayeredPane.PALETTE_LAYER);

        JButton calculateBillButton = new JButton("Calculate Bill");
        calculateBillButton.setBounds(430, 200, 150, 30);
        calculateBillButton.addActionListener(e -> new CalculateBill());
        layeredPane.add(calculateBillButton, JLayeredPane.PALETTE_LAYER);

        JButton upInfoButton = new JButton("Update Information");
        upInfoButton.setBounds(250, 150, 150, 30);
        upInfoButton.addActionListener(e -> new UpdateInfo());
        layeredPane.add(upInfoButton, JLayeredPane.PALETTE_LAYER);

        JButton viewInfoButton = new JButton("View Information");
        viewInfoButton.setBounds(70, 150, 150, 30);
        viewInfoButton.addActionListener(e -> new ViewInfo());
        layeredPane.add(viewInfoButton, JLayeredPane.PALETTE_LAYER);

        JButton payBillButton = new JButton("Pay Bill");
        payBillButton.setBounds(70, 200, 150, 30);
        layeredPane.add(payBillButton, JLayeredPane.PALETTE_LAYER);

        JButton billDetailsButton = new JButton("Bill Details");
        billDetailsButton.setBounds(70, 250, 150, 30);
        billDetailsButton.addActionListener(e -> new BillDetails(Accounttype));
        layeredPane.add(billDetailsButton, JLayeredPane.PALETTE_LAYER);

        JButton generateBillButton = new JButton("Generate Bill");
        generateBillButton.setBounds(70, 300, 150, 30);
        layeredPane.add(generateBillButton, JLayeredPane.PALETTE_LAYER);

        add(layeredPane);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.X_AXIS));
        setJMenuBar(menuBar);

        menuBar.add(Box.createHorizontalGlue());

        JMenu menu = new JMenu("Menu");
        menu.setFont(new Font("Times New Roman", Font.PLAIN, 16));

        JMenuItem newCustomer = new JMenuItem("New Customer");
        newCustomer.setFont(new Font("Courier New", Font.PLAIN, 14));
        ImageIcon newCustomIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/customer3-removebg-preview.png"));
        Image newCustImg = newCustomIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        newCustomer.setIcon(new ImageIcon(newCustImg));
        newCustomer.addActionListener(e -> new NewCustomer());
        menu.add(newCustomer);

        JMenuItem customerDetails = new JMenuItem("Customer Details");
        customerDetails.setFont(new Font("monospaced", Font.PLAIN, 14));
        ImageIcon detailsIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/customerdetails.png"));
        Image detailsImg = detailsIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        customerDetails.setIcon(new ImageIcon(detailsImg));
        customerDetails.addActionListener(e -> new CustomerDetails());
        menu.add(customerDetails);

        JMenuItem depositDetails = new JMenuItem("Deposit Details");
        depositDetails.setFont(new Font("monospaced", Font.PLAIN, 14));
        ImageIcon depositIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/depositdetails.png"));
        Image depositImg = depositIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        depositDetails.setIcon(new ImageIcon(depositImg));
        depositDetails.addActionListener(e -> new DepositDetails());
        menu.add(depositDetails);

        JMenuItem calculateBill = new JMenuItem("Calculate Bill ");
        calculateBill.setFont(new Font("monospaced", Font.PLAIN, 14));
        ImageIcon calculateBillIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/calculatorbills.png"));
        Image calculateBillImg = calculateBillIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        calculateBill.setIcon(new ImageIcon(calculateBillImg));
        calculateBill.addActionListener(e -> new CalculateBill());
        menu.add(calculateBill);

        menuBar.add(Box.createHorizontalStrut(20));

        JMenu info = new JMenu("Information");
        info.setFont(new Font("Times New Roman", Font.PLAIN, 16));

        JMenuItem upInfo = new JMenuItem("Update Information");
        upInfo.setFont(new Font("Courier New", Font.PLAIN, 14));
        ImageIcon upInfoIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/refresh.png"));
        Image upInfoImg = upInfoIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        upInfo.setIcon(new ImageIcon(upInfoImg));
        info.add(upInfo);

        JMenuItem viewInfo = new JMenuItem("View Information");
        viewInfo.setFont(new Font("Courier New", Font.PLAIN, 14));
        ImageIcon viewInfoIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/information.png"));
        Image viewInfoImg = viewInfoIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        viewInfo.setIcon(new ImageIcon(viewInfoImg));
        info.add(viewInfo);

        menuBar.add(Box.createHorizontalStrut(20));

        JMenu user = new JMenu("User");
        user.setFont(new Font("Times New Roman", Font.PLAIN, 16));

        JMenuItem userProfile = new JMenuItem("Profile");
        userProfile.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        ImageIcon userProfileImageIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/Profail.png"));
        Image userImage = userProfileImageIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        userProfile.setIcon(new ImageIcon(userImage));
        userProfile.addActionListener(e -> showProfile());
        user.add(userProfile);

        menuBar.add(Box.createHorizontalStrut(20));

        JMenu bill = new JMenu("Bill");
        bill.setFont(new Font("Times New Roman", Font.PLAIN, 16));

        JMenuItem genBill = new JMenuItem("Generate Bill");
        genBill.setFont(new Font("Courier New", Font.PLAIN, 14));
        ImageIcon genBillIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/bill.png"));
        Image genBillImg = genBillIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        genBill.setIcon(new ImageIcon(genBillImg));
        bill.add(genBill);

        JMenuItem payBill = new JMenuItem("Pay Bill");
        payBill.setFont(new Font("Courier New", Font.PLAIN, 14));
        ImageIcon payBillIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/pay.png"));
        Image payBillImg = payBillIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        payBill.setIcon(new ImageIcon(payBillImg));
        bill.add(payBill);

        JMenuItem billDetails = new JMenuItem("Bill Details");
        billDetails.setFont(new Font("Courier New", Font.PLAIN, 14));
        ImageIcon billDetailsImg = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/detail.png"));
        Image billDetailsImage = billDetailsImg.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        billDetails.setIcon(new ImageIcon(billDetailsImage));
        billDetails.addActionListener(e -> new BillDetails(Accounttype));
        bill.add(billDetails);

        menuBar.add(Box.createHorizontalStrut(20));

        JMenu utility = new JMenu("Utility");
        utility.setFont(new Font("Times New Roman", Font.PLAIN, 16));

        JMenuItem calculator = new JMenuItem("Calculator");
        calculator.setFont(new Font("Courier New", Font.PLAIN, 14));
        ImageIcon calculatorIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/calculator.png"));
        Image calculatorImg = calculatorIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        calculator.setIcon(new ImageIcon(calculatorImg));
        utility.add(calculator);

        JMenuItem notepad = new JMenuItem("Notepad");
        notepad.setFont(new Font("monospaced", Font.PLAIN, 14));
        ImageIcon notepadImg = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/notepad.png"));
        Image notepadImage = notepadImg.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        notepad.setIcon(new ImageIcon(notepadImage));
        utility.add(notepad);

        menuBar.add(Box.createHorizontalStrut(20));

        JMenu exit = new JMenu("Exit");
        exit.setFont(new Font("serif", Font.PLAIN, 15));

        JMenuItem eexit = new JMenuItem("Exit");
        eexit.setFont(new Font("monospaced", Font.PLAIN, 14));
        eexit.addActionListener(e -> {
            setVisible(false);
            new Login();
        });
        ImageIcon eexitImg = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/exit.png"));
        Image eexitImage = eexitImg.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        eexit.setIcon(new ImageIcon(eexitImage));
        exit.add(eexit);

        menuBar.add(Box.createHorizontalGlue());

        if (Accounttype.equals("Admin")) {
            menuBar.add(menu);
            payBillButton.setVisible(false);
            generateBillButton.setVisible(false);
            billDetailsButton.setVisible(false);
        } else {
            menuBar.add(bill);
            menuBar.add(info);
            newCustomerButton.setVisible(false);
            calculateBillButton.setVisible(false);
            depositDetailsButton.setVisible(false);
            customerDetailsButton.setVisible(false);
            upInfoButton.setVisible(false);
        }
        menuBar.add(user);
        menuBar.add(utility);
        menuBar.add(exit);

        profilePanel = new JPanel();
        profilePanel.setBackground(new Color(252, 186, 3));
        profilePanel.setLayout(new BorderLayout());
        profilePanel.setForeground(Color.WHITE);
        profilePanel.setVisible(false);

        JLabel profileHeading = new JLabel("Profile", JLabel.CENTER);
        profileHeading.setFont(new Font("Times New Roman", Font.BOLD, 24));
        profilePanel.add(profileHeading, BorderLayout.NORTH);

        ImageIcon profileIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/Profile0.png"));
        Image profileImage = profileIcon.getImage().getScaledInstance(150, 100, Image.SCALE_DEFAULT);
        JLabel profileLabel = new JLabel(new ImageIcon(profileImage));
        profileLabel.setBounds(170, 80, 150, 100);
        profilePanel.add(profileLabel);
        profilePanel.add(profileLabel, BorderLayout.CENTER);

        JPanel profileInfoPanel = new JPanel();
        profileInfoPanel.setBackground(new Color(252, 186, 3));
        profileInfoPanel.setPreferredSize(new Dimension((int) (getWidth() + 500), getHeight() - 550));
        profileInfoPanel.setLayout(null);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 180, 100, 16);
        profileInfoPanel.add(nameLabel);

        nameTextLabel = new JLabel("");
        nameTextLabel.setBounds(120, 180, 150, 16);
        profileInfoPanel.add(nameTextLabel);

        meterNoLabel = new JLabel("ID No:");
        meterNoLabel.setBounds(10, 210, 100, 16);
        profileInfoPanel.add(meterNoLabel);

        meterNoTextLabel = new JLabel("");
        meterNoTextLabel.setBounds(120, 210, 150, 16);
        profileInfoPanel.add(meterNoTextLabel);

        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 240, 100, 16);
        profileInfoPanel.add(emailLabel);

        emailTextLabel = new JLabel("");
        emailTextLabel.setBounds(120, 240, 150, 16);
        profileInfoPanel.add(emailTextLabel);

        phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberLabel.setBounds(10, 270, 100, 16);
        profileInfoPanel.add(phoneNumberLabel);

        phoneNumberTextLabel = new JLabel("");
        phoneNumberTextLabel.setBounds(120, 270, 150, 16);
        profileInfoPanel.add(phoneNumberTextLabel);

        addressLabel = new JLabel("Address:");
        addressLabel.setBounds(10, 300, 100, 16);
        profileInfoPanel.add(addressLabel);

        addressTextLabel = new JLabel("");
        addressTextLabel.setBounds(120, 300, 150, 16);
        profileInfoPanel.add(addressTextLabel);

        profilePanel.add(profileInfoPanel, BorderLayout.CENTER);

        JPanel profileButtonPanel = new JPanel();
        profileButtonPanel.setBackground(new Color(252, 186, 3));
        profileButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        backButton = new JButton("Back");
        backButton.addActionListener(e -> profilePanel.setVisible(false));
        profileButtonPanel.add(backButton);

        updateProfileButton = new JButton("Update Profile");
        profileButtonPanel.add(updateProfileButton);

        profilePanel.add(profileButtonPanel, BorderLayout.SOUTH);

        add(profilePanel, BorderLayout.WEST);

        setTitle("Pariseba");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loadProfileData();
    }

    private void loadProfileData() {
        try {
            DataBases c = new DataBases();
            ResultSet resultSet = c.statement.executeQuery("select * from new_customer where meterNo = '" + meterNum + "'");

            if (resultSet.next()) {
                nameTextLabel.setText(resultSet.getString("name"));
                meterNoTextLabel.setText(resultSet.getString("meterNo"));
                emailTextLabel.setText(resultSet.getString("email"));
                phoneNumberTextLabel.setText(resultSet.getString("phoneNumber"));
                addressTextLabel.setText(resultSet.getString("address"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showProfile() {
        profilePanel.setVisible(true);
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> new Main("Customer", "241916"));
    }
}
