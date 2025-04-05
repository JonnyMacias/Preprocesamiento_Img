package preprocesamiento;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import objetos.Coordenada;

public class ImagenGrices {
	private File archivo;
	private  BufferedImage imagenOriginal;
	private BufferedImage imagenGris;
	private BufferedImage newIMG;
	private ArrayList<Coordenada> listCoor;
	private ArrayList<Coordenada> newCoor;
	private ArrayList<Coordenada> listCero;
	public ImagenGrices(String url) {
		listCoor = new ArrayList<Coordenada>();
		newCoor = new ArrayList<Coordenada>();
		listCero = new ArrayList<Coordenada>();
		try {
            this.archivo = new File(url);
            this.imagenOriginal = ImageIO.read(archivo);
            imagenGris = new BufferedImage(imagenOriginal.getWidth(), imagenOriginal.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

            for (int i = 0; i < imagenOriginal.getWidth(); i++) {
                for (int j = 0; j < imagenOriginal.getHeight(); j++) {
                    int color = imagenOriginal.getRGB(i, j);
                    
                    // EXTRACCION DE LOS COLORES RGB
                    int rojo = (color >> 16) & 0xff;
                    int verde = (color >> 8) & 0xff;
                    int azul = color & 0xff;
                    
                    // FORMULA QUE CONVIERTE A GRICES
                    int gris = (int)(0.3 * rojo + 0.59 * verde + 0.11 * azul); 
                    gris = Math.max(0, Math.min(255, gris));
                    
                    
                    listCoor.add(new Coordenada(i, j, gris));
                }

            }
            desplazarIMG();
            recortarIMG();
            escalar();
            binarizacion();
            System.out.println("Fin");

        } catch (IOException e) {
            System.out.println("Error al procesar la imagen: " + e.getMessage());
        }
		
	}
    public static BufferedImage escalarImagen(BufferedImage imagenOriginal, int anchoNuevo, int altoNuevo) {
        Image imagenEscalada = imagenOriginal.getScaledInstance(anchoNuevo, altoNuevo, Image.SCALE_SMOOTH);
        BufferedImage bufferedEscalado = new BufferedImage(anchoNuevo, altoNuevo, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedEscalado.createGraphics();
        g2d.drawImage(imagenEscalada, 0, 0, null);
        g2d.dispose();
        
        return bufferedEscalado;
    }
    
	private int menorI() {
		int menorI = newCoor.get(0).getI();
		for (int x = 1; x < newCoor.size(); x++) {
			if (newCoor.get(x).getI() < menorI) {
				menorI = newCoor.get(x).getI();
			}
		}
		return menorI;
	}
	
	private int menorJ() {
		int menorJ = newCoor.get(0).getJ();
		for (int x = 1; x < newCoor.size(); x++) {
			if (newCoor.get(x).getJ() < menorJ) {
				menorJ = newCoor.get(x).getJ();
			}
		}
		return menorJ;
	}
	private int mayorI() {
		int mayorI = listCero.get(0).getI();
		for (int x = 1; x < listCero.size(); x++) {
			if (listCero.get(x).getI() > mayorI) {
				mayorI = listCero.get(x).getI();
			}
		}
		return mayorI;
	}
	
	private int mayorJ() {
		int mayorJ = listCero.get(0).getJ();
		for (int x = 1; x < listCero.size(); x++) {
			if (listCero.get(x).getJ() > mayorJ) {
				mayorJ = listCero.get(x).getJ();
			}
		}
		return mayorJ;
	}
	
	private void desplazarIMG() {
		 
		for(Coordenada c : listCoor) {
			if(c.getValor() <= 80) {
				newCoor.add(new Coordenada(c.getI(), c.getJ(), c.getValor()));
			}
		}
		int menori = menorI();
		int menorj = menorJ();
		for(Coordenada c: newCoor) {
			listCero.add(new Coordenada(c.getI() - menori, c.getJ() - menorj, c.getValor()));
		}
        
		
	}
	
	private void recortarIMG() {
		int mayorI = mayorI() + 1;
		int mayorJ = mayorJ() + 1;
		int tamañoTem;
		int divImg;
		int color = 255;
		
		//SI X ES MAYOR ENTONCES Y SE ENANCHARA PARA LA FURURA NORMALIZACION
		if(mayorI > mayorJ) {
			tamañoTem = mayorJ;
			mayorJ = mayorI;
			divImg = mayorI / 2;
			divImg = divImg - (tamañoTem / 2);
			normalizacion(0,divImg, mayorI, mayorJ, color);
		}else if(mayorJ > mayorI) { //SI Y ES MAYOR ENTONCES X SE ENANCHARA PARA LA FURURA NORMALIZACION
			tamañoTem = mayorI;
			mayorI = mayorJ;
			divImg = mayorJ / 2;
			divImg = divImg - (tamañoTem / 2);
			//System.out.println("Tamaño img: " + tamañoTem + "---- espacio: " + divImg);
			normalizacion(divImg, 0, mayorI, mayorJ, color);
		}
		
	}
	private void normalizacion(int imas, int jmas, int mayorI, int mayorJ, int color) {
		newIMG = new BufferedImage(mayorI, mayorJ, BufferedImage.TYPE_INT_RGB);
		System.out.println("-------------------NORMALIZACION-------------------------");
		System.out.println("i = " + mayorI);
		System.out.println("j = " + mayorJ);
		int[][] imgCut = new int [mayorI][mayorJ];
		// PRIMER FOR, RELLENAR TODO DE BLANCO, CON LAS DIMENCIONES DE LA IMAGEN NUEVA
		for(int i = 0 ; i < mayorI; i++) {
			for(int j = 0; j < mayorJ; j++) {
				imgCut[i][j] = color;
			}
		}
		
		// SEGUNDO FOR, PINTAR LA IAMGEN CENTRADA 
		for(Coordenada c: listCero) {
			imgCut[c.getI() + imas][c.getJ() + jmas] = 0;
			
		}
		//GUARDAR IMAGEN
		for(int i = 0 ; i < mayorI; i++) {
			for(int j = 0; j < mayorJ; j++) {
				int colorGris = (imgCut[i][j] << 16) | (imgCut[i][j] << 8) |imgCut[i][j];
				newIMG.setRGB(i, j, colorGris);
			}
		}
		try {
        	File archivoSalida = new File("Dataset\\IMGNormalizada.png");
			ImageIO.write(newIMG, "png", archivoSalida);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void escalar() {
		System.out.println("--------ESCALADOA");
		System.out.println("alto: 128 ----  ancho: 128");
		int anchoNuevo = 128;
        int altoNuevo = 128;
        newIMG = escalarImagen(newIMG, anchoNuevo, altoNuevo);
		
	}
	private void binarizacion() {
		for(int i = 0; i < newIMG.getWidth(); i++) {
			for(int j = 0; j < newIMG.getHeight(); j++) {
				int color = newIMG.getRGB(i, j);
				int valColor = color & 0xff;
				if(valColor <=150) {
					valColor = 0;
					int binario = (valColor << 16) | (valColor << 8)|valColor;
					newIMG.setRGB(i, j, binario);
					
				}else {
					valColor = 255;
					int binario = (valColor << 16)| (valColor << 8)|valColor;
					newIMG.setRGB(i, j, binario);
					
				}
			}
		}
		try {
			File archivoSalida = new File("Dataset\\IMGEscalada.png");
			ImageIO.write(newIMG, "png", archivoSalida);
            //ImageIO.write(newIMG, "png", new File("C:\\Users\\jonny\\Pictures\\imgGray.png"));
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	}
	
	public BufferedImage getImagenProcesada() {
		return this.newIMG;
	}


}
