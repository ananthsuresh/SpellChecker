import java.io.*;
import java.util.*;
import java.lang.*;


public class SpellChecker{
  
  public static Hashtable<Integer,String> dict = new Hashtable<Integer,String>();
  
  public static void dictBuild(String odict, String pdict) throws Exception{
    
    File file = new File(odict);
    BufferedReader br = new BufferedReader(new FileReader(file));
    File file2 = new File(pdict);
    BufferedReader br2 = new BufferedReader(new FileReader(file2));
    
    
    
    String currentWord  = br.readLine();
    
    while(currentWord !=null){
      currentWord = currentWord.replaceAll("\\s+","");
      dict.put(currentWord.hashCode(), currentWord);
      currentWord = br.readLine();
    }
    
    currentWord = br2.readLine();
    
    
    while(currentWord !=null){
      currentWord = currentWord.replaceAll("\\s+","");
      dict.put(currentWord.hashCode(), currentWord);
      currentWord = br2.readLine();
    }
    
  }
  
  
  public static void spellCheck(String inputFileName) throws Exception{
    
    File file = new File(inputFileName);
    LineNumberReader br = new LineNumberReader(new FileReader(file));
    
    String currentLine  = br.readLine();
    LinkedList<String> wrongWords = new LinkedList<String>();
    String[] words;
    
    while(currentLine != null){
      words = currentLine.split("\\s+");
      for(int i = 0; i < words.length; i++){
        if((dict.get(words[i].hashCode()))  == null){
          wrongWords.add(words[i]);
          if(!SpellChecker.checkAdd(words[i]).equals("") ||!SpellChecker.checkRemove(words[i]).equals("") || !SpellChecker.checkSwap(words[i]).equals("")){
            System.out.println(words[i] + "\t" + br.getLineNumber() + "\t" + "Possibly: " + SpellChecker.checkAdd(words[i]) + SpellChecker.checkRemove(words[i]) + SpellChecker.checkSwap(words[i]) );
          }
          else{
            System.out.println(words[i] + "\t" + br.getLineNumber());
          }
            
          
        }
      }
      currentLine = br.readLine();
    }
  }
  
  public static String checkAdd(String check){
    Set<String> suggestions = new HashSet<String>();
    String suggestions2 = "";
    char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    String newWord = "";
    for(char c: alphabet){
      for(int i = 0; i <=check.length(); i++){
        newWord = check.substring(0,i) + c + check.substring(i);
      }
      if(dict.get(newWord.hashCode()) != null){
        suggestions.add(newWord);
      }
    }
    for(String s : suggestions){
      suggestions2 += (s + " ");
    }
    
    return suggestions2;
  }
  
  public static String checkRemove(String check){
    Set<String> suggestions = new HashSet<String>();
    String suggestions2 = "";
    String newWord = "";
    
    for(int i = 0; i <check.length(); i++){
      newWord = check.substring(0,i) + check.substring(i+1);
      if(dict.get(newWord.hashCode()) != null){
        suggestions.add(newWord);
      }
    }
    if(dict.get(newWord.hashCode()) != null){
      suggestions.add(newWord);
    }
    
    for(String s : suggestions){
      suggestions2 += (s + " ");
    }
    
    return suggestions2;
  }
  
  public static String checkSwap(String check){
    
    Set<String> suggestions = new HashSet<String>();
    String suggestions2 = "";
    String newWord = "";
    
    char[] word = check.toCharArray();
    
    for(int i = 0; i < word.length - 1; i++){
      word = check.toCharArray();
      char temp = word[i];
      word[i] = word[i+1];
      word[i+1] = temp;
      newWord = new String(word);
      if(dict.get(newWord.hashCode()) != null){
        suggestions.add(newWord);
      }
    }
    
    if(dict.get(newWord.hashCode()) != null){
      suggestions.add(newWord);
    }
    
    for(String s : suggestions){
      suggestions2 += (s + " ");
    }
    
    
    
    return suggestions2;
  }
  
  
  public static void main(String[] args) throws Exception{
    SpellChecker.dictBuild(args[0],args[1]);
    SpellChecker.spellCheck(args[2]);
  }
  
}

