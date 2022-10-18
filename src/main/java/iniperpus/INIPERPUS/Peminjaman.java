/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iniperpus.INIPERPUS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LENOVO
 */
@Entity
@Table(name = "peminjaman")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Peminjaman.findAll", query = "SELECT p FROM Peminjaman p"),
    @NamedQuery(name = "Peminjaman.findByIdPeminjaman", query = "SELECT p FROM Peminjaman p WHERE p.idPeminjaman = :idPeminjaman"),
    @NamedQuery(name = "Peminjaman.findByTglPinjam", query = "SELECT p FROM Peminjaman p WHERE p.tglPinjam = :tglPinjam"),
    @NamedQuery(name = "Peminjaman.findByTglKembali", query = "SELECT p FROM Peminjaman p WHERE p.tglKembali = :tglKembali"),
    @NamedQuery(name = "Peminjaman.findByIdBuku", query = "SELECT p FROM Peminjaman p WHERE p.idBuku = :idBuku"),
    @NamedQuery(name = "Peminjaman.findByIdAnggota", query = "SELECT p FROM Peminjaman p WHERE p.idAnggota = :idAnggota"),
    @NamedQuery(name = "Peminjaman.findByIdPegawai", query = "SELECT p FROM Peminjaman p WHERE p.idPegawai = :idPegawai")})
public class Peminjaman implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Peminjaman")
    private String idPeminjaman;
    @Basic(optional = false)
    @Column(name = "Tgl_Pinjam")
    @Temporal(TemporalType.DATE)
    private Date tglPinjam;
    @Basic(optional = false)
    @Column(name = "Tgl_Kembali")
    @Temporal(TemporalType.DATE)
    private Date tglKembali;
    @Basic(optional = false)
    @Column(name = "Id_Buku")
    private String idBuku;
    @Basic(optional = false)
    @Column(name = "Id_Anggota")
    private String idAnggota;
    @Basic(optional = false)
    @Column(name = "Id_Pegawai")
    private String idPegawai;

    public Peminjaman() {
    }

    public Peminjaman(String idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    public Peminjaman(String idPeminjaman, Date tglPinjam, Date tglKembali, String idBuku, String idAnggota, String idPegawai) {
        this.idPeminjaman = idPeminjaman;
        this.tglPinjam = tglPinjam;
        this.tglKembali = tglKembali;
        this.idBuku = idBuku;
        this.idAnggota = idAnggota;
        this.idPegawai = idPegawai;
    }

    public String getIdPeminjaman() {
        return idPeminjaman;
    }

    public void setIdPeminjaman(String idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    public Date getTglPinjam() {
        return tglPinjam;
    }

    public void setTglPinjam(Date tglPinjam) {
        this.tglPinjam = tglPinjam;
    }

    public Date getTglKembali() {
        return tglKembali;
    }

    public void setTglKembali(Date tglKembali) {
        this.tglKembali = tglKembali;
    }

    public String getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(String idBuku) {
        this.idBuku = idBuku;
    }

    public String getIdAnggota() {
        return idAnggota;
    }

    public void setIdAnggota(String idAnggota) {
        this.idAnggota = idAnggota;
    }

    public String getIdPegawai() {
        return idPegawai;
    }

    public void setIdPegawai(String idPegawai) {
        this.idPegawai = idPegawai;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPeminjaman != null ? idPeminjaman.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Peminjaman)) {
            return false;
        }
        Peminjaman other = (Peminjaman) object;
        if ((this.idPeminjaman == null && other.idPeminjaman != null) || (this.idPeminjaman != null && !this.idPeminjaman.equals(other.idPeminjaman))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "iniperpus.INIPERPUS.Peminjaman[ idPeminjaman=" + idPeminjaman + " ]";
    }
    
}
