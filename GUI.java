/*
Paarth Kashyap
GUI.java- The interface for viewing, rating, and viewing recommendations. This class is the front end class of the program
calling backend programs where neccessary.
*/

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

import java.io.*;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Arrays;

public class GUI extends JFrame implements ActionListener
{
  //panels and frames
  private JFrame frame;
  private JPanel mainDisp, header, account, rate_panel,top; 

  //different classes
  private Sorting info; //handles the data into obj array 
  private checkTheLogin check; //check login info
  private Recommend suggest;  //recommendation system
  private WriteFiles write_file; //add new account to movie data file

  //layouts and extra
  private GridBagConstraints gbc; 
  private String[] movies; //movies array to sort and display
  private double[] avg_rating; //avg rating to sort by
  private LinkedList<DataObj> data; //obj array
  private String login_choice; 
  private TitledBorder a,b,c,d; //title border variables 
  private Boolean logged_in=false; 

  //header attributes
  private JButton login, signup, menu,btn_rate;
  private JLabel title;
  
  private String[] choices={"A-Z","Z-A","Highest Rating","Lowest Rating","Movies4U"};
  private JComboBox<String> refine;
  private JButton help;

  //main display attributes
  private JPanel[] movieP=new JPanel[25];

  //movie panel attributes
  private JLabel movieName,rating;

  //rating panel attirbutes
  private String[] rate={"1","2","3","4","5"};
  private JComboBox<String> rate_movs,nums;
  private JLabel lbl, lblrate;
  private ImageIcon show1,show2;
  private String movieNam;
  private int given;

  //account panel attributes
  private JLabel user_text,pass_text,pass2_text;
  private JTextField user_in, pass_in,pass2_in;
  private JButton clear,create;

  //constructor
  public GUI() throws IOException
  {
    //initialize frames and panels and classes
    frame=new JFrame(); //main frame 
    mainDisp=new JPanel(); 
    header=new JPanel();
    account=new JPanel();
    rate_panel=new JPanel();
    write_file=new WriteFiles();
    
    update_files(); //create obj array and laod in data

    //header instantiate------------------------------------------------
    header.setLayout(new GridBagLayout());//layout
    gbc=new GridBagConstraints(); //gbc attributes
    gbc.fill = GridBagConstraints.VERTICAL;
    gbc.insets = new Insets(5,5,5,5);  // padding around eveything
    
    title=new JLabel("Rate-A-Movie");
    title.setFont(new Font("Comic Sans MS", Font.ITALIC, 60)); 
    gbc.gridx = 1; //x and y location
    gbc.gridy = 0;
    gbc.gridwidth = 1; //width and height
    gbc.gridheight = 4;
    header.add(title, gbc); //add to panel

    login=new JButton("Login");
    login.setFont(new Font("Comic Sans MS", Font.PLAIN,20)); 
    gbc.gridx=4; //x and y location
    gbc.gridy=0;
    gbc.gridwidth = 1; //width and height
    gbc.gridheight = 1;
    gbc.weightx  = 2; //space around label horizontally
    gbc.ipadx=100; //horizontal stretch
    gbc.anchor=GridBagConstraints.EAST; //anchor east side and everything under
    header.add(login, gbc); //add to panel

    signup=new JButton("Sign Up");
    signup.setFont(new Font("Comic Sans MS", Font.PLAIN,20)); 
    gbc.gridx=4; //x and y location
    gbc.gridy=1;
    gbc.gridwidth = 1; //width and height
    gbc.gridheight = 1;
    gbc.ipadx=76; //horizontal stretch
    header.add(signup, gbc); //add to panel

    menu=new JButton("Main Menu");
    menu.setFont(new Font("Comic Sans MS", Font.PLAIN,15)); 
    gbc.gridx=0; //x and y location
    gbc.gridy=0;
    gbc.gridwidth = 3; //width and height
    gbc.gridheight = 1;
    gbc.ipadx=100; //horizontal stretch
    gbc.anchor=GridBagConstraints.WEST; //anchor west
    header.add(menu, gbc); //add to panel

    btn_rate=new JButton("Rate Movies");
    gbc.gridx=0; //x and y location
    gbc.gridy=1;
    gbc.gridwidth = 1; //width and height
    gbc.gridheight = 1;
    gbc.ipadx=96; //horizontal stretch
    btn_rate.setVisible(true);
    header.add(btn_rate, gbc); //add to panel

    refine=new JComboBox<>(choices); //combo box with choices 
    refine.setSelectedIndex(0); //default choice
    refine.setFont(new Font("Comic Sans MS", Font.PLAIN, 15)); //change font 
    gbc.gridx=0; //x and y location
    gbc.gridy=2;
    gbc.ipadx=44; //horizontal stretch
    header.add(refine, gbc); //add to panel

    help=new JButton("Help");
    help.setFont(new Font("Comic Sans MS", Font.PLAIN,20)); 
    gbc.gridx=4; //x and y location
    gbc.gridy=2;
    gbc.gridwidth = 1; //width and height
    gbc.gridheight = 1;
    gbc.ipadx=107; //horizontal stretch
    gbc.anchor=GridBagConstraints.EAST; //anchor east side and everything under
    header.add(help, gbc); //add to panel

    
    //creates the fancy border around header
    a= BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#B701F7"),2), 
     "Developed by Paarth Kashyap");
    a.setTitleJustification(TitledBorder.CENTER); //center small text
    header.setBorder(a); //assign to header

    //----------------------------------Movie Panel attributes ------------------------------------

    //add elements to combobox array
    for (int i=0;i<movieP.length;i++){
      //create n number of movie panels to display movies
      movieP[i]=createMovie_panel(movies[i],i);; 
      mainDisp.add(movieP[i]); //add each to main display
    }
    
    //adding array to main display
    mainDisp.setLayout(new GridLayout(2,3));

    //creates the fancy border around movies
    b= BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#B701F7"),2)
    ,"Recommended Movies");
    b.setTitleJustification(TitledBorder.CENTER); //center small text
    mainDisp.setBorder(b); //assign to header
    //------------------------rating panel--------------------------------------
    rate_panel.setLayout(new BorderLayout(5,10));//layout   
    top=new JPanel(new GridLayout(1,0)); //panel holding movie name and rating 
    
    rate_movs=new JComboBox<String>(movies); //choose movie
    rate_movs.setFont(new Font("Comic Sans MS", Font.ITALIC, 20)); 
    top.add(rate_movs);

    nums=new JComboBox<String>(rate); //option for rating 
    nums.setFont(new Font("Comic Sans MS", Font.ITALIC, 20)); 

    top.add(nums); 
    rate_panel.add(top,BorderLayout.NORTH);

    //creates the fancy border around movies
    d= BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#B701F7"),2)
    ,"Rate Movies");
    d.setTitleJustification(TitledBorder.CENTER); //center small text
    rate_panel.setBorder(d); //assign to header

    //------------------------login signup panel attributes--------------------------------------
    account.setLayout(new GridBagLayout());//layout
    gbc=new GridBagConstraints(); //gbc attributes

    gbc.fill = GridBagConstraints.VERTICAL;
    gbc.insets = new Insets(10,0,10,0);  // padding around eveything
    user_text=new JLabel("Username:");
    user_text.setFont(new Font("Comic Sans MS", Font.ITALIC, 20)); 
    gbc.gridx = 0; //x and y location
    gbc.gridy = 0;
    gbc.gridwidth = 1; //width and height
    gbc.gridheight = 1;
    gbc.ipadx=0; //horizontal stretch
    account.add(user_text, gbc); //add to panel

    pass_text=new JLabel("Password:");
    pass_text.setFont(new Font("Comic Sans MS", Font.ITALIC, 20)); 
    gbc.gridx = 0; //x and y location
    gbc.gridy = 1;
    gbc.gridwidth = 1; //width and height
    gbc.gridheight = 1;
    account.add(pass_text, gbc); //add to panel

    pass2_text=new JLabel("Confirm Password:");
    pass2_text.setFont(new Font("Comic Sans MS", Font.ITALIC, 20)); 
    gbc.gridx = 0; //x and y location
    gbc.gridy = 2;
    gbc.gridwidth = 1; //width and height
    gbc.gridheight = 1;
    account.add(pass2_text, gbc); //add to panel
    pass2_text.setVisible(false); //dont show by default

    user_in=new JTextField("");
    user_in.setFont(new Font("Comic Sans MS", Font.ITALIC, 20)); 
    gbc.gridx = 1; //x and y location
    gbc.gridy = 0;
    gbc.gridwidth = 3; //width and height
    gbc.gridheight = 1;
    gbc.ipadx=250; //horizontal stretch
    account.add(user_in, gbc); //add to panel

    pass_in=new JTextField("");
    pass_in.setFont(new Font("Comic Sans MS", Font.ITALIC, 20)); 
    gbc.gridx = 1; //x and y location
    gbc.gridy = 1;
    gbc.gridwidth = 1; //width and height
    gbc.gridheight = 1;
    account.add(pass_in, gbc); //add to panel

    pass2_in=new JTextField("");
    pass2_in.setFont(new Font("Comic Sans MS", Font.ITALIC, 20)); 
    gbc.gridx = 1; //x and y location
    gbc.gridy = 2;
    gbc.gridwidth = 1; //width and height
    gbc.gridheight = 1;
    account.add(pass2_in, gbc); //add to panel
    pass2_in.setVisible(false); //dont show by default
    gbc.insets = new Insets(20,5,20,5);  // padding around eveything

    create=new JButton("Login");
    create.setFont(new Font("Comic Sans MS", Font.PLAIN,20)); 
    gbc.gridx=0; //x and y location
    gbc.gridy=3;
    gbc.gridwidth = 1; //width and height
    gbc.gridheight = 1;
    gbc.weightx  = 2; //space around label horizontally
    gbc.ipadx=100; //horizontal stretch
    account.add(create, gbc); //add to panel

    clear=new JButton("Clear Text");
    clear.setFont(new Font("Comic Sans MS", Font.PLAIN,20)); 
    gbc.gridx=1; //x and y location
    gbc.gridy=3;
    gbc.gridwidth = 1; //width and height
    gbc.gridheight = 1;
    gbc.ipadx=50; //horizontal stretch
    account.add(clear, gbc); //add to panel

    //creates the fancy border around header
    c= BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#B701F7"),2)
    ,"Login");
    c.setTitleJustification(TitledBorder.CENTER); //center small text
    account.setBorder(c); //assign to header

//-----------------frame properties-------------------------------------------  
    //add all panels to frame
    frame.setLayout(new BorderLayout());
    frame.add(header,BorderLayout.NORTH);
    //frame.add(rate_panel,BorderLayout.CENTER);
    frame.add(mainDisp,BorderLayout.CENTER);

//---------change colours------------------
    header.setBackground(Color.WHITE);
    mainDisp.setBackground(Color.WHITE);
    rate_panel.setBackground(Color.WHITE);
    top.setBackground(Color.WHITE);
    account.setBackground(Color.WHITE);
    refine.setBackground(Color.decode("#B97AF1"));
    rate_movs.setBackground(Color.decode("#B97AF1"));
    nums.setBackground(Color.decode("#B97AF1"));

    //add actionlistners
    refine.addActionListener(this);
    login.addActionListener(this);
    signup.addActionListener(this);
    clear.addActionListener(this);
    help.addActionListener(this);
    create.addActionListener(this);
    menu.addActionListener(this);
    btn_rate.addActionListener(this);
    rate_movs.addActionListener(this);
    nums.addActionListener(this);
    
    frame.setSize(1555,900);
    frame.setTitle("Rate-A-Movie");
    frame.setVisible(true);
  }

  //create jpanel
  public JPanel createMovie_panel(String nam,int ind){
      //initialize panel and gbc
      JPanel moviePanel=new JPanel();
      moviePanel.setLayout(new GridBagLayout());//layout
      gbc=new GridBagConstraints(); //gbc attributes
      gbc.insets = new Insets(5,5,5,5);  // padding around eveything

      movieNam=nam; //gets movies name in order
      movieName=new JLabel(movieNam);
      movieName.setFont(new Font("Comic Sans MS", Font.ITALIC, 20)); 
      gbc.gridx = 0; //x and y location
      gbc.gridy = 0;
      gbc.gridwidth = 1; //width and height
      gbc.gridheight = 1;
      moviePanel.add(movieName, gbc); //add to panel

      //add image to label and resize
      String loc="Movie Pics/"+movieNam+".jpg";
      show1= new ImageIcon(new ImageIcon(loc).getImage().getScaledInstance(100,180, Image.SCALE_DEFAULT));
      JLabel lblImg = new JLabel();
      lblImg.setIcon(show1);
      gbc.gridx = 0; //x and y location
      gbc.gridy = 1;
      moviePanel.add(lblImg, gbc); //add to panel

      //use the movies array to find the index its index. The movies rating is parallel to the avg rating
      int pos=Arrays.asList(movies).indexOf(movieNam);
      DecimalFormat num = new DecimalFormat("#.#"); //for 1 decimal place
      String text="Rating: "+num.format(avg_rating[pos])+"/5 ☆";

      rating=new JLabel(text);
      movieName.setFont(new Font("Comic Sans MS", Font.ITALIC, 20)); 
      gbc.gridx = 0; //x and y location
      gbc.gridy = 2;
      gbc.gridwidth = 1; //width and height
      gbc.gridheight = 1;
      gbc.fill = GridBagConstraints.VERTICAL;
      moviePanel.add(rating, gbc); //add to panel
      moviePanel.setBackground(Color.WHITE);
            
      return moviePanel; //returns the created movie panel
  }

  //if button clicked 
  public void actionPerformed (ActionEvent e){
    //if sort filter chosen
    if (e.getSource() == refine) {

      //if the recommendation filter chosen
      if (refine.getSelectedItem().equals("Movies4U")){
        //check for login
        if (logged_in==true){
          //exception handling
          try{
            //create the recommendations 
            
            suggest=new Recommend(login.getText()); //get reccomendation movie array
            //clear display
            mainDisp.removeAll(); 
            frame.remove(rate_panel);
            //create movie panels and show
            for (int i=0;i<suggest.movs4u.length;i++){
              mainDisp.add(createMovie_panel(suggest.movs4u[i],i)); //add the rec movies
            }
            refresh(mainDisp);
          }//catches
          catch(IOException f){System.out.println("IO Exception Error "+f.getMessage());}
          catch(NullPointerException f){System.out.println("Null Values Error "+f.getMessage());}
          catch(Exception f){System.out.println("Big Error "+f.getMessage());}
        }
        //if not logged in
        else{
          JOptionPane.showMessageDialog(null,"Please Login to see your recommendations.");
          refine.setSelectedIndex(0);
        }
      }
      //for all other sorts
      else{
        String choice = refine.getSelectedItem().toString(); //get filter thats clicked
        //sort it based on filter name 
        info.bubble_sort(choice,movies,avg_rating);
        //clear frame
        frame.remove(rate_panel);
        mainDisp.removeAll();
        //replace all jpanels and add to main display
        for (int i=0;i<movieP.length;i++){
          mainDisp.add(createMovie_panel(movies[i],i)); //add to frame
        }
        refresh(mainDisp);
      }

    }
    
    //if login button clicked
    if (e.getSource() == login){
      
      //if no one logged in 
      if (logged_in==false){
        login_choice="Login";
        //hide signup attributes
        pass2_text.setVisible(false);
        pass2_in.setVisible(false);
        create.setText(login_choice);//change login button text
        c.setTitle(login_choice); //change text of border
        refresh(account);
        refine.setEnabled(false);
      }
      //if logged in, you cant login 
      else{
        login.setEnabled(false); //disable button
      }
    }

    //if signup clicked
    if (e.getSource() == signup){

      //check if not logged in, signup option available
      if(logged_in==false){
        login_choice="Sign-Up";
        //show signup attributes
        pass2_text.setVisible(true);
        pass2_in.setVisible(true);
        create.setText(login_choice);//change login button text
        c.setTitle(login_choice);//change text of border
        //refresh screen
        refresh(account);
        refine.setEnabled(false);
      }
      //if your logged in, sigup btn acts like a logout btn
      else{
        
        //reset labels and buttons
        login.setText("Login");
        signup.setText("Sign Up");
        logged_in=false;
        login.setEnabled(true);
         //reset the screen after loggin out
        info.bubble_sort("A-Z",movies,avg_rating);
        refine.setSelectedIndex(0); //default choice 
        //logout
        JOptionPane.showMessageDialog(null,"Logging out.");

        mainDisp.removeAll(); //clear display
        //replace all jpanels and add to main display
        for (int i=0;i<movieP.length;i++){
          movieP[i]=createMovie_panel(movies[i],i);
          mainDisp.add(movieP[i]); //add to frame
        }
        refresh(mainDisp);
        refine.setEnabled(true);
      }
    }

    //if help clicked
    if (e.getSource() == help){

      String show=("Welcome to Rate-A-Movie\n\n"+

                    "Main Page:\n"+
                    "You can sort the different movies from Alphabetically or by Average Rating using the\nfilter on the top left of the screen."+
                    "The Movies4U is a recomendation filter that will show movies chosen just for you.\n"+

                    "\nLogin/Sign Up:"+
                    "\nLogin using the button on top right."+
                    "If you don't have an account, simply create it by clicking the Sign-Up button.\n"+

                    "\nRecommendations:"+
                    "\nAfter loggin in, choose the Movies4U filter to see movies recommended just for you.\n"+
                    "The patented algorithm developed in-house, shows you Top 6 movies that you haven't watched based\n"+
                    "on other profiles in our system.\n"+

                    "\nRating Movies:"+
                    "\nThe Rate Movies button allows you to chose any movie and give a rating to the movie from 1-5.\n"+
                    "To go back to main menu, click the respective button. \n\nEnjoy Mr. Wray\n"+
                    
                    "-Paarth Kashyap"
                    );
      JOptionPane.showMessageDialog(null,show,"Help",JOptionPane.INFORMATION_MESSAGE);

    }

    //if either logged in or signed up
    if (e.getSource() == create){
      //checkuser input class
      check=new checkTheLogin();

      //if user is on login page
      if (login_choice.equals("Login")){
        //if login info is correct
        if (check.check(user_in.getText(),pass_in.getText())){
          refresh(mainDisp);
          refine.setEnabled(true);

          //after login completed
          logged_in=true;
          login.setText(user_in.getText()); //change text to username
          signup.setText("Sign Out"); //logout button
        }
        //if invliad info
        else{
          JOptionPane.showMessageDialog(null,"Invalid Username/Password");
        }
      }
      //if user is on sign up page
      else if(login_choice.equals("Sign-Up")){

        //no empty text
        if(!(user_in.getText().equals("")||pass_in.getText().equals("")||
            pass2_in.getText().equals(""))){

          //if confirm pass word is correct
          if (pass_in.getText().equals(pass2_in.getText())){ 
            //write_info=new LoginFileWriter(); ---------------------------------------

            //write if username not on login file 
            if(check.checkUser(user_in.getText())==false){
              try{ 
                write_file.write(user_in.getText(),pass_in.getText()); //write to login file saving all credentials
                JOptionPane.showMessageDialog(null,"Account created.\nPlease Login.");
               
                //add to obj array and update all arrays
                write_file.addUser(user_in.getText());
                update_files();
              }
              catch(IOException f){ System.out.println("IO Exception "+f.getMessage());}
              catch(NullPointerException f){ System.out.println("Null Pointer "+f.getMessage());}
              catch(Exception f){ System.out.println("BIG ERROR "+f.getMessage());}

              //go to login page and hide signup attributes
              pass2_text.setVisible(false);
              pass2_in.setVisible(false);
              login_choice="Login";
              create.setText(login_choice);//change login button text
              c.setTitle(login_choice); //change text of border
              refresh(account);
              refine.setEnabled(true);
            }
            else{
              JOptionPane.showMessageDialog(null,"Please create a unique username.");
            }
          }
          else{
            JOptionPane.showMessageDialog(null,"Password is not confirmed.");
          }
        }
        else{
          JOptionPane.showMessageDialog(null,"Please fill all boxes.");
        }
      }
    }

    //if menu clicked
    if (e.getSource() == menu){
      //reset the screen after loggin out
      info.bubble_sort("A-Z",movies,avg_rating);
      refine.setSelectedIndex(0); //default choice 
      //reset main disp
      mainDisp.removeAll();
      //replace all jpanels and add to main display
      for (int i=0;i<movieP.length;i++){
        movieP[i]=createMovie_panel(movies[i],i);
        mainDisp.add(movieP[i]); //add to frame
      }
      //reset middle portion of frame
      refresh(mainDisp);
      refine.setEnabled(true);
    }
    
    //if search clicked
    if (e.getSource() == clear){
      //clear all text boxes
      user_in.setText("");
      pass_in.setText("");
      pass2_in.setText("");
    }
   
    //change to rate panel when clicked
    if (e.getSource()==btn_rate){
      // if logged in
      if (logged_in==true){
        refresh(rate_panel);
        refine.setEnabled(false);

        String nam="Alien";
        String a="Movie Pics/"+nam+".jpg";
        //add image to label and resize
        show2= new ImageIcon(new ImageIcon(a).getImage().getScaledInstance(300,500, Image.SCALE_SMOOTH));

        lbl = new JLabel();
        lbl.setIcon(show2);
        lbl.setHorizontalAlignment(JLabel.CENTER); //horizontal allignment
        rate_panel.add(lbl, BorderLayout.CENTER); //add to panel

        //get index of movie name in the list
        int movie_ind=data.get(0).getTitle().indexOf(nam);
        int pos=info.find_index(login.getText()); //get index of user in list
        //rate label stuff
        given=data.get(pos).getRating().get(movie_ind);
        lblrate= new JLabel("Your Rating: "+given);
        lblrate.setFont(new Font("Comic Sans MS", Font.ITALIC, 20)); 
        lblrate.setHorizontalAlignment(JLabel.CENTER); //horizontal allignment
        rate_panel.add(lblrate,BorderLayout.SOUTH);
      }
      else{
        JOptionPane.showMessageDialog(null,"Please Login to rate movies.");
      }      
    }
    
    //if a rate is given to a movie on rate panel
    if (e.getSource()==nums){
      try{
        //store movie name, rating given, and username
        String movie=(String) rate_movs.getSelectedItem().toString();
        int number=Integer.parseInt(nums.getSelectedItem().toString());
        String user= login.getText();

        //find user position and movie position in obj array
        int pos=info.find_index(user);
        int mov_pos=data.get(pos).getTitle().indexOf(movie);
        int mov_length=movie.length();

        //update rating label
        given=number;
        rate_panel.remove(lblrate);
        rate_panel.add(lblrate);
        lblrate= new JLabel("Your Rating: "+given);
        lblrate.setFont(new Font("Comic Sans MS", Font.ITALIC, 20)); 
        lblrate.setHorizontalAlignment(JLabel.CENTER); //horizontal allignment
        rate_panel.add(lblrate,BorderLayout.SOUTH);
      
        //change the review at movie position
        data.get(pos).change_rate(mov_pos,number);
        //write to cvs file w updated ratings
        write_file.updateRating(pos,number,mov_pos,mov_length);
        update_files(); //updated local arrays and lists
        refresh(rate_panel);
      }
      catch (IOException f){System.out.println("IO EXCEPTOION "+f.getMessage());}
      catch (Exception f){System.out.println("Big Error "+f.getMessage());}
    }

    //if movie name chnages on rating panel and pic appropriate poster
    if (e.getSource()==rate_movs){
      //get movie name and update path to image
      String nam=(String) rate_movs.getSelectedItem().toString();
      String a="Movie Pics/"+nam+".jpg";
      //add image to label and resize
      ImageIcon show2= new ImageIcon(new ImageIcon(a).getImage().getScaledInstance(300,500, Image.SCALE_SMOOTH));
      JLabel lbl1 = new JLabel();
      lbl1.setIcon(show2);
      lbl1.setHorizontalAlignment(JLabel.CENTER); //horizontal allignment

      //get index of movie name in the list
      int movie_ind=data.get(0).getTitle().indexOf(nam);
      int pos=info.find_index(login.getText()); //get index of user in list
      //rate label stuff
      given=data.get(pos).getRating().get(movie_ind);
      rate_panel.remove(lblrate);
      rate_panel.add(lblrate);
      lblrate= new JLabel("Your Rating: "+given);
      lblrate.setFont(new Font("Comic Sans MS", Font.ITALIC, 20)); 
      lblrate.setHorizontalAlignment(JLabel.CENTER); //horizontal allignment

      //refresh
      rate_panel.removeAll();
      rate_panel.add(lbl1,BorderLayout.CENTER);
      rate_panel.add(lblrate,BorderLayout.SOUTH);
      rate_panel.add(top,BorderLayout.NORTH);
      refresh(rate_panel);
    }
    
    //refresh and redraw
    frame.revalidate(); 
    frame.repaint(); 
  }

  //method that reloads obj and all arrays with updated info from csv file
  public void update_files()throws IOException{
    //make the sorting object and other lists/array
    info =new Sorting();
    data=info.storeData; //obj array 
    
    avg_rating=info.avgRating(data); //avg rating array
    movies=new String[info.temp_titles.size()];
    movies=info.temp_titles.toArray(movies);
  }

  //method that "refreshes" the frame
  public void refresh(JPanel show){
    //remove all panels
    frame.remove(mainDisp); //remove the movies
    frame.remove(rate_panel); //remove rating
    frame.remove(account);
    //add display
    frame.add(show,BorderLayout.CENTER); //add login page
  }
}
