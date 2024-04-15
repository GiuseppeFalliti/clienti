/**
 * Clienti
 */
public class Clienti {
    private String nome;
    private String cognome;
    private double stipendio;
    private String email;

    public Clienti(String nome,String cognome,double stipendio,String email){
        this.nome=nome;
        this.cognome=cognome;
        this.stipendio=stipendio;
        this.email=email;

    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    public double getStipendio() {
        return stipendio;
    }
    public void setStipendio(double stipendio) {
        this.stipendio = stipendio;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    
}
