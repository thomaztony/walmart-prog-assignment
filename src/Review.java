import org.json.JSONObject;

public class Review {
    private JSONObject review;

    public Review(String product_id, String api_key){
        String url = String.format("http://api.walmartlabs.com/v1/reviews/%s?apiKey=%s&format=json", product_id, api_key);
        this.review = new JSONObject(HttpRequest.get(url));
    }

    public JSONObject getReview(){
        return this.review;
    }
}
