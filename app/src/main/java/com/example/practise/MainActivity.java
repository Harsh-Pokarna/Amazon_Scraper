package com.example.practise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.NoSuchElementException;

public class MainActivity extends AppCompatActivity {
     String value;
     TextView tv;
     String url;
     String object;
     EditText editText;
     Button search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);
        editText = findViewById(R.id.edit_text);
        object = editText.getText().toString();
        url = "https://www.amazon.com/s?k=" + object;
        search = findViewById(R.id.search_Btn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new scratch(tv, value, url).execute();
    }
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
            } catch (IOException e) {
                e.printStackTrace();
            }
            allinfo = doc.getElementsByClass("a-section aok-relative s-image-fixed-height");
            for (Element link : allinfo){
                Elements tags = link.getElementsByTag("img");
                System.out.println(tags.attr("src"));

            }


            return null;
        }
    }

}
