package visual;

import javax.imageio.ImageIO;

import javax.swing.*;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;


public class MainWindow extends JFrame {
	/**
	 * 
	 */
	private final long serialVersionUID = 1L;


	public MainWindow ()
	{
		this.setVisible(true);
		this.setSize(200,200);
		trainTheShit();
	}

	public   BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight) {
		BufferedImage scaledImage = null;
		if (imageToScale != null) {
			scaledImage = new BufferedImage(dWidth, dHeight, imageToScale.getType());
			Graphics2D graphics2D = scaledImage.createGraphics();
			graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
			graphics2D.dispose();
		}
		return scaledImage;
	}

	public void trainTheShit(){
		File dir = new File("Training Images/");
		File[] directoryListing = dir.listFiles();
		Instances data = null;
		if (directoryListing != null) {
			int imageIndex =0;
			for (File child : directoryListing) {
				System.out.println("Arreglo de bits de la imagen "+imageIndex+"\n");
				imprimirArreglo(getBinaryFromImage(child));
				String nombre = child.getName();

				System.out.println("\n\n\n");


				if(imageIndex == 0)
				{

					crearARFF(getBinaryFromImage(child));
					try {
						data = cargarARFF("training.arff", directoryListing.length);
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				imageIndex++;
				data.add(insertarInstancia(getBinaryFromImage(child)));

			}
			
			guardarARFF(data);
		} else {

		}



	}

	public int[] getBinaryFromImage(File imageFile){
		BufferedImage img = null;
		try {
			img = ImageIO.read(imageFile);
		} catch (IOException e) {
		}

		img = scale(img,40,40);

		byte[][] pixels = new byte[img.getWidth()][];
		int[] intArray = new int[img.getWidth()*img.getHeight()];

		for (int x = 0; x < img.getWidth(); x++) {
			pixels[x] = new byte[img.getHeight()];

			for (int y = 0; y < img.getHeight(); y++) {
				pixels[x][y] = (byte) (img.getRGB(x, y) == 0xFFFFFFFF ? 0 : 1);
			}
		}
		intArray = byteArrayToIntArray(pixels, img.getWidth(), img.getHeight());
		return intArray;
	}

	public int[] byteArrayToIntArray(byte[][] byteMatrix,int width,int heigt){
		int[] intArray = new int[width*heigt];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < heigt; y++) {
				intArray[x*y] = byteMatrix[x][y];
			}
		}
		return intArray;
	}

	public  void imprimirArreglo(int[] arr){
		System.out.print(Arrays.toString(arr));
	}

	public void createImage(BufferedImage img){
		try{
			File f = new File("C:/Users/felix/git/OCR-Weka-Project/OCR Project Weka/Output.jpg");
			ImageIO.write(img,"jpg",f);
		}catch(IOException e){
			System.out.println(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	public static void crearARFF(int[] arr){
		//1. Inicializar los atributos
		// Declaracion de atributo nominal y sus posibles valores
		FastVector atributos = new FastVector(arr.length);
		FastVector att = new FastVector();
		Attribute pixels = null ;

	
		for(int i=0; i <  arr.length; i++){

			pixels = new Attribute("Pixels "+i, atributos);
			att.add(pixels);
		}


		//2.Crear la clase

		String[] alphabetMinuscula = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n","�", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
		String[] alphabetMayuscula = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N","�" , "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

		FastVector letra =  new FastVector(alphabetMayuscula.length+alphabetMinuscula.length);

		letra.appendElements(Arrays.asList(alphabetMayuscula));
		letra.appendElements(Arrays.asList(alphabetMinuscula));


		Attribute ClassAttribute = new Attribute("Letra", letra);

		att.add(ClassAttribute);
		//2.Crear objeto con las instancias
		Instances data = new Instances("Objeto de Instancias", att, 0);


		System.out.println(data.attribute(0));

		try{
			PrintWriter writer = new PrintWriter("training.arff", "UTF-8");
			writer.println(data);
			writer.close();
		} catch (IOException e) {
			// do something
		}

	}

	public Instances cargarARFF(String ruta, int cant) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(ruta));
		ArffReader arff = new ArffReader(reader, cant);
		Instances data = arff.getData();
		
		return data;
	}
	
	public Instance insertarInstancia(int[] arr)
	{
		Instance inst1 = new DenseInstance(arr.length);
	     
	   // inst1.setValue((Attribute)atributos.elementAt(0),"Rojo");
		for(int i =0; i < arr.length; i++)
		{
			inst1.setValue(i, arr[i]);
			
		}
		
		
		return inst1;
	}
	
	public void guardarARFF(Instances data)
	{
		try{
			PrintWriter writer = new PrintWriter("training.arff", "UTF-8");
			writer.println(data);
			writer.close();
		} catch (IOException e) {
			// do something
		}
	}
}

