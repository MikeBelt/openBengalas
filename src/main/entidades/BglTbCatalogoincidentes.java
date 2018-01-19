/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author michael.beltran
 */
@Entity
@Table(name = "bgl_tb_catalogoincidentes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BglTbCatalogoincidentes.findAll", query = "SELECT b FROM BglTbCatalogoincidentes b")
    , @NamedQuery(name = "BglTbCatalogoincidentes.findByCatCodigo", query = "SELECT b FROM BglTbCatalogoincidentes b WHERE b.catCodigo = :catCodigo")
    , @NamedQuery(name = "BglTbCatalogoincidentes.findByCatDescripcion", query = "SELECT b FROM BglTbCatalogoincidentes b WHERE b.catDescripcion = :catDescripcion")})
public class BglTbCatalogoincidentes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CAT_CODIGO")
    private Integer catCodigo;
    @Basic(optional = false)
    @Column(name = "CAT_DESCRIPCION")
    private String catDescripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "emTincCodigo")
    private List<BglTbEmergencia> bglTbEmergenciaList;
    @JoinColumn(name = "CAT_TINCID_CODIGO", referencedColumnName = "TINCID_CODIGO")
    @ManyToOne(optional = false)
    private BglTbTipoincidente catTincidCodigo;

    public BglTbCatalogoincidentes() {
    }

    public BglTbCatalogoincidentes(Integer catCodigo) {
        this.catCodigo = catCodigo;
    }

    public BglTbCatalogoincidentes(Integer catCodigo, String catDescripcion) {
        this.catCodigo = catCodigo;
        this.catDescripcion = catDescripcion;
    }

    public Integer getCatCodigo() {
        return catCodigo;
    }

    public void setCatCodigo(Integer catCodigo) {
        this.catCodigo = catCodigo;
    }

    public String getCatDescripcion() {
        return catDescripcion;
    }

    public void setCatDescripcion(String catDescripcion) {
        this.catDescripcion = catDescripcion;
    }

    @XmlTransient
    public List<BglTbEmergencia> getBglTbEmergenciaList() {
        return bglTbEmergenciaList;
    }

    public void setBglTbEmergenciaList(List<BglTbEmergencia> bglTbEmergenciaList) {
        this.bglTbEmergenciaList = bglTbEmergenciaList;
    }

    public BglTbTipoincidente getCatTincidCodigo() {
        return catTincidCodigo;
    }

    public void setCatTincidCodigo(BglTbTipoincidente catTincidCodigo) {
        this.catTincidCodigo = catTincidCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (catCodigo != null ? catCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BglTbCatalogoincidentes)) {
            return false;
        }
        BglTbCatalogoincidentes other = (BglTbCatalogoincidentes) object;
        if ((this.catCodigo == null && other.catCodigo != null) || (this.catCodigo != null && !this.catCodigo.equals(other.catCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.BglTbCatalogoincidentes[ catCodigo=" + catCodigo + " ]";
    }
    
}
