package writer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import model.Element;
import model.Material;

public class FileWriter {

	/*
	 * Materials percentage inputed by user HashMap<materialsName, percentage>
	 */
	private List<Material> materials;
	private List<Element> elements;

	public FileWriter(List<Element> elements, List<Material> materials) {
		this.materials = materials;
		this.elements = elements;
	}

	public void write() {
		Path file = new File("/Users/deepsunkensleep/eclipse-workspace/FileReaderOutput/two-phase-steel-test_output.inp").toPath();
		List<String> lines = new ArrayList<>();

		for(Material materialsName : materials) {
			for (Element element : filterElement(materialsName.getName())) {
				lines.add(element.toString());
			}
		}

		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Filters the elements by specific materials name
	 * 
	 * @param materialsName
	 * @return elements filtered by materials name
	 */
	private List<Element> filterElement(String materialsName) {
		List<Element> filteredElements = new ArrayList<>();
		for(Element element : elements)
			for(Material material : element.getComposition())
				if(material.getName().equals(materialsName)){
					Element e = new Element(element.getId());
					e.addMaterial(material);
					filteredElements.add(e);					
				}
		return filteredElements;
	}

}
