package entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "Pedido_compra", schema = "dbo", catalog = "LojaInformatica")
public class PedidoCompra {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_pedido_compra")
    private int idPedidoCompra;
    @Basic
    @Column(name = "id_fornecedor")
    private int idFornecedor;
    @Basic
    @Column(name = "id_administrador")
    private int idAdministrador;
    @Basic
    @Column(name = "data_pedido_compra")
    private Date dataPedidoCompra;
    @Basic
    @Column(name = "valor_total_pedido_compra")
    private double valorTotalPedidoCompra;
    @Basic
    @Column(name = "status_pedido_compra")
    private String statusPedidoCompra;
    @Basic
    @Column(name = "taxa_iva")
    private double taxaIva;

    public int getIdPedidoCompra() {
        return idPedidoCompra;
    }

    public void setIdPedidoCompra(int idPedidoCompra) {
        this.idPedidoCompra = idPedidoCompra;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public int getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(int idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public Date getDataPedidoCompra() {
        return dataPedidoCompra;
    }

    public void setDataPedidoCompra(Date dataPedidoCompra) {
        this.dataPedidoCompra = dataPedidoCompra;
    }

    public double getValorTotalPedidoCompra() {
        return valorTotalPedidoCompra;
    }

    public void setValorTotalPedidoCompra(double valorTotalPedidoCompra) {
        this.valorTotalPedidoCompra = valorTotalPedidoCompra;
    }

    public String getStatusPedidoCompra() {
        return statusPedidoCompra;
    }

    public void setStatusPedidoCompra(String statusPedidoCompra) {
        this.statusPedidoCompra = statusPedidoCompra;
    }

    public double getTaxaIva() {
        return taxaIva;
    }

    public void setTaxaIva(double taxaIva) {
        this.taxaIva = taxaIva;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PedidoCompra that = (PedidoCompra) o;

        if (idPedidoCompra != that.idPedidoCompra) return false;
        if (idFornecedor != that.idFornecedor) return false;
        if (idAdministrador != that.idAdministrador) return false;
        if (Double.compare(that.valorTotalPedidoCompra, valorTotalPedidoCompra) != 0) return false;
        if (Double.compare(that.taxaIva, taxaIva) != 0) return false;
        if (dataPedidoCompra != null ? !dataPedidoCompra.equals(that.dataPedidoCompra) : that.dataPedidoCompra != null)
            return false;
        if (statusPedidoCompra != null ? !statusPedidoCompra.equals(that.statusPedidoCompra) : that.statusPedidoCompra != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idPedidoCompra;
        result = 31 * result + idFornecedor;
        result = 31 * result + idAdministrador;
        result = 31 * result + (dataPedidoCompra != null ? dataPedidoCompra.hashCode() : 0);
        temp = Double.doubleToLongBits(valorTotalPedidoCompra);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (statusPedidoCompra != null ? statusPedidoCompra.hashCode() : 0);
        temp = Double.doubleToLongBits(taxaIva);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
