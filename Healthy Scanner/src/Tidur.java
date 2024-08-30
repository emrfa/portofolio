
public class Tidur {

	private String pemantauId;
	private String userId;
	private String hari;
	private int  jumlahjamtidur;
	private String kualitas;
	
	
	
	
	
	public Tidur(String pemantauId, String userId, String hari, int jumlahjamtidur, String kualitas) {
		super();
		this.pemantauId = pemantauId;
		this.userId = userId;
		this.hari = hari;
		this.jumlahjamtidur = jumlahjamtidur;
		this.kualitas = kualitas;
	}
	public String getPemantauId() {
		return pemantauId;
	}
	public void setPemantauId(String pemantauId) {
		this.pemantauId = pemantauId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getHari() {
		return hari;
	}
	public void setHari(String hari) {
		this.hari = hari;
	}
	public int getJumlahjamtidur() {
		return jumlahjamtidur;
	}
	public void setJumlahjamtidur(int jumlahjamtidur) {
		this.jumlahjamtidur = jumlahjamtidur;
	}
	public String getKualitas() {
		return kualitas;
	}
	public void setKualitas(String kualitas) {
		this.kualitas = kualitas;
	}
	
}
