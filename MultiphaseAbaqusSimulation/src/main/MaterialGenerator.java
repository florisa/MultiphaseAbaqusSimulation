package main;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Element;
import model.Material;

public class MaterialGenerator {

	private List<Material> meshMaterialsPercentage;
	private List<Material> meshMaterialsNumberOfElements;

	private List<Element> meshElements;
	private int numberOfElements = 0;
	private List<Material> generatedMeshMaterials = new ArrayList<>();

	public MaterialGenerator(List<Material> meshMaterialsPercentage, List<Element> meshElements) {
		this.meshMaterialsPercentage = meshMaterialsPercentage;
		this.meshElements = meshElements;

		this.meshMaterialsNumberOfElements = new ArrayList<Material>();
		for(Material meshMaterial : meshMaterialsPercentage) {
			BigDecimal numberOfElements = meshMaterial.getPercentage().multiply(new BigDecimal(meshElements.size())).divide(new BigDecimal(100));
			this.meshMaterialsNumberOfElements.add(new Material(meshMaterial.getName(), numberOfElements));
		}
	}

	public void generate() {
		for(Element element : meshElements) {
			List<Material> composition = new ArrayList<>();

			composition = generateMaterials();	

			element.setComposition(composition);
			updateGeneratedMeshMaterialsPercentages(composition);
			numberOfElements++;
		}

		printMeshMaterialsPercentage();
	}

	private List<Material> generateMaterials() {
		List<Material> generatedMaterials = new ArrayList<>();
		for(int index = 0; index < this.meshMaterialsPercentage.size(); index++) {
			String materialName = this.meshMaterialsPercentage.get(index).getName();

			Material material = new Material(materialName);
			if(lastMaterial(index)) {
				material.setPercentage(getMaterialPercentage(generatedMaterials, meshMaterialsPercentage.get(meshMaterialsPercentage.indexOf(material)).getPercentage()));
				generatedMaterials.add(material);
			} else {
				generatedMaterials.add(new Material(materialName, getLastMaterialPercentage(generatedMaterials)));
			}
		}

		return generatedMaterials;
	}

	@SuppressWarnings("unused")
	private boolean isInvalid(List<Material> composition) {
		for(Material material : generatedMeshMaterials) {
			Material newMaterial = composition.get(composition.indexOf(material));
			Material totalMeshMaterial = meshMaterialsNumberOfElements.get(meshMaterialsNumberOfElements.indexOf(material));

			if(material.getPercentage().add(newMaterial.getPercentage()).compareTo(totalMeshMaterial.getPercentage()) > 0) {
				return true;
			}
		}
		return false;
	}

	private BigDecimal getMaterialPercentage(List<Material> generatedMaterials, BigDecimal higherLimit) {
		BigDecimal lowerLimit = sumPercentages(generatedMaterials);
		BigDecimal range = new BigDecimal(1.0).subtract(lowerLimit);

		BigDecimal generatedValue = null;
		do {
			generatedValue = lowerLimit.add(range.multiply(new BigDecimal(new Random().nextDouble()))).setScale(6, RoundingMode.HALF_EVEN);
		} while(generatedValue.compareTo(higherLimit.add(new BigDecimal(10)).divide(new BigDecimal(100))) > 0);

		return generatedValue;
	}

	private BigDecimal getLastMaterialPercentage(List<Material> generatedMaterials) {
		BigDecimal othersMaterialPercentage = sumPercentages(generatedMaterials);
		return new BigDecimal(1.0).subtract(othersMaterialPercentage);
	}

	private BigDecimal sumPercentages(List<Material> generatedMaterials) {
		BigDecimal sum = new BigDecimal(0.0);
		for(Material material : generatedMaterials) {
			sum = sum.add(material.getPercentage());
		}

		return sum;
	}

	private boolean lastMaterial(int index) {
		return index < this.meshMaterialsPercentage.size() - 1;
	}

	private void updateGeneratedMeshMaterialsPercentages(List<Material> composition) {
		for(Material material : composition) {
			if(generatedMeshMaterials.contains(material)) {
				Material meshMaterial = generatedMeshMaterials.get(generatedMeshMaterials.indexOf(material));
				meshMaterial.setPercentage(meshMaterial.getPercentage().add(material.getPercentage()));
			} else {
				generatedMeshMaterials.add(new Material(material.getName(), material.getPercentage()));
			}
		}
	}

	public void printMeshMaterialsPercentage() {
		System.out.println("Number of Elements: " + this.numberOfElements);
		for(Material material : this.generatedMeshMaterials) {
			System.out.println(material);
		}
	}



}
