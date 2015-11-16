/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author guitta
 */
@Entity
@Table(name = "transport")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transport.findAll", query = "SELECT t FROM Transport t"),
    @NamedQuery(name = "Transport.findByTransportId", query = "SELECT t FROM Transport t WHERE t.transportId = :transportId"),
    @NamedQuery(name = "Transport.findByTransportDate", query = "SELECT t FROM Transport t WHERE t.transportDate = :transportDate")})
public class Transport implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "transport_id")
    private Integer transportId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "transport_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transportDate;
    @JoinColumn(name = "personnel_id", referencedColumnName = "personnel_id")
    @ManyToOne(optional = false)
    private Personnel personnelId;
    @OneToMany(mappedBy = "transportId")
    private List<Transaction> transactionList;

    public Transport() {
    }

    public Transport(Integer transportId) {
        this.transportId = transportId;
    }

    public Transport(Integer transportId, Date transportDate) {
        this.transportId = transportId;
        this.transportDate = transportDate;
    }

    public Integer getTransportId() {
        return transportId;
    }

    public void setTransportId(Integer transportId) {
        this.transportId = transportId;
    }

    public Date getTransportDate() {
        return transportDate;
    }

    public void setTransportDate(Date transportDate) {
        this.transportDate = transportDate;
    }

    public Personnel getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(Personnel personnelId) {
        this.personnelId = personnelId;
    }

    @XmlTransient
    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transportId != null ? transportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transport)) {
            return false;
        }
        Transport other = (Transport) object;
        if ((this.transportId == null && other.transportId != null) || (this.transportId != null && !this.transportId.equals(other.transportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Transport[ transportId=" + transportId + " ]";
    }
    
}
