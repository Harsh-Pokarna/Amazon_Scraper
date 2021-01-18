package com.example.practise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.View;
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
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.NoSuchElementException;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

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
        tv.setHint("Image will be displayed here");
        editText = findViewById(R.id.edit_text);
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.welcome);
        search = findViewById(R.id.search_Btn);
        search.setOnClickListener(v -> {
            xyz = editText.getText().toString();
            System.out.println(xyz);
            url = "https://www.amazon.in/s?k=" + xyz + "&crid=24WYPNHZGYNIJ&sprefix=keyb%2Caps%2C282&ref=nb_sb_ss_ts-a-p_7_4";
            new scratch(tv, value, url).execute();
});

        }

    public  class scratch extends AsyncTask<String, String , String > {
        String value;
        TextView tv;
        String url;
        Elements allinfo;
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
                allinfo = doc.select("div[class=\"a-section aok-relative s-image-fixed-height\"]");
                Elements tags = allinfo.select("img[class=\"s-image\"]");
                String name = tags.attr("alt");
                name = name.replace("Sponsored Ad - ", "");
                value= name;
                String link = tags.attr("src");
                runOnUiThread(() -> Glide.with(MainActivity.this).load(link).fitCenter().into(imageView));

            } catch (IOException e) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }



            return null;
        }
    }

}
