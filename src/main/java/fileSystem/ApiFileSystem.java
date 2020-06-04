package fileSystem;

import java.util.function.Consumer;

public class ApiFileSystem implements FileSystem {
	private int fileDescriptor;
	private String path;
	private LowLevelFileSystem apiBajoNivel;
	private int finArchivo;
	
	public ApiFileSystem(String path) {
		this.path = path;
		// TODO apiBajoNivel -> crear una instancia de la clase que implemente la interface LowLevelFileSystem
		finArchivo = 0;
	}
	
	@Override
	public void abrirArchivo() {
		fileDescriptor = apiBajoNivel.openFile(path);
		if(fileDescriptor < 0) {
			throw new AbrirArchivoExcepcion();
		}
		this.obtenerTamanioArchivo();
	}
	
	@Override
	public void cerrarArchivo() {
		apiBajoNivel.closeFile(fileDescriptor);
	}
	
	@Override
	public Buffer leerBytesSincronicamente(int desdeDondeLeer, int cuantosBytesLeer) {
		Buffer buffer = new Buffer(cuantosBytesLeer);
		if(finArchivo == 0) {
			throw new LeerArchivoExcepcion();
		}else {
			int cantidadBytesLeidos = apiBajoNivel.syncReadFile(fileDescriptor, buffer.bufferBytes, desdeDondeLeer, desdeDondeLeer + cuantosBytesLeer);
			if(cantidadBytesLeidos < 0) {
				throw new LeerArchivoExcepcion();
			}
		}
		return buffer;
	}
	
	
	public void escribirBytesAlFinalDeUnArchivo(Buffer buffer) {
		this.escribirBytesEnUnArchivo(buffer, finArchivo);
	}
	
	@Override
	public void escribirBytesEnUnArchivo(Buffer buffer, int dondeEscribir) {
		apiBajoNivel.syncWriteFile(fileDescriptor, buffer.bufferBytes, dondeEscribir, dondeEscribir + buffer.bufferLength());
		finArchivo += buffer.bufferLength();
	}
	
	@Override
	public void leerBytesAsincronicamente(int desdeDondeLeer, int cuantosBytesLeer, Consumer<Integer> callback) {
		Buffer buffer = new Buffer(cuantosBytesLeer);
		apiBajoNivel.asyncReadFile(fileDescriptor, buffer.bufferBytes, desdeDondeLeer, desdeDondeLeer + cuantosBytesLeer, callback);
	}
	
	public int cantidadDeBytesDelArchivo() {
		return finArchivo;
	}
	
	private void obtenerTamanioArchivo() {
		int cantidadBytesLeidos;
		int inicio = 0;
		do {
			byte[] bufferBytes = new byte[1];
			cantidadBytesLeidos = apiBajoNivel.syncReadFile(fileDescriptor, bufferBytes, inicio, inicio + 1);
			inicio++;
		}while(cantidadBytesLeidos > 0);
		finArchivo = inicio - 1;
	}
	
	@Override
	public void copiarBytesDeOtroArchivo(String pathOtroArchivo, int tamanioDeBloques) {
		ApiFileSystem otroArchivo = new ApiFileSystem(pathOtroArchivo);
		otroArchivo.abrirArchivo();
		Buffer buffer = new Buffer(tamanioDeBloques);
		int bytesCopiados = 0;
		int bytesLeidos = apiBajoNivel.syncReadFile(fileDescriptor, buffer.bufferBytes, bytesCopiados, bytesCopiados + tamanioDeBloques);
		while(bytesLeidos > 0) {
			this.escribirBytesAlFinalDeUnArchivo(buffer);
			bytesCopiados += bytesLeidos;
			bytesLeidos = apiBajoNivel.syncReadFile(fileDescriptor, buffer.bufferBytes, bytesCopiados, bytesCopiados + tamanioDeBloques);
		}
	}
	
}
