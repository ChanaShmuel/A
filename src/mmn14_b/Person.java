/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmn14_b;

import java.io.Serializable;


public class Person implements Serializable {
 
  private String name;
  private String phoneNumber;   

  public Person(String name,String phoneNumber)
  {
    this.name=name;
    this.phoneNumber=phoneNumber; 
  }
  
  public Person(Person person)
  {
        this.name = person.name;
        this.phoneNumber = person.phoneNumber;
  }
    
  public void setName(String n)
  {
      this.name=n;
  }
  
  public void setPhoneNumber(String p)
  {
      this.phoneNumber=p;
  }
  
  public String getName()
  {
      return this.name;
  }
 
  public String getPhone()
  {
      return this.phoneNumber;
  }
  public String toString()
    {
        return this.getName()+ " " + this.getPhone();
    }
}
