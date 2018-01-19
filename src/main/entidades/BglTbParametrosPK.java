/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author michael.beltran
 */
@Embeddable
public class BglTbParametrosPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "PARAM_USU_CODIGO")
    private String paramUsuCodigo;
    @Basic(optional = false)
    @Column(name = "PARAM_CIU_CODIGO")
    private String paramCiuCodigo;

    public BglTbParametrosPK() {
    }

    public BglTbParametrosPK(String paramUsuCodigo, String paramCiuCodigo) {
        this.paramUsuCodigo = paramUsuCodigo;
        this.paramCiuCodigo = paramCiuCodigo;
    }

    public String getParamUsuCodigo() {
        return paramUsuCodigo;
    }

    public void setParamUsuCodigo(String paramUsuCodigo) {
        this.paramUsuCodigo = paramUsuCodigo;
    }

    public String getParamCiuCodigo() {
        return paramCiuCodigo;
    }

    public void setParamCiuCodigo(String paramCiuCodigo) {
        this.paramCiuCodigo = paramCiuCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paramUsuCodigo != null ? paramUsuCodigo.hashCode() : 0);
        hash += (paramCiuCodigo != null ? paramCiuCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BglTbParametrosPK)) {
            return false;
        }
        BglTbParametrosPK other = (BglTbParametrosPK) object;
        if ((this.paramUsuCodigo == null && other.paramUsuCodigo != null) || (this.paramUsuCodigo != null && !this.paramUsuCodigo.equals(other.paramUsuCodigo))) {
            return false;
        }
        if ((this.paramCiuCodigo == null && other.paramCiuCodigo != null) || (this.paramCiuCodigo != null && !this.paramCiuCodigo.equals(other.paramCiuCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.BglTbParametrosPK[ paramUsuCodigo=" + paramUsuCodigo + ", paramCiuCodigo=" + paramCiuCodigo + " ]";
    }
    
}
