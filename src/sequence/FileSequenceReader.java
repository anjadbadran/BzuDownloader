package sequence;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
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

		int size;
	
		DataInputStream dataStream=  new DataInputStream(sequence) ;
		try {
			size = dataStream.readUnsignedByte();
			sequence.read();
			sequence.read();
			sequence.read();
			System.out.println("new size="+size);
		} catch(EOFException e) { 
			return null;
		}
		System.out.println("read Size"+size);
		byte[] data = new byte[size];
		int read = 0;
		 
		 
		while(read<size) {
			System.out.println("Readed="+ read );
			int justRead = sequence.read(data, read, size);
			if(justRead==-1)
				throw new EOFException("stream ended after only "+read+" bytes of "+size+"-byte frame!");
			read += justRead;
		}
		
		return data;
	}
}
