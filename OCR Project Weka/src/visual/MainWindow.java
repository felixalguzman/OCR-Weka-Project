package visual;

import javax.imageio.ImageIO;
import javax.swing.*;

import weka.classifiers.trees.m5.Impurity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MainWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainWindow ()
	{
		this.setVisible(true);
		this.setSize(200,200);
		trainTheShit();
	}

	public static BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight) {
		BufferedImage scaledImage = null;
		if (imageToScale != null) {
			scaledImage = new BufferedImage(dWidth, dHeight, imageToScale.getType());
			Graphics2D graphics2D = scaledImage.createGraphics();
			graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
			graphics2D.dispose();
		}
		return scaledImage;
	}

	public static void trainTheShit(){
		File dir = new File("Training Images/");
		File[] directoryListing = dir.listFiles();

		if (directoryListing != null) {
			int imageIndex =0;
			for (File child : directoryListing) {
				System.out.println("Arreglo de bits de la imagen "+imageIndex+"\n");
				imprimirArreglo(getBinaryFromImage(child));
				imageIndex++;
				System.out.println("\n\n\n");
			}
		} else {

		}
	}

	public static int[] getBinaryFromImage(File imageFile){
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

	public static int[] byteArrayToIntArray(byte[][] byteMatrix,int width,int heigt){
		int[] intArray = new int[width*heigt];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < heigt; y++) {
				intArray[x*y] = byteMatrix[x][y];
			}
		}
		return intArray;
	}
	
	public static void imprimirArreglo(int[] arr){
		System.out.print(Arrays.toString(arr));
	}
	
	public static void createImage(BufferedImage img){
		try{
			File f = new File("C:/Users/felix/git/OCR-Weka-Project/OCR Project Weka/Output.jpg");
			ImageIO.write(img,"jpg",f);
		}catch(IOException e){
			System.out.println(e);
		}
	}
}

