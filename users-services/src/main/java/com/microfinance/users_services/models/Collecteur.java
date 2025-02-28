package com.microfinance.users_services.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "COLLECTEUR")
@Schema(description = "Represents a collector in the microfinance system.")
public class Collecteur {

    @Id
    @Column(name = "num", nullable = false, length = 12)
    @Schema(description = "Unique identifier for the collector.", example = "C12345678901", required = true)
    private String num;

    @Column(name = "nom", nullable = false, length = 30)
    @Schema(description = "Name of the collector.", example = "John Doe", required = true)
    private String nom;

    @Column(name = "adresse", length = 50)
    @Schema(description = "Address of the collector.", example = "123 Main St, Cityville")
    private String adresse;

    @Column(name = "commission", nullable = false)
    @Schema(description = "Commission percentage for the collector.", example = "5.0", required = true)
    private float commission;

    @Column(name = "tauxcom")
    @Schema(description = "Commission rate as a percentage.", example = "1.5")
    private Float tauxcom;

    @Column(name = "tauxPrime")
    @Schema(description = "Bonus rate as a percentage.", example = "2.0")
    private Float tauxPrime;

    @Column(name = "cni", nullable = false, length = 50)
    @Schema(description = "National ID of the collector.", example = "CNI1234567890", required = true)
    private String cni;

    @Column(name = "numcpt", length = 25)
    @Schema(description = "Account number of the collector.", example = "1234567890123456789012345")
    private String numcpt;

    @Column(name = "prime")
    @Schema(description = "Bonus amount for the collector.", example = "500.0")
    private Float prime;

    @Column(name = "password", nullable = false, length = 50)
    @Schema(description = "Password for the collector account.", example = "SecurePassword123", required = true)
    private String password;

    @Column(name = "localisation", nullable = false, length = 50)
    @Schema(description = "Location of the collector.", example = "Downtown Branch", required = true)
    private String localisation;

    @Column(name = "codage", nullable = false, length = 12)
    @Schema(description = "Codage (code)  collector agency code.", example = "COD123456789", required = true)
    private String codage;

    @Column(name = "idFront", length = 255)
    @Schema(description = "URL of the front side of the collector's ID card.", example = "http://example.com/idFront.jpg")
    private String idFront;

    @Column(name = "idBack", length = 255)
    @Schema(description = "URL of the back side of the collector's ID card.", example = "http://example.com/idBack.jpg")
    private String idBack;

    @Column(name = "chiffreAffaire")
    @Schema(description = "Business turnover of the collector.", example = "100000.0")
    private Float chiffreAffaire;

    @Column(name = "tel", length = 15)
    @Schema(description = "Phone number of the collector.", example = "+1234567890")
    private String tel;

    @Column(name = "picture", length = 255)
    @Schema(description = "URL to the collector's profile picture.", example = "http://example.com/picture.jpg")
    private String picture;

    @Column(name = "isActivated")
    @Schema(description = "Status of activation for the collector.", example = "true")
    private Boolean isActivated;

    @Column(name = "Isdebit")
    @Schema(description = "Debit status of the collector.", example = "1")
    private Integer isDebit;

    @Column(name = "Iscredit")
    @Schema(description = "Credit status of the collector.", example = "1")
    private Integer isCredit;

    @Column(name = "Isconnect")
    @Schema(description = "Connection status of the collector.", example = "1")
    private Integer isConnect;

    @Column(name = "Islocked")
    @Schema(description = "Lock status of the collector's account.", example = "0")
    private Integer isLocked;

    @Column(name = "dateCrea")
    @Schema(description = "Account creation date of the collector.", example = "2023-01-15T10:30:00Z")
    private Date dateCrea;

    @Column(name = "MntBlk")
    @Schema(description = "Blocked amount for the collector.", example = "2000.0")
    private Float mntBlk;

    @Column(name = "TelBlkSMS", length = 15)
    @Schema(description = "Blocked phone number for SMS.", example = "+1234567890")
    private String telBlkSMS;

    @Column(name = "MacAdd", length = 50)
    @Schema(description = "MAC address associated with the collector.", example = "00:14:22:01:23:45")
    private String macAdd;

    @Column(name = "ISOFFLINE")
    @Schema(description = "Offline status of the collector.", example = "true")
    private Boolean isOffline;

    @Column(name = "LastNumOp", length = 20)
    @Schema(description = "Last operation number associated with the collector.", example = "OP123456")
    private String lastNumOp;

    @Column(name = "NUMCPTMBR", length = 50)
    @Schema(description = "Member account number linked to the collector.", example = "M1234567890123456789012345")
    private String numCptMbr;

    @Column(name = "defaultPwd", length = 1)
    @Schema(description = "Indicates whether the default password is set for the collector.", example = "Y")
    private String defaultPwd;

    @Column(name = "soldeActuel", precision = 18, scale = 0)
    @Schema(description = "Current balance of the collector.", example = "1500.0")
    private Float soldeActuel;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    public Float getTauxcom() {
        return tauxcom;
    }

    public void setTauxcom(Float tauxcom) {
        this.tauxcom = tauxcom;
    }

    public Float getTauxPrime() {
        return tauxPrime;
    }

    public void setTauxPrime(Float tauxPrime) {
        this.tauxPrime = tauxPrime;
    }

    public String getCni() {
        return cni;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public String getNumcpt() {
        return numcpt;
    }

    public void setNumcpt(String numcpt) {
        this.numcpt = numcpt;
    }

    public Float getPrime() {
        return prime;
    }

    public void setPrime(Float prime) {
        this.prime = prime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getCodage() {
        return codage;
    }

    public void setCodage(String codage) {
        this.codage = codage;
    }

    public String getIdFront() {
        return idFront;
    }

    public void setIdFront(String idFront) {
        this.idFront = idFront;
    }

    public String getIdBack() {
        return idBack;
    }

    public void setIdBack(String idBack) {
        this.idBack = idBack;
    }

    public Float getChiffreAffaire() {
        return chiffreAffaire;
    }

    public void setChiffreAffaire(Float chiffreAffaire) {
        this.chiffreAffaire = chiffreAffaire;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Boolean getActivated() {
        return isActivated;
    }

    public void setActivated(Boolean activated) {
        isActivated = activated;
    }

    public Integer getIsDebit() {
        return isDebit;
    }

    public void setIsDebit(Integer isDebit) {
        this.isDebit = isDebit;
    }

    public Integer getIsCredit() {
        return isCredit;
    }

    public void setIsCredit(Integer isCredit) {
        this.isCredit = isCredit;
    }

    public Integer getIsConnect() {
        return isConnect;
    }

    public void setIsConnect(Integer isConnect) {
        this.isConnect = isConnect;
    }

    public Integer getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Integer isLocked) {
        this.isLocked = isLocked;
    }

    public Date getDateCrea() {
        return dateCrea;
    }

    public void setDateCrea(Date dateCrea) {
        this.dateCrea = dateCrea;
    }

    public Float getMntBlk() {
        return mntBlk;
    }

    public void setMntBlk(Float mntBlk) {
        this.mntBlk = mntBlk;
    }

    public String getTelBlkSMS() {
        return telBlkSMS;
    }

    public void setTelBlkSMS(String telBlkSMS) {
        this.telBlkSMS = telBlkSMS;
    }

    public String getMacAdd() {
        return macAdd;
    }

    public void setMacAdd(String macAdd) {
        this.macAdd = macAdd;
    }

    public Boolean getOffline() {
        return isOffline;
    }

    public void setOffline(Boolean offline) {
        isOffline = offline;
    }

    public String getLastNumOp() {
        return lastNumOp;
    }

    public void setLastNumOp(String lastNumOp) {
        this.lastNumOp = lastNumOp;
    }

    public String getNumCptMbr() {
        return numCptMbr;
    }

    public void setNumCptMbr(String numCptMbr) {
        this.numCptMbr = numCptMbr;
    }

    public String getDefaultPwd() {
        return defaultPwd;
    }

    public void setDefaultPwd(String defaultPwd) {
        this.defaultPwd = defaultPwd;
    }

    public Float getSoldeActuel() {
        return soldeActuel;
    }

    public void setSoldeActuel(Float soldeActuel) {
        this.soldeActuel = soldeActuel;
    }
}
