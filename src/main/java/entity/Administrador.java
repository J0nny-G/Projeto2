package entity;

import jakarta.persistence.*;

@Entity
public class Administrador {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_administrador")
    private int idAdministrador;
    @Basic
    @Column(name = "nome_administrador")
    private String nomeAdministrador;
    @Basic
    @Column(name = "email_administrador")
    private String emailAdministrador;
    @Basic
    @Column(name = "senha_administrador")
    private String senhaAdministrador;

    public int getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(int idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public String getNomeAdministrador() {
        return nomeAdministrador;
    }

    public void setNomeAdministrador(String nomeAdministrador) {
        this.nomeAdministrador = nomeAdministrador;
    }

    public String getEmailAdministrador() {
        return emailAdministrador;
    }

    public void setEmailAdministrador(String emailAdministrador) {
        this.emailAdministrador = emailAdministrador;
    }

    public String getSenhaAdministrador() {
        return senhaAdministrador;
    }

    public void setSenhaAdministrador(String senhaAdministrador) {
        this.senhaAdministrador = senhaAdministrador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Administrador that = (Administrador) o;

        if (idAdministrador != that.idAdministrador) return false;
        if (nomeAdministrador != null ? !nomeAdministrador.equals(that.nomeAdministrador) : that.nomeAdministrador != null)
            return false;
        if (emailAdministrador != null ? !emailAdministrador.equals(that.emailAdministrador) : that.emailAdministrador != null)
            return false;
        if (senhaAdministrador != null ? !senhaAdministrador.equals(that.senhaAdministrador) : that.senhaAdministrador != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idAdministrador;
        result = 31 * result + (nomeAdministrador != null ? nomeAdministrador.hashCode() : 0);
        result = 31 * result + (emailAdministrador != null ? emailAdministrador.hashCode() : 0);
        result = 31 * result + (senhaAdministrador != null ? senhaAdministrador.hashCode() : 0);
        return result;
    }
}
