package entity;

import jakarta.persistence.*;

@Entity
public class Fatura {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_fatura")
    private int idFatura;
    @Basic
    @Column(name = "id_cliente")
    private int idCliente;
    @Basic
    @Column(name = "id_venda")
    private int idVenda;
    @Basic
    @Column(name = "NIF")
    private int nif;

    public int getIdFatura() {
        return idFatura;
    }

    public void setIdFatura(int idFatura) {
        this.idFatura = idFatura;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fatura fatura = (Fatura) o;

        if (idFatura != fatura.idFatura) return false;
        if (idCliente != fatura.idCliente) return false;
        if (idVenda != fatura.idVenda) return false;
        if (nif != fatura.nif) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFatura;
        result = 31 * result + idCliente;
        result = 31 * result + idVenda;
        result = 31 * result + nif;
        return result;
    }
}
