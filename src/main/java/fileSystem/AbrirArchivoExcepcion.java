package fileSystem;

public class AbrirArchivoExcepcion extends RuntimeException {
	public AbrirArchivoExcepcion() {
        super("No pudo abrirse el archivo de ApiFileSystem");
    }
}