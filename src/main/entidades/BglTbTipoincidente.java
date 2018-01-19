/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.entidades;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author michael.beltran
 */
@Entity
@Table(name = "bgl_tb_tipoincidente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BglTbTipoincidente.findAll", query = "SELECT b FROM BglTbTipoincidente b")
    , @NamedQuery(name = "BglTbTipoincidente.findByTincidCodigo", query = "SELECT b FROM BglTbTipoincidente b WHERE b.tincidCodigo = :tincidCodigo")
    , @NamedQuery(name = "BglTbTipoincidente.findByTincidDescripcion", query = "SELECT b FROM BglTbTipoincidente b WHERE b.tincidDescripcion = :tincidDescripcion")})
public class BglTbTipoincidente implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TINCID_CODIGO")
    private Integer tincidCodigo;
    @Basic(optional = false)
    @Column(name = "TINCID_DESCRIPCION")
    private String tincidDescripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catTincidCodigo")
    private List<BglTbCatalogoincidentes> bglTbCatalogoincidentesList;

    public BglTbTipoincidente() {
    }

    public BglTbTipoincidente(Integer tincidCodigo) {
        this.tincidCodigo = tincidCodigo;
    }

    public BglTbTipoincidente(Integer tincidCodigo, String tincidDescripcion) {
        this.tincidCodigo = tincidCodigo;
        this.tincidDescripcion = tincidDescripcion;
    }

    public Integer getTincidCodigo() {
        return tincidCodigo;
    }

    public void setTincidCodigo(Integer tincidCodigo) {
        Integer oldTincidCodigo = this.tincidCodigo;
        this.tincidCodigo = tincidCodigo;
        changeSupport.firePropertyChange("tincidCodigo", oldTincidCodigo, tincidCodigo);
    }

    public String getTincidDescripcion() {
        return tincidDescripcion;
    }

    public void setTincidDescripcion(String tincidDescripcion) {
        String oldTincidDescripcion = this.tincidDescripcion;
        this.tincidDescripcion = tincidDescripcion;
        changeSupport.firePropertyChange("tincidDescripcion", oldTincidDescripcion, tincidDescripcion);
    }

    @XmlTransient
    public List<BglTbCatalogoincidentes> getBglTbCatalogoincidentesList() {
        return bglTbCatalogoincidentesList;
    }

    public void setBglTbCatalogoincidentesList(List<BglTbCatalogoincidentes> bglTbCatalogoincidentesList) {
        this.bglTbCatalogoincidentesList = bglTbCatalogoincidentesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tincidCodigo != null ? tincidCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BglTbTipoincidente)) {
            return false;
        }
        BglTbTipoincidente other = (BglTbTipoincidente) object;
        if ((this.tincidCodigo == null && other.tincidCodigo != null) || (this.tincidCodigo != null && !this.tincidCodigo.equals(other.tincidCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "util.BglTbTipoincidente[ tincidCodigo=" + tincidCodigo + " ]";
        return tincidDescripcion;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
