import org.json.JSONObject;

import java.util.Comparator;

class ReviewCompare implements Comparator<Review> {

    @Override
    public int compare(Review r1, Review r2) {
        JSONObject r1_review = r1.getReview();
        JSONObject r2_review = r2.getReview();

        JSONObject r1_stats = r1_review.has("reviewStatistics") ? r1_review.getJSONObject("reviewStatistics") : null;
        JSONObject r2_stats = r2_review.has("reviewStatistics") ? r2_review.getJSONObject("reviewStatistics") : null;

        if (r1_stats != null && r2_stats !=null){
            double r1_avg_rating = r1_stats.getDouble("averageOverallRating")/r1_stats.getDouble("overallRatingRange");
            double r2_avg_rating = r2_stats.getDouble("averageOverallRating")/r2_stats.getDouble("overallRatingRange");

            if (r1_avg_rating > r2_avg_rating){
                return 1;
            } else if (r1_avg_rating < r2_avg_rating) {
                return -1;
            }

            int r1_review_count = r1_stats.getInt("totalReviewCount");
            int r2_review_count = r2_stats.getInt("totalReviewCount");

            if (r1_review_count > r2_review_count){
                return 1;
            } else if (r1_review_count < r2_review_count){
                return -1;
            } else {
                return 0;
            }

        } else if (r1_stats != null){
            return 1;
        } else {
            return 0;
        }
    }
}
