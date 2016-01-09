package sequence;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Static class for reading from a file-sequence stream.

 */
public class FileSequenceReader {
	/**
	 * Returns the data from the next sub-file in the given file sequence stream.
	 * <p>
	 * If no sub-files remain, returns null. If the stream ends prematurely,
	 * throws an EOFException.
	 */
	public static byte[] readOneFile(InputStream sequence)
		throws IOException, EOFException {
		// sequence files consist of a (4-byte) int giving the size of the sub-file,
		// followed by the sub-file, followed by another size, followed by the sub-file,
		// and so on until EOF
		int size = new DataInputStream(sequence).readInt();
		long unsigned_size = size & 0x00000000ffffffffL;
		
		int chunckSize = (int) Math.min(1024 * 1024, unsigned_size);
		
		byte[] chunckBytes = new byte[chunckSize];
		byte[] fileBytes = new byte[0];
		while(true){
			
			int read = sequence.read(chunckBytes, 0, (int) Math.min(chunckSize, unsigned_size));
			if(read == -1 ){
				break;
			}
			unsigned_size -= read;	
			//Append bytes
			byte[] newFileBytes = new byte[fileBytes.length+read];
			System.arraycopy(fileBytes, 0, newFileBytes, 0, fileBytes.length);
			System.arraycopy(chunckBytes, 0, newFileBytes, fileBytes.length, read);
			fileBytes = newFileBytes;
			
			if (unsigned_size < 0){
				break;
			}
		}
		return fileBytes;
	}
}
