package multipart;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

public class multipartTest {


	@Test public void emptyFile() throws IOException {
		InputStream in = Multipart.openStream("http://151.80.134.50/data/empty");
		
		Assert.assertNotNull("null stream",
				in);

		Assert.assertEquals("file should be empty",
				in.read(), -1);
		
		// note that InputStream.read(byte[] buf) will return 0 if and only if buf.length==0:
		Assert.assertEquals("file should still be empty",
				in.read(new byte[1024]), -1);
		
		Assert.assertEquals("file should still be empty",
				in.read(), -1);

		in.close();
	}
	
	@Test public void emptyPartsFile() throws IOException {
		// the complexities of the parts file actually do most of the testing:
		InputStream in = Multipart.openStream("http://151.80.134.50/data/empty");

		Assert.assertNotNull("null stream",
				in);
		// (would have gotten a NullPointerException soon anyway, but doesn't hurt to be explicit.)

		Assert.assertEquals("file should be empty",
				in.read(), -1);
		
		// note that InputStream.read(byte[] buf) will return 0 if and only if buf.length==0:
		Assert.assertEquals("file should still be empty",
				in.read(new byte[1024]), -1);
		
		Assert.assertEquals("file should still be empty",
				in.read(), -1);

		in.close();
	}

}
