package techpalle.com.zomatoapp;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {

    //declare all variables
    //First 3 variables
    RecyclerView rv;
    LinearLayoutManager layoutManager;
    MyAdapter myAdapter;
    MyTask myTask;
    ArrayList<Restaurant> al;

    //create a class for RecyclerView
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = getActivity().getLayoutInflater().inflate(R.layout.row, viewGroup, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            Restaurant r = al.get(i);
            //viewHolder.image__url.set
            viewHolder.tv1.setText("Name: "+r.getHotel_name());
            viewHolder.tv2.setText("Locality: "+r.getHotel_locality());
            viewHolder.tv3.setText("Dishes: "+r.getHotel_offers());
            viewHolder.rb1.setRating(Float.parseFloat(r.getHotel_rating()));

            //Display image onto image View using glide library

            Glide.with(getActivity()).load(r.getImage_url()).into(viewHolder.image__url);
        }

        @Override
        public int getItemCount() {
            return al.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView image__url;
            public TextView tv1, tv2, tv3;
            public RatingBar rb1;
            public CardView cv1;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                image__url = itemView.findViewById(R.id.imageView1);
                tv1 = itemView.findViewById(R.id.textView1);
                tv2 = itemView.findViewById(R.id.textView2);
                tv3 = itemView.findViewById(R.id.textView3);
                rb1 = itemView.findViewById(R.id.ratingBar1);
                cv1 = itemView.findViewById(R.id.cardView1);
            }
        }
    }

    //create a class for AsynTask
    public class MyTask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("Kiran", "1");
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d("Kiran", "2");
            try {
                URL u = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) u.openConnection();
                //Extra Code for Zomato
                con.setRequestProperty("Accept", "application/json");//tell zomato to send JSON format data
                con.setRequestProperty("user-key", "edd2f26f68b1c84eed74854544435e6b");
                InputStream is = con.getInputStream();
                InputStreamReader ir = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(ir);

                String s = br.readLine();
                StringBuilder sb = new StringBuilder(s);
                while(s != null){
                    sb.append(s);
                    s = br.readLine();
                }

                return  sb.toString();//return JSON Data of restaurants

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("Kiran", "URL exception");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("Kiran", "IO exception");
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Log.d("Kiran", "3");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Log.d("Kiran", "4");
            Log.d("Kiran", "Server response : "+s);

            try {
                JSONObject obj = new JSONObject(s);
                JSONArray arr = obj.getJSONArray("nearby_restaurants");
                for(int i =0 ; i< arr.length(); i++){
                    JSONObject temp = arr.getJSONObject(i);
                    JSONObject restaurant = temp.getJSONObject("restaurant");
                    String hotel_name = restaurant.getString("name");
                    JSONObject location = restaurant.getJSONObject("location");
                    String hotel_locality = location.getString("locality");
                    //String city = location.getString("city");
                    String hotel_lat = location.getString("latitude");
                    String hotel_lon = location.getString("longitude");
                    String hotel_offers = restaurant.getString("cuisines");
                    String image_url = restaurant.getString("thumb");
                    JSONObject usr = restaurant.getJSONObject("user_rating");
                    String hotel_rating = usr.getString("aggregate_rating");

                    //Now we got all details, let us insert into array list
                    Restaurant r = new Restaurant(image_url, hotel_name, hotel_locality, hotel_offers, hotel_rating, hotel_lat, hotel_lon);
                    al.add(r);
                    myAdapter.notifyDataSetChanged();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my, container, false);
        rv = v.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        myAdapter = new MyAdapter();
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(myAdapter);
        al = new ArrayList<Restaurant>();
        //Now let us start AsyncTask
        myTask = new MyTask();

        //Now pass zomato webservice url to async task for restaurants
        //URL is in (https://developers.zomato.com/documentation#!/common/geocode) give credentials of Zomato API, Lat & Lon
        myTask.execute("https://developers.zomato.com/api/v2.1/geocode?lat=12.8984&lon=77.6179");//extend this by project fetching dynamic lat long

        return v;
    }

}
