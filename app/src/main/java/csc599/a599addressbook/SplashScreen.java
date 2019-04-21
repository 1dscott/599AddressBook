//splash screen page

//Resources
//https://github.com/pantrif/EasySplashScreen


package csc599.a599addressbook;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreen.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(5000)
                .withLogo(R.drawable.icon)
                .withBackgroundColor(Color.parseColor("#FFFFFFFF"))
                //.withHeaderText("")
                .withFooterText("Copyright: Company, L.C. 2019")
                //.withBeforeLogoText("")
                .withAfterLogoText("www.website.net");

         //set text color
        //config.getHeaderTextView().setTextColor(Color.BLACK);
        config.getFooterTextView().setTextColor(Color.BLACK);
        config.getAfterLogoTextView().setTextColor(Color.BLACK);
        //config.getBeforeLogoTextView().setTextColor(Color.BLACK);

        //set to view
        View view = config.create();

        //set view to content view
        setContentView(view);
    }
}
