package testeTexto;

import java.awt.CardLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class LoomlaUI extends javax.swing.JFrame {
	
	public static String currentLang;
	public static String secondLang;
	public static boolean[] ordemPalavra = new boolean[3];
	public static boolean[] ordemTexto = new boolean[3];
	public static User loggedUser;
	
    public LoomlaUI() {
        initComponents();
        for(int i=0; i < 3; i++){
        	ordemPalavra[i] = true;
        	ordemTexto[i] = true;
        }
    }
    
    @SuppressWarnings({ "unchecked", "serial" })
    private void initComponents() {

        FileChooser = new javax.swing.JFileChooser();
        mainPanel = new javax.swing.JPanel();
        LoginPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        LoginText = new javax.swing.JTextField();
        PasswordField = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        LogButton = new javax.swing.JButton();
        SignUpButton = new javax.swing.JButton();
        ErrorMessage = new javax.swing.JLabel();
        User = new javax.swing.JPanel();
        Buttons = new javax.swing.JPanel();
        UserPanel = new javax.swing.JPanel();
        UserButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        ReadButton = new javax.swing.JButton();
        LogOutButton = new javax.swing.JButton();
        PalavrasProcuradas = new javax.swing.JScrollPane();
        TabelaPalavras = new javax.swing.JTable();
        TextosLidos = new javax.swing.JScrollPane();
        TabelaTextos = new javax.swing.JTable();
        PalavrasTitle = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        TextosTitle = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        Fluencia = new javax.swing.JPanel();
        Fluency = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        Read = new javax.swing.JPanel();
        Buttons2 = new javax.swing.JPanel();
        ReadPanel = new javax.swing.JPanel();
        ReadButton2 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        UserButton2 = new javax.swing.JButton();
        LogOutButton2 = new javax.swing.JButton();
        SugeridosContainer = new javax.swing.JScrollPane();
        TextChooser = new javax.swing.JList<>();
        OpenButton = new javax.swing.JButton();
        SearchButton = new javax.swing.JButton();
        TextContainer = new javax.swing.JScrollPane();
        Text = new javax.swing.JTextArea();
        SugeridosTitle = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        CurrentLan = new javax.swing.JComboBox<>();
        SecondLan = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setLayout(new java.awt.CardLayout());

        LoginPanel.setBackground(new java.awt.Color(225, 225, 225));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24));
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Username:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24));
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Password:");

        LoginText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        LoginText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                LoginTextFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                LoginTextFocusLost(evt);
            }
        });
        LoginText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginTextActionPerformed(evt);
            }
        });

        PasswordField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        PasswordField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                PasswordFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                PasswordFieldFocusLost(evt);
            }
        });
        PasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasswordFieldActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon("LoomlaLogo.png"));
        jLabel3.setText("jLabel3");

        LogButton.setBackground(new java.awt.Color(255, 255, 255));
        LogButton.setText("Log in");
        LogButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        LogButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        LogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogButtonActionPerformed(evt);
            }
        });

        SignUpButton.setBackground(new java.awt.Color(255, 255, 255));
        SignUpButton.setText("Sign up");
        SignUpButton.setToolTipText("Type your desired username and password.");
        SignUpButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        ErrorMessage.setDisplayedMnemonic('h');
        ErrorMessage.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ErrorMessage.setForeground(new java.awt.Color(204, 0, 0));

        javax.swing.GroupLayout LoginPanelLayout = new javax.swing.GroupLayout(LoginPanel);
        LoginPanel.setLayout(LoginPanelLayout);
        LoginPanelLayout.setHorizontalGroup(
            LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginPanelLayout.createSequentialGroup()
                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LoginPanelLayout.createSequentialGroup()
                        .addGap(189, 189, 189)
                        .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addGroup(LoginPanelLayout.createSequentialGroup()
                                .addComponent(LogButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addComponent(SignUpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(LoginPanelLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(10, 10, 10)
                                .addComponent(PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(LoginPanelLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(10, 10, 10)
                                .addComponent(LoginText, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(LoginPanelLayout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(ErrorMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(LoginPanelLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(138, Short.MAX_VALUE))
        );

        LoginPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {LoginText, PasswordField});

        LoginPanelLayout.setVerticalGroup(
            LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginPanelLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LoginText, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ErrorMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LogButton, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SignUpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(119, Short.MAX_VALUE))
        );

        LoginPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {LoginText, PasswordField});

        mainPanel.add(LoginPanel, "Login");

        User.setBackground(new java.awt.Color(204, 255, 204));
        User.setToolTipText("");

        Buttons.setBackground(new java.awt.Color(51, 153, 0));

        UserPanel.setBackground(new java.awt.Color(255, 153, 0));

        UserButton.setBackground(new java.awt.Color(0, 153, 0));
        UserButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        UserButton.setForeground(new java.awt.Color(255, 255, 255));
        UserButton.setText("User");
        UserButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        UserButton.setContentAreaFilled(false);

        javax.swing.GroupLayout UserPanelLayout = new javax.swing.GroupLayout(UserPanel);
        UserPanel.setLayout(UserPanelLayout);
        UserPanelLayout.setHorizontalGroup(
            UserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UserPanelLayout.createSequentialGroup()
                .addComponent(UserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        UserPanelLayout.setVerticalGroup(
            UserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UserPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(UserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel7.setIcon(new javax.swing.ImageIcon("UserIcon.png")); // NOI18N

        ReadButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ReadButton.setForeground(new java.awt.Color(255, 255, 255));
        ReadButton.setText("Read");
        ReadButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ReadButton.setContentAreaFilled(false);
        ReadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReadButtonActionPerformed(evt);
            }
        });

        LogOutButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        LogOutButton.setForeground(new java.awt.Color(255, 255, 255));
        LogOutButton.setText("Log Out");
        LogOutButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 0)));
        LogOutButton.setContentAreaFilled(false);
        LogOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogOutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ButtonsLayout = new javax.swing.GroupLayout(Buttons);
        Buttons.setLayout(ButtonsLayout);
        ButtonsLayout.setHorizontalGroup(
            ButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ButtonsLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(UserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ReadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 350, Short.MAX_VALUE)
                .addComponent(LogOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        ButtonsLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {LogOutButton, ReadButton});

        ButtonsLayout.setVerticalGroup(
            ButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(UserPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(ButtonsLayout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(ButtonsLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(ButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ReadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LogOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        TabelaPalavras.setBackground(new java.awt.Color(204, 255, 204));
        JTableHeader header = TabelaPalavras.getTableHeader();
        header.setReorderingAllowed(false);
        header.setBackground(new java.awt.Color(51, 153, 0));
        TabelaPalavras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            	{null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Palavra", "Dificuldade", "Vezes"
            }
        ) {

			@SuppressWarnings("rawtypes")
			Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            @SuppressWarnings("rawtypes")
			public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TabelaPalavras.setSelectionBackground(new java.awt.Color(51, 204, 0));
        TabelaPalavras.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter(){
	        public void mouseReleased(java.awt.event.MouseEvent evt) {
	        	TabelaPalavrasMouseClicked(evt);
	        }
        });
   
        PalavrasProcuradas.setViewportView(TabelaPalavras);

        TabelaTextos.setBackground(new java.awt.Color(204, 255, 204));
        JTableHeader header2 = TabelaTextos.getTableHeader();
        header2.setReorderingAllowed(false);
        header2.setBackground(new java.awt.Color(51, 153, 0));
        TabelaTextos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Título", "Data", "Entendimento"
            }
        ) {
            @SuppressWarnings("rawtypes")
			Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            @SuppressWarnings("rawtypes")
			public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TabelaTextos.setSelectionBackground(new java.awt.Color(51, 204, 0));
        TabelaTextos.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter(){
	        public void mouseReleased(java.awt.event.MouseEvent evt) {
	        	TabelaTextosMouseClicked(evt);
	        }
        });
        TextosLidos.setViewportView(TabelaTextos);

        PalavrasTitle.setBackground(new java.awt.Color(153, 153, 153));

        jLabel8.setFont(new java.awt.Font("Bookman Old Style", 1, 18)); 
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Palavras mais procuradas");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout PalavrasTitleLayout = new javax.swing.GroupLayout(PalavrasTitle);
        PalavrasTitle.setLayout(PalavrasTitleLayout);
        PalavrasTitleLayout.setHorizontalGroup(
            PalavrasTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PalavrasTitleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PalavrasTitleLayout.setVerticalGroup(
            PalavrasTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PalavrasTitleLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        TextosTitle.setBackground(new java.awt.Color(153, 153, 153));

        jLabel9.setFont(new java.awt.Font("Bookman Old Style", 1, 18)); 
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Últimos textos lidos");

        javax.swing.GroupLayout TextosTitleLayout = new javax.swing.GroupLayout(TextosTitle);
        TextosTitle.setLayout(TextosTitleLayout);
        TextosTitleLayout.setHorizontalGroup(
            TextosTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TextosTitleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        TextosTitleLayout.setVerticalGroup(
            TextosTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TextosTitleLayout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(20, 20, 20))
        );

        Fluencia.setBackground(new java.awt.Color(153, 153, 153));

        Fluency.setFont(new java.awt.Font("Tahoma", 1, 150)); 
        Fluency.setForeground(new java.awt.Color(255, 255, 255));
        Fluency.setText("60%");

        jLabel10.setFont(new java.awt.Font("Bookman Old Style", 1, 18)); 
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Your fluency level is:");

        javax.swing.GroupLayout FluenciaLayout = new javax.swing.GroupLayout(Fluencia);
        Fluencia.setLayout(FluenciaLayout);
        FluenciaLayout.setHorizontalGroup(
            FluenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FluenciaLayout.createSequentialGroup()
                .addGap(246, 246, 246)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(FluenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(FluenciaLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(Fluency)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        FluenciaLayout.setVerticalGroup(
            FluenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FluenciaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(149, Short.MAX_VALUE))
            .addGroup(FluenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(FluenciaLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(Fluency)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout UserLayout = new javax.swing.GroupLayout(User);
        User.setLayout(UserLayout);
        UserLayout.setHorizontalGroup(
            UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Buttons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(TextosTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Fluencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(UserLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TextosLidos)
                    .addComponent(PalavrasProcuradas))
                .addContainerGap())
            .addComponent(PalavrasTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        UserLayout.setVerticalGroup(
            UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UserLayout.createSequentialGroup()
                .addComponent(Buttons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(PalavrasTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PalavrasProcuradas, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(TextosTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(TextosLidos, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(Fluencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(54, 54, 54))
        );

        mainPanel.add(User, "User");

        Read.setBackground(new java.awt.Color(204, 255, 204));

        Buttons2.setBackground(new java.awt.Color(51, 153, 0));

        ReadPanel.setBackground(new java.awt.Color(255, 153, 0));

        ReadButton2.setBackground(new java.awt.Color(0, 153, 0));
        ReadButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); 
        ReadButton2.setForeground(new java.awt.Color(255, 255, 255));
        ReadButton2.setText("Read");
        ReadButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 0)));
        ReadButton2.setContentAreaFilled(false);

        javax.swing.GroupLayout ReadPanelLayout = new javax.swing.GroupLayout(ReadPanel);
        ReadPanel.setLayout(ReadPanelLayout);
        ReadPanelLayout.setHorizontalGroup(
            ReadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReadPanelLayout.createSequentialGroup()
                .addComponent(ReadButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        ReadPanelLayout.setVerticalGroup(
            ReadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ReadPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(ReadButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel11.setIcon(new javax.swing.ImageIcon("UserIcon.png")); 

        UserButton2.setFont(new java.awt.Font("Tahoma", 1, 12));
        UserButton2.setForeground(new java.awt.Color(255, 255, 255));
        UserButton2.setText("User");
        UserButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 0)));
        UserButton2.setContentAreaFilled(false);
        UserButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserButton2ActionPerformed(evt);
            }
        });

        LogOutButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); 
        LogOutButton2.setForeground(new java.awt.Color(255, 255, 255));
        LogOutButton2.setText("Log Out");
        LogOutButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 0)));
        LogOutButton2.setContentAreaFilled(false);
        LogOutButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogOutButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Buttons2Layout = new javax.swing.GroupLayout(Buttons2);
        Buttons2.setLayout(Buttons2Layout);
        Buttons2Layout.setHorizontalGroup(
            Buttons2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Buttons2Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(UserButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ReadPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 355, Short.MAX_VALUE)
                .addComponent(LogOutButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Buttons2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {LogOutButton2, UserButton2});

        Buttons2Layout.setVerticalGroup(
            Buttons2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ReadPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(Buttons2Layout.createSequentialGroup()
                .addGroup(Buttons2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(UserButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(LogOutButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        TextChooser.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        SugeridosContainer.setViewportView(TextChooser);

        OpenButton.setText("Abrir");

        SearchButton.setText("Procurar...");
        SearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchButtonActionPerformed(evt);
            }
        });

        Text.setEditable(false);
        Text.setColumns(20);
        Text.setLineWrap(true);
        Text.setRows(5);
        Text.setWrapStyleWord(true);
        Text.setDoubleBuffered(true);
        Text.setSelectionColor(new java.awt.Color(0, 204, 0));
        Text.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                TextMouseReleased(evt);
            }
        });
        TextContainer.setViewportView(Text);

        SugeridosTitle.setBackground(new java.awt.Color(153, 153, 153));

        jLabel4.setFont(new java.awt.Font("Bookman Old Style", 1, 18)); 
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Textos Sugeridos");

        javax.swing.GroupLayout SugeridosTitleLayout = new javax.swing.GroupLayout(SugeridosTitle);
        SugeridosTitle.setLayout(SugeridosTitleLayout);
        SugeridosTitleLayout.setHorizontalGroup(
            SugeridosTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SugeridosTitleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SugeridosTitleLayout.setVerticalGroup(
            SugeridosTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        CurrentLan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Português", "Inglês", "Alemão" }));
        CurrentLan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CurrentLanActionPerformed(evt);
            }
        });

        SecondLan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Alemão", "Inglês", "Português" }));
        SecondLan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SecondLanActionPerformed(evt);
            }
        });

        jLabel5.setText("Língua nativa:");

        jLabel6.setText("Segunda língua:");

        javax.swing.GroupLayout ReadLayout = new javax.swing.GroupLayout(Read);
        Read.setLayout(ReadLayout);
        ReadLayout.setHorizontalGroup(
            ReadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Buttons2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(SugeridosTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(ReadLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ReadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SugeridosContainer)
                    .addComponent(TextContainer, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ReadLayout.createSequentialGroup()
                        .addGroup(ReadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(31, 31, 31)
                        .addGroup(ReadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(CurrentLan, 0, 172, Short.MAX_VALUE)
                            .addComponent(SecondLan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(OpenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(SearchButton)))
                .addContainerGap())
        );

        ReadLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {OpenButton, SearchButton});

        ReadLayout.setVerticalGroup(
            ReadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReadLayout.createSequentialGroup()
                .addComponent(Buttons2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SugeridosTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(SugeridosContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ReadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ReadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(OpenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(SearchButton))
                    .addGroup(ReadLayout.createSequentialGroup()
                        .addGroup(ReadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CurrentLan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(ReadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SecondLan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TextContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                .addContainerGap())
        );

        ReadLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {OpenButton, SearchButton});

        mainPanel.add(Read, "Read");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 734, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }

    private void PasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {
        LogButtonActionPerformed(evt);
    }

    private void LoginTextActionPerformed(java.awt.event.ActionEvent evt) {
    	LogButtonActionPerformed(evt);
    }

    private void LogButtonActionPerformed(java.awt.event.ActionEvent evt) {
        
        char[] password = PasswordField.getPassword();
        String pass = String.valueOf(password);
        String username = LoginText.getText();
        
        Trieteste a = new Trieteste();
        loggedUser = new User(0, "Scarlat7", "");//searchTrie(username);
          
        if(pass.equals(loggedUser.senha)){
            PasswordField.setText("");
            LoginText.setText("");
            UserButton.setText(loggedUser.nome);
            UserButton2.setText(loggedUser.nome);
            loggedUser.palavras[0] = new Palavras("Abacate", 2, 23);
            loggedUser.palavras[1] = new Palavras("Feijão", 2, 53);
            loggedUser.palavras[2] = new Palavras("Entao", 4, 12);
            loggedUser.textosLidos[0] = new TextosLidos("o Lindo Fim", "120416", (float)36.7, (short)23);
            loggedUser.textosLidos[1] = new TextosLidos("Abacates", "160416", (float)38, (short)15);
            loggedUser.textosLidos[2] = new TextosLidos("hahahaha", "120516", (float)36.4, (short)74);
            CardLayout card = (CardLayout)mainPanel.getLayout();
            card.show(mainPanel, "User");
        }
        else
            ErrorMessage.setText("Wrong password-user combination, please try again."); 
        
    }

    private void LoginTextFocusGained(java.awt.event.FocusEvent evt) {
        LoginText.selectAll();
        LoginText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 1, true));
    }

    private void LoginTextFocusLost(java.awt.event.FocusEvent evt) {
        LoginText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
    }

    private void PasswordFieldFocusGained(java.awt.event.FocusEvent evt) {
        PasswordField.selectAll();
        PasswordField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 1, true));
    }

    private void PasswordFieldFocusLost(java.awt.event.FocusEvent evt) {
       PasswordField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
    }

    private void ReadButtonActionPerformed(java.awt.event.ActionEvent evt) {
            CardLayout card = (CardLayout)mainPanel.getLayout();
            card.show(mainPanel, "Read");
    }

    private void UserButton2ActionPerformed(java.awt.event.ActionEvent evt) {
            CardLayout card = (CardLayout)mainPanel.getLayout();
            card.show(mainPanel, "User");
    }

    private void SearchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int returnVal = FileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = FileChooser.getSelectedFile();
            try {
              Text.read( new FileReader( file.getAbsolutePath() ), null );
            }catch(IOException e){
            	JOptionPane.showMessageDialog(new JFrame(),"Error loading file.");
            }
        }
    }

    private void LogOutButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        CardLayout card = (CardLayout)mainPanel.getLayout();
        card.show(mainPanel, "Login");
    }

    private void LogOutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        CardLayout card = (CardLayout)mainPanel.getLayout();
        card.show(mainPanel, "Login");
    }

    private void CurrentLanActionPerformed(java.awt.event.ActionEvent evt) {
        JComboBox cb = (JComboBox)evt.getSource();
        currentLang = (String)cb.getSelectedItem(); 
    }

    private void SecondLanActionPerformed(java.awt.event.ActionEvent evt) {
        JComboBox cb = (JComboBox)evt.getSource();
        secondLang = (String)cb.getSelectedItem(); 
    }

    private void TextMouseReleased(java.awt.event.MouseEvent evt) {
        String busca = (Text.getSelectedText());
        Trieteste teste = new Trieteste();
        RandomAccessFile arquivo;
        try {
                arquivo = new RandomAccessFile("Trie.bin", "rw");
                ArrayList<Integer> ID = teste.searchTrie(arquivo, busca);
                // ArrayList<Integer> ID2 = teste2.searchTrie(arquivo, "rena");
                 System.out.println(ID);
                 //System.out.println(ID2);
        } catch (FileNotFoundException e1) {
                e1.printStackTrace();
        }
    }
    
    
    private void TabelaPalavrasMouseClicked(java.awt.event.MouseEvent evt) {
        
    	int j=0;
    	int col = TabelaPalavras.columnAtPoint(evt.getPoint());
        String name = TabelaPalavras.getColumnName(col);
        ordemPalavra[col] = !ordemPalavra[col];
        Palavras[] s = Arrays.copyOf(loggedUser.palavras, loggedUser.palavras.length, Palavras[].class);
        
        while(j<s.length && s[j] != null)
        	j++;
        
        Sort.MergeSortPalavras(s,0,j,name,ordemPalavra[col]);
        
        for(int i = 0; i<j; i++){
			TabelaPalavras.setValueAt(s[i].palavra, i, 0);
			TabelaPalavras.setValueAt(s[i].dificuldade, i, 1);
			TabelaPalavras.setValueAt(s[i].procurada, i, 2);
        }
    }
    
    private void TabelaTextosMouseClicked(java.awt.event.MouseEvent evt) {
    	
    	int j=0;
    	int col = TabelaTextos.columnAtPoint(evt.getPoint());
        String name = TabelaTextos.getColumnName(col);
        ordemTexto[col] = !ordemTexto[col];
        TextosLidos[] s = Arrays.copyOf(loggedUser.textosLidos, loggedUser.textosLidos.length, TextosLidos[].class);
        
        while(j<s.length && s[j] != null)
        	j++;
        for(int k = 0; k<j; k++)
        	System.out.println(s[k]);
        
        Sort.MergeSortTextos(s,0,j,name,ordemTexto[col]);
        
        for(int i = 0; i<j; i++){
			TabelaTextos.setValueAt(s[i].nome, i, 0);
			String a = s[i].data.substring(0, 2)+"/" + s[i].data.substring(2, 4)+"/"+ s[i].data.substring(4, 6);
			TabelaTextos.setValueAt(a, i, 1);
			TabelaTextos.setValueAt(s[i].compreensao, i, 2);
        }
    }
    
    private Object[] getCol(JTable table, int col) {
        DefaultTableModel t = (DefaultTableModel) table.getModel();
        int nLin = t.getRowCount();
        Object[] tableData = new Object[nLin];
        for (int i = 0 ; i < nLin ; i++)
                tableData[i] = t.getValueAt(i,col);
        return tableData;
    }
    
 

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoomlaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoomlaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoomlaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoomlaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoomlaUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JPanel Buttons;
    private javax.swing.JPanel Buttons2;
    private javax.swing.JComboBox<String> CurrentLan;
    private javax.swing.JLabel ErrorMessage;
    private javax.swing.JFileChooser FileChooser;
    private javax.swing.JPanel Fluencia;
    private javax.swing.JLabel Fluency;
    private javax.swing.JButton LogButton;
    private javax.swing.JButton LogOutButton;
    private javax.swing.JButton LogOutButton2;
    private javax.swing.JPanel LoginPanel;
    private javax.swing.JTextField LoginText;
    private javax.swing.JButton OpenButton;
    private javax.swing.JScrollPane PalavrasProcuradas;
    private javax.swing.JPanel PalavrasTitle;
    private javax.swing.JPasswordField PasswordField;
    private javax.swing.JPanel Read;
    private javax.swing.JButton ReadButton;
    private javax.swing.JButton ReadButton2;
    private javax.swing.JPanel ReadPanel;
    private javax.swing.JButton SearchButton;
    private javax.swing.JComboBox<String> SecondLan;
    private javax.swing.JButton SignUpButton;
    private javax.swing.JScrollPane SugeridosContainer;
    private javax.swing.JPanel SugeridosTitle;
    private javax.swing.JTextArea Text;
    private javax.swing.JList<String> TextChooser;
    private javax.swing.JScrollPane TextContainer;
    private javax.swing.JScrollPane TextosLidos;
    private javax.swing.JPanel TextosTitle;
    private javax.swing.JPanel User;
    private javax.swing.JButton UserButton;
    private javax.swing.JButton UserButton2;
    private javax.swing.JPanel UserPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTable TabelaTextos;
    private javax.swing.JTable TabelaPalavras;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration
}
