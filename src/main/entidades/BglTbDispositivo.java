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
@Table(name = "bgl_tb_dispositivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BglTbDispositivo.findAll", query = "SELECT b FROM BglTbDispositivo b")
    , @NamedQuery(name = "BglTbDispositivo.findByDispCodigo", query = "SELECT b FROM BglTbDispositivo b WHERE b.dispCodigo = :dispCodigo")
    , @NamedQuery(name = "BglTbDispositivo.findByDispEtiqueta", query = "SELECT b FROM BglTbDispositivo b WHERE b.dispEtiqueta = :dispEtiqueta")
    , @NamedQuery(name = "BglTbDispositivo.findByDispMarca", query = "SELECT b FROM BglTbDispositivo b WHERE b.dispMarca = :dispMarca")
    , @NamedQuery(name = "BglTbDispositivo.findByDispModelo", query = "SELECT b FROM BglTbDispositivo b WHERE b.dispModelo = :dispModelo")
    , @NamedQuery(name = "BglTbDispositivo.findByDispFabricante", query = "SELECT b FROM BglTbDispositivo b WHERE b.dispFabricante = :dispFabricante")
    , @NamedQuery(name = "BglTbDispositivo.findByDispCosto", query = "SELECT b FROM BglTbDispositivo b WHERE b.dispCosto = :dispCosto")
    , @NamedQuery(name = "BglTbDispositivo.findByDispAniofab", query = "SELECT b FROM BglTbDispositivo b WHERE b.dispAniofab = :dispAniofab")
    , @NamedQuery(name = "BglTbDispositivo.findByDispSerial", query = "SELECT b FROM BglTbDispositivo b WHERE b.dispSerial = :dispSerial")})
public class BglTbDispositivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "DISP_CODIGO")
    private Integer dispCodigo;
    @Column(name = "DISP_ETIQUETA")
    private String dispEtiqueta;
    @Column(name = "DISP_MARCA")
    private String dispMarca;
    @Column(name = "DISP_MODELO")
    private String dispModelo;
    @Column(name = "DISP_FABRICANTE")
    private String dispFabricante;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DISP_COSTO")
    private Double dispCosto;
    @Column(name = "DISP_ANIOFAB")
    private Integer dispAniofab;
    @Column(name = "DISP_SERIAL")
    private String dispSerial;
    @JoinColumn(name = "DISP_SIT_CODIGO", referencedColumnName = "SIT_CODIGO")
    @ManyToOne(optional = false)
    private BglTbSitioincidente dispSitCodigo;
    @JoinColumn(name = "DISP_TDISP_CODIGO", referencedColumnName = "TDISP_CODIGO")
    @ManyToOne(optional = false)
    private BglTbTipodispositivo dispTdispCodigo;

    public BglTbDispositivo() {
    }

    public BglTbDispositivo(Integer dispCodigo) {
        this.dispCodigo = dispCodigo;
    }

    public Integer getDispCodigo() {
        return dispCodigo;
    }

    public void setDispCodigo(Integer dispCodigo) {
        this.dispCodigo = dispCodigo;
    }

    public String getDispEtiqueta() {
        return dispEtiqueta;
    }

    public void setDispEtiqueta(String dispEtiqueta) {
        this.dispEtiqueta = dispEtiqueta;
    }

    public String getDispMarca() {
        return dispMarca;
    }

    public void setDispMarca(String dispMarca) {
        this.dispMarca = dispMarca;
    }

    public String getDispModelo() {
        return dispModelo;
    }

    public void setDispModelo(String dispModelo) {
        this.dispModelo = dispModelo;
    }

    public String getDispFabricante() {
        return dispFabricante;
    }

    public void setDispFabricante(String dispFabricante) {
        this.dispFabricante = dispFabricante;
    }

    public Double getDispCosto() {
        return dispCosto;
    }

    public void setDispCosto(Double dispCosto) {
        this.dispCosto = dispCosto;
    }

    public Integer getDispAniofab() {
        return dispAniofab;
    }

    public void setDispAniofab(Integer dispAniofab) {
        this.dispAniofab = dispAniofab;
    }

    public String getDispSerial() {
        return dispSerial;
    }

    public void setDispSerial(String dispSerial) {
        this.dispSerial = dispSerial;
    }

    public BglTbSitioincidente getDispSitCodigo() {
        return dispSitCodigo;
    }

    public void setDispSitCodigo(BglTbSitioincidente dispSitCodigo) {
        this.dispSitCodigo = dispSitCodigo;
    }

    public BglTbTipodispositivo getDispTdispCodigo() {
        return dispTdispCodigo;
    }

    public void setDispTdispCodigo(BglTbTipodispositivo dispTdispCodigo) {
        this.dispTdispCodigo = dispTdispCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dispCodigo != null ? dispCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BglTbDispositivo)) {
            return false;
        }
        BglTbDispositivo other = (BglTbDispositivo) object;
        if ((this.dispCodigo == null && other.dispCodigo != null) || (this.dispCodigo != null && !this.dispCodigo.equals(other.dispCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "util.BglTbDispositivo[ dispCodigo=" + dispCodigo + " ]";
        return dispCodigo.toString();
    }
    
}
