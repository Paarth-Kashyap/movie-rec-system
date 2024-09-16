/*
Paarth Kashyap
WriteFiles.java- Updates the CSV file when a new user signs up. Also updates when 
a rating of any user is changed
*/

import java.io.*;
import java.util.LinkedList;

public class WriteFiles {
    
    //adding new users
    public void addUser(String username) {
        try {
            //read number of lines
            BufferedReader br = new BufferedReader(new java.io.FileReader("MoreMovieData.csv"));
            String line="";

            //counter for number of lines 
            int count = 0; 
            line= br.readLine();

            while (line != null) {
              count++;
              line = br.readLine();
            }
            br.close(); //close br for line counter


            String[] lines=new String[count]; //array to hold line

            //store all lines into array
            BufferedReader br2 = new BufferedReader(new java.io.FileReader("MoreMovieData.csv"));
            String pos=""; //current line
            pos = br2.readLine();
            //loop through reading the lines and creating the updated line to add to file
            for (int i=0;i<count;i++){
                if(i==0){
                    lines[i]=pos+","+username;
                }
                else{
                    lines[i]=pos+","+0;
                }
                pos = br2.readLine(); 
            }
            br2.close();


            //write to file 
            PrintWriter pw = new PrintWriter(new FileWriter("MoreMovieData.csv"));
            //for each line in file
            for (int i=0;i<count;i++){
                if(i!=count-1){
                    pw.println(lines[i]);
                }
                else{
                    pw.print(lines[i]);
                }                
            }
            pw.close();
        }
        catch(IOException e)
        {
            System.out.println("ERROR! a NEW ACC Problem occured while creating the file! "
            +e.getMessage());
        }
        catch(Exception e){System.out.println("BIG ERROR " +e.getMessage());}
    }

    // for updating ratings
    public void updateRating(int pos,int rate, int mov_pos,int nam_lnght) {
        try {
            //read number of lines
            BufferedReader br = new BufferedReader(new java.io.FileReader("MoreMovieData.csv"));
            String line="";
            //counter for number of lines ---------------------
            int count = 0; 
            line= br.readLine();
            while (line != null) {
              count++;
              line = br.readLine();
            }
            br.close(); //close br for line counter

            String[] lines=new String[count]; //array to hold line
            //read file into array
            BufferedReader br2 = new BufferedReader(new java.io.FileReader("MoreMovieData.csv"));
            String curr=""; //current line
            curr = br2.readLine();
            //loop through reading the lines and creating the array
            for (int i=0;i<count;i++){
                lines[i]=curr;
                curr=br2.readLine(); 
            }
            br2.close();

            //creates the line to change movie ratings with
            String nLine= lines[mov_pos+1].substring(0,nam_lnght+1+(2*pos))+rate+lines[mov_pos+1].substring(nam_lnght+(2*pos)+2);

            //write to file 
            PrintWriter pw = new PrintWriter(new FileWriter("MoreMovieData.csv"));
            //for each line in file
            for (int i=0;i<count;i++){
                if(i!=count-1){
                    if(i==mov_pos+1){ pw.println(nLine);}

                    else{ pw.println(lines[i]);}
                }
                else{
                    if(i==mov_pos+1){ pw.print(nLine);}

                    else{ pw.print(lines[i]);}
                }
            }
            pw.close();
        }
        catch(IOException e)
        {
            System.out.println("ERROR! b NEW ACC Problem occured while creating the file! "
            +e.getMessage());
        }
        catch(Exception e){System.out.println("BIG ERROR " +e.getMessage());}
    }
    
    //write to file
    public boolean write(String username, String password) {
        try {
            checkTheLogin checker=new checkTheLogin();
            PrintWriter pw = new PrintWriter(new FileWriter("loginFile.txt",true));
            
            //check for existing name
            if(checker.checkUser(username)){
                pw.close();
                return false;
            }
            //if unique name
            else{ 
                //write
                pw.println(username + " " + password);
                pw.close();
                return true;
            }
        }
        catch(IOException e){
            System.out.println("ERROR! c NEW ACC Problem occured while creating the file! "
            +e.getMessage());
            return false;
        }
        catch(NullPointerException e){
            System.out.println("Null Value " +e.getMessage());
            return false;
        }
        catch(Exception e){
            System.out.println("BIG ERROR " +e.getMessage());
            return false;
        }
    }
    //write sorted rating to external file
    public void write(LinkedList<DataObj> info, int count) {
        try {
            //write to sorted file
            PrintWriter sortedFile = new PrintWriter(new FileWriter("SortedRating.txt"));

            //loop to write
            for (int i = 0; i < count; i++) {
            sortedFile.println(info.get(i).getUsername() + "-" + info.get(i).getTitle() + "-" + info.get(i).getRating()); //writes usernanme, movies list, rating list
            }
            sortedFile.close();
        }
        catch(IOException e){
            System.out.println("ERROR! c NEW ACC Problem occured while creating the file! "
            +e.getMessage());
        }
        catch(NullPointerException e){
            System.out.println("Null Value " +e.getMessage());
        }
        catch(Exception e){
            System.out.println("BIG ERROR " +e.getMessage());
        }
    }
}