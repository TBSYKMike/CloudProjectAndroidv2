package com.example.miketest.cloudprojectandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends Activity {
    public Context context;

    //Activity used for the login screen
    public class MyWebViewClient extends WebViewClient {
    }

    private WebView webView;
    private String url = "http://couldprojectazurev220170522075651.azurewebsites.net/Account/Login";
    private String email;
    private String password;
    public String allCookies;
    private int counter = 0;
    private boolean finish = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_login);
    }

    //When the user presses the login button
    public void onLoginButtonPressed(View view) {
        android.webkit.CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean aBoolean) {
                    Log.d("", "Removed cookie " + aBoolean);
                }
            });

        } else {
            cookieManager.removeAllCookie();
        }

        EditText passwordEdit = (EditText) findViewById(R.id.password_Text);
        EditText emailEdit = (EditText) findViewById(R.id.email_Text);
        email = emailEdit.getText().toString();
        password = passwordEdit.getText().toString();
        webView = (WebView) findViewById(R.id.webView);

        webView.setWebViewClient(new MyWebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                System.out.println("The URL is : " + url + " " + Html.fromHtml("<br>") + "  isHttpUrl?: " + URLUtil.isHttpUrl(url));
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                try {

                } catch (Exception e) {
                    e.getLocalizedMessage();
                }
                if (finish == false) {
                    CookieSyncManager.getInstance().sync();
                    URL url1 = null;
                    try {
                        url1 = new URL(url);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    //Javascript for setting the inputted user information in the fields in the web view
                    final String javascript = "javascript:" +
                            "document.getElementById('MainContent_Email').value = '" + email + "';" +
                            "document.getElementById('MainContent_Password').value = '" + password + "';" +
                            "document.getElementById('MainContent_loginbutton').click()";

                    allCookies = CookieManager.getInstance().getCookie(url);
                    System.out.println("All COOKIES " + allCookies);

                    if (Build.VERSION.SDK_INT >= 19) {
                        view.evaluateJavascript(javascript, new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {

                            }
                        });
                    } else {
                        view.loadUrl(javascript); //Execute the javascript
                    }

                    if (counter == 1) { //onPageFinished is run twice in order to retrieve the cookies that we set. The cookies we need are retrieved on second attempt
                        String loginResult = null;
                        while (loginResult == null) { //Waiting until login cookie can be retrieved.
                            if (allCookies != null) {
                                loginResult = findCertainCookie("loginSuccessCookie");
                            }
                        }

                        if (loginResult.equals("true")) { //Login successful
                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.loadUrl(url);
                            System.out.println("Logged in as:   " + email);
                            TemporaryStorage.getInstance().setLoggedInuserEmail(email);
                            loadUserSettings();
                            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(myIntent);

                        } else { //Login not successful
                            System.out.println("Invalid login attempt");
                            Toast.makeText(getApplicationContext(), "Invalid login attempt", Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(myIntent);
                        }
                        counter = 0;
                        finish = true;

                    }
                    counter++;
                }

            }
        });
        if (finish == false) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(url);
        }
    }

    //Method for retrieving a certain cookie
    public String findCertainCookie(String cookieName) {
        String certainCookie = null;
        String[] cookieList = allCookies.split(";");
        for (String i : cookieList) {
            if (i.contains(cookieName)) {
                String[] cookiesTemp = i.split("=");
                certainCookie = cookiesTemp[1];
                break;
            }
        }
        return certainCookie;
    }

    //Calling the cookie retrieving method for all cookies as well as storing the variables retrieved in the temporary storage
    private void loadUserSettings(){
        String accelerometerOnOff = findCertainCookie("AccelerometerOnoff");
        String lightOnOff = findCertainCookie("LightOnOff");
        String proximityOnoff = findCertainCookie("ProximityOnoff");
        String samplingRate = findCertainCookie("SamplingRate");

        TemporaryStorage.getInstance().setAcceleroMeterOnOff(accelerometerOnOff);
        TemporaryStorage.getInstance().setLightOnOff(lightOnOff);
        TemporaryStorage.getInstance().setProximityOnoff(proximityOnoff);
        TemporaryStorage.getInstance().setSamplingRate(samplingRate);

    }
}
