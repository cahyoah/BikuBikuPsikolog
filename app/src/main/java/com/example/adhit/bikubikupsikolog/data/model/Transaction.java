package com.example.adhit.bikubikupsikolog.data.model;

/**
 * Created by adhit on 05/01/2018.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaction implements Parcelable {

    @SerializedName("invoice")
    @Expose
    private String invoice;
    @SerializedName("id_biquers")
    @Expose
    private String idBiquers;
    @SerializedName("id_kabim")
    @Expose
    private String idKabim;
    @SerializedName("layanan")
    @Expose
    private String layanan;
    @SerializedName("kode_mapel")
    @Expose
    private Object kodeMapel;
    @SerializedName("kode_jenjang")
    @Expose
    private Object kodeJenjang;
    @SerializedName("lama")
    @Expose
    private String lama;
    @SerializedName("penjelasan")
    @Expose
    private String penjelasan;
    @SerializedName("nominal_bayar")
    @Expose
    private String nominalBayar;
    @SerializedName("kupon")
    @Expose
    private Object kupon;
    @SerializedName("id_room")
    @Expose
    private String idRoom;
    @SerializedName("createDate")
    @Expose
    private String createDate;
    @SerializedName("status_trx")
    @Expose
    private String statusTrx;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tarif")
    @Expose
    private String tarif;
    @SerializedName("status_layanan")
    @Expose
    private String statusLayanan;
    @SerializedName("nama_mapel")
    @Expose
    private Object namaMapel;
    @SerializedName("keterangan")
    @Expose
    private Object keterangan;
    @SerializedName("aktif")
    @Expose
    private Object aktif;
    @SerializedName("nama_jenjang")
    @Expose
    private Object namaJenjang;
    @SerializedName("nama_biquers")
    @Expose
    private String namaBiquers;
    @SerializedName("foto_biquers")
    @Expose
    private Object fotoBiquers;
    @SerializedName("nama_kabim")
    @Expose
    private String namaKabim;
    @SerializedName("foto_kabim")
    @Expose
    private Object fotoKabim;

    private String lastMessage;

    private Integer unreadCount;


    protected Transaction(Parcel in) {
        invoice = in.readString();
        idBiquers = in.readString();
        idKabim = in.readString();
        layanan = in.readString();
        lama = in.readString();
        penjelasan = in.readString();
        nominalBayar = in.readString();
        idRoom = in.readString();
        createDate = in.readString();
        statusTrx = in.readString();
        id = in.readString();
        tarif = in.readString();
        statusLayanan = in.readString();
        namaBiquers = in.readString();
        namaKabim = in.readString();
        lastMessage = in.readString();
        if (in.readByte() == 0) {
            unreadCount = null;
        } else {
            unreadCount = in.readInt();
        }
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getIdBiquers() {
        return idBiquers;
    }

    public void setIdBiquers(String idBiquers) {
        this.idBiquers = idBiquers;
    }

    public String getIdKabim() {
        return idKabim;
    }

    public void setIdKabim(String idKabim) {
        this.idKabim = idKabim;
    }

    public String getLayanan() {
        return layanan;
    }

    public void setLayanan(String layanan) {
        this.layanan = layanan;
    }

    public Object getKodeMapel() {
        return kodeMapel;
    }

    public void setKodeMapel(Object kodeMapel) {
        this.kodeMapel = kodeMapel;
    }

    public Object getKodeJenjang() {
        return kodeJenjang;
    }

    public void setKodeJenjang(Object kodeJenjang) {
        this.kodeJenjang = kodeJenjang;
    }

    public String getLama() {
        return lama;
    }

    public void setLama(String lama) {
        this.lama = lama;
    }

    public String getPenjelasan() {
        return penjelasan;
    }

    public void setPenjelasan(String penjelasan) {
        this.penjelasan = penjelasan;
    }

    public String getNominalBayar() {
        return nominalBayar;
    }

    public void setNominalBayar(String nominalBayar) {
        this.nominalBayar = nominalBayar;
    }

    public Object getKupon() {
        return kupon;
    }

    public void setKupon(Object kupon) {
        this.kupon = kupon;
    }

    public String getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStatusTrx() {
        return statusTrx;
    }

    public void setStatusTrx(String statusTrx) {
        this.statusTrx = statusTrx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public String getStatusLayanan() {
        return statusLayanan;
    }

    public void setStatusLayanan(String statusLayanan) {
        this.statusLayanan = statusLayanan;
    }

    public Object getNamaMapel() {
        return namaMapel;
    }

    public void setNamaMapel(Object namaMapel) {
        this.namaMapel = namaMapel;
    }

    public Object getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(Object keterangan) {
        this.keterangan = keterangan;
    }

    public Object getAktif() {
        return aktif;
    }

    public void setAktif(Object aktif) {
        this.aktif = aktif;
    }

    public Object getNamaJenjang() {
        return namaJenjang;
    }

    public void setNamaJenjang(Object namaJenjang) {
        this.namaJenjang = namaJenjang;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public String getNamaBiquers() {
        return namaBiquers;
    }

    public void setNamaBiquers(String namaBiquers) {
        this.namaBiquers = namaBiquers;
    }

    public Object getFotoBiquers() {
        return fotoBiquers;
    }

    public void setFotoBiquers(Object fotoBiquers) {
        this.fotoBiquers = fotoBiquers;
    }

    public String getNamaKabim() {
        return namaKabim;
    }

    public void setNamaKabim(String namaKabim) {
        this.namaKabim = namaKabim;
    }

    public Object getFotoKabim() {
        return fotoKabim;
    }

    public void setFotoKabim(Object fotoKabim) {
        this.fotoKabim = fotoKabim;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(invoice);
        parcel.writeString(idBiquers);
        parcel.writeString(idKabim);
        parcel.writeString(layanan);
        parcel.writeString(lama);
        parcel.writeString(penjelasan);
        parcel.writeString(nominalBayar);
        parcel.writeString(idRoom);
        parcel.writeString(createDate);
        parcel.writeString(statusTrx);
        parcel.writeString(id);
        parcel.writeString(tarif);
        parcel.writeString(statusLayanan);
        parcel.writeString(namaBiquers);
        parcel.writeString(namaKabim);
        parcel.writeString(lastMessage);
        if (unreadCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(unreadCount);
        }
    }
}
