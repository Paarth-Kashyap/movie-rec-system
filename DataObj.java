/*
Paarth Kashyap
Movie Object Class- contains info like title, rating, username. 
One object is like 1 person who watched and rates numerous movies
*/
import java.util.LinkedList;

public class DataObj{

  private String username;
  private LinkedList<String> titles;
  private LinkedList<Integer> ratings;
  
  // constructor 
  public DataObj(String user, LinkedList<String> movie, LinkedList<Integer> rate){
    //when given parameters
    this.username = user;
    //initialize linked lists
    titles=movie;
    ratings=rate;
  }

//------------------setters---------------\\

  //set username
  public void setUsername(String user){
    this.username = user;
  }
  //add rating to end of list
  public void addRating(int rate){
    ratings.add(rate);
  }
  //add title to end of list
  public void addTitle(String title){
    this.titles.add(title);
  }
  //change rating at index
  public void change_rate(int ind, int rate){
    this.ratings.set(ind, rate); 
  }
//-----------------getters--------------------------\\
  //get username
  public String getUsername(){
    return username;
  }
  //get rating list
  public LinkedList<Integer> getRating(){
    return ratings;
  }
  //get title list
  public LinkedList<String> getTitle(){
    return titles;
  }

}