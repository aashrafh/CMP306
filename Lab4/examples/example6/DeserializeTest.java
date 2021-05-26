//test reading the stream again   
package example6;

import java.io.*;

class DeserializeTest {
	public static void main(String args[]) throws Exception {

		ObjectInputStream in = new ObjectInputStream(new FileInputStream("f.txt"));
		// we cast the bytes back to its original type
		Student s = (Student) in.readObject();
		System.out.println(s.id + " " + s.name);

		in.close();
	}
}