/*
Paarth Kashyap
Recommend.java- The movie recommendation system of the program.
Based on the ratings given to movies by others and the similarity of other users
compared to yours, a list of movies is determined. Recommended movies are movies 
from the list that are unwatched.
*/

import java.io.*;
import java.util.LinkedList;

public class Recommend{

    //obj array and related items
    private Sorting info;
    private LinkedList<DataObj> data;

    //local arrays and dtypes
    private int[][] all_revs; //all origianl reviews  
    private int[] dot_product; //dot product array 
    private int[] final_revs; //sum of all reviews after mulitply by dot procuct

    private String[] movs; //all movies 
    protected String[] movs4u; //final recommendations
    private int[] zeros; //number of zero values per movie
    
    private int loc_user;
 
    //creates the array with all relative rating to check and reccomend
    public Recommend(String curr_user) throws IOException{
        //creates the obj array through sorting method
        info=new Sorting();
        //saves local copy of obj array
        data=info.storeData;
        // initialize arrays
        all_revs=new int[data.size()][1];
        dot_product=new int[data.size()];
        final_revs=new int[data.get(0).getTitle().size()]; //sum of all calculated revews for each movie
        movs4u=new String[6];
        zeros=new int[all_revs.length];
        //create movies array to sort with final revs
        movs=new String[data.get(0).getTitle().size()];
        movs=(data.get(0).getTitle()).toArray(movs); //list to array

        //loop through obj array create array of reviews 
        for (int i=0;i<data.size();i++){
            //find current user
            if ((data.get(i).getUsername()).equals(curr_user)){
                loc_user=i; //store index of user
                all_revs[i]=toArray_int(data.get(i).getRating()); //store array in index
            }
            else{
                //add all others to array
                all_revs[i]=toArray_int(data.get(i).getRating()); //store array in index
            }
        }
        //create dot product
        int product=0;
        //i represents the persent index and j represents the review index
        for (int i=0;i<all_revs.length;i++){
            for (int j=0;j<all_revs[i].length;j++){
                //current user rating * index user rating 
                product+=all_revs[loc_user][j]*all_revs[i][j];
            }
            dot_product[i]=product; //create the array with the multiplier 
            product=0; //reset the product sum
        }

        //create array with final ratings to reccomened
        int sum=0; //sum to add to array for each movie
        //i represents the persent index and j represents the review index
        for (int i=0;i<movs.length;i++){
            for (int j=0;j<all_revs.length;j++){
                if (j==loc_user){
                    continue;}
                else{
                    if (all_revs[j][i]==0){
                        zeros[i]+=1;
                    }
                    sum+=dot_product[j]*all_revs[j][i];
                } 
            }
            final_revs[i]=sum; //add to array
            sum=0; //reset sum counter  
        }

        //find the avg ratings
        for (int i=0;i<final_revs.length;i++){
            final_revs[i]/=(data.size()-zeros[i]); //gets the avg rating for the movies and excludes number of 0
        }

       
        movs=info.bubble_sort(final_revs, movs, info.avg_r);  
        //sort based on final revs score
        movs=info.bubble_sort(final_revs,movs);
        keep_unwatched(); 
    }

    //keep the unwatched movies from the sorted films
    private void keep_unwatched(){
        LinkedList<String> recommended=new LinkedList<String>(); //list to store variable amount of unseen movies

        //loop through the number of movies 
        for (int i=0;i<movs.length;i++){
            //access the movies from chosen person and find index of movies
            int pos = data.get(loc_user).getTitle().indexOf(movs[i]);
            //if the rating at the index is 0 then add to recommended
            if (data.get(loc_user).getRating().get(pos)==0){
                //keep only 6 movies to watch
                if (recommended.size()<6){
                    recommended.add(movs[i]); //add the movie if not watched
                }
                else{
                    break;
                }
            }
        }
        //if user few movies unwatched, add the top rated movies to the end of the 4U movies
        if (recommended.size()<6){
            int ctr=0;
            //add movies until filled
            while(recommended.size()!=6){
                recommended.add(movs[ctr]);
                ctr++;
            }
        }

        movs4u=recommended.toArray(movs4u); //return array version of list
    }
  //converrt integer list to int array 
  private int[] toArray_int(LinkedList<Integer> a){
    int num[]=new int[a.size()];
    //loop through the list to convert individually
    for (int i=0;i<a.size();i++){
        num[i]=a.get(i);
    }
    return num;
  }
  
 // self test main
 /*
  public static void main (String[] args)throws IOException{
    Recommend rec=new Recommend("Patrick C");
  }
  */

}
