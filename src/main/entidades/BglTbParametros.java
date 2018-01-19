/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author michael.beltran
 */
@Entity
@Table(name = "bgl_tb_parametros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BglTbParametros.findAll", query = "SELECT b FROM BglTbParametros b")
    , @NamedQuery(name = "BglTbParametros.findByParamUsuCodigo", query = "SELECT b FROM BglTbParametros b WHERE b.bglTbParametrosPK.paramUsuCodigo = :paramUsuCodigo")
    , @NamedQuery(name = "BglTbParametros.findByParamCiuCodigo", query = "SELECT b FROM BglTbParametros b WHERE b.bglTbParametrosPK.paramCiuCodigo = :paramCiuCodigo")
    , @NamedQuery(name = "BglTbParametros.findByParamDvrIp", query = "SELECT b FROM BglTbParametros b WHERE b.paramDvrIp = :paramDvrIp")
    , @NamedQuery(name = "BglTbParametros.findByParamDvrPuerto", query = "SELECT b FROM BglTbParametros b WHERE b.paramDvrPuerto = :paramDvrPuerto")
    , @NamedQuery(name = "BglTbParametros.findByParamDvrUsuario", query = "SELECT b FROM BglTbParametros b WHERE b.paramDvrUsuario = :paramDvrUsuario")
    , @NamedQuery(name = "BglTbParametros.findByParamDvrPassword", query = "SELECT b FROM BglTbParametros b WHERE b.paramDvrPassword = :paramDvrPassword")
    , @NamedQuery(name = "BglTbParametros.findByParamDvrTiempomini", query = "SELECT b FROM BglTbParametros b WHERE b.paramDvrTiempomini = :paramDvrTiempomini")
    , @NamedQuery(name = "BglTbParametros.findByParamDvrTiempomaxi", query = "SELECT b FROM BglTbParametros b WHERE b.paramDvrTiempomaxi = :paramDvrTiempomaxi")
    , @NamedQuery(name = "BglTbParametros.findByParamAmbiente", query = "SELECT b FROM BglTbParametros b WHERE b.paramAmbiente = :paramAmbiente")})
public class BglTbParametros implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BglTbParametrosPK bglTbParametrosPK;
    @Basic(optional = false)
    @Column(name = "PARAM_DVR_IP")
    private String paramDvrIp;
    @Basic(optional = false)
    @Column(name = "PARAM_DVR_PUERTO")
    private int paramDvrPuerto;
    @Basic(optional = false)
    @Column(name = "PARAM_DVR_USUARIO")
    private String paramDvrUsuario;
    @Basic(optional = false)
    @Column(name = "PARAM_DVR_PASSWORD")
    private String paramDvrPassword;
    @Basic(optional = false)
    @Column(name = "PARAM_DVR_TIEMPOMINI")
    private int paramDvrTiempomini;
    @Basic(optional = false)
    @Column(name = "PARAM_DVR_TIEMPOMAXI")
    private int paramDvrTiempomaxi;
    @Basic(optional = false)
    @Column(name = "PARAM_AMBIENTE")
    private int paramAmbiente;

    public BglTbParametros() {
    }

    public BglTbParametros(BglTbParametrosPK bglTbParametrosPK) {
        this.bglTbParametrosPK = bglTbParametrosPK;
    }

    public BglTbParametros(BglTbParametrosPK bglTbParametrosPK, String paramDvrIp, int paramDvrPuerto, String paramDvrUsuario, String paramDvrPassword, int paramDvrTiempomini, int paramDvrTiempomaxi, int paramAmbiente) {
        this.bglTbParametrosPK = bglTbParametrosPK;
        this.paramDvrIp = paramDvrIp;
        this.paramDvrPuerto = paramDvrPuerto;
        this.paramDvrUsuario = paramDvrUsuario;
        this.paramDvrPassword = paramDvrPassword;
        this.paramDvrTiempomini = paramDvrTiempomini;
        this.paramDvrTiempomaxi = paramDvrTiempomaxi;
        this.paramAmbiente = paramAmbiente;
    }

    public BglTbParametros(String paramUsuCodigo, String paramCiuCodigo) {
        this.bglTbParametrosPK = new BglTbParametrosPK(paramUsuCodigo, paramCiuCodigo);
    }

    public BglTbParametrosPK getBglTbParametrosPK() {
        return bglTbParametrosPK;
    }

    public void setBglTbParametrosPK(BglTbParametrosPK bglTbParametrosPK) {
        this.bglTbParametrosPK = bglTbParametrosPK;
    }

    public String getParamDvrIp() {
        return paramDvrIp;
    }

    public void setParamDvrIp(String paramDvrIp) {
        this.paramDvrIp = paramDvrIp;
    }

    public int getParamDvrPuerto() {
        return paramDvrPuerto;
    }

    public void setParamDvrPuerto(int paramDvrPuerto) {
        this.paramDvrPuerto = paramDvrPuerto;
    }

    public String getParamDvrUsuario() {
        return paramDvrUsuario;
    }

    public void setParamDvrUsuario(String paramDvrUsuario) {
        this.paramDvrUsuario = paramDvrUsuario;
    }

    public String getParamDvrPassword() {
        return paramDvrPassword;
    }

    public void setParamDvrPassword(String paramDvrPassword) {
        this.paramDvrPassword = paramDvrPassword;
    }

    public int getParamDvrTiempomini() {
        return paramDvrTiempomini;
    }

    public void setParamDvrTiempomini(int paramDvrTiempomini) {
        this.paramDvrTiempomini = paramDvrTiempomini;
    }

    public int getParamDvrTiempomaxi() {
        return paramDvrTiempomaxi;
    }

    public void setParamDvrTiempomaxi(int paramDvrTiempomaxi) {
        this.paramDvrTiempomaxi = paramDvrTiempomaxi;
    }

    public int getParamAmbiente() {
        return paramAmbiente;
    }

    public void setParamAmbiente(int paramAmbiente) {
        this.paramAmbiente = paramAmbiente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bglTbParametrosPK != null ? bglTbParametrosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BglTbParametros)) {
            return false;
        }
        BglTbParametros other = (BglTbParametros) object;
        if ((this.bglTbParametrosPK == null && other.bglTbParametrosPK != null) || (this.bglTbParametrosPK != null && !this.bglTbParametrosPK.equals(other.bglTbParametrosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.BglTbParametros[ bglTbParametrosPK=" + bglTbParametrosPK + " ]";
    }
    
}
