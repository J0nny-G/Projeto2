package entity;

import jakarta.persistence.*;

@Entity
public class Fornecedores {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_fornecedor")
    private int idFornecedor;
    @Basic
    @Column(name = "nome_fornecedor")
    private String nomeFornecedor;
    @Basic
    @Column(name = "email_fornecedor")
    private String emailFornecedor;
    @Basic
    @Column(name = "telefone_fornecedor")
    private int telefoneFornecedor;

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public String getEmailFornecedor() {
        return emailFornecedor;
    }

    public void setEmailFornecedor(String emailFornecedor) {
        this.emailFornecedor = emailFornecedor;
    }

    public int getTelefoneFornecedor() {
        return telefoneFornecedor;
    }

    public void setTelefoneFornecedor(int telefoneFornecedor) {
        this.telefoneFornecedor = telefoneFornecedor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fornecedores that = (Fornecedores) o;

        if (idFornecedor != that.idFornecedor) return false;
        if (nomeFornecedor != that.nomeFornecedor) return false;
        if (telefoneFornecedor != that.telefoneFornecedor) return false;
        if (emailFornecedor != null ? !emailFornecedor.equals(that.emailFornecedor) : that.emailFornecedor != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFornecedor;
        result = 31 * result + (nomeFornecedor != null ? nomeFornecedor.hashCode() : 0);
        result = 31 * result + (emailFornecedor != null ? emailFornecedor.hashCode() : 0);
        result = 31 * result + telefoneFornecedor;
        return result;
    }
}
