package multipart;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.bzu.softwareconstruction.segments.SegmentPartUrl;
import com.bzu.softwareconstruction.services.Services;

/**
 * Static class for reading from a multipart stream.

 */
public class Multipart {	
	/**
	 * Returns an InputStream which streams from the given url.
	 * <p>
	 * If the url ends with the suffix .segments or has the MIME type
	 * text/segments-manifest it is treated as a multipart manifest stream,
	 * and the returned InputStream streams the data represented by the manifest,
	 * not the contents of the manifest itself.
	 * <p>
	 * Otherwise, when the url does not point to a manifest,
	 * the returned input stream streams data directly from the url target.
	 */
	public static InputStream openStream(String url)
		throws IOException {
		//Download mutipart and accomlate it to disk
		return Services.multipartDownloader.download(new SegmentPartUrl(url));
	}
}
