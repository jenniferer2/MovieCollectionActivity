import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MovieCollection {
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName) {
        importMovieList(fileName);
        scanner = new Scanner(System.in);

    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void menu() {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q")) {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q")) {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option) {
        if (option.equals("t")) {
            searchTitles();
        } else if (option.equals("c")) {
            searchCast();
        } else if (option.equals("k")) {
            searchKeywords();
        } else if (option.equals("g")) {
            listGenres();
        } else if (option.equals("r")) {
            listHighestRated();
        } else if (option.equals("h")) {
            listHighestRevenue();
        } else {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles() {
        System.out.print("Enter a tital search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++) {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1) {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort) {
        for (int j = 1; j < listToSort.size(); j++) {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0) {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie) {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast() {
        ArrayList<String> cast = new ArrayList<String>();
        int index = 0;
        for (Movie m : movies) {
            String s = m.getCast();
            while (s.indexOf("|") != -1) {
                String w = s.substring(0, s.indexOf("|"));
                s = s.substring(w.length() + 1);
                w = w.toLowerCase();
                if (!cast.contains(w)) {
                    cast.add(w);
                }
            }

        }


        System.out.print("Enter an actor/actress :  ");
        String user = scanner.nextLine();

        user = user.toLowerCase();
        ArrayList<String> results = new ArrayList<String>();

        for (int i = 0; i < cast.size(); i++) {
            if (cast.get(i).indexOf(user) != -1) {
                results.add(cast.get(i));
            }


        }


        String temp = "";
        for (int i = 0; i < results.size(); i++) {
            for (int j = i + 1; j < results.size(); j++) {
                if (results.get(i).compareTo(results.get(j)) > 0) {
                    temp = results.get(i);
                    results.set(i, results.get(j));
                    results.set(j, temp);

                }
            }


        }

        for (int i = 0; i < results.size(); i++) {
            String name = results.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + name);
        }
        System.out.println("Which actor/actress would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedPerson = results.get(choice - 1);
        ArrayList<Movie> mo = new ArrayList<Movie>();
        for (Movie m : movies) {
            if (m.getCast().toLowerCase().indexOf(selectedPerson) != -1) {
                mo.add(m);

            }

        }
        sortResults(mo);

        for (int i = 0; i < mo.size(); i++) {
            String title = mo.get(i).getTitle();


            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choicee = scanner.nextInt();
        scanner.nextLine();

        Movie s = mo.get(choicee - 1);

        displayMovieInfo(s);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();


    }

    private void searchKeywords() {
        System.out.print("Enter a keyword search term: ");
        String user = scanner.nextLine();

        // prevent case sensitivity
        user = user.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++) {
            String keyWords = movies.get(i).getKeywords();
            keyWords = keyWords.toLowerCase();

            if (keyWords.indexOf(user) != -1) {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }


    private void listGenres() {
        ArrayList<String> genres = new ArrayList<String>();
        int index = 0;
        for (Movie m : movies) {
            String s = m.getGenres();
            while (s.indexOf("|") != -1) {
                String w = s.substring(0, s.indexOf("|"));
                s = s.substring(w.length() + 1);
                w = w.toLowerCase();
                if (!genres.contains(w)) {
                    genres.add(w);
                }
            }

        }


        String temp = "";
        for (int i = 0; i < genres.size(); i++) {
            for (int j = i + 1; j < genres.size(); j++) {
                if (genres.get(i).compareTo(genres.get(j)) > 0) {
                    temp = genres.get(i);
                    genres.set(i, genres.get(j));
                    genres.set(j, temp);

                }
            }


        }


        for (int i = 0; i < genres.size(); i++) {
            String name = genres.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + name);
        }
        System.out.println("Which genre do you want?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedGenre = genres.get(choice - 1);
        ArrayList<Movie> mo = new ArrayList<Movie>();
        for (Movie m : movies) {
            if (m.getGenres().toLowerCase().indexOf(selectedGenre) != -1) {
                mo.add(m);

            }

        }
        sortResults(mo);

        for (int i = 0; i < mo.size(); i++) {
            String title = mo.get(i).getTitle();


            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie do you want to learn more about?");
        System.out.print("Enter number: ");

        int choicee = scanner.nextInt();
        scanner.nextLine();

        Movie s = mo.get(choicee - 1);

        displayMovieInfo(s);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();


    }

    private void listHighestRated() {

        Movie[] rates = new Movie[50];
        ArrayList<Double> xx = new ArrayList<Double>();

        ArrayList<Movie> ss = movies;

        for (int i = 0; i < movies.size(); i++) {
            xx.add(movies.get(i).getUserRating());
        }
        for (int j = 0; j < xx.size() - 1; j++) {
            int minIndex = j;
            for (int k = j + 1; k < xx.size(); k++) {
                if (xx.get(k) < xx.get(minIndex)) {
                    minIndex = k;
                }
            }
            double temp = xx.get(j);
            xx.set(j, xx.get(minIndex));
            xx.set(minIndex, temp);

            Movie tem = movies.get(j);
            ss.set(j, movies.get(minIndex));
            ss.set(minIndex, tem);

        }
        int g = ss.size() - 1;
        for (int i = 0; i < rates.length; i++) {
            rates[i] = ss.get(g);
            g--;

        }


        for (int i = 0; i < rates.length; i++) {
            String see = rates[i].getTitle();
            double s = rates[i].getUserRating();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + see + "   ...rating: " + s);
        }

        System.out.println("Which movie do you want to learn more about?");
        System.out.print("Enter number: ");

        int choicee = scanner.nextInt();
        scanner.nextLine();

        Movie s = rates[choicee - 1];

        displayMovieInfo(s);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }



    private void listHighestRevenue()
    {

        Movie[] revenue = new Movie[50];
        ArrayList<Integer> xx = new ArrayList<Integer>();

        ArrayList<Movie> ss = movies;

        for (int i = 0; i < movies.size(); i++) {
            xx.add(movies.get(i).getRevenue());
        }
        for (int j = 0; j < xx.size() - 1; j++) {
            int minIndex = j;
            for (int k = j + 1; k < xx.size(); k++) {
                if (xx.get(k) < xx.get(minIndex)) {
                    minIndex = k;
                }
            }
            int temp = xx.get(j);
            xx.set(j, xx.get(minIndex));
            xx.set(minIndex, temp);

            Movie tem = movies.get(j);
            ss.set(j, movies.get(minIndex));
            ss.set(minIndex, tem);

        }
        int g = ss.size() - 1;
        for (int i = 0; i < revenue.length; i++) {
            revenue[i] = ss.get(g);
            g--;

        }


        for (int i = 0; i < revenue.length; i++) {
            String see = revenue[i].getTitle();
            double s = revenue[i].getRevenue();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + see + "   ...revenue: " + s);
        }

        System.out.println("Which movie do you want to learn more about?");
        System.out.print("Enter number: ");

        int choicee = scanner.nextInt();
        scanner.nextLine();

        Movie s = revenue[choicee - 1];

        displayMovieInfo(s);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();



    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}