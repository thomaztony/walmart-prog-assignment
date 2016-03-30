import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;



public class RecommendationFinder {
    private String api_key = "ep7t6jg5npsedx4nj2mqh2d5";

    private String search_product;


    public RecommendationFinder(String search_product){
        this.search_product = search_product;
    }


    private Integer getProductId(){
        String query = search_product.replaceAll("\\s+", "%20");
        String url = String.format("http://api.walmartlabs.com/v1/search?apiKey=%s&query=%s", api_key, query);
        JSONObject response = new JSONObject(HttpRequest.get(url));
        if (response.has("items")) {
            JSONObject product = response.getJSONArray("items").getJSONObject(0);
            return product.getInt("itemId");
        } else {
            return -1;
        }
    }


    public ArrayList<Review> findRecommendations(){
        ArrayList<Review> reccs = new ArrayList<>();

        Integer product_id = getProductId();
        if (product_id == -1){
            return reccs;
        }
        String url = String.format("http://api.walmartlabs.com/v1/nbp?apiKey=%s&itemId=%d", api_key, product_id);
        JSONArray response = new JSONArray(HttpRequest.get(url));
        int length = response.length() < 10 ? response.length(): 10;

        for (int i=0; i < length; i++){
            try {
                Thread.sleep(200);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            Integer recc_id = response.getJSONObject(i).getInt("itemId");
            reccs.add(new Review(recc_id, api_key));
        }

        Collections.sort(reccs, Collections.reverseOrder());

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
        search_product = user_input.nextLine().trim();

        System.out.println("Searching for Recommendations...");
        RecommendationFinder rf = new RecommendationFinder(search_product);
        rf.printRecommendations(rf.findRecommendations());
    }

}