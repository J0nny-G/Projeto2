package entity;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
public class Sysdiagrams {
    @Basic
    @Column(name = "name")
    private Object name;
    @Basic
    @Column(name = "principal_id")
    private int principalId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "diagram_id")
    private int diagramId;
    @Basic
    @Column(name = "version")
    private Integer version;
    @Basic
    @Column(name = "definition")
    private byte[] definition;

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public int getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(int principalId) {
        this.principalId = principalId;
    }

    public int getDiagramId() {
        return diagramId;
    }

    public void setDiagramId(int diagramId) {
        this.diagramId = diagramId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public byte[] getDefinition() {
        return definition;
    }

    public void setDefinition(byte[] definition) {
        this.definition = definition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sysdiagrams that = (Sysdiagrams) o;

        if (principalId != that.principalId) return false;
        if (diagramId != that.diagramId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (!Arrays.equals(definition, that.definition)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + principalId;
        result = 31 * result + diagramId;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(definition);
        return result;
    }
}
