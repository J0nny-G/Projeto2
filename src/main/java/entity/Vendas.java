package entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Vendas {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_venda")
    private int idVenda;
    @Basic
    @Column(name = "id_cliente")
    private int idCliente;
    @Basic
    @Column(name = "id_funcionario")
    private int idFuncionario;
    @Basic
    @Column(name = "data_venda")
    private Date dataVenda;
    @Basic
    @Column(name = "valor_total_venda")
    private double valorTotalVenda;
    @Basic
    @Column(name = "forma_pag_venda")
    private String formaPagVenda;

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public double getValorTotalVenda() {
        return valorTotalVenda;
    }

    public void setValorTotalVenda(double valorTotalVenda) {
        this.valorTotalVenda = valorTotalVenda;
    }

    public String getFormaPagVenda() {
        return formaPagVenda;
    }

    public void setFormaPagVenda(String formaPagVenda) {
        this.formaPagVenda = formaPagVenda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vendas vendas = (Vendas) o;

        if (idVenda != vendas.idVenda) return false;
        if (idCliente != vendas.idCliente) return false;
        if (idFuncionario != vendas.idFuncionario) return false;
        if (Double.compare(vendas.valorTotalVenda, valorTotalVenda) != 0) return false;
        if (dataVenda != null ? !dataVenda.equals(vendas.dataVenda) : vendas.dataVenda != null) return false;
        if (formaPagVenda != null ? !formaPagVenda.equals(vendas.formaPagVenda) : vendas.formaPagVenda != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idVenda;
        result = 31 * result + idCliente;
        result = 31 * result + idFuncionario;
        result = 31 * result + (dataVenda != null ? dataVenda.hashCode() : 0);
        temp = Double.doubleToLongBits(valorTotalVenda);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (formaPagVenda != null ? formaPagVenda.hashCode() : 0);
        return result;
    }
}
