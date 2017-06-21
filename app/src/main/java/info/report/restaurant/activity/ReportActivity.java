package info.report.restaurant.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;

import info.report.restaurant.R;
import info.report.restaurant.helper.SQLiteHandler;
import info.report.restaurant.helper.SessionManager;

public class ReportActivity extends AppCompatActivity {

    private WebView myWebView;


    private SQLiteHandler db;
    private SessionManager session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        String name = user.get("name");
        String pass = user.get("uid");
        String url = "http://royalrestaurantbd.com/api-sales-report?username="+name+"&password="+pass;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        myWebView = (WebView)findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());
        // session manager
        session = new SessionManager(getApplicationContext());



        myWebView.loadUrl(url);
        myWebView.setWebViewClient(new WebViewClient());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_report_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        // Launching the login activity
        Intent intent = new Intent(ReportActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.dash_board:
                Intent intent2 = new Intent(ReportActivity.this, DashBoard.class);
                startActivity(intent2);
                return true;


            case R.id.sales_report:
//                Intent intent = new Intent(ReportActivity.this, DashBoard.class);
//                startActivity(intent);
                return true;

            case R.id.summary_report:
                Intent intent1= new Intent(ReportActivity.this, SummaryActivity.class);
                startActivity(intent1);
                return true;

            case R.id.logout:
                logoutUser();
                // check for updates action
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
