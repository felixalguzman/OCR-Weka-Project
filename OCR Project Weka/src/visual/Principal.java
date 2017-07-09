package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.border.TitledBorder;



import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class Principal extends JFrame {

	private JPanel contentPane;
	private JTextField rutaEntrenamiento;
	private JTextField rutaImagen;
	private File defaultlocation = new File(".");
	private JButton btnSeleccionEntrena;
	private JButton btnSeleccionImagen;

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
		tabbedPane.setBounds(5, 5, 1431, 819);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Seleccionar Carpeta con Imagenes de entrenamiento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabbedPane.addTab("Entrenar", null, panel, null);
		panel.setLayout(new MigLayout("", "[86px][96.00,grow][][40.00][][][][37.00][61.00][155.00]", "[20px]"));
		
		JLabel lblDirectorio = new JLabel("Directorio: ");
		panel.add(lblDirectorio, "flowx,cell 0 0,alignx trailing,growy");
		
		rutaEntrenamiento = new JTextField();
		rutaEntrenamiento.setEditable(false);
		panel.add(rutaEntrenamiento, "cell 1 0,growx");
		rutaEntrenamiento.setColumns(10);
		
		btnSeleccionEntrena = new JButton("Seleccionar");
		btnSeleccionEntrena.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				seleccionarCarpeta();
			}
		});
		panel.add(btnSeleccionEntrena, "cell 9 0,alignx right,aligny center");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Seleccionar Imagen a Procesar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabbedPane.addTab("Procesar", null, panel_1, null);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] {89, 427, 68, 147, 80};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblDireccion = new JLabel("Directorio:");
		GridBagConstraints gbc_lblDireccion = new GridBagConstraints();
		gbc_lblDireccion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDireccion.gridx = 0;
		gbc_lblDireccion.gridy = 0;
		panel_1.add(lblDireccion, gbc_lblDireccion);
		
		rutaImagen = new JTextField();
		rutaImagen.setEditable(false);
		GridBagConstraints gbc_rutaImagen = new GridBagConstraints();
		gbc_rutaImagen.fill = GridBagConstraints.HORIZONTAL;
		gbc_rutaImagen.insets = new Insets(0, 0, 5, 5);
		gbc_rutaImagen.gridx = 1;
		gbc_rutaImagen.gridy = 0;
		panel_1.add(rutaImagen, gbc_rutaImagen);
		rutaImagen.setColumns(10);
		
		btnSeleccionImagen = new JButton("Seleccionar");
		btnSeleccionImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				seleccionarImagen();
				
				
			}
		});
		GridBagConstraints gbc_btnSeleccionImagen = new GridBagConstraints();
		gbc_btnSeleccionImagen.anchor = GridBagConstraints.EAST;
		gbc_btnSeleccionImagen.insets = new Insets(0, 0, 5, 5);
		gbc_btnSeleccionImagen.gridx = 2;
		gbc_btnSeleccionImagen.gridy = 0;
		panel_1.add(btnSeleccionImagen, gbc_btnSeleccionImagen);
		
		JButton btnProcesar = new JButton("Procesar");
		GridBagConstraints gbc_btnProcesar = new GridBagConstraints();
		gbc_btnProcesar.insets = new Insets(0, 0, 5, 0);
		gbc_btnProcesar.gridx = 3;
		gbc_btnProcesar.gridy = 0;
		panel_1.add(btnProcesar, gbc_btnProcesar);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	
	public void seleccionarCarpeta()
	{
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(defaultlocation);
		fc.setDialogTitle("Elegir archivo");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		
		if(fc.showOpenDialog(btnSeleccionEntrena) == JFileChooser.APPROVE_OPTION){
			rutaEntrenamiento.setText(fc.getCurrentDirectory().getAbsolutePath());
			
			try {
			
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
		}
		
	}
	
	public void seleccionarImagen()
	{
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(defaultlocation);
		fc.setDialogTitle("Elegir imagen");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		
		if(fc.showOpenDialog(btnSeleccionImagen) == JFileChooser.APPROVE_OPTION){
			rutaImagen.setText(fc.getSelectedFile().getAbsolutePath());
			
			try {
			
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
		}
		
	}
}
