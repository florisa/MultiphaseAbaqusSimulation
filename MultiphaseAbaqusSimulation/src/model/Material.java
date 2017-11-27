package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This class defines the material's phases name 
 * and the percentage (Volume Fraction) for each phase in an element
 * @author abr_fm
 *
 */

public class Material {

	private String name;
	private BigDecimal percentage;
	
	public Material() {}
	
	public Material(String name) {
		this.name = name;
	}
	
	public Material(String name, BigDecimal percentage) {
		this.name = name;
		this.percentage = percentage;
	}
	
	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }
	
	public BigDecimal getPercentage() { return percentage; }
	public void setPercentage(BigDecimal percentage) { this.percentage = percentage; }
	
	@Override
	public String toString() {
		return ", Workpiece-1." + name + ", " + percentage.setScale(6, RoundingMode.UP) ;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (((Material) obj).name.equals(this.name))
			return true;
		return super.equals(obj);
	}
}