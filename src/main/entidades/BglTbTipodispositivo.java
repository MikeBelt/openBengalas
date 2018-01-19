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
@Table(name = "bgl_tb_tipodispositivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BglTbTipodispositivo.findAll", query = "SELECT b FROM BglTbTipodispositivo b")
    , @NamedQuery(name = "BglTbTipodispositivo.findByTdispCodigo", query = "SELECT b FROM BglTbTipodispositivo b WHERE b.tdispCodigo = :tdispCodigo")
    , @NamedQuery(name = "BglTbTipodispositivo.findByTdispDescripcion", query = "SELECT b FROM BglTbTipodispositivo b WHERE b.tdispDescripcion = :tdispDescripcion")})
public class BglTbTipodispositivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TDISP_CODIGO")
    private Integer tdispCodigo;
    @Basic(optional = false)
    @Column(name = "TDISP_DESCRIPCION")
    private String tdispDescripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dispTdispCodigo")
    private List<BglTbDispositivo> bglTbDispositivoList;

    public BglTbTipodispositivo() {
    }

    public BglTbTipodispositivo(Integer tdispCodigo) {
        this.tdispCodigo = tdispCodigo;
    }

    public BglTbTipodispositivo(Integer tdispCodigo, String tdispDescripcion) {
        this.tdispCodigo = tdispCodigo;
        this.tdispDescripcion = tdispDescripcion;
    }

    public Integer getTdispCodigo() {
        return tdispCodigo;
    }

    public void setTdispCodigo(Integer tdispCodigo) {
        this.tdispCodigo = tdispCodigo;
    }

    public String getTdispDescripcion() {
        return tdispDescripcion;
    }

    public void setTdispDescripcion(String tdispDescripcion) {
        this.tdispDescripcion = tdispDescripcion;
    }

    @XmlTransient
    public List<BglTbDispositivo> getBglTbDispositivoList() {
        return bglTbDispositivoList;
    }

    public void setBglTbDispositivoList(List<BglTbDispositivo> bglTbDispositivoList) {
        this.bglTbDispositivoList = bglTbDispositivoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tdispCodigo != null ? tdispCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BglTbTipodispositivo)) {
            return false;
        }
        BglTbTipodispositivo other = (BglTbTipodispositivo) object;
        if ((this.tdispCodigo == null && other.tdispCodigo != null) || (this.tdispCodigo != null && !this.tdispCodigo.equals(other.tdispCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "util.BglTbTipodispositivo[ tdispCodigo=" + tdispCodigo + " ]";
            return tdispDescripcion;
                    
    }
    
}
