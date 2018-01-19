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
@Table(name = "bgl_tb_emergencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BglTbEmergencia.findAll", query = "SELECT b FROM BglTbEmergencia b")
    , @NamedQuery(name = "BglTbEmergencia.findByEmIdfuente", query = "SELECT b FROM BglTbEmergencia b WHERE b.emIdfuente = :emIdfuente")
    , @NamedQuery(name = "BglTbEmergencia.findByEmCedula", query = "SELECT b FROM BglTbEmergencia b WHERE b.emCedula = :emCedula")
    , @NamedQuery(name = "BglTbEmergencia.findByEmNombre", query = "SELECT b FROM BglTbEmergencia b WHERE b.emNombre = :emNombre")
    , @NamedQuery(name = "BglTbEmergencia.findByEmApellido", query = "SELECT b FROM BglTbEmergencia b WHERE b.emApellido = :emApellido")
    , @NamedQuery(name = "BglTbEmergencia.findByEmCodpais", query = "SELECT b FROM BglTbEmergencia b WHERE b.emCodpais = :emCodpais")
    , @NamedQuery(name = "BglTbEmergencia.findByEmTelefono", query = "SELECT b FROM BglTbEmergencia b WHERE b.emTelefono = :emTelefono")
    , @NamedQuery(name = "BglTbEmergencia.findByEnLatitud", query = "SELECT b FROM BglTbEmergencia b WHERE b.enLatitud = :enLatitud")
    , @NamedQuery(name = "BglTbEmergencia.findByEmLongitud", query = "SELECT b FROM BglTbEmergencia b WHERE b.emLongitud = :emLongitud")
    , @NamedQuery(name = "BglTbEmergencia.findByEmDireccion", query = "SELECT b FROM BglTbEmergencia b WHERE b.emDireccion = :emDireccion")
    , @NamedQuery(name = "BglTbEmergencia.findByEmDescripcion", query = "SELECT b FROM BglTbEmergencia b WHERE b.emDescripcion = :emDescripcion")
    , @NamedQuery(name = "BglTbEmergencia.findByEmEcuatoriano", query = "SELECT b FROM BglTbEmergencia b WHERE b.emEcuatoriano = :emEcuatoriano")
    , @NamedQuery(name = "BglTbEmergencia.findByEmDiscapacidad", query = "SELECT b FROM BglTbEmergencia b WHERE b.emDiscapacidad = :emDiscapacidad")
    , @NamedQuery(name = "BglTbEmergencia.findByEmSangre", query = "SELECT b FROM BglTbEmergencia b WHERE b.emSangre = :emSangre")
    , @NamedQuery(name = "BglTbEmergencia.findByEmAlergias", query = "SELECT b FROM BglTbEmergencia b WHERE b.emAlergias = :emAlergias")
    , @NamedQuery(name = "BglTbEmergencia.findByEmContactNombre", query = "SELECT b FROM BglTbEmergencia b WHERE b.emContactNombre = :emContactNombre")
    , @NamedQuery(name = "BglTbEmergencia.findByEmContactApellido", query = "SELECT b FROM BglTbEmergencia b WHERE b.emContactApellido = :emContactApellido")
    , @NamedQuery(name = "BglTbEmergencia.findByEmContactCodpais", query = "SELECT b FROM BglTbEmergencia b WHERE b.emContactCodpais = :emContactCodpais")
    , @NamedQuery(name = "BglTbEmergencia.findByEmContactTelefono", query = "SELECT b FROM BglTbEmergencia b WHERE b.emContactTelefono = :emContactTelefono")
    , @NamedQuery(name = "BglTbEmergencia.findByEmContactRelacion", query = "SELECT b FROM BglTbEmergencia b WHERE b.emContactRelacion = :emContactRelacion")
    , @NamedQuery(name = "BglTbEmergencia.findByEmTimestamp", query = "SELECT b FROM BglTbEmergencia b WHERE b.emTimestamp = :emTimestamp")})
public class BglTbEmergencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "EM_IDFUENTE")
    private String emIdfuente;
    @Basic(optional = false)
    @Column(name = "EM_CEDULA")
    private String emCedula;
    @Basic(optional = false)
    @Column(name = "EM_NOMBRE")
    private String emNombre;
    @Basic(optional = false)
    @Column(name = "EM_APELLIDO")
    private String emApellido;
    @Basic(optional = false)
    @Column(name = "EM_CODPAIS")
    private String emCodpais;
    @Column(name = "EM_TELEFONO")
    private String emTelefono;
    @Basic(optional = false)
    @Column(name = "EN_LATITUD")
    private double enLatitud;
    @Basic(optional = false)
    @Column(name = "EM_LONGITUD")
    private double emLongitud;
    @Basic(optional = false)
    @Column(name = "EM_DIRECCION")
    private String emDireccion;
    @Basic(optional = false)
    @Column(name = "EM_DESCRIPCION")
    private String emDescripcion;
    @Column(name = "EM_ECUATORIANO")
    private Boolean emEcuatoriano;
    @Column(name = "EM_DISCAPACIDAD")
    private String emDiscapacidad;
    @Column(name = "EM_SANGRE")
    private String emSangre;
    @Column(name = "EM_ALERGIAS")
    private Boolean emAlergias;
    @Column(name = "EM_CONTACT_NOMBRE")
    private String emContactNombre;
    @Column(name = "EM_CONTACT_APELLIDO")
    private String emContactApellido;
    @Column(name = "EM_CONTACT_CODPAIS")
    private String emContactCodpais;
    @Column(name = "EM_CONTACT_TELEFONO")
    private String emContactTelefono;
    @Column(name = "EM_CONTACT_RELACION")
    private String emContactRelacion;
    @Basic(optional = false)
    @Column(name = "EM_TIMESTAMP")
    private String emTimestamp;
    @JoinColumn(name = "EM_ESTADO", referencedColumnName = "EST_CODIGO")
    @ManyToOne(optional = false)
    private BglTbEstadoemergencia emEstado;
    @JoinColumn(name = "EM_TINC_CODIGO", referencedColumnName = "CAT_CODIGO")
    @ManyToOne(optional = false)
    private BglTbCatalogoincidentes emTincCodigo;

    public BglTbEmergencia() {
    }

    public BglTbEmergencia(String emIdfuente) {
        this.emIdfuente = emIdfuente;
    }

    public BglTbEmergencia(String emIdfuente, String emCedula, String emNombre, String emApellido, String emCodpais, double enLatitud, double emLongitud, String emDireccion, String emDescripcion, String emTimestamp) {
        this.emIdfuente = emIdfuente;
        this.emCedula = emCedula;
        this.emNombre = emNombre;
        this.emApellido = emApellido;
        this.emCodpais = emCodpais;
        this.enLatitud = enLatitud;
        this.emLongitud = emLongitud;
        this.emDireccion = emDireccion;
        this.emDescripcion = emDescripcion;
        this.emTimestamp = emTimestamp;
    }

    public String getEmIdfuente() {
        return emIdfuente;
    }

    public void setEmIdfuente(String emIdfuente) {
        this.emIdfuente = emIdfuente;
    }

    public String getEmCedula() {
        return emCedula;
    }

    public void setEmCedula(String emCedula) {
        this.emCedula = emCedula;
    }

    public String getEmNombre() {
        return emNombre;
    }

    public void setEmNombre(String emNombre) {
        this.emNombre = emNombre;
    }

    public String getEmApellido() {
        return emApellido;
    }

    public void setEmApellido(String emApellido) {
        this.emApellido = emApellido;
    }

    public String getEmCodpais() {
        return emCodpais;
    }

    public void setEmCodpais(String emCodpais) {
        this.emCodpais = emCodpais;
    }

    public String getEmTelefono() {
        return emTelefono;
    }

    public void setEmTelefono(String emTelefono) {
        this.emTelefono = emTelefono;
    }

    public double getEnLatitud() {
        return enLatitud;
    }

    public void setEnLatitud(double enLatitud) {
        this.enLatitud = enLatitud;
    }

    public double getEmLongitud() {
        return emLongitud;
    }

    public void setEmLongitud(double emLongitud) {
        this.emLongitud = emLongitud;
    }

    public String getEmDireccion() {
        return emDireccion;
    }

    public void setEmDireccion(String emDireccion) {
        this.emDireccion = emDireccion;
    }

    public String getEmDescripcion() {
        return emDescripcion;
    }

    public void setEmDescripcion(String emDescripcion) {
        this.emDescripcion = emDescripcion;
    }

    public Boolean getEmEcuatoriano() {
        return emEcuatoriano;
    }

    public void setEmEcuatoriano(Boolean emEcuatoriano) {
        this.emEcuatoriano = emEcuatoriano;
    }

    public String getEmDiscapacidad() {
        return emDiscapacidad;
    }

    public void setEmDiscapacidad(String emDiscapacidad) {
        this.emDiscapacidad = emDiscapacidad;
    }

    public String getEmSangre() {
        return emSangre;
    }

    public void setEmSangre(String emSangre) {
        this.emSangre = emSangre;
    }

    public Boolean getEmAlergias() {
        return emAlergias;
    }

    public void setEmAlergias(Boolean emAlergias) {
        this.emAlergias = emAlergias;
    }

    public String getEmContactNombre() {
        return emContactNombre;
    }

    public void setEmContactNombre(String emContactNombre) {
        this.emContactNombre = emContactNombre;
    }

    public String getEmContactApellido() {
        return emContactApellido;
    }

    public void setEmContactApellido(String emContactApellido) {
        this.emContactApellido = emContactApellido;
    }

    public String getEmContactCodpais() {
        return emContactCodpais;
    }

    public void setEmContactCodpais(String emContactCodpais) {
        this.emContactCodpais = emContactCodpais;
    }

    public String getEmContactTelefono() {
        return emContactTelefono;
    }

    public void setEmContactTelefono(String emContactTelefono) {
        this.emContactTelefono = emContactTelefono;
    }

    public String getEmContactRelacion() {
        return emContactRelacion;
    }

    public void setEmContactRelacion(String emContactRelacion) {
        this.emContactRelacion = emContactRelacion;
    }

    public String getEmTimestamp() {
        return emTimestamp;
    }

    public void setEmTimestamp(String emTimestamp) {
        this.emTimestamp = emTimestamp;
    }

    public BglTbEstadoemergencia getEmEstado() {
        return emEstado;
    }

    public void setEmEstado(BglTbEstadoemergencia emEstado) {
        this.emEstado = emEstado;
    }

    public BglTbCatalogoincidentes getEmTincCodigo() {
        return emTincCodigo;
    }

    public void setEmTincCodigo(BglTbCatalogoincidentes emTincCodigo) {
        this.emTincCodigo = emTincCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emIdfuente != null ? emIdfuente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BglTbEmergencia)) {
            return false;
        }
        BglTbEmergencia other = (BglTbEmergencia) object;
        if ((this.emIdfuente == null && other.emIdfuente != null) || (this.emIdfuente != null && !this.emIdfuente.equals(other.emIdfuente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.BglTbEmergencia[ emIdfuente=" + emIdfuente + " ]";
    }
    
}
