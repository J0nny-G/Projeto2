package entity;

import jakarta.persistence.*;

@Entity
public class Produtos {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_produto")
    private int idProduto;
    @Basic
    @Column(name = "nome_produto")
    private String nomeProduto;
    @Basic
    @Column(name = "id_fornecedor")
    private int idFornecedor;
    @Basic
    @Column(name = "descricao_produto")
    private String descricaoProduto;
    @Basic
    @Column(name = "marca_produto")
    private String marcaProduto;
    @Basic
    @Column(name = "modelo_produto")
    private String modeloProduto;
    @Basic
    @Column(name = "preco_venda_produto")
    private double precoVendaProduto;
    @Basic
    @Column(name = "qtd_stock_produto")
    private int qtdStockProduto;
    @Basic
    @Column(name = "qtd_min_stock")
    private int qtdMinStock;

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public String getMarcaProduto() {
        return marcaProduto;
    }

    public void setMarcaProduto(String marcaProduto) {
        this.marcaProduto = marcaProduto;
    }

    public String getModeloProduto() {
        return modeloProduto;
    }

    public void setModeloProduto(String modeloProduto) {
        this.modeloProduto = modeloProduto;
    }

    public double getPrecoVendaProduto() {
        return precoVendaProduto;
    }

    public void setPrecoVendaProduto(double precoVendaProduto) {
        this.precoVendaProduto = precoVendaProduto;
    }

    public int getQtdStockProduto() {
        return qtdStockProduto;
    }

    public void setQtdStockProduto(int qtdStockProduto) {
        this.qtdStockProduto = qtdStockProduto;
    }

    public int getQtdMinStock() {
        return qtdMinStock;
    }

    public void setQtdMinStock(int qtdMinStock) {
        this.qtdMinStock = qtdMinStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Produtos produtos = (Produtos) o;

        if (idProduto != produtos.idProduto) return false;
        if (idFornecedor != produtos.idFornecedor) return false;
        if (Double.compare(produtos.precoVendaProduto, precoVendaProduto) != 0) return false;
        if (qtdStockProduto != produtos.qtdStockProduto) return false;
        if (qtdMinStock != produtos.qtdMinStock) return false;
        if (nomeProduto != null ? !nomeProduto.equals(produtos.nomeProduto) : produtos.nomeProduto != null)
            return false;
        if (descricaoProduto != null ? !descricaoProduto.equals(produtos.descricaoProduto) : produtos.descricaoProduto != null)
            return false;
        if (marcaProduto != null ? !marcaProduto.equals(produtos.marcaProduto) : produtos.marcaProduto != null)
            return false;
        if (modeloProduto != null ? !modeloProduto.equals(produtos.modeloProduto) : produtos.modeloProduto != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idProduto;
        result = 31 * result + (nomeProduto != null ? nomeProduto.hashCode() : 0);
        result = 31 * result + idFornecedor;
        result = 31 * result + (descricaoProduto != null ? descricaoProduto.hashCode() : 0);
        result = 31 * result + (marcaProduto != null ? marcaProduto.hashCode() : 0);
        result = 31 * result + (modeloProduto != null ? modeloProduto.hashCode() : 0);
        temp = Double.doubleToLongBits(precoVendaProduto);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + qtdStockProduto;
        result = 31 * result + qtdMinStock;
        return result;
    }
}
