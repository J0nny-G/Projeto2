package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Itens_venda", schema = "dbo", catalog = "LojaInformatica")
public class ItensVenda {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_item_venda")
    private int idItemVenda;
    @Basic
    @Column(name = "id_venda")
    private int idVenda;
    @Basic
    @Column(name = "id_produto")
    private int idProduto;
    @Basic
    @Column(name = "qtd_item_venda")
    private int qtdItemVenda;
    @Basic
    @Column(name = "valor_item_venda")
    private double valorItemVenda;

    public int getIdItemVenda() {
        return idItemVenda;
    }

    public void setIdItemVenda(int idItemVenda) {
        this.idItemVenda = idItemVenda;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getQtdItemVenda() {
        return qtdItemVenda;
    }

    public void setQtdItemVenda(int qtdItemVenda) {
        this.qtdItemVenda = qtdItemVenda;
    }

    public double getValorItemVenda() {
        return valorItemVenda;
    }

    public void setValorItemVenda(double valorItemVenda) {
        this.valorItemVenda = valorItemVenda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItensVenda that = (ItensVenda) o;

        if (idItemVenda != that.idItemVenda) return false;
        if (idVenda != that.idVenda) return false;
        if (idProduto != that.idProduto) return false;
        if (qtdItemVenda != that.qtdItemVenda) return false;
        if (Double.compare(that.valorItemVenda, valorItemVenda) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idItemVenda;
        result = 31 * result + idVenda;
        result = 31 * result + idProduto;
        result = 31 * result + qtdItemVenda;
        temp = Double.doubleToLongBits(valorItemVenda);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
