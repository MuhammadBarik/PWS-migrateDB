/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iniperpus.INIPERPUS;

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
 * @author LENOVO
 */
@Entity
@Table(name = "anggota")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Anggota.findAll", query = "SELECT a FROM Anggota a"),
    @NamedQuery(name = "Anggota.findByIdAnggota", query = "SELECT a FROM Anggota a WHERE a.idAnggota = :idAnggota"),
    @NamedQuery(name = "Anggota.findByNama", query = "SELECT a FROM Anggota a WHERE a.nama = :nama"),
    @NamedQuery(name = "Anggota.findByJKelamin", query = "SELECT a FROM Anggota a WHERE a.jKelamin = :jKelamin"),
    @NamedQuery(name = "Anggota.findByNoTelp", query = "SELECT a FROM Anggota a WHERE a.noTelp = :noTelp"),
    @NamedQuery(name = "Anggota.findByAlamat", query = "SELECT a FROM Anggota a WHERE a.alamat = :alamat")})
public class Anggota implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Anggota")
    private String idAnggota;
    @Basic(optional = false)
    @Column(name = "Nama")
    private String nama;
    @Basic(optional = false)
    @Column(name = "JKelamin")
    private Character jKelamin;
    @Basic(optional = false)
    @Column(name = "noTelp")
    private String noTelp;
    @Basic(optional = false)
    @Column(name = "Alamat")
    private String alamat;

    public Anggota() {
    }

    public Anggota(String idAnggota) {
        this.idAnggota = idAnggota;
    }

    public Anggota(String idAnggota, String nama, Character jKelamin, String noTelp, String alamat) {
        this.idAnggota = idAnggota;
        this.nama = nama;
        this.jKelamin = jKelamin;
        this.noTelp = noTelp;
        this.alamat = alamat;
    }

    public String getIdAnggota() {
        return idAnggota;
    }

    public void setIdAnggota(String idAnggota) {
        this.idAnggota = idAnggota;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Character getJKelamin() {
        return jKelamin;
    }

    public void setJKelamin(Character jKelamin) {
        this.jKelamin = jKelamin;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAnggota != null ? idAnggota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Anggota)) {
            return false;
        }
        Anggota other = (Anggota) object;
        if ((this.idAnggota == null && other.idAnggota != null) || (this.idAnggota != null && !this.idAnggota.equals(other.idAnggota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "iniperpus.INIPERPUS.Anggota[ idAnggota=" + idAnggota + " ]";
    }
    
}
