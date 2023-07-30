package com.example.appdownload;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SecondActivity extends AppCompatActivity {

    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_NAME = "name";
    private static final String KEY_NIM = "nim";

    private EditText editTextName;
    private EditText editTextNim;
    private Button buttonSave;
    private Button buttonDownload;
    private ImageView imageView;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        editTextName = findViewById(R.id.editTextName);
        editTextNim = findViewById(R.id.editTextNim);
        buttonSave = findViewById(R.id.buttonSave);
        buttonDownload = findViewById(R.id.buttonDownload);
        imageView = findViewById(R.id.imageView);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        String savedName = sharedPreferences.getString(KEY_NAME, "");
        String savedNim = sharedPreferences.getString(KEY_NIM, "");

        editTextName.setText(savedName);
        editTextNim.setText(savedNim);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String nim = editTextNim.getText().toString().trim();
                saveData(name, nim);
            }
        });

        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imageUrl = "https://1.bp.blogspot.com/-my53HU4mCnY/XzRd8m221aI/AAAAAAACHZw/HJFTqlHphzA8KNZxmVOQoc1XMi8JeZpsQCLcBGAsYHQ/s1280/inspirasi-lampu-yang-unik.jpg";  // Ganti dengan URL gambar yang ingin Anda unduh
                new DownloadImageTask().execute(imageUrl);
            }
        });
    }

    private void saveData(String name, String nim) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_NIM, nim);
        editor.apply();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            String imageUrl = urls[0];
            Bitmap bitmap = null;
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);
            }
        }
    }
}
