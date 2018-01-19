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
@Table(name = "bgl_tb_sector")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BglTbSector.findAll", query = "SELECT b FROM BglTbSector b")
    , @NamedQuery(name = "BglTbSector.findBySecCodigo", query = "SELECT b FROM BglTbSector b WHERE b.secCodigo = :secCodigo")
    , @NamedQuery(name = "BglTbSector.findBySecDescripcion", query = "SELECT b FROM BglTbSector b WHERE b.secDescripcion = :secDescripcion")})
public class BglTbSector implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SEC_CODIGO")
    private Integer secCodigo;
    @Basic(optional = false)
    @Column(name = "SEC_DESCRIPCION")
    private String secDescripcion;

    public BglTbSector() {
    }

    public BglTbSector(Integer secCodigo) {
        this.secCodigo = secCodigo;
    }

    public BglTbSector(Integer secCodigo, String secDescripcion) {
        this.secCodigo = secCodigo;
        this.secDescripcion = secDescripcion;
    }

    public Integer getSecCodigo() {
        return secCodigo;
    }

    public void setSecCodigo(Integer secCodigo) {
        this.secCodigo = secCodigo;
    }

    public String getSecDescripcion() {
        return secDescripcion;
    }

    public void setSecDescripcion(String secDescripcion) {
        this.secDescripcion = secDescripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secCodigo != null ? secCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BglTbSector)) {
            return false;
        }
        BglTbSector other = (BglTbSector) object;
        if ((this.secCodigo == null && other.secCodigo != null) || (this.secCodigo != null && !this.secCodigo.equals(other.secCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "main.entidades.BglTbSector[ secCodigo=" + secCodigo + " ]";
        return secDescripcion;
    }
    
}
