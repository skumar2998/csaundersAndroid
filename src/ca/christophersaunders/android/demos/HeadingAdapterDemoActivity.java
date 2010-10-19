/*******************************************************************************
 * Copyright (c) 2010 - Christopher Saunders
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
package ca.christophersaunders.android.demos;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import ca.christophersaunders.android.R;
import ca.christophersaunders.android.widget.HeadingAdapter;
import ca.christophersaunders.android.widget.HeadingAdapter.OnHeadingChangeListener;

public class HeadingAdapterDemoActivity extends Activity implements OnHeadingChangeListener{
	
	private String[] listing_one, listing_two, listing_three;
	private String heading_one, heading_two, heading_three;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.heading_adapter_demo_view);
		
		heading_one = "Swords";
		listing_one = new String[] {
				"Aikuchi", "Barong", "Cutlass", "Dao", "Sabre"
		};
		
		heading_two = "Serpents";
		listing_two = new String[] {
				"Asp", "Boa", "Cobra", "Python"
		};
		
		heading_three = "Magic";
		listing_three = new String[] {
				"Death", "Earth", "Fire", "Ice", "Life", "Light", "Shadow", "Water" 
		};
		
		ArrayAdapter<String> adapter_one = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listing_one);
		ArrayAdapter<String> adapter_two = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listing_two);
		ArrayAdapter<String> adapter_three = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listing_three);
		
		HeadingAdapter headingAdapter = new HeadingAdapter(this);
		headingAdapter.addHeading(heading_one, adapter_one);
		headingAdapter.addHeading(heading_two, adapter_two);
		headingAdapter.addHeading(heading_three, adapter_three);
		
		ListView lv = (ListView) findViewById(R.id.headingAdapterDemoListView);
		lv.setAdapter(headingAdapter);
		headingAdapter.setOnHeadingChangeListener(this);
	}

	@Override
	public void headingChanged(HeadingAdapter adapter) {
		Log.i(this.toString(), "Heading has changed: " + adapter.getCurrentHeading());
		
	}

}
