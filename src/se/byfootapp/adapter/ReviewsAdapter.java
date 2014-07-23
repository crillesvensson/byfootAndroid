package se.byfootapp.adapter;

import java.util.List;

import se.byfootapp.R;
import se.byfootapp.model.Review;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ReviewsAdapter extends ArrayAdapter<Review>{

    private Context context;
    private List<Review> reviews;
    
    public ReviewsAdapter(Context context, int resource, List<Review> reviews) {
        super(context, resource, reviews);
        this.context = context;
        this.reviews = reviews;
    }
    
    @Override 
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_list_reviews, parent, false);
        Review review = reviews.get(position);
        
        if(review.getName() != null && review.getName().length() > 0){
            TextView name = (TextView)rowView.findViewById(R.id.review_name);
            name.setText(review.getName());
        }
        
        if(review.getComment() != null && review.getName().length() > 0){
            TextView comment = (TextView)rowView.findViewById(R.id.review_comment);
            comment.setText(review.getComment());
        }
        return rowView;
    }

}
