package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Material;

public class MaterialGenerator {

	private List<Material> meshMaterials;
	private List<Material> generatedMaterials;
	
	private static int numberOfElements = 0;
	private static List<Material> generatedMeshMaterials = new ArrayList<>();

	public MaterialGenerator(List<Material> meshMaterials, int numberOfElements) {
		this.meshMaterials = meshMaterials;
	}

	public List<Material> generate() {
		generatedMaterials = new ArrayList<>();

		for(int index = 0; index < this.meshMaterials.size(); index++) {
			String materialName = this.meshMaterials.get(index).getName();

			if(lastMaterial(index)) {
				this.generatedMaterials.add(new Material(materialName, getMaterialPercentage()));
			} else {
				this.generatedMaterials.add(new Material(materialName, getLastMaterialPercentage()));
			}
		}
		
		updateGeneratedMeshMaterialsPercentages();
		numberOfElements++;
		
		return generatedMaterials;
	}

	private BigDecimal getMaterialPercentage() {
		BigDecimal lowerLimit = sumPercentages();
		BigDecimal range = new BigDecimal(1.0).subtract(lowerLimit);
		BigDecimal generatedValue = new BigDecimal(new Random().nextDouble());

		return lowerLimit.add(range.multiply(generatedValue)).setScale(6, RoundingMode.HALF_EVEN);
	}
	
	private BigDecimal getLastMaterialPercentage() {
		BigDecimal othersMaterialPercentage = sumPercentages();

		return new BigDecimal(1.0).subtract(othersMaterialPercentage);
	}

	private BigDecimal sumPercentages() {
		BigDecimal sum = new BigDecimal(0.0);
		for(Material material : this.generatedMaterials) {
			sum = sum.add(material.getPercentage());
		}

		return sum;
	}

	private boolean lastMaterial(int index) {
		return index < this.meshMaterials.size() - 1;
	}
	
	private void updateGeneratedMeshMaterialsPercentages() {
		for(Material material : this.generatedMaterials) {
			if(generatedMeshMaterials.contains(material)) {
				Material meshMaterial = generatedMeshMaterials.get(generatedMeshMaterials.indexOf(material));
				meshMaterial.setPercentage(meshMaterial.getPercentage().add(material.getPercentage()));
			} else {
				generatedMeshMaterials.add(new Material(material.getName(), material.getPercentage()));
			}
		}
	}
	
	public static void printMeshMaterialsPercentage() {
		System.out.println("Number of Elements: " + numberOfElements);
		for(Material material : generatedMeshMaterials) {
			System.out.println(material);
		}
	}



}
