package visual;

import javax.imageio.ImageIO;

import javax.swing.*;

import org.apache.commons.compress.utils.Charsets;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import sun.nio.cs.StandardCharsets;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
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
	/*
    public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }*/
	// @param sbi image to scale
	//* @param imageType type of image
	//* @param dWidth width of destination image
	// * @param dHeight height of destination image
	//* @param fWidth x-factor for transformation / scaling
	//* @param fHeight y-factor for transformation / scaling
	// * @return scaled image

	/* public static BufferedImage scale(BufferedImage sbi, int dWidth, int dHeight, double fWidth, double fHeight) {
        BufferedImage dbi = null;

        if(sbi != null) {
            dbi = new BufferedImage(dWidth, dHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = dbi.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
            g.drawRenderedImage(sbi, at);
        }
        return dbi;
    }*/

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
	/*
	public static BufferedImage resize(BufferedImage img, double fWidth, double fHeight){
		BufferedImage bffImage = null;

		if(img != null) {
			bffImage = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = bffImage.createGraphics();
			AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
			g.drawRenderedImage(img, at);
		}
		return bffImage;
	}*/

	public static void trainTheShit(){
		File dir = new File("Training Images/");
		File[] directoryListing = dir.listFiles();

		if (directoryListing != null) {
			for (File child : directoryListing) {

				getBinaryStringFromImage(child);
				System.out.println("\n\n\n");
			}
		} else {

		}
	}

	public static void getBinaryStringFromImage(File imageFile){
		BufferedImage img = null;
		try {
			img = ImageIO.read(imageFile);
		} catch (IOException e) {
		}
		img = scale(img,40,40);
		int[] num = new int[img.getWidth()*img.getHeight()];
		byte[][] pixels = new byte[img.getWidth()][];
		Byte[] second = new Byte[img.getWidth()*img.getHeight()];
		String imageDataString;
		for (int x = 0; x < img.getWidth(); x++) {
			pixels[x] = new byte[img.getHeight()];
			for (int y = 0; y < img.getHeight(); y++) {
				pixels[x][y] = (byte) (img.getRGB(x, y) == 0xFFFFFFFF ? 0 : 1);
				second[x*y]=(byte) (img.getRGB(x, y) == 0xFFFFFFFF ? 0 : 1);
				num[x*y] = second[x*y].intValue();
			}
		}
		for (int i = 0; i < num.length; i++) {
			System.out.println("Posicion "+i+" :"+num[i]);
		}
	}


	public static String toString(byte[][] M) {
		String separator = ", ";
		StringBuffer result = new StringBuffer();

		// iterate over the first dimension
		for (int i = 0; i < M.length; i++) {
			// iterate over the second dimension
			for(int j = 0; j < M[i].length; j++){
				result.append(M[i][j]);
				result.append(separator);
			}
			// remove the last separator
			result.setLength(result.length() - separator.length());
			// add a line break.
			result.append("\n");
		}


		return result.toString();
	}

	public static void createImage(BufferedImage img){
		try{
			File f = new File("C:/Users/felix/git/OCR-Weka-Project/OCR Project Weka/Output.jpg");
			ImageIO.write(img,"jpg",f);
		}catch(IOException e){
			System.out.println(e);
		}
	}

	/* Eduardo
    public static byte[] getBinaryStringFromImage(File imageFile)
    {
        BufferedImage img = null;
        try {
            img = ImageIO.read(imageFile);
        } catch (IOException e) {

        }
        img = resize(img,40,40);
        String name = imageFile.getName();

        byte[][] pixels = new byte[img.getWidth()][];
        byte[] second = new byte[img.getWidth()*img.getHeight()];

        for (int x = 0; x < img.getWidth(); x++) {
            pixels[x] = new byte[img.getHeight()];
            System.out.println();
            for (int y = 0; y < img.getHeight(); y++) {
                pixels[x][y] = (byte) (img.getRGB(x, y) == 0xFFFFFFFF ? 0 : 1);
                second[x*y]=(byte) (img.getRGB(x, y) == 0xFFFFFFFF ? 0 : 1);
                System.out.print(pixels[x][y]);
            }

        }

        for (Byte B:second
             ) {
            //System.out.print(B);
        }
        return second;
    }*/


}

