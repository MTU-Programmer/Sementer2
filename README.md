# Semester2
Little snippets of source code I wrote while studying during semester 2.
These are in Java.

The following text explains the source code in file CalendarApp.java


An application's main() method will always be called by a special "main" thread when the Java Virtual Machine starts up. And this main thread is not the event dispatch thread. Therefore any changes to Swing GUI components should be done from the event dispatch thread.  Any updates to the user interface must happen on the event dispatch thread in order to avoid hard-to-find bugs, crashes and code freezes.

For that reason I use this code to start up the Swing Calendar app from a separate thread from the main thread.

	SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                
                //runs code in constructor then sets up the panel               
                new CalendarApp().setupPanel();
            }
        });

The main thread ends, but the Swing app remains alive until the user closes it.

This calendar app has two classes in the same file for simplicity, but normally they should be in separate files.

This calendar app demonstrates a separation of concerns: the calculating engine in the Calendar class is separate from the main class. The result of the calculating engine is a big long string containing calendar data, which is plugged into a JTextArea component by code in the main class.


