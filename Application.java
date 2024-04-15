import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.prefs.Preferences;


public class Application extends JFrame {
    private static final String WIDTH_KEY = "width";
    private static final String HEIGHT_KEY = "height";
    private static final String POS_X = "x";
    private static final String POS_Y = "y";
    private Container cp;
    private JTextArea textArea;
    private Dialog dialog;
    
    private Preferences preferences;
    public Application(){
        super();
        cp=this.getContentPane();
        cp.setLayout(new BoxLayout(cp,BoxLayout.PAGE_AXIS));
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
        this.setupApp();
    }
    private void setupApp(){

        //creazione della text area che conterra tutti i clienti 
        textArea=new JTextArea(10,30);
        textArea.setEditable(false);
        textArea.setBackground(new Color(52,52,52));
        textArea.setForeground(new Color(255,255,255));
        //creazione del pannello che conterrà la text area
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        //creazione della JMenuBar
        JMenuBar menuBar=new JMenuBar();
        menuBar.setBackground(new Color(255,255,255));
        //creazione del menu File
        JMenu fileMenu=new JMenu("File");
        JMenuItem addclient= new JMenuItem("add Client");
        JMenuItem checksalary=new JMenuItem("Check salary");
        JMenuItem openItem=new JMenuItem("Open");
        JMenuItem saveItem=new JMenuItem("Save");

        //actionLister addClient
        addclient.addActionListener(e->{
            dialog= new JDialog(this,"add Client",true);
            dialog.setLocation(this.getX(),this.getY());
            JPanel panelDialog=new JPanel();
            panelDialog.setLayout(new GridLayout(5,4));
            //label
            JLabel name=new JLabel("Name");
            JLabel surname=new JLabel("Surname");
            JLabel salary=new JLabel("Salary");
            JLabel email = new JLabel("email");
           //inserimento dati
            JTextField nameField=new JTextField(10);
            JTextField surnameField=new JTextField(10);
            JTextField salaryField=new JTextField(10);
            JTextField emailField= new JTextField(10);
            JButton add=new JButton("Add");
            //button add
            add.addActionListener(e2->{
                Clienti cli1= new Clienti(nameField.getText(),surnameField.getText(),Double.parseDouble(salaryField.getText()),emailField.getText());
                textArea.append(cli1.getNome()+"#"+cli1.getCognome()+"#"+cli1.getStipendio()+"#"+cli1.getEmail()+"\n");
                dialog.dispose();

            });

            checksalary.addActionListener(e3->{
                String[] s=textArea.getText().split("\n");
                double max=0;
                for(int i=0;i<s.length;i++){
                    String[] s1=s[i].split("#");
                    double stipendio=Double.parseDouble(s1[2]);
                    if(stipendio>max){
                        max=stipendio;
                    }
                }
                JOptionPane.showMessageDialog(this, "Il massimo stipendio è: "+max);

            });
            saveItem.addActionListener(e4->{
                save();
            });
            openItem.addActionListener(e5->{
                open();
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
            dialog.setVisible(true);

        });

        //file bars
        fileMenu.add(addclient);
        fileMenu.add(checksalary);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        cp.add(scrollPane);
    }
    public void save(){
        

        JFileChooser fileChooser=new JFileChooser();
        int result=fileChooser.showSaveDialog(this);
        if(result==JFileChooser.APPROVE_OPTION){
            File file=fileChooser.getSelectedFile();
            try {
                FileWriter fileWriter=new FileWriter(file);
                String[] s=textArea.getText().split("\n");
                for(int i=0;i<s.length;i++){
                    String[] s1=s[i].split("#");
                   
                    fileWriter.write(s1[0]+"#");
                    fileWriter.write(s1[1]+"#");
                    fileWriter.write( s1[2]+"#");
                    fileWriter.write(s1[3]+"#");
                       
                    
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
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Errore lettura file: La riga non contiene abbastanza elementi");
                        System.out.println("Riga con errore: " + line);
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

    public void startApp(boolean packElements){
        if(packElements) this.pack();
        this.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            new Application().startApp(false);
        });
    }
   
}
