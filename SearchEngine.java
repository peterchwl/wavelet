import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler2 implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> listOfStrings = new ArrayList<String>();

    public String handleRequest(URI url) {
        // home screen displays all of the elements in the list of strings
        if (url.getPath().equals("/")) {
            return "Current List!: " + listOfStrings.toString() + "\n" + 
            "Available commands: '/add' and '/search'" + "\n" +
            "Please use 's=' before each query";
        } 
        // adding new string to list
        else if (url.getPath().equals("/add")) {
            String[] parameters = url.getQuery().split("=");
            // checks if only one string is added
            if(parameters.length == 2){
                listOfStrings.add(parameters[1]);
                return String.format("String added!: " + parameters[1]);
            }
            return "Please enter only one parameter!";
        }
        // searching for strings given a substring
        else if(url.getPath().equals("/search")){
            String[] substring = url.getQuery().split("=");
            // initializes arraylist of strings containing substring
            ArrayList<String> containsSubstring = new ArrayList<String>();
            // checks if there is only one query item
            if(substring.length == 2){
                /* loops through all strings and adds string if contains
                substring */
                for(int i = 0; i < listOfStrings.size(); i++){
                    if(listOfStrings.get(i).contains(substring[1])){
                        containsSubstring.add(listOfStrings.get(i));
                    }
                }
            }
            else{
                return "Please enter just one substring in the query!";
            }
            // returns all valid strings
            return containsSubstring.toString();
        }
        return "404 Not Found!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler2());
    }
}
