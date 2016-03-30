import org.json.JSONObject;

public class Review implements Comparable<Review> {
    private JSONObject review;

    public Review(Integer product_id, String api_key){
        String url = String.format("http://api.walmartlabs.com/v1/reviews/%d?apiKey=%s&format=json", product_id, api_key);
        this.review = new JSONObject(HttpRequest.get(url));
    }

    public JSONObject getReview(){
        return this.review;
    }

    @Override
    public int compareTo(Review o) {
        JSONObject r1_review = this.getReview();
        JSONObject r2_review = o.getReview();

        JSONObject r1_stats = r1_review.has("reviewStatistics") ? r1_review.getJSONObject("reviewStatistics") : null;
        JSONObject r2_stats = r2_review.has("reviewStatistics") ? r2_review.getJSONObject("reviewStatistics") : null;

        if (r1_stats != null && r2_stats != null) {
            double r1_avg_rating = r1_stats.getDouble("averageOverallRating") / r1_stats.getDouble("overallRatingRange");
            double r2_avg_rating = r2_stats.getDouble("averageOverallRating") / r2_stats.getDouble("overallRatingRange");

            if (r1_avg_rating > r2_avg_rating) {
                return 1;
            } else if (r1_avg_rating < r2_avg_rating) {
                return -1;
            }

            int r1_review_count = r1_stats.getInt("totalReviewCount");
            int r2_review_count = r2_stats.getInt("totalReviewCount");

            if (r1_review_count > r2_review_count) {
                return 1;
            } else if (r1_review_count < r2_review_count) {
                return -1;
            } else {
                return 0;
            }

        } else if (r1_stats != null) {
            return 1;
        } else {
            return 0;
                }
    }
}
