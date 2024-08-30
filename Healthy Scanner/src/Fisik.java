
public class Fisik {
	 private String fisikId;
	    private String userId;
	    private int jumlahLangkah;
	    private int jarakTempuh;
	    private int durasiLatihan;
	    
	     
	    
		public Fisik(String fisikId, String userId, int jumlahLangkah, int jarakTempuh, int durasiLatihan) {
			super();
			this.fisikId = fisikId;
			this.userId = userId;
			this.jumlahLangkah = jumlahLangkah;
			this.jarakTempuh = jarakTempuh;
			this.durasiLatihan = durasiLatihan;
		}
		public String getFisikId() {
			return fisikId;
		}
		public void setFisikId(String fisikId) {
			this.fisikId = fisikId;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public int getJumlahLangkah() {
			return jumlahLangkah;
		}
		public void setJumlahLangkah(int jumlahLangkah) {
			this.jumlahLangkah = jumlahLangkah;
		}
		public int getJarakTempuh() {
			return jarakTempuh;
		}
		public void setJarakTempuh(int jarakTempuh) {
			this.jarakTempuh = jarakTempuh;
		}
		public int getDurasiLatihan() {
			return durasiLatihan;
		}
		public void setDurasiLatihan(int durasiLatihan) {
			this.durasiLatihan = durasiLatihan;
		}
}
