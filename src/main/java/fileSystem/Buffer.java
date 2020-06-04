package fileSystem;

public class Buffer {
	public byte [] bufferBytes;
	private int cantidadMaxima;
	private int cantidadElementos;
	
	public Buffer(int cantidadDeBytes) {
		bufferBytes = new byte[cantidadDeBytes];
		this.cantidadElementos = 0;
		this.cantidadMaxima = cantidadDeBytes;
	}
	
	public int bufferLength() {
		return this.cantidadElementos;
	}
	
	public void agregarByte(byte elemento) {
		if(cantidadElementos < cantidadMaxima) {
			bufferBytes[cantidadElementos] = elemento;
			cantidadElementos++;
		}else {
			//TODO no puedo agregar mas elementos porque es arreglo estatico excepcion
		}
	}
}
