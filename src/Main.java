import javax.swing.*;
import java.awt.*;

class Main extends JFrame 
{

    Main() 
    {
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
        newCustomerButton.setBounds(70, 100, 150, 30);
        newCustomerButton.addActionListener(e->
        {
            new NewCustomer();
        }
        );
        layeredPane.add(newCustomerButton, JLayeredPane.PALETTE_LAYER);

        JButton customerDetailsButton = new JButton("Customer Details");
        customerDetailsButton.setBounds(250, 100, 150, 30);
        layeredPane.add(customerDetailsButton, JLayeredPane.PALETTE_LAYER);

        JButton depositDetailsButton = new JButton("Deposit Details");
        depositDetailsButton.setBounds(430, 100, 150, 30);
        layeredPane.add(depositDetailsButton, JLayeredPane.PALETTE_LAYER);

        JButton calculateBillButton = new JButton("Calculate Bill");
        calculateBillButton.setBounds(70, 150, 150, 30);
        layeredPane.add(calculateBillButton, JLayeredPane.PALETTE_LAYER);

        JButton upInfoButton = new JButton("Update Information");
        upInfoButton.setBounds(250, 150, 150, 30);
        layeredPane.add(upInfoButton, JLayeredPane.PALETTE_LAYER);

        JButton viewInfoButton = new JButton("View Information");
        viewInfoButton.setBounds(430, 150, 150, 30);
        layeredPane.add(viewInfoButton, JLayeredPane.PALETTE_LAYER);

        JButton payBillButton = new JButton("Pay Bill");
        payBillButton.setBounds(70, 200, 150, 30);
        layeredPane.add(payBillButton, JLayeredPane.PALETTE_LAYER);

        JButton billDetailsButton = new JButton("Bill Details");
        billDetailsButton.setBounds(250, 200, 150, 30);
        layeredPane.add(billDetailsButton, JLayeredPane.PALETTE_LAYER);

        JButton generateBillButton = new JButton("Generate Bill");
        generateBillButton.setBounds(430, 200, 150, 30);
        layeredPane.add(generateBillButton, JLayeredPane.PALETTE_LAYER);

        add(layeredPane);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.X_AXIS));
        setJMenuBar(menuBar);

        menuBar.add(Box.createHorizontalGlue());

        JMenu menu = new JMenu("Menu");
        menu.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        menuBar.add(menu);

        JMenuItem newCustomer = new JMenuItem("New Customer");
        newCustomer.setFont(new Font("Courier New", Font.PLAIN, 14));
        ImageIcon newCustomIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/customer3-removebg-preview.png"));
        Image newCustImg = newCustomIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        newCustomer.setIcon(new ImageIcon(newCustImg));
        menu.add(newCustomer);

        JMenuItem customerDetails = new JMenuItem("Customer Details");
        customerDetails.setFont(new Font("monospaced", Font.PLAIN, 14));
        ImageIcon detailsIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/customerdetails.png"));
        Image detailsImg = detailsIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        customerDetails.setIcon(new ImageIcon(detailsImg));
        menu.add(customerDetails);

        JMenuItem depositDetails = new JMenuItem("Deposit Details");
        depositDetails.setFont(new Font("monospaced", Font.PLAIN, 14));
        ImageIcon depositIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/depositdetails.png"));
        Image depositImg = depositIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        depositDetails.setIcon(new ImageIcon(depositImg));
        menu.add(depositDetails);

        JMenuItem calculateBill = new JMenuItem("Calculate Bill ");
        calculateBill.setFont(new Font("monospaced", Font.PLAIN, 14));
        ImageIcon calculateBillIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/calculatorbills.png"));
        Image calculateBillImg = calculateBillIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        calculateBill.setIcon(new ImageIcon(calculateBillImg));
        menu.add(calculateBill);

        menuBar.add(Box.createHorizontalStrut(20));

        JMenu info = new JMenu("Information");
        info.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        menuBar.add(info);

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
        menuBar.add(user);

        JMenuItem payBill = new JMenuItem("Pay Bill");
        payBill.setFont(new Font("Courier New", Font.PLAIN, 14));
        ImageIcon payBillIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/pay.png"));
        Image payBillImg = payBillIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        payBill.setIcon(new ImageIcon(payBillImg));
        user.add(payBill);

        JMenuItem billDetails = new JMenuItem("Bill Details");
        billDetails.setFont(new Font("Courier New", Font.PLAIN, 14));
        ImageIcon billDetailsImg = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/detail.png"));
        Image billDetailsImage = billDetailsImg.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        billDetails.setIcon(new ImageIcon(billDetailsImage));
        user.add(billDetails);

        menuBar.add(Box.createHorizontalStrut(20));

        JMenu bill = new JMenu("Bill");
        bill.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        menuBar.add(bill);

        JMenuItem genBill = new JMenuItem("Generate Bill");
        genBill.setFont(new Font("Courier New", Font.PLAIN, 14));
        ImageIcon genBillIcon = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/bill.png"));
        Image genBillImg = genBillIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        genBill.setIcon(new ImageIcon(genBillImg));
        bill.add(genBill);

        menuBar.add(Box.createHorizontalStrut(20));

        JMenu utility = new JMenu("Utility");
        utility.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        menuBar.add(utility);

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
        menuBar.add(exit);

        JMenuItem eexit = new JMenuItem("Exit");
        eexit.setFont(new Font("monospaced", Font.PLAIN, 14));
        ImageIcon eexitImg = new ImageIcon(ClassLoader.getSystemResource("ImagePariseba/exit.png"));
        Image eexitImage = eexitImg.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        eexit.setIcon(new ImageIcon(eexitImage));
        exit.add(eexit);

        menuBar.add(Box.createHorizontalGlue());

        setTitle("Pariseba");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) 
    {
        new Main();
    }
}