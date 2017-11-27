package main;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import model.Element;
import model.Material;
import utils.Chronometer;
import reader.FileReader;
import writer.FileWriter;

public class Main {

	public static void main(String[] args) {
		Chronometer processBegin = Chronometer.newInstance();
		processBegin.start("Begin process");
		Chronometer processFileReader = Chronometer.newInstance();
		processFileReader.start("Starts read file");
		FileReader fr = new FileReader();
		List<Element> meshElements = fr.readFile();
		processFileReader.stop("Finish read file");

		/*
		 * Materials percentage inputed by user HashMap<materialsName,
		 * percentage>
		 */

		List<Material> meshMaterialsPercentage = new ArrayList<>();
		meshMaterialsPercentage.add(new Material("ferrite-1", new BigDecimal(30)));
		meshMaterialsPercentage.add(new Material("pearlite-1", new BigDecimal(70)));

		new MaterialGenerator(meshMaterialsPercentage, meshElements).generate();

		Chronometer processFileOutput = Chronometer.newInstance();
		processFileOutput.start("Starts write file");
		new FileWriter(meshElements, meshMaterialsPercentage).write();
		processFileOutput.stop("Finish write file");

		processBegin.stop("End process");

	}

}