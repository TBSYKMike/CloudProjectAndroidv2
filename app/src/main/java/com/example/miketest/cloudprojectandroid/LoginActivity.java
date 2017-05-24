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

    public class MyWebViewClient extends WebViewClient {
    }

    private WebView webView;
    private String url = "http://couldprojectazurev220170522075651.azurewebsites.net/Account/Login";//@SuppressLint("SetJavaScriptEnabled")
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
/*
        mWebView = (WebView) findViewById(R.id.webView1);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://webapplication120170522065558.azurewebsites.net/");}}
*/
    }

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
        //   mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //   mWebView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(new MyWebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                System.out.println("Current URL : " + url + " " + Html.fromHtml("<br>") + " is http url ? " + URLUtil.isHttpUrl(url));
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
                        view.loadUrl(javascript);
                    }

                    if (counter == 1) {
                        String loginResult = null;
                        while (loginResult == null) {
                            if (allCookies != null) {
                                loginResult = findCertainCookie("loginSuccessCookie");
                            }
                        }
                        System.out.println("look:   " + loginResult);
                        if (loginResult.equals("true")) {
                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.loadUrl(url);
                            System.out.println("Logged in as:   " + email);
                            TemporaryStorage.getInstance().setLoggedInuserEmail(email);
                            loadUserSettings();
                            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(myIntent);

                        } else {
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
