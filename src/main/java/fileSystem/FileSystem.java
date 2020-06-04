package fileSystem;

import java.util.function.Consumer;

public interface FileSystem {
	public void abrirArchivo();
	
	public void cerrarArchivo();
	
	public Buffer leerBytesSincronicamente(int desdeDondeLeer, int cuantosBytesLeer);
	
	public void escribirBytesAlFinalDeUnArchivo(Buffer buffer);
	
	public void escribirBytesEnUnArchivo(Buffer buffer, int dondeEscribir);
	
	public void leerBytesAsincronicamente(int desdeDondeLeer, int cuantosBytesLeer, Consumer<Integer> callback);
	
	public void copiarBytesDeOtroArchivo(String pathOtroArchivo, int tamanioDeBloques);
}
