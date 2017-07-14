package logical;

import java.io.IOException;
import java.io.PrintWriter;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Generates a little ARFF file with different attribute types.
 *
 * @author FracPete
 */
@SuppressWarnings("deprecation")
public class AttTest {
	
  public static void main(String[] args) throws Exception {
	  
	  FastVector atributos = new FastVector(3);
	 
    //1. Inicializar los atributos
	// Declaracion de atributo nominal y sus posibles valores
	  
	  String[] alphabetMinuscula = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
	  String[] alphabetMayuscula = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "G", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	  
	  FastVector<String> fvLetrasMinusculasVal = new FastVector(alphabetMinuscula.length);
	  
	  for(int i=0; i < alphabetMinuscula.length; i++)
	  {
		  fvLetrasMinusculasVal.addElement(alphabetMinuscula[i]);
		  
	  }
	  
	
	  Attribute at1 = new Attribute("Minusculas", fvLetrasMinusculasVal);
	  
	  Attribute at2 = new Attribute("Binario");
	  
	  // Declaracion de la clase y sus posibles valores
	  FastVector fvClaseVal = new FastVector(2);
	  fvClaseVal.addElement("Si");
	  fvClaseVal.addElement("No");
	  Attribute atClase = new Attribute("Clase", fvClaseVal);
	  
	  //Crear el vector con todos los atributos
	  atributos.addElement(at1);
	  atributos.addElement(at2);
	  atributos.addElement(atClase);
	  
    //2.Crear objeto con las instancias
     Instances data = new Instances("Objeto de Instancias", atributos, 0);

    // 3.Llenarlo de informacion 
    // Primera Instancia
     Instance inst1 = new DenseInstance(3);
     inst1.setValue((Attribute)atributos.elementAt(0),"Rojo");
     inst1.setValue((Attribute)atributos.elementAt(1), 1);
     inst1.setValue((Attribute)atributos.elementAt(2), "Si");
     data.add(inst1);
     
     // Segunda Instancia
     Instance inst2 = new DenseInstance(3);
     inst1.setValue((Attribute)atributos.elementAt(0),"Azul");
     inst1.setValue((Attribute)atributos.elementAt(1), 0);
     inst1.setValue((Attribute)atributos.elementAt(2), "No");
     data.add(inst1);
     data.add(inst2);
     
    // 4. output data
    System.out.println(data);
    
    try{
	    PrintWriter writer = new PrintWriter("training.arff", "UTF-8");
	    writer.println(data);
	    writer.close();
	} catch (IOException e) {
	   // do something
	}
  }
}
