package objetos;

public class Coordenada {
	private int i;
	private int j;
	private int valor;
	private int valorD;
	public Coordenada() {}
	public Coordenada(int i, int j, int valor) {
		this.valor = valor;
		this.i = i;
		this.j = j;	
	}
	
	public Coordenada(int i, int j, int valor, int d) {
		this.valor = valor;
		this.i = i;
		this.j = j;	
		this.valorD = d;
	}
	
	public int getI() {
		return this.i;
	}
	public int getJ() {
		return this.j;
	}
	public int getValor() {
		return this.valor;
	}
	public int getValorD() {
		return this.valorD;
	}
	
	public void setI(int i) {
		this.i = i;
	}
	public void setJ(int j) {
		this.j = j;
	}
	public void setValor(int v) {
		this.valor = v;
	}
	public void setValorD(int d) {
		this.valorD = d;
	}
}
