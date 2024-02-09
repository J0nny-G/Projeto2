package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Cod_Postal", schema = "dbo", catalog = "LojaInformatica")
public class CodPostal {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CodPostal")
    private int codPostal;
    @Basic
    @Column(name = "Localidade")
    private String localidade;

    public int getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(int codPostal) {
        this.codPostal = codPostal;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodPostal codPostal1 = (CodPostal) o;

        if (codPostal != codPostal1.codPostal) return false;
        if (localidade != null ? !localidade.equals(codPostal1.localidade) : codPostal1.localidade != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codPostal;
        result = 31 * result + (localidade != null ? localidade.hashCode() : 0);
        return result;
    }
}
