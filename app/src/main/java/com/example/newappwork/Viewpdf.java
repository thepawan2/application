package com.example.newappwork;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URLEncoder;

public class Viewpdf extends AppCompatActivity {

    WebView pdfview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpdf);


        pdfview = (WebView) findViewById(R.id.viewpdf);
        pdfview.getSettings().setLoadsImagesAutomatically(true);
        pdfview.getSettings().setJavaScriptEnabled(true);
        pdfview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        String filename = getIntent().getStringExtra("filename");
        String fileurl = getIntent().getStringExtra("fileurl");

        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle(filename);
        pd.setMessage("Opening!!!...");

        pdfview.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
            }
        });

        String url = "";

       try {
           url = URLEncoder.encode(fileurl,"UTF-8");
        }
       catch (Exception ex){}

        pdfview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);



    }

    @Override
    public void onBackPressed() {

        if (pdfview.canGoBack()){
            pdfview.goBack();
        }else {
            super.onBackPressed();
        }
    }


}