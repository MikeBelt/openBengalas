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
@Table(name = "bgl_tb_estadoemergencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BglTbEstadoemergencia.findAll", query = "SELECT b FROM BglTbEstadoemergencia b")
    , @NamedQuery(name = "BglTbEstadoemergencia.findByEstCodigo", query = "SELECT b FROM BglTbEstadoemergencia b WHERE b.estCodigo = :estCodigo")
    , @NamedQuery(name = "BglTbEstadoemergencia.findByEstDescCorta", query = "SELECT b FROM BglTbEstadoemergencia b WHERE b.estDescCorta = :estDescCorta")
    , @NamedQuery(name = "BglTbEstadoemergencia.findByEstDescripcion", query = "SELECT b FROM BglTbEstadoemergencia b WHERE b.estDescripcion = :estDescripcion")})
public class BglTbEstadoemergencia implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "EST_CODIGO")
    private Integer estCodigo;
    @Basic(optional = false)
    @Column(name = "EST_DESC_CORTA")
    private String estDescCorta;
    @Basic(optional = false)
    @Column(name = "EST_DESCRIPCION")
    private String estDescripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "emEstado")
    private List<BglTbEmergencia> bglTbEmergenciaList;

    public BglTbEstadoemergencia() {
    }

    public BglTbEstadoemergencia(Integer estCodigo) {
        this.estCodigo = estCodigo;
    }

    public BglTbEstadoemergencia(Integer estCodigo, String estDescCorta, String estDescripcion) {
        this.estCodigo = estCodigo;
        this.estDescCorta = estDescCorta;
        this.estDescripcion = estDescripcion;
    }

    public Integer getEstCodigo() {
        return estCodigo;
    }

    public void setEstCodigo(Integer estCodigo) {
        Integer oldEstCodigo = this.estCodigo;
        this.estCodigo = estCodigo;
        changeSupport.firePropertyChange("estCodigo", oldEstCodigo, estCodigo);
    }

    public String getEstDescCorta() {
        return estDescCorta;
    }

    public void setEstDescCorta(String estDescCorta) {
        String oldEstDescCorta = this.estDescCorta;
        this.estDescCorta = estDescCorta;
        changeSupport.firePropertyChange("estDescCorta", oldEstDescCorta, estDescCorta);
    }

    public String getEstDescripcion() {
        return estDescripcion;
    }

    public void setEstDescripcion(String estDescripcion) {
        String oldEstDescripcion = this.estDescripcion;
        this.estDescripcion = estDescripcion;
        changeSupport.firePropertyChange("estDescripcion", oldEstDescripcion, estDescripcion);
    }

    @XmlTransient
    public List<BglTbEmergencia> getBglTbEmergenciaList() {
        return bglTbEmergenciaList;
    }

    public void setBglTbEmergenciaList(List<BglTbEmergencia> bglTbEmergenciaList) {
        this.bglTbEmergenciaList = bglTbEmergenciaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estCodigo != null ? estCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BglTbEstadoemergencia)) {
            return false;
        }
        BglTbEstadoemergencia other = (BglTbEstadoemergencia) object;
        if ((this.estCodigo == null && other.estCodigo != null) || (this.estCodigo != null && !this.estCodigo.equals(other.estCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.BglTbEstadoemergencia[ estCodigo=" + estCodigo + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
