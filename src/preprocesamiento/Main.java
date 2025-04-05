package preprocesamiento;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import objetos.Coordenada;

public class Main extends JPanel{
	private ImagenGrices grices;
	private BufferedImage image;
	private String rutaCarpeta;
	private File carpeta , carpetaTXT;
	private int contador = 9;
	private File[] archivos;
	private String coorpuesL = "";
	CuatroD cuatoD;
	
	public Main(String r, String nR) {
		leerRuta(r, nR);
		
	}
	
	
	public Main() {
		grices = new ImagenGrices("C:\\Users\\jonny\\Pictures\\Cero-1.jpg");
		cuatoD = new CuatroD(grices.getImagenProcesada());
		image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);
		
		dibujarLinea();
		//guardaIMG();
	}
	//--------------------------------------------LEER IMAGEN ------------------------------------------------------------
	private void leerRuta(String ruta, String newRuta) {
		rutaCarpeta = ruta;
		carpeta = new File(rutaCarpeta);
		coorpuesL = "";
		if (carpeta.isDirectory()) {
            archivos = carpeta.listFiles();

            if (archivos != null) {
                for (File archivo : archivos) {
                	
                	grices = new ImagenGrices(archivo.getAbsolutePath());
            		cuatoD = new CuatroD(grices.getImagenProcesada());
            		image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);
            		
            		
            		dibujarLinea();
            		guardaIMG(newRuta, archivo.getName());
            		
            		for(Coordenada c : cuatoD.getlista()) {
            			coorpuesL += c.getValorD();
            		}
            		coorpuesL += "\n";
            		grices = null;
            		cuatoD = null;
            		image = null;
                }
                crearTxt("ImgPreprocesadas\\Corpues\\Numeros-", coorpuesL);
            } else {
                System.out.println("La carpeta está vacía o no se pudo acceder.");
            }
        } else {
            System.out.println("La ruta especificada no es un directorio válido.");
        }
	}
	//------------------------------------------- CREAR CORPUES TXT ------------------------------------------------------
	private void crearTxt(String ruta, String corpues) {
		carpetaTXT = new File(ruta + contador + ".txt");
		try {
            // Verificar si el archivo ya existe
            if (carpetaTXT.createNewFile()) {
                System.out.println("Archivo creado: " + carpetaTXT.getName());
            } else {
                System.out.println("El archivo ya existe.");
            }

            // Escribir en el archivo
            FileWriter escritor = new FileWriter(carpetaTXT);
            escritor.write(corpues);
            escritor.close();

            System.out.println("Contenido escrito en el archivo.");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al crear el archivo.");
            e.printStackTrace();
        }
	}
	
	//--------------------------------------------DIBUJAR IMAGEN-----------------------------------------------------------
	
	@Override
    protected void paintComponent(Graphics g) {
		super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
        
	}
	
	private void dibujarLinea() {
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.setColor(Color.BLACK);
        int x1;
        int x2;
        int y1;
        int y2;
        
        for (int i = 0; i < cuatoD.getlista().size(); i++) {
        	if( i + 1 < cuatoD.getlista().size()) {
        		x1 = cuatoD.getlista().get(i).getI();
                y1 = cuatoD.getlista().get(i).getJ();
                x2 = cuatoD.getlista().get(i + 1).getI();
                y2 = cuatoD.getlista().get(i + 1).getJ();
        	}else {
        		x1 = cuatoD.getlista().get(0).getI();
                y1 = cuatoD.getlista().get(0).getJ();
                x2 = cuatoD.getlista().get(i).getI();
                y2 = cuatoD.getlista().get(i).getJ();
        	}
            
            g2d.drawLine(x1, y1, x2, y2);
        }
        g2d.dispose();
    }
	
	private void guardaIMG(String ruta, String nombre) {
        try {
            // Guardamos la imagen en un archivo PNG
            File file = new File(ruta + nombre);
            ImageIO.write(image, "png", file);
            System.out.println("Imagen guardada como 'lineas.png'");
        } catch (IOException e) {
            System.out.println("Error al guardar la imagen: " + e.getMessage());
        }
    }
	
	//----------------------------------------------
	public static void main(String[] args) {
		
		//Aquí se hizo la prueba individualmente, pero se puede implementar una función
		//para crear las carpetas según las muestras que se tengan para preprocesar. 
		
		//Main numero0 = new Main("Dataset\\Dígito 0", "ImgPreprocesadas\\Contorno 0\\");
		//Main numero1 = new Main("Dataset\\Dígito 1", "ImgPreprocesadas\\Contorno 1\\");
		//Main numero2 = new Main("Dataset\\Dígito 2", "ImgPreprocesadas\\Contorno 2\\");
		//Main numero3 = new Main("Dataset\\Dígito 3", "ImgPreprocesadas\\Contorno 3\\");
		//Main numero4 = new Main("Dataset\\Dígito 4", "ImgPreprocesadas\\Contorno 4\\");
		//Main numero5 = new Main("Dataset\\Dígito 5", "ImgPreprocesadas\\Contorno 5\\");
		//Main numero6 = new Main("Dataset\\Dígito 6", "ImgPreprocesadas\\Contorno 6\\");
		//Main numero7 = new Main("Dataset\\Dígito 7", "ImgPreprocesadas\\Contorno 7\\");
		//Main numero8 = new Main("Dataset\\Dígito 8", "ImgPreprocesadas\\Contorno 8\\");
		//Main numero9 = new Main("Dataset\\Dígito 9", "ImgPreprocesadas\\Contorno 9\\");
		
		
		//Main call = new Main();
		/*JFrame frame = new JFrame("Dibujar Múltiples Líneas en JPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        	
        Main panel = new Main();
        frame.add(panel);

        frame.setVisible(true);*/

	}

}
