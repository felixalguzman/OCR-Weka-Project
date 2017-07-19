package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import weka.classifiers.functions.SMO;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.border.TitledBorder;



import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import java.awt.Color;

public class Principal extends JFrame {

	private JPanel contentPane;
	private JTextField carpetaImagenesPrueba;
	private File defaultlocation = new File(".");
	private JButton btnSeleccionImagen;
	private static String entrenamiento = "ARFF/training.arff";
	private static String prueba = "ARFF/prueba.arff";
	private JTextField rutaEntrenamiento;
	private Instances data = null;
	private boolean vacio = false;
	
	
	private JButton btnEntrenar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);


		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(5, 5, 1432, 819);
		contentPane.add(tabbedPane);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Entrenamiento de Imagenes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabbedPane.addTab("Entrenamiento", null, panel, null);
		panel.setLayout(new MigLayout("", "[64px][279.00px][138px][124px]", "[25px][21.00][]"));

		JLabel lblDirectorio = new JLabel("Directorio: ");
		panel.add(lblDirectorio, "cell 0 0,alignx left,aligny center");

		rutaEntrenamiento = new JTextField();
		rutaEntrenamiento.setEditable(false);
		panel.add(rutaEntrenamiento, "cell 1 0,growx,aligny center");
		rutaEntrenamiento.setColumns(10);

		JButton btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				seleccionarCarpetaEntrenamiento();
			}
		});
		panel.add(btnSeleccionar, "cell 2 0,growx,aligny top");

		btnEntrenar = new JButton("Entrenar");
		btnEntrenar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			
				if(rutaEntrenamiento.getText().equalsIgnoreCase(""))
				{
					vacio = false;
					JOptionPane.showMessageDialog(null, "Debe elegir la carpeta con imagenes");
				}
				else
				{
					vacio = true;
					entrenar(rutaEntrenamiento.getText(), entrenamiento);
				}
				
				
			}
		});
		panel.add(btnEntrenar, "cell 3 0,growx,aligny top");

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Seleccionar carpeta de imagenes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		tabbedPane.addTab("Procesar", null, panel_1, null);
		panel_1.setLayout(new MigLayout("", "[60px][168px][99px][83px]", "[25px]"));

		JLabel lblDireccion = new JLabel("Directorio:");
		panel_1.add(lblDireccion, "cell 0 0,alignx center,aligny center");

		carpetaImagenesPrueba = new JTextField();
		carpetaImagenesPrueba.setEditable(false);
		panel_1.add(carpetaImagenesPrueba, "cell 1 0,growx,aligny center");
		carpetaImagenesPrueba.setColumns(10);

		btnSeleccionImagen = new JButton("Seleccionar");
		btnSeleccionImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				seleccionarImagen();


			}
		});
		panel_1.add(btnSeleccionImagen, "cell 2 0,alignx right,aligny center");

		JButton btnProcesar = new JButton("Procesar");
		btnProcesar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(carpetaImagenesPrueba.getText().equalsIgnoreCase(""))
				{
					JOptionPane.showMessageDialog(null, "Debe elegir la carpeta con imagenes");
				}
				else
				{
					entrenar(carpetaImagenesPrueba.getText(), prueba);
				}
				
			}
		});
		panel_1.add(btnProcesar, "cell 3 0,alignx center,aligny center");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	public void seleccionarImagen()
	{
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(defaultlocation);
		fc.setDialogTitle("Elegir imagen");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);


		if(fc.showOpenDialog(btnSeleccionImagen) == JFileChooser.APPROVE_OPTION){
			carpetaImagenesPrueba.setText(fc.getSelectedFile().getAbsolutePath());

			try {

			} catch (Exception e1) {

				e1.printStackTrace();
			}
		}

	}

	public void seleccionarCarpetaEntrenamiento()
	{
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(defaultlocation);
		fc.setDialogTitle("Elegir carpeta con imagenes de entrenamiento");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);


		if(fc.showOpenDialog(btnSeleccionImagen) == JFileChooser.APPROVE_OPTION){
			rutaEntrenamiento.setText(fc.getSelectedFile().getAbsolutePath());

			try {

			} catch (Exception e1) {

				e1.printStackTrace();
			}
		}

	}

	public void entrenar(String ruta, String lugarGuardar){
		File dir = new File(ruta);
		File[] directoryListing = dir.listFiles();
		
		if (directoryListing != null) {
			int imageIndex =0;
		
			
			for (File child : directoryListing) {
				System.out.println("Arreglo de bits de la imagen "+imageIndex+"\n");
				//imprimirArreglo(getBinaryFromImage(child));
				//System.out.println("\n\n\n");


				if(imageIndex == 0){
					//data = new Instances("Objeto de instancias", crearARFF(getBinaryFromImage(child)),0);

					crearARFF(getBinaryFromImage(child), lugarGuardar);
					try {
						data = cargarARFF(entrenamiento, directoryListing.length);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				imageIndex++;
				//System.out.println("Clase " + obtenerClase(child));
				data.add(insertarInstancia(getBinaryFromImage(child), obtenerClase(child), data));
				
			}

			guardarARFF(data, lugarGuardar);
		} else {
			
			
		}
		if(vacio)
		{
			JOptionPane.showMessageDialog(null, "Entrenado correctamente");
		}
		

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
				System.out.print(pixels[x][y]);
			}
			System.out.println("\n");
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

	public static FastVector crearARFF(int[] arr, String ruta){
		//1. Inicializar los atributos
		// Declaracion de atributo nominal y sus posibles valores
		FastVector atributos = new FastVector(arr.length);
		FastVector att = new FastVector();
		Attribute pixels = null ;
		atributos.addElement("0");
		atributos.addElement("1");


		for(int i=0; i <  arr.length; i++){

			pixels = new Attribute("Pixels "+i, atributos);
			att.add(pixels);
		}


		//2.Crear la clase

		String[] alphabetMinuscula = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n","ene", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
		String[] alphabetMayuscula = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N","ENE" , "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

		FastVector letra =  new FastVector(alphabetMayuscula.length+alphabetMinuscula.length);

		letra.appendElements(Arrays.asList(alphabetMayuscula));
		letra.appendElements(Arrays.asList(alphabetMinuscula));


		Attribute ClassAttribute = new Attribute("Letra", letra);

		att.add(ClassAttribute);
		//2.Crear objeto con las instancias
		Instances data = new Instances("Objeto de Instancias", att, 0);


		System.out.println(data.attribute(0));

		try{
			PrintWriter writer = new PrintWriter(ruta, "UTF-8");
			writer.println(data);
			writer.close();
		} catch (IOException e) {
			// do something
		}

		return att;
	}

	public Instances cargarARFF(String ruta, int cant) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(ruta));
		ArffReader arff = new ArffReader(reader, cant);
		Instances data = arff.getData();
		//data.setClassIndex(data.numAttributes()-1);
		return data;
	}

	public Instance insertarInstancia(int[] arr,String clase, Instances data){
		Instance inst1 = new DenseInstance(arr.length+1);
		String[] alphabetMinuscula = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n","ene", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
		String[] alphabetMayuscula = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N","ENE" , "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

		FastVector letra =  new FastVector(alphabetMayuscula.length+alphabetMinuscula.length);

		letra.appendElements(Arrays.asList(alphabetMayuscula));
		letra.appendElements(Arrays.asList(alphabetMinuscula));

		inst1.setDataset(data);
		// inst1.setValue((Attribute)atributos.elementAt(0),"Rojo");
		for(int i =0; i < arr.length; i++)
		{
			inst1.setValue(i, arr[i]);

		}


		inst1.setValue(arr.length, clase);


		return inst1;
	}

	public void guardarARFF(Instances data, String ruta)
	{
		try{
			PrintWriter writer = new PrintWriter(ruta, "UTF-8");
			writer.println(data);
			writer.close();
		} catch (IOException e) {
			// do something
		}
	}

	public String obtenerClase(File f ){
		String clase = null;
		//System.out.println(f.getName());

		if(f.getName().substring(0, 3).equals("ENE") )
		{
			clase = "ENE";
			//	System.out.println(clase);
		}
		else if(f.getName().substring(0, 3).equals("ene") )
		{
			clase = "ene";
			//	System.out.println(clase);
		}
		else
		{
			clase = f.getName().substring(0,1);
			//	System.out.println(clase);
		}



		return clase;
	}
	
	public void predecirCaracteres() throws IOException
	{
		 BufferedReader datafile = readDatafile("training.arff");
	        Instances data = new Instances(datafile);
	        datafile.close();
	        
	        data.setClassIndex(data.numAttributes()-1);
	        
	        SMO smo = new SMO();
	        
	}
	
	//public void
	
	 public  BufferedReader readDatafile(String ruta){
	        BufferedReader inputreader = null;
	        
	        try{
	            inputreader = new BufferedReader(new FileReader(ruta));
	        }catch(Exception ex){
	            ex.printStackTrace();
	        }
	        return inputreader;
	    }
}
