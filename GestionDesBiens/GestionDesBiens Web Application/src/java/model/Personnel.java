/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author guitta
 */
@Entity
@Table(name = "personnel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personnel.findAll", query = "SELECT p FROM Personnel p"),
    @NamedQuery(name = "Personnel.findByPersonnelId", query = "SELECT p FROM Personnel p WHERE p.personnelId = :personnelId"),
    @NamedQuery(name = "Personnel.findByPersonnelName", query = "SELECT p FROM Personnel p WHERE p.personnelName = :personnelName")})
public class Personnel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "personnel_id")
    private Integer personnelId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "personnel_name")
    private String personnelName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personnelId")
    private List<Location> locationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personnelId")
    private List<Transport> transportList;

    public Personnel() {
    }

    public Personnel(Integer personnelId) {
        this.personnelId = personnelId;
    }

    public Personnel(Integer personnelId, String personnelName) {
        this.personnelId = personnelId;
        this.personnelName = personnelName;
    }

    public Integer getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(Integer personnelId) {
        this.personnelId = personnelId;
    }

    public String getPersonnelName() {
        return personnelName;
    }

    public void setPersonnelName(String personnelName) {
        this.personnelName = personnelName;
    }

    @XmlTransient
    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    @XmlTransient
    public List<Transport> getTransportList() {
        return transportList;
    }

    public void setTransportList(List<Transport> transportList) {
        this.transportList = transportList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personnelId != null ? personnelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personnel)) {
            return false;
        }
        Personnel other = (Personnel) object;
        if ((this.personnelId == null && other.personnelId != null) || (this.personnelId != null && !this.personnelId.equals(other.personnelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Personnel[ personnelId=" + personnelId + " ]";
    }
    
}
