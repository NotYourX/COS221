import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.regex.PatternSyntaxException;

public class prac4 extends JFrame {

   /* public prac4() {
        setTitle("COS221Prac4");
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


        //----------------------------------------------------TAB 1 (Staff)----------------------------------------------------------------------------------------------------
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
    }*/

    public prac4() {

        setTitle("FILMS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 450);
        setLocationRelativeTo(null);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("Tabs");
        menuBar.add(menu);
        JMenuItem tab1Item = new JMenuItem("Films");
        menu.add(tab1Item);
        JTabbedPane tabbedPane = new JTabbedPane();
        getContentPane().add(tabbedPane);
        JPanel tab1 = new JPanel();
        tabbedPane.addTab("Films", tab1);
        tab1Item.addActionListener(e -> tabbedPane.setSelectedIndex(0));
        String[] columnNames = {"film_id","title","release_year", "description", "language", "original_language", "rental_duration", "rental_rate", "length", "replacement_cost", "rating", "special_feature"};
        Object[][] data = getAllFilms();
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(950, 300));
        JScrollPane scrollPane = new JScrollPane(table);
        tab1.add(scrollPane);
        setVisible(true);
        getLanguages();

        JButton button = new JButton("Add Record");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //------------------------------------------------------------------------------------------------------------------------
                    JDialog insertPopup = new JDialog();
                    JPanel insertPanel = new JPanel(new GridLayout(0, 2));
                    insertPanel.add(new JLabel("title:"));
                    JTextField titleField = new JTextField();
                    insertPanel.add(titleField);
                    insertPanel.add(new JLabel("release_year:"));
                    JTextField release_dateField = new JTextField();
                    insertPanel.add(release_dateField);
                    insertPanel.add(new JLabel("description:"));
                    JTextField descriptionField = new JTextField();
                    insertPanel.add(descriptionField);
                    insertPanel.add(new JLabel("language:"));
                    //JTextField languageField = new JTextField();
                JComboBox<String> comboBox = new JComboBox<>();
                String[] lang = new String[getNumOfLanguages()];
                lang = getLanguages();
                for (int i = 0; i < getNumOfLanguages(); i++) {
                    comboBox.addItem(lang[i]);
                }
                    insertPanel.add(comboBox);
                    insertPanel.add(new JLabel("original_language:"));
                    //JTextField original_languageField = new JTextField();
                JComboBox<String> comboBox2 = new JComboBox<>();
                for (int i = 0; i < getNumOfLanguages(); i++) {
                    comboBox2.addItem(lang[i]);
                }
                    insertPanel.add(comboBox2);
                    insertPanel.add(new JLabel("rental_duration:"));
                    JTextField rental_durationField = new JTextField();
                    insertPanel.add(rental_durationField);
                    insertPanel.add(new JLabel("rental_rate:"));
                    JTextField rental_rateField = new JTextField();
                    insertPanel.add(rental_rateField);
                    insertPanel.add(new JLabel("length:"));
                    JTextField lengthField = new JTextField();
                    insertPanel.add(lengthField);
                    insertPanel.add(new JLabel("replacement_cost:"));
                    JTextField replacement_costField = new JTextField();
                    insertPanel.add(replacement_costField);
                    insertPanel.add(new JLabel("rating:"));
                    JTextField ratingField = new JTextField();
                    insertPanel.add(ratingField);
                    insertPanel.add(new JLabel("special_feature:"));
                    //JTextField special_featureField = new JTextField();
                JComboBox<String> comboBox3 = new JComboBox<>();
                    comboBox3.addItem("Trailer");
                    comboBox3.addItem("Commentaries");
                    comboBox3.addItem("Deleted Scenes");
                    comboBox3.addItem("Behind the Scenes");
                    insertPanel.add(comboBox3);
                    JButton saveButton = new JButton("Add Record");
                    saveButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            String title = titleField.getText();
                            int year = Integer.parseInt(release_dateField.getText());
                            String description = descriptionField.getText();
                            String language = (String) comboBox.getSelectedItem();//************************************
                            String original_language = (String) comboBox2.getSelectedItem();//**************************
                            String[] lang = new String[getNumOfLanguages()];
                            lang = getLanguages();
                            int languageNum = 1;
                            int original_languageNum = 1;
                            System.out.println(language);
                            System.out.println(original_language);
                            for (int i = 1; i <= getNumOfLanguages(); i++) {
                                if(language.equals(lang[i]))
                                {
                                    System.out.println("i am in here");
                                    //languageNum = String.valueOf(i);
                                    int temp = i;
                                    languageNum = temp;
                                }
                            }
                            for (int i = 1; i <= getNumOfLanguages(); i++) {
                                if(original_language.equals(lang[i]))
                                {
                                    int temp = i;
                                    original_languageNum = temp;
                                }
                            }

                            int rental_duration = Integer.parseInt(rental_durationField.getText());
                            float rental_rate = Float.parseFloat(rental_rateField.getText());
                            int length = Integer.parseInt(lengthField.getText());
                            String rating = ratingField.getText();
                            Float replacement_cost = Float.parseFloat(replacement_costField.getText());
                            String special_feature = (String) comboBox3.getSelectedItem();
                            if(special_feature == "Trailer")
                            {
                                special_feature = "1";
                            } else if (special_feature == "Commentaries") {
                                special_feature = "2";
                            }
                            else if (special_feature == "Deleted Scenes") {
                                special_feature = "3";
                            }
                            else if (special_feature == "Behind the Scenes") {
                                special_feature = "4";
                            }

                            try {
                                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "Bakkies1");
                                PreparedStatement stmt = conn.prepareStatement("INSERT INTO film (title, release_year, description, language_id, original_language_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                                stmt.setString(1, title);
                                stmt.setInt(2,  year);
                                stmt.setString(3, description);
                                stmt.setInt(4, languageNum);
                                stmt.setInt(5, original_languageNum);
                                stmt.setInt(6, rental_duration);
                                stmt.setFloat(7, rental_rate);
                                stmt.setInt(8, length);
                                stmt.setFloat(9, replacement_cost);
                                stmt.setString(10, rating);
                                stmt.setString(11, special_feature);
                                stmt.executeUpdate();
                                Object[][] filmdata = getAllFilms();
                                DefaultTableModel dtm = (DefaultTableModel) table.getModel();
                                dtm.setDataVector(filmdata,columnNames);
                                conn.close();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }

                            // Close the popup
                            insertPopup.dispose();

                        }
                    });
                    insertPanel.add(saveButton);
                    insertPopup.add(insertPanel);
                    insertPopup.pack();
                    insertPopup.setVisible(true);


                //-----------------------------------------------------------------------------------------------------------------------
            }
        });
        add(button, BorderLayout.SOUTH);
    }
    int numOfLanguages = 0;
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
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return arr;
    }
    public String[][] getAllFilms() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String[][] arr = new String[getNumOfFilms()][12];//****************************************************************
        String url = "jdbc:mysql://localhost:3306/sakila";
        String username = "root";
        String password = "Bakkies1";
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);

            stmt = conn.createStatement();
            String SQL = "SELECT * FROM film";
            rs = stmt.executeQuery(SQL);
            int x = 0;
            while (rs.next()) {
                //System.out.println(rs.getString("first_name")+rs.getString("last_name"));
                arr[x][0] = rs.getString("film_id");
                arr[x][1] = rs.getString("title");
                arr[x][2] = rs.getString("release_year");
                arr[x][3] = rs.getString("description");
                arr[x][4] = rs.getString("language_id");
                arr[x][5] = rs.getString("original_language_id");
                arr[x][6] = rs.getString("rental_duration");
                arr[x][7] = rs.getString("rental_rate");
                arr[x][8] = rs.getString("length");
                arr[x][9] = rs.getString("replacement_cost");
                arr[x][10] = rs.getString("rating");
                arr[x][11] = rs.getString("special_features");
                x++;
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return arr;
    }

    public String[] getLanguages()
    {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String[] arr = new String[getNumOfLanguages()+1];//****************************************************************
        String url = "jdbc:mysql://localhost:3306/sakila";
        String username = "root";
        String password = "Bakkies1";
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();
            String SQL = "SELECT * FROM language";
            rs = stmt.executeQuery(SQL);
            int x = 0;
            while (rs.next()) {
                //System.out.println(rs.getString("first_name")+rs.getString("last_name"));
                arr[x] = rs.getString("name");
                x++;
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return arr;
    }
    public int getNumOfLanguages()
    {
        int result = 0;
        Connection conn = null;
        Statement stmt2 = null;
        ResultSet rsCNT = null;
        String url = "jdbc:mysql://localhost:3306/sakila";
        String username = "root";
        String password = "Bakkies1";
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);

            //----------------------------------------------get-result--------------------------------------------------
            stmt2 = conn.createStatement();
            String CNT = "SELECT COUNT(name) FROM language";
            rsCNT = stmt2.executeQuery(CNT);
            if (rsCNT.next()) {
                result = rsCNT.getInt(1);
                return result;
            }

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return result;
    }
    public int getNumOfFilms()
    {
        int result = 0;
        Connection conn = null;
        Statement stmt2 = null;
        ResultSet rsCNT = null;
        String url = "jdbc:mysql://localhost:3306/sakila";
        String username = "root";
        String password = "Bakkies1";
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);

            //----------------------------------------------get-result--------------------------------------------------
            stmt2 = conn.createStatement();
            String CNT = "SELECT COUNT(film_id) FROM film";
            rsCNT = stmt2.executeQuery(CNT);
            if (rsCNT.next()) {
                result = rsCNT.getInt(1);
                return result;
            }

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return result;
    }

}

