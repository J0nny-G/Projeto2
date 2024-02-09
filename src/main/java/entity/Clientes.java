package entity;

import jakarta.persistence.*;

@Entity
public class Clientes {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_cliente")
    private int idCliente;
    @Basic
    @Column(name = "nome")
    private String nome;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "telefone")
    private String telefone;
    @Basic
    @Column(name = "morada")
    private String morada;
    @Basic
    @Column(name = "codPostal")
    private int codPostal;

    public Clientes() {
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public int getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(int codPostal) {
        this.codPostal = codPostal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Clientes clientes = (Clientes) o;

        if (idCliente != clientes.idCliente) return false;
        if (codPostal != clientes.codPostal) return false;
        if (nome != null ? !nome.equals(clientes.nome) : clientes.nome != null) return false;
        if (email != null ? !email.equals(clientes.email) : clientes.email != null) return false;
        if (telefone != null ? !telefone.equals(clientes.telefone) : clientes.telefone != null) return false;
        if (morada != null ? !morada.equals(clientes.morada) : clientes.morada != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCliente;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (telefone != null ? telefone.hashCode() : 0);
        result = 31 * result + (morada != null ? morada.hashCode() : 0);
        result = 31 * result + codPostal;
        return result;
    }
}
