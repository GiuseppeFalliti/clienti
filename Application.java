import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;
import java.io.*;

public class Application extends JFrame {
    private static final String WIDTH_KEY = "width";
    private static final String HEIGHT_KEY = "height";
    private static final String POS_X = "x";
    private static final String POS_Y = "y";
    private Container cp;
    private JTextArea textArea;
    private Dialog dialog;
    private JPanel panelDialog;
    private JLabel name;
    private JLabel surname;
    private JLabel salary;
    private JLabel email;
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField salaryField;
    private JTextField emailField;
    private JButton add;

    private Preferences preferences;

    public Application() {
        super();
        cp = this.getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.PAGE_AXIS));
        this.setTitle("Gestione Clienti");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        preferences = Preferences.userNodeForPackage(Application.class);
        int width = preferences.getInt(WIDTH_KEY, 300);
        int height = preferences.getInt(HEIGHT_KEY, 400);
        int posx = preferences.getInt(POS_X, 100);
        int posy = preferences.getInt(POS_Y, 100);

        this.setSize(width, height);
        this.setLocation(posx, posy);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveUserDimensions();
                System.exit(0);
            }
        });
        startDialog(); // Metodo per inizializzare il dialogo
        this.setupApp();
    }

    private void startDialog() {
        dialog = new JDialog(this, "add Client", true);
        dialog.setLocation(this.getX(), this.getY());
        panelDialog = new JPanel();
        panelDialog.setLayout(new GridLayout(5, 4));
        // Add tutti i componenti necessari al pannello del dialogo.
        name = new JLabel("Name");
        surname = new JLabel("Surname");
        salary = new JLabel("Salary");
        email = new JLabel("Email");
        //inserimento dati
        nameField = new JTextField(10);
        surnameField = new JTextField(10);
        salaryField = new JTextField(10);
        emailField = new JTextField(10);
        add = new JButton("Add");

        add.addActionListener(e -> {
            Clienti cli1 = new Clienti(nameField.getText(), surnameField.getText(), Double.parseDouble(salaryField.getText()), emailField.getText());
            textArea.append(cli1.getNome() + "#" + cli1.getCognome() + "#" + cli1.getStipendio() + "#" + cli1.getEmail() + "\n");
            dialog.dispose();
        });

        panelDialog.add(name);
        panelDialog.add(nameField);
        panelDialog.add(surname);
        panelDialog.add(surnameField);
        panelDialog.add(salary);
        panelDialog.add(salaryField);
        panelDialog.add(email);
        panelDialog.add(emailField);
        panelDialog.add(add);

        dialog.add(panelDialog);
        dialog.pack();
    }

    private void setupApp() {
        // Creazione della text area che conterrà tutti i clienti
        textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        textArea.setBackground(new Color(52, 52, 52));
        textArea.setForeground(new Color(255, 255, 255));
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setFont(new Font(scrollPane.getFont().getName(),Font.BOLD,18));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
              

        // Creazione della JMenuBar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(255, 255, 255));
        // Creazione del menu File
        JMenu fileMenu = new JMenu("File");
        JMenuItem addclient = new JMenuItem("add Client");
        JMenuItem checksalary = new JMenuItem("Check salary");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");

        addclient.addActionListener(e -> {
            dialog.setVisible(true); 
        });

        checksalary.addActionListener(e -> {
            String[] s = textArea.getText().split("\n");
            double max = 0;
            for (int i = 0; i < s.length; i++) {
                String[] s1 = s[i].split("#");
                double stipendio = Double.parseDouble(s1[2]);
                if (stipendio > max) {
                    max = stipendio;
                }
            }
            JOptionPane.showMessageDialog(this, "Il massimo stipendio è: " + max);
        });

        saveItem.addActionListener(e -> {
            save();
        });

        openItem.addActionListener(e -> {
            open();
        });

        
        fileMenu.add(addclient);
        fileMenu.add(checksalary);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        cp.add(scrollPane);
    }

    public void save() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                FileWriter fileWriter = new FileWriter(file);
                String[] s = textArea.getText().split("\n");
                for (int i = 0; i < s.length; i++) {
                    String[] s1 = s[i].split("#");

                    fileWriter.write(s1[0] + "#");
                    fileWriter.write(s1[1] + "#");
                    fileWriter.write(s1[2] + "#");
                    fileWriter.write(s1[3] + "#");

                }

                fileWriter.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    public void open() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                StringBuilder content = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    String[] client = line.split("#");
                    if (client.length >= 4) {
                        String nome = client[0];
                        String cognome = client[1];
                        double stipendio = Double.parseDouble(client[2]);
                        String email = client[3];
                        // add informazioni del cliente alla textArea
                        content.append(nome).append("#").append(cognome).append("#").append(stipendio).append("#").append(email).append("\n");
                    } else {
                        JOptionPane.showMessageDialog(this, "Errore lettura file: La riga non contiene abbastanza elementi");
                        
                    }

                }
                textArea.setText(content.toString());
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Errore lettura file");
            }
        }
    }

    public void saveUserDimensions() {
        preferences.putInt(WIDTH_KEY, getWidth());
        preferences.putInt(HEIGHT_KEY, getHeight());
        preferences.putInt(POS_X, getX());
        preferences.putInt(POS_Y, getY());
    }

    public void startApp(boolean packElements) {
        if (packElements) this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Application().startApp(false);
        });
    }
}
