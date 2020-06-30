package kurzlistky;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="Kurzovnilistky")
public class KurzListky {
	@Id
	private String shortName;
	private String validFrom;
	private String name;
	private String country;
	private Integer amount;
	private double valBuy;
	private double valSell;
	private double valMid;
	private double currBuy;
	private double currSell;
	private double currMid;
	private double move;
	private double cnbMid;
	private double version;
	private double ecbMid;
	
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public double getValBuy() {
		return valBuy;
	}
	public void setValBuy(double valBuy) {
		this.valBuy = valBuy;
	}
	public double getValSell() {
		return valSell;
	}
	public void setValSell(double valSell) {
		this.valSell = valSell;
	}
	public double getValMid() {
		return valMid;
	}
	public void setValMid(double valMid) {
		this.valMid = valMid;
	}
	public double getCurrBuy() {
		return currBuy;
	}
	public void setCurrBuy(double currBuy) {
		this.currBuy = currBuy;
	}
	public double getCurrSell() {
		return currSell;
	}
	public void setCurrSell(double currSell) {
		this.currSell = currSell;
	}
	public double getCurrMid() {
		return currMid;
	}
	public void setCurrMid(double currMid) {
		this.currMid = currMid;
	}
	public double getMove() {
		return move;
	}
	public void setMove(double move) {
		this.move = move;
	}
	public double getCnbMid() {
		return cnbMid;
	}
	public void setCnbMid(double cnbMid) {
		this.cnbMid = cnbMid;
	}
	public double getVersion() {
		return version;
	}
	public void setVersion(double version) {
		this.version = version;
	}
	public double getEcbMid() {
		return ecbMid;
	}
	public void setEcbMid(double ecbMid) {
		this.ecbMid = ecbMid;
	}
	@Override
	public String toString() {
		return "KurzListky [shortName=" + shortName + ", validFrom=" + validFrom + ", name=" + name + ", country="
				+ country + ", amount=" + amount + ", valBuy=" + valBuy + ", valSell=" + valSell + ", valMid=" + valMid
				+ ", currBuy=" + currBuy + ", currSell=" + currSell + ", currMid=" + currMid + ", move=" + move
				+ ", cnbMid=" + cnbMid + ", version=" + version + ", ecbMid=" + ecbMid + "]";
	}
	
	
}
