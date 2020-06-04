package fileSystem;

public class LeerArchivoExcepcion extends RuntimeException {
	public LeerArchivoExcepcion() {
        super("No pudo se pudo leer el archivo de ApiFileSystem");
    }
}