/*
Paarth Kashyap
Sorting.java- creates the objects and allocates them into lists and arrays. 
The obj list is then sorted to display ratings and to be utilized for recommendations.
*/

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

class Sorting{
  //sorting properties
  private String line;
  //import write files class
  private WriteFiles writer;
  //linked lists for obj array
  protected LinkedList<DataObj> storeData;
  protected LinkedList<String> temp_titles;
  private LinkedList<Integer> ratings;
  //arrays for avg rating 
  protected double[] avg_r;
  private int[] zeros;

  //constructor
  public Sorting() throws IOException {
    //instantiate obj lists and class obj
    storeData=new LinkedList<DataObj>();
    writer=new WriteFiles();

    //reading file --------------------
    BufferedReader lineReader2 = new BufferedReader(new java.io.FileReader("MoreMovieData.csv"));
    BufferedReader lineRead = new BufferedReader(new java.io.FileReader("MoreMovieData.csv"));

    //counter for number of lines ---------------------
    int count = 0; 
    line= lineReader2.readLine();
    while (line != null) {
      count++;
      line = lineReader2.readLine();
    }
    lineReader2.close(); //close br for line counter
    
    //instantiate linked list for temporary holdings -------------------
    temp_titles=new LinkedList<String>();
    LinkedList<String> temp_rates=new LinkedList<String>();
    LinkedList<String> temp_users=new LinkedList<String>();

    //read each line ----------------------
    for (int i = 0; i < count; i++) {
      line = lineRead.readLine();

      //for the first line in csv file
      if (i==0){
        //array of names to add to login txt file
        String a=line.substring(line.indexOf(",")+1); //returns substring of everything after first ,
        temp_users=new LinkedList<String>(Arrays.asList(a.split(","))); //saves the split array as linked list
       
      }
      //for the rest of the lines
      else{
        temp_titles.add(line.split(",")[0]); //add the movie part of line to titles list
        String getRate=line.substring(line.indexOf(",")+1); //save string as everything after title for ratings
        temp_rates.add((getRate)); //add all the ratings of a movie as one entry
      }
       
    }
    lineRead.close(); //close br for line reading

    //create objects, for each username from file ---------------
    for (int i=0;i<temp_users.size();i++){
      //create linked list to pass to DataObj Class
     // movies=new LinkedList<String>();
      ratings=new LinkedList<Integer>();

      //loop through each movie each user watched
      for (int j=0;j<temp_titles.size();j++){
        //movies.add(temp_titles.get(j)); //add the movies to list
        ratings.add(Integer.parseInt(temp_rates.get(j).split(",")[i])); //add int value of ratings using the index number the user is on
      }
      //each user gets object and stored in linked list of objects
      DataObj temp= new DataObj(temp_users.get(i),temp_titles,ratings);
      storeData.add(temp);
    }
    avgRating(storeData); //create avg rating array
    
    writer.write(storeData, storeData.size()); // writes file --------------------
    
  }
  
  //sort by avg rating increasing or decreasing - bubble sort
  public void bubble_sort(String choice,String[] movies,double[]rates){
    double avg_temp;
    String mov_temp;
    
    //for ratings sort
    for (int i=0;i<rates.length;i++){
      for (int j=0;j<rates.length-i-1;j++){
        //rating increasing sort
        if (choice.equals("Lowest Rating")){
          //if current is greater than next 
          if (rates[j]>rates[j+1]){
            avg_temp=rates[j+1];
            rates[j+1]=rates[j];
            rates[j]=avg_temp;
            //sort movies accordingly
            mov_temp=movies[j+1];
            movies[j+1]=movies[j];
            movies[j]=mov_temp;
          }
        }
        //rating decreasing
        else if (choice.equals("Highest Rating")){
          //if next is greater than current 
          if (rates[j]<rates[j+1]){
            avg_temp=rates[j];
            rates[j]=rates[j+1];
            rates[j+1]=avg_temp;
            //sort movies accordingly
            mov_temp=movies[j];
            movies[j]=movies[j+1];
            movies[j+1]=mov_temp;
          }
        }
        //A-Z sort movie title
        else if (choice.equals("A-Z")){
          //if next is greater than current 
          if (compareStr(movies[j],movies[j+1])==false){
            avg_temp=rates[j];
            rates[j]=rates[j+1];
            rates[j+1]=avg_temp;
            //sort movies accordingly
            mov_temp=movies[j];
            movies[j]=movies[j+1];
            movies[j+1]=mov_temp;
          }
        }
        //Z-A sort movie title
        else if (choice.equals("Z-A")){
          if (compareStr(movies[j+1],movies[j])==false){
            avg_temp=rates[j];
            rates[j]=rates[j+1];
            rates[j+1]=avg_temp;
            //sort movies accordingly
            mov_temp=movies[j];
            movies[j]=movies[j+1];
            movies[j+1]=mov_temp;
          }
        }
      }
    }
  }
 
  //bubble sort reviews and movies based on avg rating
  // designed to have rec system give rec based on avg rating for new users
  public String[] bubble_sort(int[] revs, String[] movs,double[] avg){
    double avg_temp;
    String mov_temp;
    int rev_temp;

    for (int i=0;i<revs.length;i++){
      for (int j=0;j<revs.length-i-1;j++){
        
        //sorts avg rating first decreasing
        if (avg[j]<avg[j+1]){
          avg_temp=avg[j];
          avg[j]=avg[j+1];
          avg[j+1]=avg_temp;

          //sort movies accordingly
          mov_temp=movs[j];
          movs[j]=movs[j+1];
          movs[j+1]=mov_temp;

          //sort revs accordingly
          rev_temp=revs[j];
          revs[j]=revs[j+1];
          revs[j+1]=rev_temp;
        }
      }
    }
    return movs;
  }

  //bubble sort reviews and movies based (main)
  // designed to have rec system give rec based dot product sum
  public String[] bubble_sort(int[] revs, String[] movs){
    String mov_temp;
    int rev_temp;

    for (int i=0;i<revs.length;i++){
      for (int j=0;j<revs.length-i-1;j++){
        
        //sorts avg rating first decreasing
        if (revs[j]<revs[j+1]){
          rev_temp=revs[j];
          revs[j]=revs[j+1];
          revs[j+1]=rev_temp;

          //sort movies accordingly
          mov_temp=movs[j];
          movs[j]=movs[j+1];
          movs[j+1]=mov_temp;
        }
      }
    }
    return movs;
  }
  
  //compare string method for bubble sort
  //alpha sort compares chars of words
  private boolean compareStr(String first, String second) {
    //get lower case words
    first=first.toLowerCase();
    second=second.toLowerCase();
    //ascii values and counter
    int val1,val2,ctr=0;
    boolean is_first=true; //first words is first in order


    //if the first word is longer than second, assume its false until later
    if (first.length()>second.length()){
        is_first=false;
    }
    
    //loop until no more chars
    do{
        val1=(int)first.charAt(ctr);
        val2=(int)second.charAt(ctr);

        // first word is first in order
        if (val2>val1){
            is_first=true;
        } 
        // second word is first in order
        else if (val1>val2){
            is_first=false;
        } 
        else{
            ctr++; //progress counter and check other ascii values
        }
      }while(val1==val2 && (ctr<first.length() && ctr<second.length())); //loop while ounter is less than both words length and both ascii values are same (exact letters)
    return is_first; 
  }

  //get avg rating for each movie
  public double[] avgRating(LinkedList<DataObj>info){
    avg_r=new double[info.get(0).getTitle().size()]; //since all objects have same num of movies, set size to first 
    zeros=new int[avg_r.length];

    try{
      int total_revs=info.size(); //number of objects = total number of viewers;

      //for each movie for each object
      for (int i=0;i<avg_r.length;i++){
        //add ratings of each object
        avg_r=add(info.get(i).getRating(),avg_r,avg_r.length); //add the list from object to total array
      }

      //find the avg ratings
      for (int i=0;i<avg_r.length;i++){
        avg_r[i]/=(total_revs-zeros[i]); //gets the avg rating for the movies and excludes number of 0
      }
      return avg_r;
    }
    //different catches and exceptions
    catch(NullPointerException e) {
      System.out.println("Null Value ERROR!!!"+"\n"+e.getMessage());
      return avg_r;
    }
    catch(Exception e) {
      System.out.println("ERROR!!!"+"\n"+e.getMessage());
      return avg_r;
    }
  }

  //adds the ratings from each person and combines into array
  private double[] add(LinkedList<Integer> reviews,double[] old,int size){
    double stars[]=new double[size]; //rating array to add too
    //adds the new person ratings to the old sum of ratings
    for (int i=0;i<reviews.size();i++){
      if(reviews.get(i)==0){
        zeros[i]+=1;
      }
      stars[i]=reviews.get(i)+old[i];
    }
    return stars;
  }

  //loops through obj list to find user index
  public int find_index(String user){
    int pos=0;
    for (int i=0;i<storeData.size();i++){
      if (storeData.get(i).getUsername().equals(user)){
        pos=i;
      }
    }
    return pos;
  }

  
  //self testing main
  /*
  public static void main (String[] args)throws IOException{
    Sorting sort= new Sorting();
  }
  */
}