package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines the element ID's and material's phases name
 * @author abr_fm
 *
 */

public class Element {
	
	private int id;
	private List<Material> composition;
	
	public Element() { super(); }
	public Element(int id) {
		this.id = id;
	}
	
	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; }
	
	public List<Material> getComposition() { return composition; }
	public void setComposition(List<Material> composition) { this.composition = composition; }
	
	public void addMaterial(Material material) {
		if(this.composition == null) {
			this.composition = new ArrayList<>();
		}
		
		this.composition.add(material);
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj || (obj instanceof Element && this.id == ((Element)obj).getId());
	}
	
	@Override
	public String toString() {
		return "Workpiece-1." + id + composition.toString().replace("[", "").replace("]", "");
	}
}
