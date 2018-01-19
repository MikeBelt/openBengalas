/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Priscila
 */
@Entity
@Table(name = "bgl_tb_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BglTbUsuario.findAll", query = "SELECT b FROM BglTbUsuario b")
    , @NamedQuery(name = "BglTbUsuario.findByUsuCodigo", query = "SELECT b FROM BglTbUsuario b WHERE b.usuCodigo = :usuCodigo")
    , @NamedQuery(name = "BglTbUsuario.findByUsuAlias", query = "SELECT b FROM BglTbUsuario b WHERE b.usuAlias = :usuAlias")
    , @NamedQuery(name = "BglTbUsuario.findByUsuNombre", query = "SELECT b FROM BglTbUsuario b WHERE b.usuNombre = :usuNombre")
    , @NamedQuery(name = "BglTbUsuario.findByUsuApellido", query = "SELECT b FROM BglTbUsuario b WHERE b.usuApellido = :usuApellido")
    , @NamedQuery(name = "BglTbUsuario.findByUsuHash", query = "SELECT b FROM BglTbUsuario b WHERE b.usuHash = :usuHash")
    , @NamedQuery(name = "BglTbUsuario.findByUsuSalt", query = "SELECT b FROM BglTbUsuario b WHERE b.usuSalt = :usuSalt")
    , @NamedQuery(name = "BglTbUsuario.findByUsuFeculting", query = "SELECT b FROM BglTbUsuario b WHERE b.usuFeculting = :usuFeculting")})
public class BglTbUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USU_CODIGO")
    private Integer usuCodigo;
    @Basic(optional = false)
    @Column(name = "USU_ALIAS")
    private String usuAlias;
    @Basic(optional = false)
    @Column(name = "USU_NOMBRE")
    private String usuNombre;
    @Basic(optional = false)
    @Column(name = "USU_APELLIDO")
    private String usuApellido;
    @Basic(optional = false)
    @Column(name = "USU_HASH")
    private String usuHash;
    @Basic(optional = false)
    @Column(name = "USU_SALT")
    private String usuSalt;
    @Basic(optional = false)
    @Column(name = "USU_FECULTING")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuFeculting;

    public BglTbUsuario() {
    }

    public BglTbUsuario(Integer usuCodigo) {
        this.usuCodigo = usuCodigo;
    }

    public BglTbUsuario(Integer usuCodigo, String usuAlias, String usuNombre, String usuApellido, String usuHash, String usuSalt, Date usuFeculting) {
        this.usuCodigo = usuCodigo;
        this.usuAlias = usuAlias;
        this.usuNombre = usuNombre;
        this.usuApellido = usuApellido;
        this.usuHash = usuHash;
        this.usuSalt = usuSalt;
        this.usuFeculting = usuFeculting;
    }

    public Integer getUsuCodigo() {
        return usuCodigo;
    }

    public void setUsuCodigo(Integer usuCodigo) {
        this.usuCodigo = usuCodigo;
    }

    public String getUsuAlias() {
        return usuAlias;
    }

    public void setUsuAlias(String usuAlias) {
        this.usuAlias = usuAlias;
    }

    public String getUsuNombre() {
        return usuNombre;
    }

    public void setUsuNombre(String usuNombre) {
        this.usuNombre = usuNombre;
    }

    public String getUsuApellido() {
        return usuApellido;
    }

    public void setUsuApellido(String usuApellido) {
        this.usuApellido = usuApellido;
    }

    public String getUsuHash() {
        return usuHash;
    }

    public void setUsuHash(String usuHash) {
        this.usuHash = usuHash;
    }

    public String getUsuSalt() {
        return usuSalt;
    }

    public void setUsuSalt(String usuSalt) {
        this.usuSalt = usuSalt;
    }

    public Date getUsuFeculting() {
        return usuFeculting;
    }

    public void setUsuFeculting(Date usuFeculting) {
        this.usuFeculting = usuFeculting;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuCodigo != null ? usuCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BglTbUsuario)) {
            return false;
        }
        BglTbUsuario other = (BglTbUsuario) object;
        if ((this.usuCodigo == null && other.usuCodigo != null) || (this.usuCodigo != null && !this.usuCodigo.equals(other.usuCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "main.entidades.BglTbUsuario[ usuCodigo=" + usuCodigo + " ]";
    }
    
}
