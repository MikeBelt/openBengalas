/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author michael.beltran
 */
@Entity
@Table(name = "bgl_tb_tipoactividad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BglTbTipoactividad.findAll", query = "SELECT b FROM BglTbTipoactividad b")
    , @NamedQuery(name = "BglTbTipoactividad.findByTactCodigo", query = "SELECT b FROM BglTbTipoactividad b WHERE b.tactCodigo = :tactCodigo")
    , @NamedQuery(name = "BglTbTipoactividad.findByTactDescCorta", query = "SELECT b FROM BglTbTipoactividad b WHERE b.tactDescCorta = :tactDescCorta")
    , @NamedQuery(name = "BglTbTipoactividad.findByTactDescLarga", query = "SELECT b FROM BglTbTipoactividad b WHERE b.tactDescLarga = :tactDescLarga")})
public class BglTbTipoactividad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TACT_CODIGO")
    private Integer tactCodigo;
    @Basic(optional = false)
    @Column(name = "TACT_DESC_CORTA")
    private String tactDescCorta;
    @Column(name = "TACT_DESC_LARGA")
    private String tactDescLarga;

    public BglTbTipoactividad() {
    }

    public BglTbTipoactividad(Integer tactCodigo) {
        this.tactCodigo = tactCodigo;
    }

    public BglTbTipoactividad(Integer tactCodigo, String tactDescCorta) {
        this.tactCodigo = tactCodigo;
        this.tactDescCorta = tactDescCorta;
    }

    public Integer getTactCodigo() {
        return tactCodigo;
    }

    public void setTactCodigo(Integer tactCodigo) {
        this.tactCodigo = tactCodigo;
    }

    public String getTactDescCorta() {
        return tactDescCorta;
    }

    public void setTactDescCorta(String tactDescCorta) {
        this.tactDescCorta = tactDescCorta;
    }

    public String getTactDescLarga() {
        return tactDescLarga;
    }

    public void setTactDescLarga(String tactDescLarga) {
        this.tactDescLarga = tactDescLarga;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tactCodigo != null ? tactCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BglTbTipoactividad)) {
            return false;
        }
        BglTbTipoactividad other = (BglTbTipoactividad) object;
        if ((this.tactCodigo == null && other.tactCodigo != null) || (this.tactCodigo != null && !this.tactCodigo.equals(other.tactCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.BglTbTipoactividad[ tactCodigo=" + tactCodigo + " ]";
    }
    
}
