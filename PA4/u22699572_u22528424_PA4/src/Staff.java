//this file is edid
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.regex.PatternSyntaxException;

public class prac4 extends JFrame {

    public prac4() {
        setTitle("COS221Prac4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 450);
        setLocationRelativeTo(null);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("Tabs");
        menuBar.add(menu);
        
        JMenuItem tab1Item = new JMenuItem("Staff");
        JMenuItem tab2Item = new JMenuItem("Films");
        JMenuItem tab3Item = new JMenuItem("Reports");
        JMenuItem tab4Item = new JMenuItem("Notifications");

        menu.add(tab1Item);
        menu.add(tab2Item);
        menu.add(tab3Item);
        menu.add(tab4Item);

        JTabbedPane tabbedPane = new JTabbedPane();
        getContentPane().add(tabbedPane);

        JPanel tab1 = new JPanel();
        JPanel tab2 = new JPanel();
        JPanel tab3 = new JPanel();
        JPanel tab4 = new JPanel();

        tabbedPane.addTab("Staff", tab1);
        tabbedPane.addTab("Films", tab2);
        tabbedPane.addTab("Reports", tab3);
        tabbedPane.addTab("Notifications", tab4);

        tab1Item.addActionListener(e -> tabbedPane.setSelectedIndex(0));
        tab2Item.addActionListener(e -> tabbedPane.setSelectedIndex(1));
        tab3Item.addActionListener(e -> tabbedPane.setSelectedIndex(2));
        tab4Item.addActionListener(e -> tabbedPane.setSelectedIndex(3));

        String[] columnNames = {"first_name", "last_name", "address", "address2", "district", "city", "postal_code", "phone", "store_id", "active", "last_update"};
        Object[][] data = getAllStaff();
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(950, 100));
        JScrollPane scrollPane = new JScrollPane(table);
        tab1.add(scrollPane);
        table = new JTable(model);
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);
        add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Filter");
        panel.add(label, BorderLayout.WEST);
        final JTextField filterText = new JTextField("");
        panel.add(filterText, BorderLayout.CENTER);
        add(panel, BorderLayout.NORTH);
        JButton button = new JButton("Filter");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = filterText.getText();
                if(text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter(text));
                    } catch(PatternSyntaxException pse) {
                        System.out.println("Bad regex pattern");
                    }
                }
            }
        });
        add(button, BorderLayout.SOUTH);
        setSize(1000, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new prac4().setVisible(true));
    }

    public String[][] getAllStaff() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String[][] arr = new String[2][12];
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
}

