import java.sql.Date;


public class Penerbangan {

	private String penerbanganId;
	private String rute;
	private Date tanggalBerangkat;
	private String maskapaiPenerbangan;
	private String kelasPenerbangan;
	private String durasiPenerbangan;
	private String bandaraKeberangkatan;
	private String bandaraKedatangan;
	private String jenisPesawat;
	private int price;
	
	public Penerbangan(String penerbanganId, String rute, Date tanggalBerangkat, String maskapaiPenerbangan,
			String kelasPenerbangan, String durasiPenerbangan, String bandaraKeberangkatan,String bandaraKedatangan, String jenisPesawat, int price) {
		super();
		this.penerbanganId = penerbanganId;
		this.rute = rute;
		this.tanggalBerangkat = tanggalBerangkat;
		this.maskapaiPenerbangan = maskapaiPenerbangan;
		this.kelasPenerbangan = kelasPenerbangan;
		this.durasiPenerbangan = durasiPenerbangan;
		this.bandaraKeberangkatan = bandaraKeberangkatan;
		this.bandaraKedatangan = bandaraKedatangan;
		this.jenisPesawat = jenisPesawat;
		this.price = price;
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

	public Date getTanggalBerangkat() {
		return tanggalBerangkat;
	}

	public void setTanggalBerangkat(Date tanggalBerangkat) {
		this.tanggalBerangkat = tanggalBerangkat;
	}

	public String getMaskapaiPenerbangan() {
		return maskapaiPenerbangan;
	}

	public void setMaskapaiPenerbangan(String maskapaiPenerbangan) {
		this.maskapaiPenerbangan = maskapaiPenerbangan;
	}

	public String getKelasPenerbangan() {
		return kelasPenerbangan;
	}

	public void setKelasPenerbangan(String kelasPenerbangan) {
		this.kelasPenerbangan = kelasPenerbangan;
	}

	public String getDurasiPenerbangan() {
		return durasiPenerbangan;
	}

	public void setDurasiPenerbangan(String durasiPenerbangan) {
		this.durasiPenerbangan = durasiPenerbangan;
	}

	public String getBandaraKeberangkatan() {
		return bandaraKeberangkatan;
	}

	public void setBandaraKeberangkatan(String bandaraKeberangkatan) {
		this.bandaraKeberangkatan = bandaraKeberangkatan;
	}

	
	
	public String getBandaraKedatangan() {
		return bandaraKedatangan;
	}



	public void setBandaraKedatangan(String bandaraKedatangan) {
		this.bandaraKedatangan = bandaraKedatangan;
	}



	public String getJenisPesawat() {
		return jenisPesawat;
	}

	public void setJenisPesawat(String jenisPesawat) {
		this.jenisPesawat = jenisPesawat;
	}



	public int getPrice() {
		return price;
	}



	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
	
}
