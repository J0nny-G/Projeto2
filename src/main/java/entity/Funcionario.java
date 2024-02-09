package entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Funcionario {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_funcionario")
    private int idFuncionario;
    @Basic
    @Column(name = "nome_funcionario")
    private String nomeFuncionario;
    @Basic
    @Column(name = "email_funcionario")
    private String emailFuncionario;
    @Basic
    @Column(name = "telefone_funcionario")
    private int telefoneFuncionario;
    @Basic
    @Column(name = "morada_funcionario")
    private String moradaFuncionario;
    @Basic
    @Column(name = "codPostal")
    private int codPostal;
    @Basic
    @Column(name = "data_contratacao_funcionario")
    private Date dataContratacaoFuncionario;
    @Basic
    @Column(name = "salario_funcionario")
    private double salarioFuncionario;

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getEmailFuncionario() {
        return emailFuncionario;
    }

    public void setEmailFuncionario(String emailFuncionario) {
        this.emailFuncionario = emailFuncionario;
    }

    public int getTelefoneFuncionario() {
        return telefoneFuncionario;
    }

    public void setTelefoneFuncionario(int telefoneFuncionario) {
        this.telefoneFuncionario = telefoneFuncionario;
    }

    public String getMoradaFuncionario() {
        return moradaFuncionario;
    }

    public void setMoradaFuncionario(String moradaFuncionario) {
        this.moradaFuncionario = moradaFuncionario;
    }

    public int getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(int codPostal) {
        this.codPostal = codPostal;
    }

    public Date getDataContratacaoFuncionario() {
        return dataContratacaoFuncionario;
    }

    public void setDataContratacaoFuncionario(Date dataContratacaoFuncionario) {
        this.dataContratacaoFuncionario = dataContratacaoFuncionario;
    }

    public double getSalarioFuncionario() {
        return salarioFuncionario;
    }

    public void setSalarioFuncionario(double salarioFuncionario) {
        this.salarioFuncionario = salarioFuncionario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Funcionario that = (Funcionario) o;

        if (idFuncionario != that.idFuncionario) return false;
        if (telefoneFuncionario != that.telefoneFuncionario) return false;
        if (codPostal != that.codPostal) return false;
        if (Double.compare(that.salarioFuncionario, salarioFuncionario) != 0) return false;
        if (nomeFuncionario != null ? !nomeFuncionario.equals(that.nomeFuncionario) : that.nomeFuncionario != null)
            return false;
        if (emailFuncionario != null ? !emailFuncionario.equals(that.emailFuncionario) : that.emailFuncionario != null)
            return false;
        if (moradaFuncionario != null ? !moradaFuncionario.equals(that.moradaFuncionario) : that.moradaFuncionario != null)
            return false;
        if (dataContratacaoFuncionario != null ? !dataContratacaoFuncionario.equals(that.dataContratacaoFuncionario) : that.dataContratacaoFuncionario != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idFuncionario;
        result = 31 * result + (nomeFuncionario != null ? nomeFuncionario.hashCode() : 0);
        result = 31 * result + (emailFuncionario != null ? emailFuncionario.hashCode() : 0);
        result = 31 * result + telefoneFuncionario;
        result = 31 * result + (moradaFuncionario != null ? moradaFuncionario.hashCode() : 0);
        result = 31 * result + codPostal;
        result = 31 * result + (dataContratacaoFuncionario != null ? dataContratacaoFuncionario.hashCode() : 0);
        temp = Double.doubleToLongBits(salarioFuncionario);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
