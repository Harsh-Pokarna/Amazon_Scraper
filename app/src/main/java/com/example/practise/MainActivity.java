package com.example.practise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
     String value, url, xyz;
     TextView tv;
     EditText editText;
     Button search;
     ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);
        editText = findViewById(R.id.edit_text);
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.welcome);
        search = findViewById(R.id.search_Btn);
        search.setOnClickListener(v -> {
            xyz = editText.getText().toString();
            url = "https://www.amazon.in/s?k=" + xyz + "&crid=1XXFNAX736QQI&sprefix=jean%2Caps%2C381&ref=nb_sb_ss_ts-a-p_4_4" ;
            new scratch(tv, value, url).execute();
});

        }

    public  class scratch extends AsyncTask<String, String , String > {
        String value, name, link, url;
        Elements tags;
        TextView tv;
        Elements allinfo1;
        Element allinfo2;
        Document doc;


        scratch(TextView tv, String value, String url){
            this.tv = tv;
            this.value = value;
            this.url = url;

        }
        @Override
        protected void onPostExecute(String s) {
            tv.setText(value);
            Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
              doc = Jsoup.connect(url).get();
                allinfo1 = doc.select("a[class=\"a-link-normal a-text-normal\"]");
                allinfo2 = doc.select("a[class=\"a-link-normal s-no-outline\"]").first();
                tags = allinfo2.getElementsByTag("img");
                link = tags.attr("src");
                name = tags.attr("alt");
                System.out.println(name);
                name = name.replace("Sponsored Ad - ", "");
                value= name;
                runOnUiThread(() -> Glide.with(MainActivity.this).load(link).fitCenter().into(imageView));

            } catch (IOException e) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }



            return null;
        }
    }

}
