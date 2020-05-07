/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmn14_b;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JFileChooser;

public class FilesUtils {

    public static void writeObjects(File f, ArrayList<?> objects) throws IOException
    {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
        out.writeObject(objects);
        out.close();
    }

	public static File getFile(){
        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(null);
        return fc.getSelectedFile();
    }
	
    public static String readFromFile(File f) throws IOException {
        FileReader fr = new FileReader(f);
        int size = (int) f.length();
        char[] data = new char[size];
        fr.read(data);
        return new String(data);
    }
    
    public static void writeToFile(PhoneBook book) throws IOException {
    	FileWriter fw = new FileWriter(getFile());
    	TreeMap<String, Person> bookTree = book.getPhonebook();
    	for( Entry<String, Person> t : bookTree.entrySet()) {
    		String phone = t.getValue().getPhone();
    		String newLine = t.getKey() + " " + phone + "\n";
    		fw.write(newLine);
    	}
    	fw.close();
    }
    
}
