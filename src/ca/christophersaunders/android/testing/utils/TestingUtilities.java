/*******************************************************************************
 * Copyright (c) 2010, 2011 - Christopher Saunders
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ******************************************************************************/
package ca.christophersaunders.android.testing.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Instrumentation;

public class TestingUtilities {
	public enum FileType {
		TEXT, BINARY
	}
	
	private Instrumentation instrumentation;
	
	public TestingUtilities(Instrumentation instrumentation) {
		this.instrumentation = instrumentation;
	}
	
	public Instrumentation getInstrumentation() {
		return this.instrumentation;
	}
	
	public Object loadAsset(String filename, FileType fileType) {
		switch(fileType) {
		case TEXT:
			return loadStringAsset(filename);
		case BINARY:
			return loadBinaryAsset(filename);
		default:
			return null;
		}
	}
	
	String loadStringAsset(String filename) {
		StringBuilder builder = new StringBuilder();
		char[] buffer = new char[1024];
		int lettersRead = 0;
		try {
			InputStreamReader dataStream = new InputStreamReader(instrumentation.getContext().getAssets().open(filename));
			while( (lettersRead = dataStream.read(buffer)) > 0) {
				builder.append(buffer, 0, lettersRead);
			}
		} catch (IOException e) {
			return "";
		}
		return builder.toString();
	}
	
	ByteArrayOutputStream loadBinaryAsset(String filename) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int bytesRead = 0;
		try {
			InputStream dataStream = instrumentation.getContext().getAssets().open(filename);
			while( (bytesRead = dataStream.read(buffer)) > 0) {
				output.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			return new ByteArrayOutputStream();
		}
		return output;
	}

}
