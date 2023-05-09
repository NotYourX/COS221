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
        String[] columnNames = {"film_id", "title","release_year", "description", "language", "original_language", "rental_duration", "rental_rate", "length", "replacement_cost", "rating", "special_feature"};

        Object[][] data = getAllFilms();
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(950, 300));
        JScrollPane scrollPane = new JScrollPane(table);
        tab1.add(scrollPane);
        setVisible(true);

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
                    JTextField languageField = new JTextField();
                    insertPanel.add(languageField);
                    insertPanel.add(new JLabel("original_language:"));
                    JTextField original_languageField = new JTextField();
                    insertPanel.add(original_languageField);
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
                    JTextField special_featureField = new JTextField();
                    insertPanel.add(special_featureField);
                    JButton saveButton = new JButton("Add Record");
                    saveButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            String title = titleField.getText();
                            //-----------------------------------------help
                            int year = Integer.parseInt(release_dateField.getText());
                            /*Date year = toDate(release_date);

                            String year1 = release_dateField.getText();
                            Year year = Year.parse(year1);
                            System.out.println(year);*/
                            //---------------------------------------------
                            String description = descriptionField.getText();
                            String language = languageField.getText();
                            String original_language = original_languageField.getText();
                            int rental_duration = Integer.parseInt(rental_durationField.getText());
                            float rental_rate = Float.parseFloat(rental_rateField.getText());
                            int length = Integer.parseInt(lengthField.getText());
                            String rating = ratingField.getText();
                            Float replacement_cost = Float.parseFloat(replacement_costField.getText());
                            String special_feature = special_featureField.getText();
                            
                            try {
                                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "Bakkies1");
                                PreparedStatement stmt = conn.prepareStatement("INSERT INTO film (title, release_year, description, language_id, original_language_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                                stmt.setString(1, title);
                                stmt.setInt(2,  year);
                                stmt.setString(3, description);
                                stmt.setString(4, language);
                                stmt.setString(5, original_language);
                                stmt.setInt(6, rental_duration);
                                stmt.setFloat(7, rental_rate);
                                stmt.setInt(8, length);
                                stmt.setString(9, rating);
                                stmt.setFloat(10, replacement_cost);
                                stmt.setString(11, special_feature);
                                stmt.executeUpdate();
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

public String[][] getAllFilms() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String[][] arr = new String[1010][12];
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return arr;
    }
