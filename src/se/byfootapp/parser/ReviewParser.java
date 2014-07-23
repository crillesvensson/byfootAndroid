package se.byfootapp.parser;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import org.json.JSONObject;

import se.byfootapp.model.Review;

public class ReviewParser implements ModelParser<Review>{

    @Override
    public Review doParse(JSONObject json) throws Exception {
        Review review = new Review();
        String name = "";
        String comment = "";
        Calendar date = null;
        
        if(json.has("author_name") && ! json.isNull("author_name")){
            name = json.getString("author_name");
        }
        if(json.has("text") && !json.isNull("text")){
            comment = json.getString("text");
        }     
        if(json.has("time") && !json.isNull("time")){
            long time = json.getLong("time");
            Calendar reviewDate = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
            reviewDate.setTimeInMillis(time);
            date = reviewDate;
        }
        review.setName(name);
        review.setComment(comment);
        review.setDate(date);
        return review;
    }

}
