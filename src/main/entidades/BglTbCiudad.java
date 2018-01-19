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
@Table(name = "bgl_tb_ciudad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BglTbCiudad.findAll", query = "SELECT b FROM BglTbCiudad b")
    , @NamedQuery(name = "BglTbCiudad.findByCiuCodigo", query = "SELECT b FROM BglTbCiudad b WHERE b.ciuCodigo = :ciuCodigo")
    , @NamedQuery(name = "BglTbCiudad.findByCiuNombre", query = "SELECT b FROM BglTbCiudad b WHERE b.ciuNombre = :ciuNombre")
    , @NamedQuery(name = "BglTbCiudad.findByCiuLatitud", query = "SELECT b FROM BglTbCiudad b WHERE b.ciuLatitud = :ciuLatitud")
    , @NamedQuery(name = "BglTbCiudad.findByCiuLongitud", query = "SELECT b FROM BglTbCiudad b WHERE b.ciuLongitud = :ciuLongitud")})
public class BglTbCiudad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CIU_CODIGO")
    private String ciuCodigo;
    @Basic(optional = false)
    @Column(name = "CIU_NOMBRE")
    private String ciuNombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CIU_LATITUD")
    private Double ciuLatitud;
    @Column(name = "CIU_LONGITUD")
    private Double ciuLongitud;

    public BglTbCiudad() {
    }

    public BglTbCiudad(String ciuCodigo) {
        this.ciuCodigo = ciuCodigo;
    }

    public BglTbCiudad(String ciuCodigo, String ciuNombre) {
        this.ciuCodigo = ciuCodigo;
        this.ciuNombre = ciuNombre;
    }

    public String getCiuCodigo() {
        return ciuCodigo;
    }

    public void setCiuCodigo(String ciuCodigo) {
        this.ciuCodigo = ciuCodigo;
    }

    public String getCiuNombre() {
        return ciuNombre;
    }

    public void setCiuNombre(String ciuNombre) {
        this.ciuNombre = ciuNombre;
    }

    public Double getCiuLatitud() {
        return ciuLatitud;
    }

    public void setCiuLatitud(Double ciuLatitud) {
        this.ciuLatitud = ciuLatitud;
    }

    public Double getCiuLongitud() {
        return ciuLongitud;
    }

    public void setCiuLongitud(Double ciuLongitud) {
        this.ciuLongitud = ciuLongitud;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ciuCodigo != null ? ciuCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BglTbCiudad)) {
            return false;
        }
        BglTbCiudad other = (BglTbCiudad) object;
        if ((this.ciuCodigo == null && other.ciuCodigo != null) || (this.ciuCodigo != null && !this.ciuCodigo.equals(other.ciuCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "main.entidades.BglTbCiudad[ ciuCodigo=" + ciuCodigo + " ]";
            return ciuNombre;
    }
    
}
