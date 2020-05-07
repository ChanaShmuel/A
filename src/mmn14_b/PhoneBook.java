/*
 *  Suppose the phone number and name match unequivocally
 * 
 */
package mmn14_b;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

public class PhoneBook {
    private TreeMap<String, Person> phoneBook ; 
  
    //the phoneBook order by "a,b,c....."
    public PhoneBook()
    {
        this.phoneBook  = new TreeMap<String, Person>();
    }
    
    //the phoneBook order by "a,b,c....."
    public  void addNew(Person person)throws ContactExistException
    {
         String name = person.getName();

        if(phoneBook .containsKey(name)) {
            throw new ContactExistException("Person " + name + " already exists");
        }

        phoneBook .put(name, new Person(person));
    }
    
    //delete person and num
    public void delete(String name)
    {
        phoneBook.remove(name);
    }
    
    public void edit(Person p)
    {
        Person updatePerson=new Person(p);
        phoneBook.put(updatePerson.getName(),updatePerson);     
    }
    
    public Person getPerson(String name)
    {
        return phoneBook.get(name);
    }
    
    public TreeMap<String, Person>  getPhonebook() {
    	return this.phoneBook;
    }
    
    public ArrayList<Person> getPersons()
    {
        ArrayList<Person> res = new ArrayList<Person>();
        for(Entry<String,Person> contactEntry: phoneBook.entrySet()) {
            res.add(new Person(contactEntry.getValue()));
        }
        return res;
    }
   
    public String FileToSave(File f)throws IOException
    {
        File fileSave=new File(f.getPath()+"Person.txt");
        FilesUtils.writeObjects(fileSave,getPersons());
        fileSave.createNewFile();
        return fileSave.getPath();
    }
    
    
     public void ImportFile(File f) throws IOException, ClassNotFoundException {
        String fileData = FilesUtils.readFromFile(f);
        phoneBook.clear();
        String[] fileArr = fileData.split("\n");
        for(int i=0 ; i<fileArr.length ; i++) {
        	String[] row = fileArr[i].split(" " );
        	String fullName = row[0] + " " + row[1];
        	String phone = row[2];
        	phoneBook.put(fullName, new Person(fullName, phone));
        }
    }
     
   
}
