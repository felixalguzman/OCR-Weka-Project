package visual;

import javax.imageio.ImageIO;

import javax.swing.*;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;


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

		if (directoryListing != null) {
			int imageIndex =0;
			for (File child : directoryListing) {
				System.out.println("Arreglo de bits de la imagen "+imageIndex+"\n");
				imprimirArreglo(getBinaryFromImage(child));
				String nombre = child.getName();
				imageIndex++;
				System.out.println("\n\n\n");


				if(imageIndex == 0)
				{
					crearARFF(getBinaryFromImage(child));
				}
			}
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
	public void crearARFF(int[] arr)
	{
		//1. Inicializar los atributos
		// Declaracion de atributo nominal y sus posibles valores

		FastVector atributos = new FastVector(arr.length);

		for(int i=0; i <  arr.length; i++)
		{
			atributos.addElement("Pixel "+i);

		}
		
		 Attribute pixels = new Attribute("Pixels", atributos);
		 

	}
}

