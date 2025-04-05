package preprocesamiento;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import objetos.Coordenada;

public class CuatroD {
	private BufferedImage imagen;
	private Coordenada primerVal;
	private Coordenada ultimoVal;
	private ArrayList<Coordenada> listaD;
	private ArrayList<Coordenada> imgextremos;

	public CuatroD(BufferedImage img) {
		System.out.println("Iniciado");
		imagen = new BufferedImage(32, 32, BufferedImage.TYPE_BYTE_GRAY);
		this.imagen = img;
		listaD = new ArrayList<Coordenada>();
		imgextremos = new ArrayList<Coordenada>();
		ultimoVal = new Coordenada();
		primerValor();
		recorrido();
	}

	private void primerValor() {
		boolean primval = false;
		for (int al = 0; al < imagen.getHeight(); al++) {
			for (int an = 0; an < imagen.getWidth(); an++) {
				int color = imagen.getRGB(an, al);
				int valColor = color & 0xff;
				//System.out.print(valColor);
				if (valColor == 0) {
					//System.out.println("Me meti: " + an + ", " + al);
					primerVal = new Coordenada(an, al, valColor, 0);
					primval = true;
					break;
				}
			}
			if (primval == true) {
				break;
			}
			//System.out.println("");
		}

		//System.out.println("Terminado");
	}

	private void recorrido() {
		String opc = "ningina";
		int contaI = primerVal.getI();
		int contaJ = primerVal.getJ();

		while (primerVal.getI() != ultimoVal.getI() || primerVal.getJ() != ultimoVal.getJ()) {
			//System.out.println(".........................................................................");
			if (contaI < imagen.getHeight() - 1 && (imagen.getRGB((contaI + 1), contaJ) & 0xff) == 0 && contaJ == 0) {
				opc = "OPCION 1";
				contaI++;
				ultimoVal.setI(contaI);
				ultimoVal.setJ(contaJ);
				listaD.add(new Coordenada(contaI, contaJ, 0, 0));
				continue;
			}

			// ---------------------------------------IZQUIERDA------------------------------
			if ( contaI > 0 && (imagen.getRGB(contaI - 1, contaJ) & 0xff) == 0 && contaJ == imagen.getHeight() -1) {
				opc = "OPCION 4$";
				contaI--;
				ultimoVal.setI(contaI);
				ultimoVal.setJ(contaJ);
				listaD.add(new Coordenada(contaI, contaJ, 0, 2));
				//continue;
				//break;
			}
			// ---------------------------------------DERECHA-----------------------------
			if (contaJ > 0 && contaJ < (imagen.getHeight() - 1) && contaI < (imagen.getHeight() - 1) && (imagen.getRGB(contaI + 1, contaJ) & 0xff) == 0
					&& (imagen.getRGB(contaI + 1, contaJ - 1) & 0xff) == 255) {
				opc = "OPCION 2";
				contaI++;
				ultimoVal.setI(contaI);
				ultimoVal.setJ(contaJ);
				listaD.add(new Coordenada(contaI, contaJ, 0, 0));
				// continue;
			}else if(contaJ < imagen.getWidth() - 1 && contaI < imagen.getWidth() - 1 && (imagen.getRGB(contaI + 1, contaJ) & 0xff) == 0 && (imagen.getRGB(contaI , contaJ - 1) & 0xff) == 255) {
				opc = "OPCION 2$";
				contaI++;
				ultimoVal.setI(contaI);
				ultimoVal.setJ(contaJ);
				listaD.add(new Coordenada(contaI, contaJ, 0, 0));
			}
			// ---------------------------------------ABAJO---------------------------------
			

			if(contaJ > 0 && contaI > 0 && contaJ < imagen.getHeight() - 1 && contaI < imagen.getHeight() - 1
					&& (imagen.getRGB(contaI + 1, contaJ + 1) & 0xff) == 0
					&& (imagen.getRGB(contaI + 1, contaJ) & 0xff) == 255
					&& (imagen.getRGB(contaI - 1, contaJ) & 0xff) == 255
					&& (imagen.getRGB(contaI, contaJ - 1) & 0xff) == 255) {
				opc = "OPCION 3$$$$";
				contaJ++;
				contaI++;
				ultimoVal.setI(contaI);
				ultimoVal.setJ(contaJ);
				listaD.add(new Coordenada(contaI, contaJ, 0, 3));
				//continue;
			}
			
			if (contaJ < imagen.getHeight() - 1 && contaI < imagen.getHeight() - 1 && (imagen.getRGB(contaI, contaJ + 1) & 0xff) == 0 && (imagen.getRGB(contaI + 1, contaJ) & 0xff) == 255) {
				opc = "OPCION 3";
				contaJ++;
				ultimoVal.setI(contaI);
				ultimoVal.setJ(contaJ);
				listaD.add(new Coordenada(contaI, contaJ, 0, 3));
			} else if (contaJ < imagen.getHeight() - 1 && contaI < imagen.getHeight() - 1 && (imagen.getRGB(contaI, contaJ + 1) & 0xff) == 0 && (imagen.getRGB(contaI + 1, contaJ + 1) & 0xff) == 255) {
				opc = "OPCION 3$";
				contaJ++;
				ultimoVal.setI(contaI);
				ultimoVal.setJ(contaJ);
				listaD.add(new Coordenada(contaI, contaJ, 0, 3));
			}else if(contaJ < imagen.getHeight() - 1 && (imagen.getRGB(contaI, contaJ + 1) & 0xff) == 0 && contaI == imagen.getHeight() - 1) {
				opc = "OPCION 3$$";
				contaJ++;
				ultimoVal.setI(contaI);
				ultimoVal.setJ(contaJ);
				listaD.add(new Coordenada(contaI, contaJ, 0, 3));
			}
			if(contaI + 1 <= imagen.getHeight() - 1 && contaJ + 1 <= imagen.getHeight() - 1 && (imagen.getRGB(contaI, contaJ + 1) & 0xff) == 0 && (imagen.getRGB(contaI + 1, contaJ + 1) & 0xff) == 255) {
				opc = "OPCION 3$$$";
				contaJ++;
				ultimoVal.setI(contaI);
				ultimoVal.setJ(contaJ);
				listaD.add(new Coordenada(contaI, contaJ, 0, 3));
			}
			
			
		 	// ---------------------------------------IZQUIERDA------------------------------
			if (contaI > 0 && contaJ < imagen.getHeight() - 1 && contaI < imagen.getHeight() - 1 && (imagen.getRGB(contaI - 1, contaJ) & 0xff) == 0 && (imagen.getRGB(contaI, contaJ + 1) & 0xff) == 255) {
				opc = "OPCION 4";
				contaI--;
				ultimoVal.setI(contaI);
				ultimoVal.setJ(contaJ);
				listaD.add(new Coordenada(contaI, contaJ, 0, 2));
				// continue;
				// break;
				//entro = true;
			} 
			if (contaI > 0 && contaJ < imagen.getHeight() - 1 && (imagen.getRGB(contaI - 1, contaJ) & 0xff) == 0 && (imagen.getRGB(contaI - 1, contaJ + 1) & 0xff) == 255 ) {
				opc = "OPCION 5";
				contaI--;
				ultimoVal.setI(contaI);
				ultimoVal.setJ(contaJ);
				listaD.add(new Coordenada(contaI, contaJ, 0, 2));
				// continue;
				//break;
			}else if( contaI > 0 && contaI == imagen.getHeight()-1 && (imagen.getRGB(contaI - 1, contaJ) & 0xff) == 0 && (imagen.getRGB(contaI, contaJ + 1) & 0xff) == 255) {
				opc = "OPCION 5$";
				contaI--;
				ultimoVal.setI(contaI);
				ultimoVal.setJ(contaJ);
				listaD.add(new Coordenada(contaI, contaJ, 0, 2));
				// continue;
				//break;
			}
			// -------------------------------------ARRIBA-----------------------------------
			if (contaI > 0 && contaJ > 0 && contaJ < imagen.getHeight()
					&& (imagen.getRGB(contaI, contaJ - 1) & 0xff) == 0
					&& (imagen.getRGB(contaI - 1, contaJ) & 0xff) == 255) {
				opc = "OPCION 6";
				contaJ--;
				ultimoVal.setI(contaI);
				ultimoVal.setJ(contaJ);
				listaD.add(new Coordenada(contaI, contaJ, 0, 1));
				// continue;

			}
			if(contaI > 0 && contaJ > 0 && contaJ < imagen.getHeight()
					&& (imagen.getRGB(contaI, contaJ - 1) & 0xff) == 0
					&& (imagen.getRGB(contaI - 1, contaJ - 1) & 0xff) == 255) {
				opc = "OPCION 6$";
				contaJ--;
				ultimoVal.setI(contaI);
				ultimoVal.setJ(contaJ);
				listaD.add(new Coordenada(contaI, contaJ, 0, 1));
			}else if(contaI == 0 && (imagen.getRGB(contaI, contaJ - 1) & 0xff) == 0) {
				opc = "OPCION 6$$";
				contaJ--;
				ultimoVal.setI(contaI);
				ultimoVal.setJ(contaJ);
				listaD.add(new Coordenada(contaI, contaJ, 0, 1));
			} else if(contaI > 0 && contaJ > 0 && contaJ < imagen.getHeight() - 1 && contaI < imagen.getHeight() - 1
					  && (imagen.getRGB(contaI + 1 , contaJ - 1) & 0xff) == 0
					  && (imagen.getRGB(contaI , contaJ - 1) & 0xff) == 255
					  && (imagen.getRGB(contaI , contaJ + 1) & 0xff) == 255
					  && (imagen.getRGB(contaI - 1, contaJ) & 0xff) == 255) {
				opc = "OPCION 6$$$";
				contaJ--;
				ultimoVal.setI(contaI);
				ultimoVal.setJ(contaJ);
				listaD.add(new Coordenada(contaI, contaJ, 0, 1));
			}

			/*if (contaI > 0 && contaJ > 0 &&  contaJ < imagen.getHeight() - 1 && contaI < imagen.getHeight() - 1 ) {
				System.out.println(opc);
				System.out.println("Ultimo i: " + contaI + "-- Ultimo J: " + contaJ);
				System.out.println(imagen.getRGB(contaI, contaJ) & 0xff);
			}
			//opc = "";
			System.out.println(".........................................................................");*/
		}

		
		System.out.println("Tamaño de lista 4D: " + listaD.size());
		
		for (Coordenada c : listaD) {
			System.out.print(c.getValorD());
		}
		System.out.println("--------------------------------------------------------");

	}
	
	public ArrayList<Coordenada> getlista(){
		return this.listaD;
	}
	
}
