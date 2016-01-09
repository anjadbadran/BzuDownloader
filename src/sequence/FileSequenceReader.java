package sequence;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

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
	public static  byte[]  readOneFile(InputStream sequence)
		throws IOException, EOFException {
		// animations consist of a (4-byte) int giving the size of the frame,
		// followed by the frame, followed by another size, followed by the frame,
		// and so on until EOF
		int size;
		// note that this hides errors involving less than 4 trailing bytes:

		//  String inputStreamString = new Scanner(sequence,"UTF-8").useDelimiter("\\A").next();
	     //   System.out.println("--"+inputStreamString+"--");
		try {
			size = new DataInputStream(sequence).readInt();
		} catch(EOFException e) { // no more frames
			return null;
		}
		System.out.println("Size=="+size);
		byte[] data = new byte[size];
		int read = 0;
		while(read<size) {
			int justRead = sequence.read(data, read, size-read);
			if(justRead==-1)
				throw new EOFException("stream ended after only "+read+" bytes of "+size+"-byte frame!");
			read += justRead;
		}
		return data;
			
	}
}
