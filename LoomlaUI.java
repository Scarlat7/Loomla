/************************************************************
 * Codigo principal do programa Loomla						*
 * Por Natalia Gubiani Rampon e Leonardo da Luz Dorneles	*
 * Ultima atualizacao: 22/06/2016							*
 ************************************************************/

package testeTexto;


/* Bibliotecas graficas do java */
import java.awt.CardLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/* Bibliotecas de arquivos do java */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/* Bibliotecas de tipos de dado necessarios do java */
import java.math.BigDecimal;		//para fazer o arrendondamento de floats
import java.text.SimpleDateFormat;	//formatadora de data
import java.util.Date;				//data
import java.util.ArrayList;			//lista encadeada
import java.util.Arrays;			//classe arrays


/* Classe da interface grafica
 * Nela que o programa em si eh rodado */
public class LoomlaUI extends javax.swing.JFrame {
	
	
	/* vetores booleanos para controle de que ordem (descrescente ou crescente)
	* se encontram mostradas as tabelas de palavras mais procuradas e textos lidos recentemente
	* true - crescente, false - decrescente*/ 
	public static boolean[] ordemPalavra = new boolean[3];
	public static boolean[] ordemTexto = new boolean[3];
	
	/* variaveis para controle da atual sessão do usuario */
	public static User loggedUser;					//usuario atualmente logado
	public static String textLang;					//lingua do texto sendo lido
	public static String translationLang;			//lingua em que vao ser traduzidas as palavras
	public static TextosLidos currentText;			//texto sendo lido atualmente
	public static int	palavrasTraduzidas = 0;		//contador de palavras traduzidas pelo usuario no texto atual
	
	/* inicia componentes graficos e variaveis de controle das tabelas */
    public LoomlaUI() {
        initComponents();
        for(int i=0; i < 3; i++){
        	ordemPalavra[i] = true;
        	ordemTexto[i] = true;
        }
    }
    
    /* declaracao dos componentes graficos 
     * daqui em diante, somente as partes importantes para o
     * desenvolvimento de algoritmos em si serão comentadas */
    
    @SuppressWarnings({ "unchecked", "serial" })
    private void initComponents() {

        FileChooser = new javax.swing.JFileChooser();
        mainPanel = new javax.swing.JPanel();
        LoginPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        LoginText = new javax.swing.JTextField(10);
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
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("AlanTuring.txt");							//textos sugeridos pela plataforma
        listModel.addElement("Sigismundo.txt");
        listModel.addElement("Diefenbakers.txt");
        listModel.addElement("Lebensmitte.txt");
        TextChooser = new javax.swing.JList<>(listModel);
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
                try {
					LoginTextActionPerformed(evt);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(new JFrame(),"Erro ao abrir o arquivo de usuários.");
				}
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
                try {
					PasswordFieldActionPerformed(evt);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(new JFrame(),"Erro ao abrir o arquivo de usários.");
				}
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon("dados//LoomlaLogo.png"));
        jLabel3.setText("jLabel3");

        LogButton.setBackground(new java.awt.Color(255, 255, 255));
        LogButton.setText("Log in");
        LogButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        LogButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        LogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
					LogButtonActionPerformed(evt);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(new JFrame(),"Erro ao abrir o arquivo de usuários.");
				}
            }
        });

        SignUpButton.setBackground(new java.awt.Color(255, 255, 255));
        SignUpButton.setText("Sign up");
        SignUpButton.setToolTipText("Type your desired username and password.");
        SignUpButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        SignUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
					SignUpButtonActionPerformed(evt);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(new JFrame(),"Erro ao abrir o arquivo de usuários.");
				}
            }
        });

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
        UserButton.setFont(new java.awt.Font("Tahoma", 1, 12)); 
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

        jLabel7.setIcon(new javax.swing.ImageIcon("dados//UserIcon.png")); 

        ReadButton.setFont(new java.awt.Font("Tahoma", 1, 12)); 
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
                try {
					LogOutButtonActionPerformed(evt);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(new JFrame(),"Erro ao abrir o arquivo de usuários.");
				}
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
                {null, null, null},
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
        Fluency.setText("0%");

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
                try {
					LogOutButton2ActionPerformed(evt);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(new JFrame(),"Erro ao abrir o arquivo de usuários.");
				}
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

        TextChooser.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        SugeridosContainer.setViewportView(TextChooser);

        OpenButton.setText("Abrir");
        OpenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenButtonActionPerformed(evt);
            }
        });

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
                try {
					TextMouseReleased(evt);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(new JFrame(),"Erro ao abrir os arquivos de dados.\r\n"+e.getMessage());
				}
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

        jLabel5.setText("Língua do texto:");

        jLabel6.setText("Língua nativa:");

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

    private void PasswordFieldActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        LogButtonActionPerformed(evt);
    }

    private void LoginTextActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
    	LogButtonActionPerformed(evt);
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
    
    private void LogOutButton2ActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
	    LogOutButtonActionPerformed(evt);
    }
    
    /* Função que faz login do usuário */
    private void LogButtonActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        
    	/* Pega a senha e nome do usuário dos campos de leitura */
        char[] password = PasswordField.getPassword();
        String pass = String.valueOf(password);
        String username = LoginText.getText();
        
        /* Pega o usuário no arquivo de usuários (arquivo serial indexado por TRIE)*/
        loggedUser = UserFiles.getUserData(username);
        
        if(loggedUser == null){
        	ErrorMessage.setText("Nome de usuário não encontrado.");
        }	
        	
        if(pass.equals(ToString.toString(loggedUser.getSenha()))){	//testa se a senha digitada é igual a senha do usuário no arquivo de dados
            /* Atribui campos da interface grafica, pois o usuario efetuou o login com sucesso */
        	PasswordField.setText("");
            LoginText.setText("");
            UserButton.setText(username);
            UserButton2.setText(username);
            Text.setText("");
            updateUserPage();
            
            CardLayout card = (CardLayout)mainPanel.getLayout();
            card.show(mainPanel, "User");
        }
        else
            ErrorMessage.setText("Senha errada."); 
        
    }
    
    /* Faz o cadastro de um novo usuario */
    private void SignUpButtonActionPerformed(java.awt.event.ActionEvent evt) throws IOException{
    	
    	/* Pega a senha e nome do usuário dos campos de leitura */
    	char[] password = PasswordField.getPassword();
        String pass = String.valueOf(password);
        String username = LoginText.getText();
        
        /* Testa se os caracteres são validos */
        for(int i = 0; i<username.length(); i++){
        	if(Trieteste.charToIndex(username.charAt(i)) == -1){
        		ErrorMessage.setText("Caracteres devem ser letras minúsculas.");
        		return;
        	}
        }
         
        /* Testa se o usuário já não existe no arquivo de usuários
         * e se o tamanho da senha e do nome de usuário estão dentro dos limites aceitáveis */
        if(UserFiles.getUserData(username) == null){
	        if(pass.length() <= 10 && username.length() <= 10){
	        	UserFiles.addUser(username, pass); //novo usuário correto, então o salvo no arquivo de usuários
	        	ErrorMessage.setText("Usuário criado.");
	        }
	        else
	        	ErrorMessage.setText("O limite de caracteres é 10."); 
        }
        else
        	ErrorMessage.setText("Usuário já criado."); 
    	
    }

    /* Troca para a janela de leitura */
    private void ReadButtonActionPerformed(java.awt.event.ActionEvent evt) {
    		
    		/* Salva as línguas atualmente selecionadas para as variáveis de controle */
    		textLang = (String) CurrentLan.getSelectedItem();
        	translationLang = (String) SecondLan.getSelectedItem();
        	
    		CardLayout card = (CardLayout)mainPanel.getLayout();
            card.show(mainPanel, "Read");
    }

    /* Troca para a janela de informações do usuário */
    private void UserButton2ActionPerformed(java.awt.event.ActionEvent evt) {
    		try {
    			/* Salva os dados da seção atual no arquivo de usuários e atualiza a janela */
				UserFiles.addUserData(loggedUser);
				updateUserPage();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(new JFrame(),"Erro ao abrir o arquivo de usuários.");
			}
    	
    		CardLayout card = (CardLayout)mainPanel.getLayout();
            card.show(mainPanel, "User");
    }

    /* Abre um dos textos recomendados */
    private void OpenButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	
    	/* Pega o texto atualmente selecionado */
    	String selected = TextChooser.getSelectedValue();
    
    	/* Se o campo de texto não está vazio então o usuário estava lendo um texto, logo,
    	 * as suas informações sobre a leitura do texto antigo devem ser salvas antes de abrir um novo */
    	if(!Text.getText().equals("")){
        	roundUpText();
        }
    	/* Cria instância do novo texto sendo lido */
    	currentText = new TextosLidos();
    	
    	/* Pega a data em que o arquivo foi aberto */
        Date time = new Date();
        SimpleDateFormat ft =  new SimpleDateFormat("ddMMyy");
        ft.format(time);
        
        /* Atribui a data e o nome do arquivo ao texto atual */
        currentText.setData(ft.format(time).toCharArray());
        currentText.setNome(selected.toCharArray());
    	
        try {
          Text.read( new FileReader(selected), null );
        }catch(IOException e){
        	JOptionPane.showMessageDialog(new JFrame(),"Erro ao abrir o arquivo.");
        }
    }
    
    /* Abre um arquivo selecionado pelo usuário */
    private void SearchButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	
    	/* Pega o texto que o usuário escolheu */
    	int returnVal = FileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = FileChooser.getSelectedFile();
            
            /* Se o campo de texto não está vazio então o usuário estava lendo um texto, logo,
        	 * as suas informações sobre a leitura do texto antigo devem ser salvas antes de abrir um novo */
            if(Text.getText() != ""){
            	roundUpText();
            }
            
            /* Cria instância do novo texto sendo lido */
            currentText = new TextosLidos();
            
            /* Atribui a data e o nome do arquivo ao texto atual */
            Date time = new Date();
            SimpleDateFormat ft =  new SimpleDateFormat("ddMMyy");
            ft.format(time);
            currentText.setData(ft.format(time).toCharArray());
            currentText.setNome(file.getName().toCharArray()); 
            
            try {
              Text.read( new FileReader( file.getAbsolutePath() ), null );
            }catch(IOException e){
            	JOptionPane.showMessageDialog(new JFrame(),"Erro ao abrir o arquivo.");
            }
        }
    }

    /* Função de log out */
    private void LogOutButtonActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        
    	/* Como o usuário saiu, os dados sobre a leitura do texto devem ser salvos */
    	roundUpText();
    	
    	/* Assim como toda a instância do usuário deve ser atualizada no arquivo de usuários */
    	UserFiles.addUserData(loggedUser);
    	
    	CardLayout card = (CardLayout)mainPanel.getLayout();
        card.show(mainPanel, "Login");
    }

    /* Selecionador de língua do texto */
    private void CurrentLanActionPerformed(java.awt.event.ActionEvent evt) {
        
    	/* Atualiza língua do texto escolhida pelo usuário */
    	JComboBox cb = (JComboBox)evt.getSource();
        textLang = (String)cb.getSelectedItem(); 
    }

    /* Selecionador de língua de tradução */
    private void SecondLanActionPerformed(java.awt.event.ActionEvent evt) {
        
    	/* Atualiza língua do texto escolhida pelo usuário */
    	JComboBox cb = (JComboBox)evt.getSource();
        translationLang = (String)cb.getSelectedItem(); 
    }

    /* Função que pega a palavra selecionada pelo usuário */
    private void TextMouseReleased(java.awt.event.MouseEvent evt) throws IOException {
        
    	/* Pega a palavra selecionada pelo usuário e bota na variável busca */
    	String busca = (Text.getSelectedText());
    	
    	/* Como uma nova palavra foi pesquisada, incrementa-se o número de palavras pequisadas nesse texto */ 
        palavrasTraduzidas++;
        
        /* Adiciona-se essa nova palavra às palavras procuradas pelo usuário */
        loggedUser.newWord(busca);
        
        /* Atribui à variável language a língua em que a palavra será traduzida
         * e à variável nativa a língua em que a palavra buscada está */
        String language = translationLang;
        String nativa = textLang;
        
        /* Contadores */
        int nIDs = 0, i = 1;
        
        /* Atribui à variável teste o handle da árvore TRIE, que é usada para pesquisar a palavra selecionada 
         * e conseguir seu ID */
        Trieteste teste = new Trieteste();
        
        try {
        		/* Atribui à variável data o handle da árvore B em que serão pesquisadas as traduções da palavra */
        		BNode data = new BNode(language);
        		
                String translation = ""; //variável em que serão concatenadas as traduções
                
                /* Atribui à variável ID a lista de IDs (significados) que essa palavra têm */
                ArrayList<Integer> ID = teste.searchTrie("words.data", busca, nativa, 4);
                
                /* Passa por todos IDs procurando-os na árvore B */
                while(nIDs < 4 && ID.get(nIDs)!= -1){
                	
                	/* Pega da árvore B a lista de traduções desse ID */
                	ArrayList<String> translations = data.getTranslations(ID.get(nIDs), language);
                	
                	/* Se existem traduções, as escrevemos na string */
                	if(translations != null)
	                	for(String meaning: translations){
	                		translation += i+ ". " +meaning+ "\r\n";
	                		i++;
	                	}
                	nIDs++;
                }

                /* Mostra ao usuário, em um pop-up as traduções da palavra */
                JOptionPane.showMessageDialog(new JFrame(),
                	    translation,
                	    ("Tradução de "+busca),
                	    JOptionPane.PLAIN_MESSAGE);
        } catch (FileNotFoundException e1) {
        	JOptionPane.showMessageDialog(new JFrame(),"Erro ao abrir os arquivos de dados. " + e1.getMessage());
        }
    }
    
    /* Efetua a ordenação da coluna em que o usuário clicou na tabela de palavras*/
    private void TabelaPalavrasMouseClicked(java.awt.event.MouseEvent evt) {
        
    	int j=0;
    	
    	/* Pega o nome da coluna em que o usuário clicou */
    	int col = TabelaPalavras.columnAtPoint(evt.getPoint());
        String name = TabelaPalavras.getColumnName(col);
        
        ordemPalavra[col] = !ordemPalavra[col]; //a ordem da coluna muda de decrescente para crescente ou vice-versa
       
        /* Copia o array de palavras procuradas pelo usuário atual ao vetor s, que será ordenado*/
        Palavras[] s = Arrays.copyOf(loggedUser.palavras, loggedUser.palavras.length, Palavras[].class);
        
        /* Acha até que índice está ocupado o array, levando em conta o fato de a dificuldade 0 ser inválida */
        while(j<s.length && s[j].dificuldade != 0)
        	j++;
        
        /* Aplica o MergeSort no vetor e atualiza a tabela */
        Sort.MergeSortPalavras(s,0,j,name,ordemPalavra[col]);
        updateTabelaPalavra(s, j);
 
    }
    
    /* Efetua a ordenação da coluna em que o usuário clicou na tabela de textos */
    private void TabelaTextosMouseClicked(java.awt.event.MouseEvent evt) {
    	
    	int j=0;
    	/* Pega o nome da coluna em que o usuário clicou */
    	int col = TabelaTextos.columnAtPoint(evt.getPoint());
        String name = TabelaTextos.getColumnName(col);
        
        ordemTexto[col] = !ordemTexto[col];//a ordem da coluna muda de decrescente para crescente ou vice-versa
        
        /* Copia o array de textos procuradas pelo usuário atual ao vetor s, que será ordenado */
        TextosLidos[] s = Arrays.copyOf(loggedUser.textosLidos, loggedUser.textosLidos.length, TextosLidos[].class);
        
        /* Acha até que índice está ocupado o array, levando em conta o fato de a dificuldade 0 ser inválida */
        while(j<s.length && s[j].dificuldade != 0)
        	j++;
        
        /* Aplica o MergeSort no vetor e atualiza a tabela */
        Sort.MergeSortTextos(s,0,j,name,ordemTexto[col]);
        updateTabelaTexto(s, j);
        
    }
    
    /* Função que salva os dados do texto sendo lido para o arquivo */
    private void roundUpText(){
    	
    	/* Calcula a dificuldade do texto e o quanto o usuário entendeu dele */
    	int totalPalavras = currentText.dificuldadeTexto(Text.getText());
    	currentText.calculaCompreensao(totalPalavras, palavrasTraduzidas);
    	
    	/*Atualiza fluência do usuário fazendo uma média simples */
    	loggedUser.fluence = (loggedUser.fluence + currentText.compreensao)/2;
    	palavrasTraduzidas = 0;
    	loggedUser.newRead(currentText);  //adiciona o novo texto aos textos lidos do usuário
    	
    	try {
			UserFiles.addUserData(loggedUser);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(),"Erro ao abrir o arquivo de usuários.");
		}
    }
    
    /* Atualiza tabela de palavras procuradas pelo usuário */
    private void updateTabelaPalavra(Palavras s[], int tam){
    	
    	for(int i = 0; i<tam; i++){
			TabelaPalavras.setValueAt(ToString.toString(s[i].palavra), i, 0);
			TabelaPalavras.setValueAt(s[i].dificuldade, i, 1);
			TabelaPalavras.setValueAt(s[i].procurada, i, 2);
        }
    }
    
    /* Atualiza tabela de textos lidos pelo usuário */
    private void updateTabelaTexto(TextosLidos s[], int tam){
    	
    	for(int i = 0; i<tam; i++){
			TabelaTextos.setValueAt(ToString.toString(s[i].nome), i, 0);
			String aux = ToString.toString(s[i].data);
			String a = aux.substring(0, 2)+"/" + aux.substring(2, 4)+"/"+ aux.substring(4, 6);
			TabelaTextos.setValueAt(a, i, 1);
			TabelaTextos.setValueAt(round(s[i].compreensao)+"%", i, 2);
        }
    	
    }
    
    /* Dá um refresh nos dados da página do usuário */
    private void updateUserPage(){
       
    	int i = 0;
        
    	while(i<loggedUser.palavras.length && loggedUser.palavras[i].dificuldade != 0){
    		for(int j = 0; j<TabelaPalavras.getRowCount(); j++){
	        	TabelaPalavras.setValueAt(ToString.toString(loggedUser.palavras[i].getPalavra()), i, 0);
	        	TabelaPalavras.setValueAt(loggedUser.palavras[i].dificuldade, i, 1);
	        	TabelaPalavras.setValueAt(loggedUser.palavras[i].procurada, i, 2);
            }
            i++;
        }
       
    	i = 0;
    	while(i<loggedUser.textosLidos.length && loggedUser.textosLidos[i].dificuldade != 0){
    		for(int j = 0; j<TabelaTextos.getRowCount(); j++){
			    TabelaTextos.setValueAt(ToString.toString(loggedUser.textosLidos[i].getNome()), i, 0);
			    String aux = ToString.toString(loggedUser.textosLidos[i].data);
			    String a = aux.substring(0, 2)+"/" + aux.substring(2, 4)+"/"+ aux.substring(4, 6);
			    TabelaTextos.setValueAt(a, i, 1);
			    TabelaTextos.setValueAt(round(loggedUser.textosLidos[i].compreensao)+"%", i, 2);
    		}
    		i++;
    	}
    	Fluency.setText(String.valueOf(round(loggedUser.fluence))+"%");
   }

   /* Função que arredonda um float para duas casas decimais (usada para impressão de dados) */
   public static float round(float n){
    	
    	BigDecimal result = new BigDecimal(Float.toString(n));
    	result = result.setScale(2, BigDecimal.ROUND_HALF_UP);
    	
    	return result.floatValue();
   }
    
    /* Mais inicializações gráficas */
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
}
