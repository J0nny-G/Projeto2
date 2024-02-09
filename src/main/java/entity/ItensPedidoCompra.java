package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Itens_pedido_compra", schema = "dbo", catalog = "LojaInformatica")
public class ItensPedidoCompra {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_item_pedido_compra")
    private int idItemPedidoCompra;
    @Basic
    @Column(name = "id_pedido_compra")
    private int idPedidoCompra;
    @Basic
    @Column(name = "id_produto")
    private int idProduto;
    @Basic
    @Column(name = "qtd_item_pedido_compra")
    private int qtdItemPedidoCompra;
    @Basic
    @Column(name = "valor_unitario")
    private double valorUnitario;

    public int getIdItemPedidoCompra() {
        return idItemPedidoCompra;
    }

    public void setIdItemPedidoCompra(int idItemPedidoCompra) {
        this.idItemPedidoCompra = idItemPedidoCompra;
    }

    public int getIdPedidoCompra() {
        return idPedidoCompra;
    }

    public void setIdPedidoCompra(int idPedidoCompra) {
        this.idPedidoCompra = idPedidoCompra;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getQtdItemPedidoCompra() {
        return qtdItemPedidoCompra;
    }

    public void setQtdItemPedidoCompra(int qtdItemPedidoCompra) {
        this.qtdItemPedidoCompra = qtdItemPedidoCompra;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItensPedidoCompra that = (ItensPedidoCompra) o;

        if (idItemPedidoCompra != that.idItemPedidoCompra) return false;
        if (idPedidoCompra != that.idPedidoCompra) return false;
        if (idProduto != that.idProduto) return false;
        if (qtdItemPedidoCompra != that.qtdItemPedidoCompra) return false;
        if (Double.compare(that.valorUnitario, valorUnitario) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idItemPedidoCompra;
        result = 31 * result + idPedidoCompra;
        result = 31 * result + idProduto;
        result = 31 * result + qtdItemPedidoCompra;
        temp = Double.doubleToLongBits(valorUnitario);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
