/* 
Paarth Kashyap
checkTheLogin.java- returns boolean value by searching for login information and checking if present
in current database. 
*/

import java.io.*;

class checkTheLogin{
  private String read;

  //check if the login information exists in file
  public boolean check(String user,String pass) {
    String data=user+" "+pass;

    try{
      //br to read the login file to check for correct input
      BufferedReader fr2 = new BufferedReader(new java.io.FileReader("loginFile.txt")); //line counter

      //line counter
      int count = 0; 
      String entry= fr2.readLine();
      while (entry != null) {
        count++;
        entry=fr2.readLine();
      }
      fr2.close(); //close br for line counter
          
      read="";
      int ctr=0;
      BufferedReader fr=new BufferedReader(new java.io.FileReader("loginFile.txt"));

      //loop through each line until a match hits
      while (!read.equals(data)){
        //if looped through each line, entry is not present and unsuccess
        if (ctr==count){
          fr.close();
          break;
        }

        read = fr.readLine();
        //if line = entered information, success
        if (read.equals(data)){
          fr.close();
          return true;
        }
  
        ctr++;
      }
      return false;
    }
    catch(IOException e)
    {
      System.out.println("ERROR! NEW ACC Problem occured while creating the file! "
      +e.getMessage());
      return false;
    }
  }
  //check if username exists in file
  public boolean checkUser(String user) {
    try{
      //br to read the login file to check for correct input
      BufferedReader fr2 = new BufferedReader(new java.io.FileReader("loginFile.txt")); //line counter

      //line counter
      int count = 0; 
      String entry= fr2.readLine();
      while (entry != null) {
        count++;
        entry=fr2.readLine();
      }
      fr2.close(); //close br for line counter

      read="";
      int ctr=0;
      boolean found=false;
      BufferedReader fr = new BufferedReader(new java.io.FileReader("loginFile.txt"));

      //loop through each line until a match hits
      while (found==false){
        //if looped through each line, entry is not present and unsuccess
        if(ctr==count){
          fr.close();
          break;
        }

        //second space
        read = fr.readLine();
        int s=read.lastIndexOf(" ");
        read=read.substring(0,s);

        //if line = entered information, success
        if (read.equals(user)){
          fr.close();
          found= true;
        }
        ctr++; 
      }
      return found;
    }

    catch(IOException e)
    {
        System.out.println("ERROR! abc NEW ACC Problem occured while creating the file! "
        +e.getMessage());
        return false;
    }
  }
}