import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class WebCrawler {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String url = input.next();
        crawler(url);
    }

    public static void crawler(String startingUrl) {
        ArrayList<String> listOfPendingURLs = new ArrayList<>();
        ArrayList<String> listOfVisitedURLs = new ArrayList<>();

        listOfPendingURLs.add(startingUrl);
        while (!listOfPendingURLs.isEmpty() && listOfVisitedURLs.size() <= 100) {
            String urlString = listOfPendingURLs.remove(0);
            if (!listOfVisitedURLs.contains(urlString)) {
                listOfVisitedURLs.add(urlString);
                System.out.println("Craw " + urlString);
                for (String s : getSubURLs(urlString)) {
                    if (!listOfVisitedURLs.contains(s))
                        listOfPendingURLs.add(s);
                }
            }
        }
    }

    public static ArrayList<String> getSubURLs(String urlString) {
        ArrayList<String> list = new ArrayList<>();

        try {
            URL url = new URL(urlString);
            Scanner input = new Scanner(url.openStream());
            int current = 0;
            while (input.hasNext()) {
                String line = input.nextLine();
                current = line.indexOf("https:", current);
                while (current > 0) {
                    int endIndex = line.indexOf("\"", current);
                    if (endIndex > 0) {
                        list.add(line.substring(current, endIndex));
                        current = line.indexOf("http:", endIndex);
                    }
                    else {
                        current = 0;
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return list;

    }
}
