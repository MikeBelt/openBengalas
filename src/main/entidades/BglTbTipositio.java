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
@Table(name = "bgl_tb_tipositio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BglTbTipositio.findAll", query = "SELECT b FROM BglTbTipositio b")
    , @NamedQuery(name = "BglTbTipositio.findByTsitCodigo", query = "SELECT b FROM BglTbTipositio b WHERE b.tsitCodigo = :tsitCodigo")
    , @NamedQuery(name = "BglTbTipositio.findByTsitDescripcion", query = "SELECT b FROM BglTbTipositio b WHERE b.tsitDescripcion = :tsitDescripcion")})
public class BglTbTipositio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TSIT_CODIGO")
    private Integer tsitCodigo;
    @Basic(optional = false)
    @Column(name = "TSIT_DESCRIPCION")
    private String tsitDescripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sitTsitCodigo")
    private List<BglTbSitioincidente> bglTbSitioincidenteList;

    public BglTbTipositio() {
    }

    public BglTbTipositio(Integer tsitCodigo) {
        this.tsitCodigo = tsitCodigo;
    }

    public BglTbTipositio(Integer tsitCodigo, String tsitDescripcion) {
        this.tsitCodigo = tsitCodigo;
        this.tsitDescripcion = tsitDescripcion;
    }

    public Integer getTsitCodigo() {
        return tsitCodigo;
    }

    public void setTsitCodigo(Integer tsitCodigo) {
        this.tsitCodigo = tsitCodigo;
    }

    public String getTsitDescripcion() {
        return tsitDescripcion;
    }

    public void setTsitDescripcion(String tsitDescripcion) {
        this.tsitDescripcion = tsitDescripcion;
    }

    @XmlTransient
    public List<BglTbSitioincidente> getBglTbSitioincidenteList() {
        return bglTbSitioincidenteList;
    }

    public void setBglTbSitioincidenteList(List<BglTbSitioincidente> bglTbSitioincidenteList) {
        this.bglTbSitioincidenteList = bglTbSitioincidenteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tsitCodigo != null ? tsitCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BglTbTipositio)) {
            return false;
        }
        BglTbTipositio other = (BglTbTipositio) object;
        if ((this.tsitCodigo == null && other.tsitCodigo != null) || (this.tsitCodigo != null && !this.tsitCodigo.equals(other.tsitCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "main.entidades.BglTbTipositio[ tsitCodigo=" + tsitCodigo + " ]";
          return tsitDescripcion;
    }
    
}
