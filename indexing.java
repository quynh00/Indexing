package Automata;

import java.util.*; 
import java.io.*;

public class indexing
{
   public static void main(String[] args) throws IOException
  {
     Scanner keyboard = new Scanner(System.in);
     System.out.print("\nEnter input file name: ");
     String fileName = keyboard.nextLine().trim();
     Scanner inputFile = new Scanner(new File(fileName));
     System.out.print("\nEnter output file name: ");
     fileName = keyboard.nextLine().trim();
     PrintWriter outputFile = new PrintWriter(new FileWriter(fileName));
     DocumentIndex index = new DocumentIndex();   
     String line = null;
     int lineNum = 0;
     while(inputFile.hasNextLine())
     {
        lineNum++;
        index.addAllWords(inputFile.nextLine(), lineNum);
     }
     for(IndexEntry entry : index)
        outputFile.println(entry);
     inputFile.close(); 						
     outputFile.close();
     System.out.println("Done.");
  }
}
class DocumentIndex extends ArrayList<IndexEntry>
{
   public DocumentIndex()
  {
     super(); 
  }
	
   public DocumentIndex(int size)
  {
     super(size); 
  }
	
   public void addWord(String str, int num)
  {
     boolean found = false; 
     IndexEntry ie = new IndexEntry(str); 
     ie.add(num); 
     for(IndexEntry i : this) 
        if(str.toUpperCase().equals(i.getWord())) 
        {
           i.add(num); 
           found = true; 
        }
     if(found == false && size() > 0) 
     {
        if(get(size()-1).getWord().compareTo(str.toUpperCase()) < 0) 
        {
           add(ie); 
           return; 
        } 
        for(IndexEntry i : this) 
        {
           if(i.getWord().compareTo(str.toUpperCase()) > 0) 
           {
              add(indexOf(i), ie); 
              return; 
           }
        }
     }
     else if(found == false) 
        add(ie); 
  }
	
   public void addAllWords(String str, int num)
  {
     String[] strArray = str.split("[., \"!?]"); 
     for(String s : strArray) 
        if(!s.equals("")) 
           addWord(s, num); 
  }
	
   private int foundOrInserted(String word)
  {
     IndexEntry temp = new IndexEntry(word); 
     for(IndexEntry element : this) 
     {
        if(element == get(indexOf(element))) 
           return 0; 
     }
  	
     for(IndexEntry element : this) 
     {
        if(word.compareTo(element.getWord()) > 0) 
        {
           add(indexOf(element) - 1, temp); 
           return indexOf(element) - 1; 
        }
     }
     return -1; 
  }
}
class IndexEntry implements Comparable<IndexEntry>
{
  private String word;
  private ArrayList<Integer> numsList;
	
   public IndexEntry(String s)
  {
     word = s.toUpperCase(); 
     numsList = new ArrayList<Integer>(); 
  }
   public void add(int num)
  {
     if(!numsList.contains(num)) 
        numsList.add(num); 
  }
	
   public String getWord()
  {
     return word; 
  }
	
   public String toString()
  {
     String s = word + " "; 
     for(Integer i : numsList) 
        s += i + ", "; 
     s = s.substring(0, s.length()-2); 
     return s; 
  }
	
   public int compareTo(IndexEntry entry)
  {
     return word.compareTo(entry.toString()); 
  }
}

