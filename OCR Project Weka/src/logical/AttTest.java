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
	  FastVector fvColoresVal = new FastVector(3);
	  fvColoresVal.addElement("Rojo");
	  fvColoresVal.addElement("Azul");
	  fvColoresVal.addElement("Amarillo");
	  Attribute at1 = new Attribute("Colores", fvColoresVal);
	  
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
