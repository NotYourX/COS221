import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import static javax.swing.JOptionPane.showMessageDialog;
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

    DefaultTableModel ClientModel;
        
    JTable ClientTable;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem staffItem;
    JMenuItem filmsItem ;
    JMenuItem reportItem ;
    JMenuItem notificationItem; 
    JTabbedPane tabbedPane;


   public prac4() {
        setTitle("COS221Prac4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 450);
        setLocationRelativeTo(null);

        // Create a menu bar
        // menuBar = new JMenuBar();
        // setJMenuBar(menuBar);

        // // Create a menu with three items
        // menu = new JMenu("Tabs");
        // menuBar.add(menu);

        // staffItem = new JMenuItem("Staff");
        // filmsItem = new JMenuItem("Films");
        // reportItem = new JMenuItem("Reports");
        // notificationItem = new JMenuItem("Notifications");

        // // Add the items to the menu
        // menu.add(staffItem);
        // menu.add(filmsItem);
        // menu.add(reportItem);
        // menu.add(notificationItem);

        // Create a tabbed pane with three tabs
        tabbedPane = new JTabbedPane();
        getContentPane().add(tabbedPane);

        JPanel stafftab = new JPanel();
        JPanel filmtab = new JPanel();
        JPanel reporttab = new JPanel();
        JPanel notificationtab = new JPanel();

        // Add the tabs to the tabbed pane
        tabbedPane.addTab("Staff", stafftab);
        tabbedPane.addTab("Films", filmtab);
        tabbedPane.addTab("Reports", reporttab);
        tabbedPane.addTab("Notifications", notificationtab);

        // Add action listeners to the menu items
        // staffItem.addActionListener(e -> tabbedPane.setSelectedIndex(0));
        // filmsItem.addActionListener(e -> tabbedPane.setSelectedIndex(1));
        // reportItem.addActionListener(e -> tabbedPane.setSelectedIndex(2));
        // notificationItem.addActionListener(e -> tabbedPane.setSelectedIndex(3));


        //----------------------------------------------------TAB 1 (Staff)----------------------------------------------------------------------------------------------------
        String[] StaffCol = {"first_name", "last_name", "address", "address2", "district", "city", "postal_code", "phone", "store_id", "active", "last_update"};
        Object[][] staffData = getAllStaff();
        DefaultTableModel model = new DefaultTableModel(staffData, StaffCol);
        JTable table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(950, 100));
        JScrollPane scrollPane = new JScrollPane(table);
        stafftab.add(scrollPane);
        // table = new JTable(model);
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);
        // stafftab.add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Filter");
        panel.add(label, BorderLayout.WEST);
        JTextField filterText = new JTextField("");
        filterText.setPreferredSize(new Dimension(100, 30)); 
        panel.add(filterText, BorderLayout.CENTER);
        stafftab.add(panel, BorderLayout.NORTH);
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
        stafftab.add(button, BorderLayout.SOUTH);


        //----------------------------------------------------TAB 2 (films)--------------------------------------------------------------------------------------------------
        String[] FilmCol = {"film_id","title","release_year", "description", "language", "original_language", "rental_duration", "rental_rate", "length", "replacement_cost", "rating", "special_feature"};
        Object[][] filmData = getAllFilms();
        DefaultTableModel filmModel = new DefaultTableModel(filmData, FilmCol);
        JTable filmtable = new JTable(filmModel);
        filmtable.setPreferredScrollableViewportSize(new Dimension(950, 300));
        JScrollPane scrollPaneFil = new JScrollPane(filmtable);
        filmtab.add(scrollPaneFil);
        setVisible(true);
        getLanguages();

        JButton filmAdd = new JButton("Add Record");
        filmAdd.addActionListener(new ActionListener() {
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
                                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/u22699572_u22528424_sakila", "root", "X1dr31n1");

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
                                stmt.execute();
                                Object[][] filmdata = getAllFilms();
                                DefaultTableModel dtm = (DefaultTableModel) table.getModel();
                                dtm.setDataVector(filmdata,FilmCol);
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
        filmtab.add(filmAdd, BorderLayout.SOUTH);

        // ------------------------------------------------------- Reports
        String[] repCol = {"Store_id", "Genre", "Number of Films"};

        JPanel topPanel = new JPanel();
        JPanel bttmPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
      
        
        DefaultTableModel repModel = new DefaultTableModel(new String[0][0], repCol);
        JTable repTable = new JTable(repModel);
        repTable.setPreferredScrollableViewportSize(new Dimension(950, 300));
        JScrollPane scrollPaneRep = new JScrollPane(repTable);
        topPanel.add(scrollPaneRep);
        setVisible(true);

        JButton genRep = new JButton("Generate Report");
        genRep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Object[][] repData = getReport();
                DefaultTableModel dtm = (DefaultTableModel) repTable.getModel();
                dtm.setDataVector(repData, repCol);
            }
        });
        bttmPanel.add(genRep, BorderLayout.SOUTH);

        reporttab.add(topPanel, BorderLayout.NORTH);
        reporttab.add(bttmPanel,BorderLayout.SOUTH);

        // -------------------------------------------------------------------- Tab 4 Notifications ---------------------------------------------------------------------
    
        String[] columnNames = {"Customer_id", "First Name", "Last Name","Email", "Address", "City", "Phone", "store_id", "Active"};
      
        JPanel topPanelN = new JPanel();
        JPanel bttmPanelN = new JPanel(new FlowLayout(FlowLayout.CENTER));

        
        DefaultTableModel ClientModel = new DefaultTableModel(new String[0][0],columnNames);
        
        JTable ClientTable = new JTable(ClientModel);
        ClientTable.setPreferredScrollableViewportSize(new Dimension(950, 300));
        JScrollPane scrollPaneClient = new JScrollPane(ClientTable);
        topPanelN.add(scrollPaneClient);
        setVisible(true);

        JButton buttonSeeAllClients = new JButton("Get all clients");
        buttonSeeAllClients.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object[][] data = getAllClients();
                DefaultTableModel dtm = (DefaultTableModel) ClientTable.getModel();
                dtm.setDataVector(data, columnNames);
            }
        });
        bttmPanelN.add(buttonSeeAllClients);

        JButton buttonAddclients = new JButton("Add a new client");
        buttonAddclients.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addClient();
            }
        });
        bttmPanelN.add(buttonAddclients);

        JButton buttonUpdateclient = new JButton("Update a clients info");
        buttonUpdateclient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UpdateClient();
            }
        });
        bttmPanelN.add(buttonUpdateclient);

        JButton buttonDeleteclient = new JButton("Delete a client");
        buttonDeleteclient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DeleteClient();
            }
        });
        bttmPanelN.add(buttonDeleteclient);

        JButton buttonDropclient = new JButton("Drop inactive clients");
        buttonDropclient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DropClient();
            }
        });
        bttmPanelN.add(buttonDropclient);

        JButton buttonInactiveAllClients = new JButton("Past clients");
        buttonInactiveAllClients.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object[][] data = PastClients();
                DefaultTableModel dtm = (DefaultTableModel) ClientTable.getModel();
                dtm.setDataVector(data, columnNames);
            }
        });
        bttmPanelN.add(buttonInactiveAllClients);

        notificationtab.add(topPanelN, BorderLayout.NORTH);
        notificationtab.add(bttmPanelN,BorderLayout.SOUTH);
    }

    int numOfLanguages = 0;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new prac4().setVisible(true));
    }

    public String[][] PastClients(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String[][] arr = new String[2][12];
        String url = "jdbc:mysql://localhost:3306/u22699572_u22528424_sakila";
        String username = "root";
        String password = "X1dr31n1";
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);

            stmt = conn.createStatement();
            String SQL = "SELECT c.customer_id, c.first_name, c.last_name, c.email, a.address, ci.city, a.phone, c.store_id, c.active"+
            " FROM inactiveclient AS c INNER JOIN address AS a ON c.address_id = a.address_id INNER JOIN city AS ci ON a.city_id = ci.city_id";
            rs = stmt.executeQuery(SQL);
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
            }

            arr = new String[rowCount][9];

            stmt = conn.createStatement();
            SQL = "SELECT c.customer_id, c.first_name, c.last_name,c.email, a.address, ci.city, a.phone, c.store_id, c.active"+
            " FROM inactiveclient AS c INNER JOIN address AS a ON c.address_id = a.address_id INNER JOIN city AS ci ON a.city_id = ci.city_id";
            rs = stmt.executeQuery(SQL);
            int x = 0;
            while (rs.next()) {
                //System.out.println(rs.getString("first_name")+rs.getString("last_name"));
                arr[x][0] = rs.getString("c.customer_id");
                arr[x][1] = rs.getString("c.first_name");
                arr[x][2] = rs.getString("c.last_name");
                arr[x][3] = rs.getString("c.email");
                arr[x][4] = rs.getString("a.address");
                arr[x][5] = rs.getString("ci.city");
                arr[x][6] = rs.getString("a.phone");
                arr[x][7] = rs.getString("c.store_id");
                if(rs.getString("c.active").equals("1")){
                    arr[x][8] = "yes";
                }
                else{
                    arr[x][8] = "no";
                }                
                x++;
                
            }
            // conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return arr;
    }

    public void DropClient(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/u22699572_u22528424_sakila", "root", "X1dr31n1");
            String SQL ="ALTER TABLE payment DROP CONSTRAINT fk_payment_customer;";
            PreparedStatement stmt1 = conn.prepareStatement(SQL);
            // stmt1.executeUpdate();

            SQL ="ALTER TABLE rental DROP CONSTRAINT fk_rental_customer;";
            stmt1 = conn.prepareStatement(SQL);
            // stmt1.executeUpdate();

            SQL = "CREATE TABLE IF NOT EXISTS inactiveClient (customer_id smallint(5),store_id tinyint(3),first_name varchar(45),last_name varchar(45), email varchar(50),address_id smallint(5),active tinyint(1), create_date datetime, last_update timestamp, PRIMARY KEY(customer_id),FOREIGN KEY (address_id) REFERENCES  address(address_id),FOREIGN KEY (store_id) REFERENCES  store(store_id));";
            stmt1 = conn.prepareStatement(SQL);
            stmt1.executeUpdate();

            SQL = "INSERT INTO inactiveClient(customer_id,store_id,first_name,last_name, email,address_id,active,create_date, last_update) (SELECT customer_id,store_id,first_name,last_name, email,address_id,active,create_date, last_update FROM customer Where active = 0);";
            stmt1 = conn.prepareStatement(SQL);
            stmt1.executeUpdate();

            stmt1 = conn.prepareStatement("DELETE  FROM customer Where active=0");
            stmt1.execute();
            

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }    
    }

    private void DeleteClient(){
        JDialog insertPopup = new JDialog();
        JPanel insertPanel = new JPanel(new GridLayout(0, 2));
        insertPanel.add(new JLabel("Search by Client ID:"));
        JTextField clientID = new JTextField();
        insertPanel.add(clientID);
        JButton SearchUser = new JButton("Delete the user");
        SearchUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/u22699572_u22528424_sakila", "root", "X1dr31n1");
                    PreparedStatement stmt1 = conn.prepareStatement("DELETE  FROM customer Where customer_id=" + clientID.getText());
                    stmt1.execute();
                    
                    insertPopup.dispose();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }

            }

        });

        insertPanel.add(SearchUser);
        insertPopup.add(insertPanel);
        insertPopup.pack();
        insertPopup.setVisible(true);
    }

    public void UpdateClient(){
        JDialog insertPopup = new JDialog();
        JPanel insertPanel = new JPanel(new GridLayout(0, 2));
        insertPanel.add(new JLabel("Search by Client ID:"));
        JTextField clientID = new JTextField();
        insertPanel.add(clientID);
        JButton SearchUser = new JButton("Search for user");
        SearchUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fName;
                String lName;
                String email;
                int addressid;
                String address;
                String address2;
                String district;
                int cityid = -1;
                String city;
                int active;
                String posCod;
                String phone;
                int idnum;

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/u22699572_u22528424_sakila", "root", "X1dr31n1");
                    Statement stmt1 = conn.createStatement();
                    String SQL = "SELECT * FROM customer Where customer_id=" + clientID.getText();
                    ResultSet rs = stmt1.executeQuery(SQL);
                    if (rs.next()) {
                        fName = rs.getString("first_name");
                        lName = rs.getString("last_name");
                        email = rs.getString("email");
                        addressid = rs.getInt("address_id");
                        active = rs.getInt("active");
                        idnum = Integer.parseInt(clientID.getText());
                        stmt1 = conn.createStatement();
                        SQL = "SELECT * FROM address Where address_id = " + addressid;
                        rs = stmt1.executeQuery(SQL);
                        if (rs.next()) {
                            address = rs.getString("address");
                            address2 = rs.getString("address2");
                            district = rs.getString("district");
                            cityid = rs.getInt("city_id");
                            posCod = rs.getString("postal_code");
                            phone = rs.getString("phone");
                            

                            if(cityid == -1){
                                JOptionPane.showMessageDialog(null, "Please select a valid city name");
                                return;
                            }

                            stmt1 = conn.createStatement();
                            SQL = "SELECT * FROM city Where city_id = " + cityid;
                            rs = stmt1.executeQuery(SQL);
                            if (rs.next()) {
                                city = rs.getString("city");
                                JDialog insertPopup2 = new JDialog();
                                JPanel insertPanel2 = new JPanel(new GridLayout(0, 2));
                                insertPanel2.add(new JLabel("First Name:"));
                                JTextField fNameF = new JTextField(fName);
                                insertPanel2.add(fNameF);
                                insertPanel2.add(new JLabel("Last Name:"));
                                JTextField lNameF = new JTextField(lName);
                                insertPanel2.add(lNameF);
                                insertPanel2.add(new JLabel("Email:"));
                                JTextField emailF = new JTextField(email);
                                insertPanel2.add(emailF);
                                insertPanel2.add(new JLabel("Active:"));
                                JTextField activeF = new JTextField(String.valueOf(active));
                                insertPanel2.add(activeF);
                                insertPanel2.add(new JLabel("Address:"));
                                JTextField addressF = new JTextField(address);
                                insertPanel2.add(addressF);
                                insertPanel2.add(new JLabel("Address 2:"));
                                JTextField address2F = new JTextField(address2);
                                insertPanel2.add(address2F);
                                insertPanel2.add(new JLabel("Distrit:"));
                                JTextField districtF = new JTextField(district);
                                insertPanel2.add(districtF);
                                // drop down
                                insertPanel2.add(new JLabel("City:"));
                                JComboBox<String> cityF = new JComboBox<>();
                                String[] lang = getCities();
                                for (int i = 0; i < lang.length; i++) {
                                    cityF.addItem(lang[i]);
                                }
                                cityF.setSelectedItem(city);
                                insertPanel2.add(cityF);
                                insertPanel2.add(new JLabel("Postal Code:"));
                                JTextField posCodeF = new JTextField(posCod);
                                insertPanel2.add(posCodeF);
                                insertPanel2.add(new JLabel("Phone number:"));
                                JTextField phoneF = new JTextField(phone);
                                insertPanel2.add(phoneF);
                                JButton saveButton = new JButton("Add Record");
                                insertPanel2.add(new JLabel("address id"));
                                JLabel addressidF = new JLabel(String.valueOf(addressid));
                                insertPanel2.add(phoneF);
                                saveButton.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        String fName = fNameF.getText();
                                        String lName = lNameF.getText();
                                        String email = emailF.getText();
                                        String address = addressF.getText();
                                        String address2 = address2F.getText();
                                        String district = districtF.getText();
                                        int active = Integer.parseInt(activeF.getText());
                                        int cityid = -1;
                                        int addressid = Integer.parseInt(addressidF.getText());
                                        String city = (String) cityF.getSelectedItem();
                                        try {
                                            Class.forName("com.mysql.cj.jdbc.Driver");
                                            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/u22699572_u22528424_sakila", "root", "X1dr31n1");
                                            Statement stmt1 = conn.createStatement();
                                            String SQL = "SELECT * FROM city Where city = '" + city +"'";
                                            ResultSet rs = stmt1.executeQuery(SQL);
                                            if (rs.next()) {
                                                cityid = rs.getInt("city_id");
                                            }
                                            if(cityid == -1){
                                                JOptionPane.showMessageDialog(null, "Please select a valid city name");
                                                return;
                                            }
                                            

                                        } catch (SQLException ex) {
                                            ex.printStackTrace();
                                        } catch (ClassNotFoundException ex) {
                                            ex.printStackTrace();
                                        }
                                        
                                        String posCode = posCodeF.getText();
                                        String phone = phoneF.getText();

                                        if(fName.equals("")){
                                            JOptionPane.showMessageDialog(null, "Please insert the client's first name before trying to insert into the database");
                                            return;
                                        }
                                        if(lName.equals("")){
                                            JOptionPane.showMessageDialog(null, "Please insert the client's last name before trying to insert into the database");
                                            return;
                                        }
                                        if(email.equals("")){
                                            JOptionPane.showMessageDialog(null, "Please insert the client's email before trying to insert into the database");
                                            return; 
                                        }

                                        if(address.equals("")){
                                            JOptionPane.showMessageDialog(null, "Please insert the client's address before trying to insert into the database");
                                            return;  
                                        }
                                        if(address2.equals("")){
                                            // can be empty
                                        }
                                        if(district.equals("")){
                                            JOptionPane.showMessageDialog(null, "Please insert the client's district before trying to insert into the database");
                                            return; 
                                        }
                                        if(posCode.equals("")){
                                            JOptionPane.showMessageDialog(null, "Please insert the client's postal code before trying to insert into the database");
                                            return; 
                                        }

                                        if(phone.equals("")){
                                            JOptionPane.showMessageDialog(null, "Please insert the client's phone number before trying to insert into the database");
                                            return; 
                                        }

                                        if(cityid == -1){
                                            JOptionPane.showMessageDialog(null, "Please select the client's city before trying to insert into the database");
                                            return;   
                                        }

                                    
                                        try {
                                            // insert address
                                            PreparedStatement stmt = conn.prepareStatement("UPDATE address SET address = ?, address2 = ?, district = ?,city_id = ?, postal_code = ?,phone = ?, last_update = NOW() WHERE address_id = ?");
                                            stmt.setString(1, address);
                                            stmt.setString(2,  address2);
                                            stmt.setString(3, district);
                                            stmt.setInt(4, cityid);
                                            stmt.setString(5, posCode);
                                            stmt.setString(6, phone);
                                            stmt.setInt(7, addressid);
                                            stmt.executeUpdate();

                                            // insert user
                                            stmt = conn.prepareStatement("UPDATE customer SET first_name = ?, last_name = ?, email = ?, active = ?, last_update = NOW() where customer_id = "+ idnum);
                                            stmt.setString(1, fName);
                                            stmt.setString(2,  lName);
                                            stmt.setString(3, email);
                                            stmt.setInt(4, active);
                                            stmt.executeUpdate();
                                        } catch (SQLException ex) {
                                            ex.printStackTrace();
                                        }

                                        // Close the popup
                                        insertPopup2.dispose();
                                    }
                                });
                                insertPanel2.add(saveButton);
                                insertPopup2.add(insertPanel2);
                                insertPopup2.pack();
                                insertPopup2.setVisible(true);
                            
                                // Close the popup
                                insertPopup.dispose();
                                // conn.close();
                            }
                            

                        }
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }

            }

        });

        insertPanel.add(SearchUser);
        insertPopup.add(insertPanel);
        insertPopup.pack();
        insertPopup.setVisible(true);
       
    }


    public void addClient(){
        JDialog insertPopup = new JDialog();
        JPanel insertPanel = new JPanel(new GridLayout(0, 2));
        insertPanel.add(new JLabel("First Name:"));
        JTextField fNameF = new JTextField();
        insertPanel.add(fNameF);
        insertPanel.add(new JLabel("Last Name:"));
        JTextField lNameF = new JTextField();
        insertPanel.add(lNameF);
        insertPanel.add(new JLabel("Email:"));
        JTextField emailF = new JTextField();
        insertPanel.add(emailF);
        insertPanel.add(new JLabel("Address:"));
        JTextField addressF = new JTextField();
        insertPanel.add(addressF);
        insertPanel.add(new JLabel("Address 2:"));
        JTextField address2F = new JTextField();
        insertPanel.add(address2F);
        insertPanel.add(new JLabel("Distrit:"));
        JTextField districtF = new JTextField();
        insertPanel.add(districtF);
        // drop down
        insertPanel.add(new JLabel("City:"));
        JComboBox<String> cityF = new JComboBox<>();
        String[] lang = getCities();
        for (int i = 0; i < lang.length; i++) {
            cityF.addItem(lang[i]);
        }
        insertPanel.add(cityF);
        insertPanel.add(new JLabel("Postal Code:"));
        JTextField posCodeF = new JTextField();
        insertPanel.add(posCodeF);
        insertPanel.add(new JLabel("Phone number:"));
        JTextField phoneF = new JTextField();
        insertPanel.add(phoneF);
        JButton saveButton = new JButton("Add Record");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fName = fNameF.getText();
                String lName = lNameF.getText();
                String email = emailF.getText();
                String address = addressF.getText();
                String address2 = address2F.getText();
                String district = districtF.getText();
                int cityid = -1;
                String city = (String) cityF.getSelectedItem();
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/u22699572_u22528424_sakila", "root", "X1dr31n1");
                    Statement stmt1 = conn.createStatement();
                    String SQL = "SELECT * FROM city Where city = '" + city +"'";
                    ResultSet rs = stmt1.executeQuery(SQL);
                    int x = 0;
                    if (rs.next()) {
                        cityid = rs.getInt("city_id");
                    }
                    if(cityid == -1){
                        JOptionPane.showMessageDialog(null, "Please select a valid city name");
                        return;
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                
                String posCode = posCodeF.getText();
                String phone = phoneF.getText();

                if(fName.equals("")){
                    JOptionPane.showMessageDialog(null, "Please insert the client's first name before trying to insert into the database");
                    return;
                }
                if(lName.equals("")){
                    JOptionPane.showMessageDialog(null, "Please insert the client's last name before trying to insert into the database");
                    return;
                }
                if(email.equals("")){
                    JOptionPane.showMessageDialog(null, "Please insert the client's email before trying to insert into the database");
                    return; 
                }

                if(address.equals("")){
                    JOptionPane.showMessageDialog(null, "Please insert the client's address before trying to insert into the database");
                    return;  
                }
                if(address2.equals("")){
                    // can be empty
                }
                if(district.equals("")){
                    JOptionPane.showMessageDialog(null, "Please insert the client's district before trying to insert into the database");
                    return; 
                }
                if(posCode.equals("")){
                    JOptionPane.showMessageDialog(null, "Please insert the client's postal code before trying to insert into the database");
                    return; 
                }

                if(phone.equals("")){
                    JOptionPane.showMessageDialog(null, "Please insert the client's phone number before trying to insert into the database");
                    return; 
                }

                if(cityid == -1){
                    JOptionPane.showMessageDialog(null, "Please select the client's city before trying to insert into the database");
                    return;   
                }

                // check if user exists
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/u22699572_u22528424_sakila", "root", "X1dr31n1");
                    Statement stmt1 = conn.createStatement();
                    String SQL = "SELECT * FROM customer Where email = '" + email + "'";
                    ResultSet rs = stmt1.executeQuery(SQL);
                    int x = 0;
                    while (rs.next()) {
                        x++;
                    }
                    if(x > 0){
                        JOptionPane.showMessageDialog(null, "This user already exists");
                        return;
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }


                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/u22699572_u22528424_sakila", "root", "X1dr31n1");
                    // insert address
                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO  address(address,address2, district,city_id, postal_code,phone, last_update) VALUES (?, ?, ?, ?, ?, ?, NOW())");
                    stmt.setString(1, address);
                    stmt.setString(2,  address2);
                    stmt.setString(3, district);
                    stmt.setInt(4, cityid);
                    stmt.setString(5, posCode);
                    stmt.setString(6, phone);
                    stmt.executeUpdate();

                    Statement stmt1 = conn.createStatement();
                    String SQL = "SELECT * FROM address Where address = '" + address + "' AND district ='" + district +  "' AND city_id =" + cityid;
                    ResultSet rs = stmt1.executeQuery(SQL);
                    int x = 0;
                    int addressid = 0;
                    while (rs.next()) {
                        addressid = rs.getInt("address_id");
                    }
                    if(x > 0){
                        JOptionPane.showMessageDialog(null, "This user already exists");
                        return;
                    }

                    // insert user
                    stmt = conn.prepareStatement("INSERT INTO customer (store_id, first_name, last_name, email, address_id, active, create_date, last_update)"+
                    " VALUES (1, ?, ?, ?, ?, 1, NOW(), NOW())");
                    stmt.setString(1, fName);
                    stmt.setString(2,  lName);
                    stmt.setString(3, email);
                    stmt.setInt(4, addressid);
                    stmt.executeUpdate();
                    // conn.close();
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
                
    }

    public String[] getCities()
    {
        Connection conn = null;
        Statement stmt = null;
        Statement stmt2 = null;

        ResultSet rs = null;
        ResultSet rsCNT = null;
        
        String[] arr = new String[0];
        String url = "jdbc:mysql://localhost:3306/u22699572_u22528424_sakila";
        String username = "root";
        String password = "X1dr31n1";
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);

            int result = 0;
            stmt2 = conn.createStatement();
            String CNT = "SELECT * FROM city";
            rsCNT = stmt2.executeQuery(CNT);
            while (rsCNT.next()) {
                result++;
                
            }

            System.out.println(result);

            arr = new String[result];

            stmt = conn.createStatement();
            String SQL = "SELECT * FROM city";
            rs = stmt.executeQuery(SQL);
            int x = 0;
            while (x < result) {
                //System.out.println(rs.getString("first_name")+rs.getString("last_name"));
                rs.next();
                arr[x] = rs.getString("city");
                x++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return arr;
    }

    
    public String[][] getAllClients() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String[][] arr = new String[2][12];
        String url = "jdbc:mysql://localhost:3306/u22699572_u22528424_sakila";
        String username = "root";
        String password = "X1dr31n1";
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);

            stmt = conn.createStatement();
            String SQL = "SELECT c.customer_id, c.first_name, c.last_name, c.email, a.address, ci.city, a.phone, c.store_id, c.active"+
            " FROM customer AS c INNER JOIN address AS a ON c.address_id = a.address_id INNER JOIN city AS ci ON a.city_id = ci.city_id";
            rs = stmt.executeQuery(SQL);
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
            }

            arr = new String[rowCount][9];

            stmt = conn.createStatement();
            SQL = "SELECT c.customer_id, c.first_name, c.last_name,c.email, a.address, ci.city, a.phone, c.store_id, c.active"+
            " FROM customer AS c INNER JOIN address AS a ON c.address_id = a.address_id INNER JOIN city AS ci ON a.city_id = ci.city_id";
            rs = stmt.executeQuery(SQL);
            int x = 0;
            while (rs.next()) {
                //System.out.println(rs.getString("first_name")+rs.getString("last_name"));
                arr[x][0] = rs.getString("c.customer_id");
                arr[x][1] = rs.getString("c.first_name");
                arr[x][2] = rs.getString("c.last_name");
                arr[x][3] = rs.getString("c.email");
                arr[x][4] = rs.getString("a.address");
                arr[x][5] = rs.getString("ci.city");
                arr[x][6] = rs.getString("a.phone");
                arr[x][7] = rs.getString("c.store_id");
                if(rs.getString("c.active").equals("1")){
                    arr[x][8] = "yes";
                }
                else{
                    arr[x][8] = "no";
                }                
                x++;
                
            }
            // conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return arr;
    }

    public String[][] getAllStaff() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String[][] arr = new String[2][12];
        String url = "jdbc:mysql://localhost:3306/u22699572_u22528424_sakila";
        String username = "root";
        String password = "X1dr31n1";
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
    public String[][] getAllFilms() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String[][] arr = new String[1010][12];
        String url = "jdbc:mysql://localhost:3306/u22699572_u22528424_sakila";
        String username = "root";
        String password = "X1dr31n1";
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return arr;
    }

    public String[][] getReport() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String[][] arr = new String[1][3];
        String url = "jdbc:mysql://localhost:3306/u22699572_u22528424_sakila";
        String username = "root";
        String password = "X1dr31n1";
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);

            stmt = conn.createStatement();
            String SQL = "SELECT i.store_id, c.name, COUNT(i.inventory_id) AS NumFilms"+
            " FROM inventory AS i INNER JOIN film_category AS fc ON i.film_id = fc.film_id INNER JOIN category AS c ON fc.category_id = c.category_id " +
            " GROUP BY i.store_id, c.name";
            rs = stmt.executeQuery(SQL);
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
            }

            stmt = conn.createStatement();
            SQL = "SELECT i.store_id, c.name, COUNT(i.inventory_id) AS NumFilms"+
            " FROM inventory AS i INNER JOIN film_category AS fc ON i.film_id = fc.film_id INNER JOIN category AS c ON fc.category_id = c.category_id " +
            " GROUP BY i.store_id, c.name";
            rs = stmt.executeQuery(SQL);
            arr = new String[rowCount][3];
            int x = 0;
            while (rs.next()) {
                //System.out.println(rs.getString("first_name")+rs.getString("last_name"));
                arr[x][0] = rs.getString("i.store_id");
                arr[x][1] = rs.getString("c.name");
                arr[x][2] = rs.getString("NumFilms");
                x++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return arr;
    }

    public int getNumOfFilms()
    {
        int result = 0;
        Connection conn = null;
        Statement stmt2 = null;
        ResultSet rsCNT = null;
        String url = "jdbc:mysql://localhost:3306/u22699572_u22528424_sakila";
        String username = "root";
        String password = "X1dr31n1";
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

    public String[] getLanguages()
    {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String[] arr = new String[getNumOfLanguages()+1];//****************************************************************
        String url = "jdbc:mysql://localhost:3306/u22699572_u22528424_sakila";
        String username = "root";
        String password = "X1dr31n1";
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
        String url = "jdbc:mysql://localhost:3306/u22699572_u22528424_sakila";
        String username = "root";
        String password = "X1dr31n1";
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
}

