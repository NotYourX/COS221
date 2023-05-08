import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class prac4 extends JFrame {
    String element;
    String value;

    public void setElement(String element) {
        this.element = element;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public prac4() {
        setTitle("Tabbed Menu Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 450);
        setLocationRelativeTo(null);

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Create a menu with three items
        JMenu menu = new JMenu("Tabs");
        menuBar.add(menu);

        JMenuItem tab1Item = new JMenuItem("Staff");
        JMenuItem tab2Item = new JMenuItem("Films");
        JMenuItem tab3Item = new JMenuItem("Reports");
        JMenuItem tab4Item = new JMenuItem("Notifications");

        // Add the items to the menu
        menu.add(tab1Item);
        menu.add(tab2Item);
        menu.add(tab3Item);
        menu.add(tab4Item);

        // Create a tabbed pane with three tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        getContentPane().add(tabbedPane);

        JPanel tab1 = new JPanel();
        JPanel tab2 = new JPanel();
        JPanel tab3 = new JPanel();
        JPanel tab4 = new JPanel();

        // Add the tabs to the tabbed pane
        tabbedPane.addTab("Staff", tab1);
        tabbedPane.addTab("Films", tab2);
        tabbedPane.addTab("Reports", tab3);
        tabbedPane.addTab("Notifications", tab4);

        // Add action listeners to the menu items
        tab1Item.addActionListener(e -> tabbedPane.setSelectedIndex(0));
        tab2Item.addActionListener(e -> tabbedPane.setSelectedIndex(1));
        tab3Item.addActionListener(e -> tabbedPane.setSelectedIndex(2));
        tab4Item.addActionListener(e -> tabbedPane.setSelectedIndex(3));

        String[] columnNames = {"first_name", "last_name", "address", "address2", "district", "city", "postal_code", "phone", "store_id", "active", "last_update"};
        Object[][] data = getAllStaff();
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(950, 100));

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the tab
        tab1.add(scrollPane);

        JTextField textField1;
        textField1 = new JTextField(20);
        textField1.setPreferredSize(new Dimension(200, 20));

        // Create button
        JButton button = new JButton("SEARCH");

        // Add components to the frame
        tab1.add(new JLabel("Choose what to filter by:"));
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("");
        comboBox.addItem("first_name");
        comboBox.addItem("last_name");
        comboBox.addItem("address");
        comboBox.addItem("address2");
        comboBox.addItem("district");
        comboBox.addItem("city");
        comboBox.addItem("postal_code");
        comboBox.addItem("phone");
        comboBox.addItem("store_id");
        comboBox.addItem("active");
        comboBox.addItem("last_update");


        tab1.add(comboBox);
        tab1.add(textField1);
        button.setPreferredSize(new Dimension(100, 20));
        tab1.add(button);
        add(tab1);

        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) comboBox.getSelectedItem();
                setElement(selectedOption);
            }
        });

        button.addActionListener(e -> {
            String text = textField1.getText();
            setValue(text);
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new prac4().setVisible(true));
    }

    public String[][] getAllStaff() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String[][] arr = new String[5][12];
        String url = "jdbc:mysql://localhost:3306/sakila";
        String username = "root";
        String password = "Bakkies1";
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);

            stmt = conn.createStatement();
            String SQL = "SELECT s.first_name, s.last_name, a.address, a.address2, a.district, c.city, a.postal_code, a.phone, s.store_id, s.active, s.last_update FROM staff AS s INNER JOIN address AS a ON s.address_id = a.address_id INNER JOIN city AS c ON a.city_id = c.city_id";
            rs = stmt.executeQuery(SQL);
            int x = 0;
            while (rs.next()) {
                //System.out.println(rs.getString("first_name")+rs.getString("last_name"));
                arr[x][0] = rs.getString("s.first_name");
                arr[x][1] = rs.getString("s.last_name");
                arr[x][2] = rs.getString("a.address");
                arr[x][3] = rs.getString("a.address2");
                arr[x][4] = rs.getString("a.district");
                arr[x][5] = rs.getString("c.city");
                arr[x][6] = rs.getString("a.postal_code");
                arr[x][7] = rs.getString("a.phone");
                arr[x][8] = rs.getString("s.store_id");
                arr[x][9] = rs.getString("s.active");
                arr[x][10] = rs.getString("s.last_update");
                x++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return arr;
    }

    public String[][] getSomeStaff() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String[][] arr = new String[5][12];
        String url = "jdbc:mysql://localhost:3306/sakila";
        String username = "root";
        String password = "Bakkies1";
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);

            stmt = conn.createStatement();
            String SQL = "SELECT s.first_name, s.last_name, a.address, a.address2, a.district, c.city, a.postal_code, a.phone, s.store_id, s.active, s.last_update FROM staff AS s INNER JOIN address AS a ON s.address_id = a.address_id INNER JOIN city AS c ON a.city_id = c.city_id WHERE " +element+"='"+value+"'";
            rs = stmt.executeQuery(SQL);
            int x = 0;
            while (rs.next()) {
                //System.out.println(rs.getString("first_name")+rs.getString("last_name"));
                arr[x][0] = rs.getString("s.first_name");
                arr[x][1] = rs.getString("s.last_name");
                arr[x][2] = rs.getString("a.address");
                arr[x][3] = rs.getString("a.address2");
                arr[x][4] = rs.getString("a.district");
                arr[x][5] = rs.getString("c.city");
                arr[x][6] = rs.getString("a.postal_code");
                arr[x][7] = rs.getString("a.phone");
                arr[x][8] = rs.getString("s.store_id");
                arr[x][9] = rs.getString("s.active");
                arr[x][10] = rs.getString("s.last_update");
                x++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return arr;
    }

}

