package com.bzu.softwareconstruction.services.multipart;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MultipartInputStream extends InputStream {
	private List<InputStream> streams = new ArrayList<>();
	private InputStream currentIS;
	boolean hasNext = true;
	public MultipartInputStream(List<InputStream> iss) {
		streams = iss;
		next();
	}

	private boolean next() {
		if (currentIS != null)
			try {
				currentIS.close();
			} catch (IOException e) {
				new RuntimeException(e);
			}
		if (streams.isEmpty()) {
			hasNext = false;
			return false;
		}
		currentIS = streams.get(0);
		streams.remove(0);
		return true;
	}

	@Override
	public int read() throws IOException {
		if (!hasNext){
			return -1;
		}
		int i = currentIS.read();
		if (i == -1 && next()) {
			i = read();
		}
		return i;
	}

}
