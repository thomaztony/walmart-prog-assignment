import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;


/*
    1. Search for products based upon a user-provided search string

    2. Use the first item in the search response as input for a product recommendation search

    3. Retrieve reviews of the first 10 product recommendations

    4. Rank order the recommended products based upon the review sentiments
*/

public class RecommendationFinder {
    private String api_key = "ep7t6jg5npsedx4nj2mqh2d5";

    private String search_product;


    public RecommendationFinder(String search_product){
        this.search_product = search_product;
    }


    private String getProductId(){
        String url = String.format("http://api.walmartlabs.com/v1/search?apiKey=%s&query=%s", api_key, search_product);
        JSONObject response = new JSONObject(HttpRequest.get(url));
        JSONObject product = response.getJSONArray("items").getJSONObject(0);
        return product.getString("itemId");
    }


    public ArrayList<Review> findRecommendations(){
        String product_id = "42120600"; // currently hard coded because the search api is down
        String url = String.format("http://api.walmartlabs.com/v1/nbp?apiKey=%s&itemId=%s", api_key, product_id);
        JSONArray response = new JSONArray(HttpRequest.get(url));
        int length = response.length() < 10 ? response.length(): 10;

        ArrayList<Review> reccs = new ArrayList<>();
        for (int i=0; i < length; i++){
            try {
                Thread.sleep(200);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            String recc_id = String.valueOf(response.getJSONObject(i).getInt("itemId"));
            reccs.add(new Review(recc_id, api_key));
        }

        Collections.sort(reccs, Collections.reverseOrder(new ReviewCompare()));

        return reccs;
    }

    public void printRecommendations(ArrayList<Review> reviews){
        int length = reviews.size();

        if (length == 0){
            System.out.printf("Sorry, there are currently no recommendations for %s\n", search_product);
            return;
        }

        System.out.printf("Top %d Recommendations to Purchase with %s\n", length, search_product);

        for (int i = 0; i < length; i++) {
            Review r = reviews.get(i);
            System.out.printf("%02d. %s\n", i+1, r.getReview().getString("name"));
        }
    }


    public static void main(String args[]){
        Scanner user_input = new Scanner(System.in);
        String search_product;
        System.out.print("Enter a product name: ");
        search_product = user_input.nextLine();

        System.out.println("Searching for Recommendations...");
        RecommendationFinder rf = new RecommendationFinder(search_product);
        rf.printRecommendations(rf.findRecommendations());
    }

}