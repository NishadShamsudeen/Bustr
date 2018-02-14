package com.example.digital.bustr;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private EditText mUserNameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserNameEditText = (EditText) findViewById(R.id.username);
        mPasswordEditText = (EditText) findViewById(R.id.password);
        mLoginButton = (Button) findViewById(R.id.login);
    }

    public void loginUser (View v){
        if ((TextUtils.isEmpty(mUserNameEditText.getText().toString())) || (TextUtils.isEmpty(mPasswordEditText.getText().toString()))) {
            Toast.makeText(this, "Username or password missing", Toast.LENGTH_SHORT).show();
        } else {
            new LoginUser().execute(mUserNameEditText.getText().toString(), mPasswordEditText.getText().toString());
        }

    }

    private class LoginUser extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            Toast.makeText(MainActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
        }

       /@Override
        protected String doInBackground(String... arg0) {

            try {

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("Username", arg0[0]);
                postDataParams.put("Password", arg0[1]);
                Log.e("Params", postDataParams.toString());

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                httpURLConnection.setRequestProperty("Content-Language", "en-US");
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(getPostDataString(postDataParams));
                writer.flush();
                writer.close();

                InputStream is = httpURLConnection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                return response.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return new String("Exception: " + e.getMessage());
            }

        }

        protected void onPostExecute(String result) {
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();

            if (result.contains("Success")) {
                Toast.makeText(getApplicationContext(), "Welcome" + result, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("username", mUserNameEditText.getText().toString());
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getPostDataString(JSONObject postDataParams) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = postDataParams.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = postDataParams.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }

        return result.toString();
    }
    public void recovery (View v){
        Intent intent=new Intent(this,PasswordRecovery.class);
        intent.putExtra("display","recoveryuser");
        startActivity(intent);

    }

}
}
