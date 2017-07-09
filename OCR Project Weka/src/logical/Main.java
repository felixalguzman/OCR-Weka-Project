package logical;

import java.io.IOException;
import java.io.PrintWriter;

import visual.MainWindow;

public class Main {

	public static void main(String[] args) {

		MainWindow m = new MainWindow();
		System.out.println("\n \n \n");
		//crearDataset();

	}
	
	public static void crearDataset()
	{
		try{
		    PrintWriter writer = new PrintWriter("Datasets/training.arff", "UTF-8");
		    writer.println("%% Datos de entrenamiento\n");
		    writer.println("@relation training \n \n");
		    writer.println("@data");
		    writer.close();
		} catch (IOException e) {
		   // do something
		}
	}

}
