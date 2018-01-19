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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author michael.beltran
 */
@Entity
@Table(name = "bgl_tb_sitioincidente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BglTbSitioincidente.findAll", query = "SELECT b FROM BglTbSitioincidente b")
    , @NamedQuery(name = "BglTbSitioincidente.findBySitCodigo", query = "SELECT b FROM BglTbSitioincidente b WHERE b.sitCodigo = :sitCodigo")
    , @NamedQuery(name = "BglTbSitioincidente.findBySitNombre", query = "SELECT b FROM BglTbSitioincidente b WHERE b.sitNombre = :sitNombre")
    , @NamedQuery(name = "BglTbSitioincidente.findBySitLatitud", query = "SELECT b FROM BglTbSitioincidente b WHERE b.sitLatitud = :sitLatitud")
    , @NamedQuery(name = "BglTbSitioincidente.findBySitLongitud", query = "SELECT b FROM BglTbSitioincidente b WHERE b.sitLongitud = :sitLongitud")
    , @NamedQuery(name = "BglTbSitioincidente.findBySitAvenida", query = "SELECT b FROM BglTbSitioincidente b WHERE b.sitAvenida = :sitAvenida")
    , @NamedQuery(name = "BglTbSitioincidente.findBySitCalle", query = "SELECT b FROM BglTbSitioincidente b WHERE b.sitCalle = :sitCalle")
    , @NamedQuery(name = "BglTbSitioincidente.findBySitReferencia", query = "SELECT b FROM BglTbSitioincidente b WHERE b.sitReferencia = :sitReferencia")
    , @NamedQuery(name = "BglTbSitioincidente.findBySitHoraaper", query = "SELECT b FROM BglTbSitioincidente b WHERE b.sitHoraaper = :sitHoraaper")
    , @NamedQuery(name = "BglTbSitioincidente.findBySitHoracierre", query = "SELECT b FROM BglTbSitioincidente b WHERE b.sitHoracierre = :sitHoracierre")})
public class BglTbSitioincidente implements Serializable {

    @Basic(optional = false)
    @Column(name = "SIT_CIU_CODIGO")
    private String sitCiuCodigo;
    @Basic(optional = false)
    @Column(name = "SIT_SECTOR")
    private int sitSector;
    

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SIT_CODIGO")
    private Integer sitCodigo;
    @Basic(optional = false)
    @Column(name = "SIT_NOMBRE")
    private String sitNombre;
    @Basic(optional = false)
    @Column(name = "SIT_LATITUD")
    private double sitLatitud;
    @Basic(optional = false)
    @Column(name = "SIT_LONGITUD")
    private double sitLongitud;
    @Column(name = "SIT_AVENIDA")
    private String sitAvenida;
    @Column(name = "SIT_CALLE")
    private String sitCalle;
    @Column(name = "SIT_REFERENCIA")
    private String sitReferencia;
    @Column(name = "SIT_HORAAPER")
    private String sitHoraaper;
    @Column(name = "SIT_HORACIERRE")
    private String sitHoracierre;
    @JoinColumn(name = "SIT_TSIT_CODIGO", referencedColumnName = "TSIT_CODIGO")
    @ManyToOne(optional = false)
    private BglTbTipositio sitTsitCodigo;

    public BglTbSitioincidente() {
    }

    public BglTbSitioincidente(Integer sitCodigo) {
        this.sitCodigo = sitCodigo;
    }

    public BglTbSitioincidente(Integer sitCodigo, String sitNombre, double sitLatitud, double sitLongitud) {
        this.sitCodigo = sitCodigo;
        this.sitNombre = sitNombre;
        this.sitLatitud = sitLatitud;
        this.sitLongitud = sitLongitud;
    }

    public Integer getSitCodigo() {
        return sitCodigo;
    }

    public void setSitCodigo(Integer sitCodigo) {
        this.sitCodigo = sitCodigo;
    }

    public String getSitNombre() {
        return sitNombre;
    }

    public void setSitNombre(String sitNombre) {
        this.sitNombre = sitNombre;
    }

    public double getSitLatitud() {
        return sitLatitud;
    }

    public void setSitLatitud(double sitLatitud) {
        this.sitLatitud = sitLatitud;
    }

    public double getSitLongitud() {
        return sitLongitud;
    }

    public void setSitLongitud(double sitLongitud) {
        this.sitLongitud = sitLongitud;
    }

    public String getSitAvenida() {
        return sitAvenida;
    }

    public void setSitAvenida(String sitAvenida) {
        this.sitAvenida = sitAvenida;
    }

    public String getSitCalle() {
        return sitCalle;
    }

    public void setSitCalle(String sitCalle) {
        this.sitCalle = sitCalle;
    }

    public String getSitReferencia() {
        return sitReferencia;
    }

    public void setSitReferencia(String sitReferencia) {
        this.sitReferencia = sitReferencia;
    }

    public String getSitHoraaper() {
        return sitHoraaper;
    }

    public void setSitHoraaper(String sitHoraaper) {
        this.sitHoraaper = sitHoraaper;
    }

    public String getSitHoracierre() {
        return sitHoracierre;
    }

    public void setSitHoracierre(String sitHoracierre) {
        this.sitHoracierre = sitHoracierre;
    }

    public BglTbTipositio getSitTsitCodigo() {
        return sitTsitCodigo;
    }

    public void setSitTsitCodigo(BglTbTipositio sitTsitCodigo) {
        this.sitTsitCodigo = sitTsitCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sitCodigo != null ? sitCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BglTbSitioincidente)) {
            return false;
        }
        BglTbSitioincidente other = (BglTbSitioincidente) object;
        if ((this.sitCodigo == null && other.sitCodigo != null) || (this.sitCodigo != null && !this.sitCodigo.equals(other.sitCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "main.entidades.BglTbSitioincidente[ sitCodigo=" + sitCodigo + " ]";
        return sitNombre;
    }

    public String getSitCiuCodigo() {
        return sitCiuCodigo;
    }

    public void setSitCiuCodigo(String sitCiuCodigo) {
        this.sitCiuCodigo = sitCiuCodigo;
    }

    public int getSitSector() {
        return sitSector;
    }

    public void setSitSector(int sitSector) {
        this.sitSector = sitSector;
    }


    
}
