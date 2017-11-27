package reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import model.Element;
import model.Material;

import java.nio.charset.Charset;

/**
 * This class reads the Abaqus *.inp file 
 * @author abr_fm
 *
 */

public class FileReader {

	private List<Element> elements = new ArrayList<>();

	public List<Element> readFile() {
		Path file = new File("/Users/deepsunkensleep/eclipse-workspace/FileReaderInput/two-phase-steel-test.inp").toPath();

		try (BufferedReader reader = Files.newBufferedReader(file, Charset.forName("ISO-8859-15"))) {
			String line = null;
			while((line = reader.readLine()) != null) {
				if(isValid(line)) {		    	   
					getData(line);
				}
			}		    
			return elements;
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	private void getData(String line) {
		String[] tokens = line.split(",");

		int elementId = getElementId(tokens);
		Element element = new Element(elementId);

		if(elements.contains(element)) {	
			element = elements.get(elements.indexOf(element));
			element.addMaterial(getMaterial(tokens));
		} else {
			element.addMaterial(getMaterial(tokens));
			elements.add(element);
		}

	}

	/**
	 * Return the ID of the element
	 * @return ID of the element
	 */

	private int getElementId(String[] tokens) {
		return Integer.valueOf(tokens[0].replace("Workpiece-1.", "").trim());
	}

	/**
	 * Return the material's phase name 
	 * and the percentage of each material inside the element
	 * @return material's phase name and percentage
	 */

	private Material getMaterial(String[] tokens) {
		Material material = new Material();

		material.setName(tokens[1].replace("Workpiece-1.", "").trim());
		material.setPercentage(new BigDecimal(Double.valueOf(tokens[2])));

		return material;
	}

	/**
	 * Return true if the line starts with a specific string
	 * @return true when the line matches
	 */

	public boolean isValid(String line){	
		return line.startsWith("Workpiece-1.");
	}

}
