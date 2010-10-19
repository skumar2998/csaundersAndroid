# csaundersAndroid #

This is a set of custom classes that I have created for use within
Android.  The initial reason for this was because no permissive 
version of ListViews with headings exist, nor are they baked into
the Android platform by default.

## ca.christophersaunders.android.widget ##

### HeadingAdapter ###

This is a wrapper for multiple adapters that you can use within a
ListView.  This will allow you to add data from multiple sources
and display them in the List with a heading for each section.

**Usage:**

All you need to do to use this is set up your various adapters
you wish have displayed and add them to the heading adapter with
a name for the heading.

<pre>
Adapter myAdapter;

// ... code ...

HeadingAdapter headingAdapter = new HeadingAdapter(Context);
headingAdapter.add("myAdapter Heading", myAdapter);

// ... code ...
</pre>


**Requirements**

The only requirement for usage of the HeadingAdapter class is the
inclusion of the **heading_adapter_heading.xml** file which is needed
by the empty constructor of the class.

It is encouraged that you include this file with the your resources and
use the alternate constructor if you wish to use a custom TextView for your
headings.