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

import ca.christophersaunders.android.testing.utils.TestingUtilities;

import android.test.InstrumentationTestCase;

public class TestingUtilitiesTest extends InstrumentationTestCase {
	
	TestingUtilities testUtils;
	
	public void setUp() throws Exception {
		testUtils = new TestingUtilities(getInstrumentation());
	}
	
	public void testInitialization() {
		TestingUtilities testUtils = new TestingUtilities(getInstrumentation());
		assertEquals(getInstrumentation(), testUtils.getInstrumentation());
	}
	
	public void testLoadTestAssetAsString() {
		assertEquals("Hello World!", testUtils.loadStringAsset("hello_world.txt"));
	}
	
	public void testLoadAssetAsBinary() throws Exception {
		byte[] expectedBytes = "Hello World!".getBytes();
		byte[] actualBytes = testUtils.loadBinaryAsset("hello_world.txt").toByteArray();
		
		assertEquals(expectedBytes.length, actualBytes.length);
		for(int i = 0; i < expectedBytes.length; ++i) {
			assertEquals(expectedBytes[i], actualBytes[i]);
		}
	}
	
	public void testLoadTestAssetUsingFileTypes() {
		Object textResponse = testUtils.loadAsset("hello_world.txt", TestingUtilities.FileType.TEXT);
		Object binaryResponse = testUtils.loadAsset("hello_world.txt", TestingUtilities.FileType.BINARY);
		
		assertEquals(String.class, textResponse.getClass());
		assertEquals("Hello World!", textResponse);
		
		assertEquals(ByteArrayOutputStream.class, binaryResponse.getClass());
		
		byte[] expectedBytes = "Hello World!".getBytes();
		byte[] actualBytes = ((ByteArrayOutputStream) binaryResponse).toByteArray();
		
		assertEquals(expectedBytes.length, actualBytes.length);
		for(int i = 0; i < expectedBytes.length; ++i) {
			assertEquals(expectedBytes[i], actualBytes[i]);
		}
	}

}
