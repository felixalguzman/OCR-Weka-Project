package visual;

import java.awt.EventQueue;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;
import weka.core.converters.ConverterUtils.DataSource;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.border.TitledBorder;



import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JCheckBox;

@SuppressWarnings("deprecation")
public class Principal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private JRadioButton classificadorSMO;
	private JRadioButton clasificadorNB;
	private JRadioButton clasificadorJ48;
	private JTextArea textArea;
	private JScrollPane scrollPane_1 ;
	private JCheckBox chckbxMinusculas;
	private JCheckBox chckbxMayusculas;
	private String Mayusculas = "Mayusculas Training Images/";
	private String Minusculas = "Minusculas Training Images/";
	private String Todas = "Training Images/";
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
		setTitle("OCR - Weka");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[641px,grow]", "[796px,grow,fill]"));



		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, "cell 0 0,growx,aligny top");

		pack();

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Entrenamiento de Imagenes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabbedPane.addTab("Entrenamiento", null, panel, null);
		panel.setLayout(new MigLayout("", "[53px][279px][138px][124px]", "[23px][23px]"));

		JLabel lblDirectorio = new JLabel("Directorio: ");
		panel.add(lblDirectorio, "cell 0 0,alignx left,aligny center");

		rutaEntrenamiento = new JTextField();
		rutaEntrenamiento.setEditable(false);
		panel.add(rutaEntrenamiento, "cell 1 0,growx,aligny bottom");
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


				if(rutaEntrenamiento.getText().equalsIgnoreCase("") && !chckbxMayusculas.isSelected() && !chckbxMinusculas.isSelected())
				{
					vacio = false;
					if(rutaEntrenamiento.getText().equalsIgnoreCase("") &&  (!chckbxMayusculas.isSelected() && !chckbxMinusculas.isSelected()))
					{
						JOptionPane.showMessageDialog(null, "Debe elegir la carpeta con imagenes");
					}
					else if( !chckbxMayusculas.isSelected() && !chckbxMinusculas.isSelected())
					{
						JOptionPane.showMessageDialog(null, "Debe elegir por lo menos 1 tipo de letra");
					}

				}
				else
				{
					vacio = true;
					if(chckbxMayusculas.isSelected() && !chckbxMinusculas.isSelected())
					{
						rutaEntrenamiento.setText(Mayusculas);
						entrenar(Mayusculas, entrenamiento, "Entrenado correctamente");
					}
					else if(!chckbxMayusculas.isSelected() && chckbxMinusculas.isSelected())
					{
						rutaEntrenamiento.setText(Minusculas);
						entrenar(Minusculas, entrenamiento, "Entrenado correctamente");
					}
					else if(chckbxMayusculas.isSelected() && chckbxMinusculas.isSelected())
					{
						rutaEntrenamiento.setText(Todas);
						entrenar(Todas, entrenamiento, "Entrenado correctamente");
					}
					else
					{
						entrenar(rutaEntrenamiento.getText(), entrenamiento, "Entrenado correctamente");
					}

				}


			}
		});
		panel.add(btnEntrenar, "cell 3 0,growx,aligny top");

		chckbxMayusculas = new JCheckBox("Mayusculas");
		chckbxMayusculas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {


				if(chckbxMayusculas.isSelected() || chckbxMinusculas.isSelected())
				{
					btnSeleccionar.setEnabled(false);
				}
				else if(!chckbxMayusculas.isSelected() && !chckbxMinusculas.isSelected())
				{
					btnSeleccionar.setEnabled(true);
				}
			}
		});
		panel.add(chckbxMayusculas, "cell 2 1,alignx left,aligny top");

		chckbxMinusculas = new JCheckBox("Minusculas");
		chckbxMinusculas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(chckbxMayusculas.isSelected() || chckbxMinusculas.isSelected())
				{
					btnSeleccionar.setEnabled(false);
				}
				else if(!chckbxMayusculas.isSelected() && !chckbxMinusculas.isSelected())
				{
					btnSeleccionar.setEnabled(true);
				}
			}
		});
		panel.add(chckbxMinusculas, "cell 3 1,alignx left,aligny top");

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Seleccionar carpeta de imagenes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		tabbedPane.addTab("Procesar", null, panel_1, null);
		panel_1.setLayout(new MigLayout("", "[89.00px][150.00px][99px][83px][57.00][55.00]", "[25px][][][46.00][51.00][32.00][527.00]"));

		JLabel lblDireccion = new JLabel("Directorio:");
		panel_1.add(lblDireccion, "cell 0 0,alignx center,aligny center");

		carpetaImagenesPrueba = new JTextField();
		carpetaImagenesPrueba.setEditable(false);
		panel_1.add(carpetaImagenesPrueba, "cell 1 0 3 1,growx,aligny center");
		carpetaImagenesPrueba.setColumns(10);

		JButton btnProcesar = new JButton("Procesar");
		btnProcesar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(carpetaImagenesPrueba.getText().equalsIgnoreCase("") ||  (!clasificadorJ48.isSelected() && !clasificadorNB.isSelected() && !classificadorSMO.isSelected()))
				{

					if(carpetaImagenesPrueba.getText().equalsIgnoreCase("") && (clasificadorJ48.isSelected() || clasificadorNB.isSelected() || classificadorSMO.isSelected()) )
					{
						JOptionPane.showMessageDialog(null, "Debe elegir la carpeta con imagenes");
					}
					else if(carpetaImagenesPrueba.getText().equalsIgnoreCase("") && (!clasificadorJ48.isSelected() && !clasificadorNB.isSelected() && !classificadorSMO.isSelected()))
					{
						JOptionPane.showMessageDialog(null, "Debe elegir la carpeta con imagenes y  1 clasificador");

					} 
					else
					{
						JOptionPane.showMessageDialog(null, "Debe elegir 1 clasificador");
					}
				}
				else if(clasificadorJ48.isSelected() || clasificadorNB.isSelected() || classificadorSMO.isSelected())
				{
					vacio = false;
					entrenar(carpetaImagenesPrueba.getText(), prueba, "Clasificado correctamente");


					try {
						if (classificadorSMO.isSelected() || clasificadorJ48.isSelected() || clasificadorNB.isSelected()) {

							if(classificadorSMO.isSelected())
							{
								predecirCaracteresSMO();
							}
							else if(clasificadorJ48.isSelected())
							{
								predecirCaracteresJ48();

							}else if(clasificadorNB.isSelected())
							{
								predecirCaracteresNB();
							}

						}
						else
						{
							JOptionPane.showMessageDialog(null, "Debe elegir 1 clasificador");
						}

					} catch (Exception e1) {
						
						e1.printStackTrace();
					}
				}

			}
		});

		btnSeleccionImagen = new JButton("Seleccionar");
		btnSeleccionImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				seleccionarImagen();


			}
		});
		panel_1.add(btnSeleccionImagen, "cell 4 0,alignx right,aligny center");
		panel_1.add(btnProcesar, "cell 5 0,alignx center,aligny center");

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Clasificador", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.add(panel_2, "cell 0 2 2 4,grow");
		panel_2.setLayout(null);

		classificadorSMO = new JRadioButton("SMO");
		classificadorSMO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clasificadoresS();
			}
		});
		classificadorSMO.setBounds(8, 31, 123, 25);
		panel_2.add(classificadorSMO);

		clasificadorNB = new JRadioButton("Naive Bayes");
		clasificadorNB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				clasificadoresN();

			}
		});
		clasificadorNB.setBounds(8, 61, 123, 25);
		panel_2.add(clasificadorNB);

		clasificadorJ48 = new JRadioButton("J48");
		clasificadorJ48.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				clasificadoresJ();
			}

		});
		clasificadorJ48.setBounds(8, 91, 123, 25);
		panel_2.add(clasificadorJ48);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Resultados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.add(panel_3, "cell 0 6 6 1,grow");
		panel_3.setLayout(null);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(12, 23, 600, 491);
		panel_3.add(scrollPane_1);



		textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		textArea.setEditable(false);
		setExtendedState(JFrame.MAXIMIZED_BOTH);


		clasificadorJ48.setSelected(false);
		clasificadorNB.setSelected(false);
		classificadorSMO.setSelected(false);


		

	}
	/*
	public void CreateResizeImage(String path){
		File f = new File(path);
		File f2 = null;
		BufferedImage  bffImg= null;
		try {
			bffImg = escala(ImageIO.read(f),80,80);
		} catch (IOException e) {
			e.printStackTrace();
		}
		f2 = new File(path+f.getName()+"Output.png");
		try {
			ImageIO.write(bffImg,"png",f2);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}*/

	private static BufferedImage escalaGrises(BufferedImage img) {

		int alpha, red, green, blue;
		int newPixel;

		BufferedImage lum = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

		for(int i=0; i<img.getWidth(); i++) {
			for(int j=0; j<img.getHeight(); j++) {

				// Get pixels by R, G, B
				alpha = new Color(img.getRGB(i, j)).getAlpha();
				red = new Color(img.getRGB(i, j)).getRed();
				green = new Color(img.getRGB(i, j)).getGreen();
				blue = new Color(img.getRGB(i, j)).getBlue();

				red = (int) (0.21 * red + 0.71 * green + 0.07 * blue);
				// Return back to img format
				newPixel = colorToRGB(alpha, red, red, red);

				// Write pixels into image
				lum.setRGB(i, j, newPixel);

			}
		}

		return lum;

	}

	private static int colorToRGB(int alpha, int red, int green, int blue) {

		int newPixel = 0;
		newPixel += alpha;
		newPixel = newPixel << 8;
		newPixel += red; newPixel = newPixel << 8;
		newPixel += green; newPixel = newPixel << 8;
		newPixel += blue;

		return newPixel;

	}

	public void clasificadoresJ()
	{
		if(clasificadorJ48.isSelected())
		{
			clasificadorNB.setSelected(false);
			classificadorSMO.setSelected(false);

		} 
	}

	public void clasificadoresN()
	{
		if(clasificadorNB.isSelected())
		{
			classificadorSMO.setSelected(false);
			clasificadorJ48.setSelected(false);

		}
	}

	public void clasificadoresS()
	{
		if(classificadorSMO.isSelected())
		{
			clasificadorJ48.setSelected(false);
			clasificadorNB.setSelected(false);
		}
	}

	public void seleccionarImagen()
	{
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(defaultlocation);
		fc.setDialogTitle("Elegir imagen");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);


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
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);


		if(fc.showOpenDialog(btnSeleccionImagen) == JFileChooser.APPROVE_OPTION){
			rutaEntrenamiento.setText(fc.getSelectedFile().getAbsolutePath());

			try {

			} catch (Exception e1) {

				e1.printStackTrace();
			}
		}

	}

	/*
	public void resize(String ruta){
		File dir = new File(ruta);
		File[] directoryListing = dir.listFiles();

		if (directoryListing != null) {

			for (File child : directoryListing) {

				CreateResizeImage(child.getAbsolutePath());

			}
		}


	}*/


	public void entrenar(String ruta, String lugarGuardar, String mensaje){
		File dir = new File(ruta);
		File[] directoryListing = dir.listFiles();

		if (directoryListing != null) {
			int imageIndex =0;

			for (File child : directoryListing) {
				System.out.println("Arreglo de bits de la imagen "+imageIndex+"\n");
				
				if(imageIndex == 0){
					
					crearARFF(convertirBinario(child), lugarGuardar);
					try {
						data = cargarARFF(entrenamiento, directoryListing.length);

					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
				imageIndex++;
				
				data.add(insertarInstancia(convertirBinario(child), obtenerClase(child), data));
			}

			guardarARFF(data, lugarGuardar);
		} 
		
		if(vacio)
		{
			JOptionPane.showMessageDialog(null, mensaje);
		}


	}

	private static BufferedImage metodoOtsu(BufferedImage img) {

		int red;
		int newPixel;
		int threshold = segmentarImagen(img);

		BufferedImage binarized = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

		for(int i=0; i<img.getWidth(); i++)
		{
			for(int j=0; j<img.getHeight(); j++) 
			{

				red = new Color(img.getRGB(i, j)).getRed();
				int alpha = new Color(img.getRGB(i, j)).getAlpha();
				
				if(red > threshold) 
				{
					newPixel = 255;
				}
				else 
				{
					newPixel = 0;
				}
				
				newPixel = colorToRGB(alpha, newPixel, newPixel, newPixel);
				binarized.setRGB(i, j, newPixel); 

			}
		}

		return binarized;

	}

	private static int segmentarImagen(BufferedImage img) {

		int[] histogram = realizarHistograma(img);
		int total = img.getHeight() * img.getWidth();

		float sum = 0;
		for(int i=0; i<256; i++) sum += i * histogram[i];

		float sumB = 0;
		int wB = 0;
		int wF = 0;

		float varMax = 0;
		int threshold = 0;

		for(int i=0 ; i<256 ; i++) {
			wB += histogram[i];
			if(wB == 0) continue;
			wF = total - wB;

			if(wF == 0) break;

			sumB += (float) (i * histogram[i]);
			float mB = sumB / wB;
			float mF = (sum - sumB) / wF;

			float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

			if(varBetween > varMax) {
				varMax = varBetween;
				threshold = i;
			}
		}

		return threshold;

	}

	public static int[] realizarHistograma(BufferedImage input) {

		int[] histogram = new int[256];

		for(int i=0; i<histogram.length; i++) histogram[i] = 0;

		for(int i=0; i<input.getWidth(); i++) {
			for(int j=0; j<input.getHeight(); j++) {
				int red = new Color(input.getRGB (i, j)).getRed();
				histogram[red]++;
			}
		}

		return histogram;

	}
	/*
	public   BufferedImage escala(BufferedImage imageToScale, int dWidth, int dHeight) {
		BufferedImage scaledImage = null;
		if (imageToScale != null) {
			scaledImage = new BufferedImage(dWidth, dHeight, imageToScale.getType());
			Graphics2D graphics2D = scaledImage.createGraphics();
			graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
			graphics2D.dispose();
		}
		return scaledImage;
	}*/

	private static BufferedImage resize(BufferedImage img, int newW, int newH) {
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return dimg;
	}


	public int[] convertirBinario(File imageFile){
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(imageFile);
		} catch (IOException e) {
		}

		img = resize(img,80,80);
		img = escalaGrises(img);
		img = metodoOtsu(img);

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
		intArray = arregloBytesaInt(pixels, img.getWidth(), img.getHeight());
		return intArray;
	}

	public int[] arregloBytesaInt(byte[][] byteMatrix,int width,int heigt){
		int[] intArray = new int[width*heigt];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < heigt; y++) {
				intArray[x*y] = byteMatrix[x][y];
			}
		}
		return intArray;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
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
			System.out.println(e);
		}
	}

	public String obtenerClase(File f ){
		String clase = null;

		if(f.getName().substring(0, 3).equals("ENE") )
		{
			clase = "ENE";
		}
		else if(f.getName().substring(0, 3).equals("ene") )
		{
			clase = "ene";
		}
		else
		{
			clase = f.getName().substring(0,1);
		}

		return clase;
	}

	@SuppressWarnings("unused")
	public void predecirCaracteresSMO() throws Exception
	{

		BufferedReader datafile = leerARFF(entrenamiento);
		DataSource source = new DataSource(entrenamiento);
		Instances data = source.getDataSet();
		data.setClassIndex(data.numAttributes() -1);

		SMO smo = new SMO();
		smo.buildClassifier(data);

		DataSource source1 = new DataSource(prueba);
		Instances pruebadataset = source1.getDataSet();
		pruebadataset.setClassIndex(pruebadataset.numAttributes() -1);

		Evaluation eval = new Evaluation(data);
		eval.evaluateModel(smo, pruebadataset);

		String resultados = "";
		resultados += "\nResultados \n=========================\n";
		resultados += "Clase Actual, Prediccion del SMO\n ";

		for(int i =0; i < pruebadataset.numInstances(); i++)
		{
			double valorActual = pruebadataset.instance(i).classValue();
			Instance nuevaInstancia = pruebadataset.instance(i);
			double predSMO = smo.classifyInstance(nuevaInstancia);
			resultados += "Real:  " + pruebadataset.classAttribute().value((int)pruebadataset.instance(i).classValue()) + " . Prediccion: " + pruebadataset.classAttribute().value((int)predSMO) + "\n";
		}

		resultados += "\n";
		resultados += eval.toSummaryString(); // Imprime un resumen de los datos (estadisticas)
		guardarResultados("resultados.txt", resultados);
		resultados += "\n";
		resultados += eval.toClassDetailsString(); // Imprime el detalle por clase

		//resultados += eval.toMatrixString(); // Imprime la matriz de confusion
		textArea.setText(resultados);

		//return resultados;*/
		JOptionPane.showMessageDialog(null, "Clasificado correctamente");
		textArea.setCaretPosition(0);

	}


	@SuppressWarnings("unused")
	public void predecirCaracteresJ48() throws Exception
	{

		BufferedReader datafile = leerARFF(entrenamiento);
		DataSource source = new DataSource(entrenamiento);
		Instances data = source.getDataSet();
		data.setClassIndex(data.numAttributes() -1);

		J48 j = new J48();
		j.buildClassifier(data);


		DataSource source1 = new DataSource(prueba);
		Instances pruebadataset = source1.getDataSet();
		pruebadataset.setClassIndex(pruebadataset.numAttributes() -1);

		Evaluation eval = new Evaluation(data);
		eval.evaluateModel(j, pruebadataset);

		String resultados = "";
		resultados += "\nResultados \n=========================\n";
		resultados += "Clase Actual, Prediccion del J48\n ";

		for(int i =0; i < pruebadataset.numInstances(); i++)
		{
			double valorActual = pruebadataset.instance(i).classValue();
			Instance nuevaInstancia = pruebadataset.instance(i);
			double predSMO = j.classifyInstance(nuevaInstancia);
			resultados += "Real:  " + pruebadataset.classAttribute().value((int)pruebadataset.instance(i).classValue()) + " . Prediccion: " + pruebadataset.classAttribute().value((int)predSMO) + "\n";
		}

		resultados += "\n";
		resultados += eval.toSummaryString(); // Imprime un resumen de los datos (estadisticas)
		resultados += "\n";
		resultados += eval.toClassDetailsString(); // Imprime el detalle por clase

		//resultados += eval.toMatrixString(); // Imprime la matriz de confusion
		textArea.setText(resultados);
		//return resultados;*/
		JOptionPane.showMessageDialog(null, "Clasificado correctamente");
		textArea.setCaretPosition(0);

	}


	@SuppressWarnings("unused")
	public void predecirCaracteresNB() throws Exception
	{

		BufferedReader datafile = leerARFF(entrenamiento);
		DataSource source = new DataSource(entrenamiento);
		Instances data = source.getDataSet();
		data.setClassIndex(data.numAttributes() -1);

		NaiveBayes nb = new NaiveBayes();
		nb.buildClassifier(data);


		DataSource source1 = new DataSource(prueba);
		Instances pruebadataset = source1.getDataSet();
		pruebadataset.setClassIndex(pruebadataset.numAttributes() -1);

		Evaluation eval = new Evaluation(data);
		eval.evaluateModel(nb, pruebadataset);

		String resultados = "";
		resultados += "\nResultados \n=========================\n";
		resultados += "Clase Actual, Prediccion del Naive Bayes\n ";

		for(int i =0; i < pruebadataset.numInstances(); i++)
		{
			double valorActual = pruebadataset.instance(i).classValue();
			Instance nuevaInstancia = pruebadataset.instance(i);
			double predSMO = nb.classifyInstance(nuevaInstancia);
			resultados += "Real:  " + pruebadataset.classAttribute().value((int)pruebadataset.instance(i).classValue()) + " . Prediccion: " + pruebadataset.classAttribute().value((int)predSMO) + "\n";
		}

		resultados += "\n";
		resultados += eval.toSummaryString(); // Imprime un resumen de los datos (estadisticas)
		resultados += "\n";
		resultados += eval.toClassDetailsString(); // Imprime el detalle por clase

		//resultados += eval.toMatrixString(); // Imprime la matriz de confusion
		textArea.setText(resultados);
		//return resultados;*/
		JOptionPane.showMessageDialog(null, "Clasificado correctamente");
		textArea.setCaretPosition(0);

	}

	public void guardarResultados(String ruta, String datos) throws FileNotFoundException
	{
		try{
			PrintWriter writer = new PrintWriter(ruta, "UTF-8");
			writer.println(datos);
			writer.close();
		} catch (IOException e) {
			
		}
	}



	public  BufferedReader leerARFF(String ruta){
		BufferedReader inputreader = null;

		try{
			inputreader = new BufferedReader(new FileReader(ruta));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return inputreader;
	}

}
