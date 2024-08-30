import java.sql.Date;

public class Reservation {
    private String reservationId;
    private String userId;
    private String penerbanganId;
    private String rute;
    private String maskapaiPenerbangan;
    private Date tanggalKeberangkatan;
    private double totalPrice;
    private String layananTambahan;

    // Constructor
    public Reservation(String reservationId, String userId, String penerbanganId, String rute,
                       String maskapaiPenerbangan, Date tanggalKeberangkatan, double totalPrice,String layananTambahan) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.penerbanganId = penerbanganId;
        this.rute = rute;
        this.maskapaiPenerbangan = maskapaiPenerbangan;
        this.tanggalKeberangkatan = tanggalKeberangkatan;
        this.totalPrice = totalPrice;
        this.layananTambahan = layananTambahan;
    }
    
    

    public String getLayananTambahan() {
		return layananTambahan;
	}



	public void setLayananTambahan(String layananTambahan) {
		this.layananTambahan = layananTambahan;
	}



	// Getters and Setters
    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPenerbanganId() {
        return penerbanganId;
    }

    public void setPenerbanganId(String penerbanganId) {
        this.penerbanganId = penerbanganId;
    }

    public String getRute() {
        return rute;
    }

    public void setRute(String rute) {
        this.rute = rute;
    }

    public String getMaskapaiPenerbangan() {
        return maskapaiPenerbangan;
    }

    public void setMaskapaiPenerbangan(String maskapaiPenerbangan) {
        this.maskapaiPenerbangan = maskapaiPenerbangan;
    }

    public Date getTanggalKeberangkatan() {
        return tanggalKeberangkatan;
    }

    public void setTanggalKeberangkatan(Date tanggalKeberangkatan) {
        this.tanggalKeberangkatan = tanggalKeberangkatan;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}