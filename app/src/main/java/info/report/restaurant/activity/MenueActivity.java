package info.report.restaurant.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import info.report.restaurant.R;
import info.report.restaurant.helper.SQLiteHandler;
import info.report.restaurant.helper.SessionManager;

public class MenueActivity extends AppCompatActivity {

    private Button dashboardButton;
    private Button salesForReport;
    private Button anotherReport;



    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;

    private SQLiteHandler db;
    private SessionManager session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menue);

        salesForReport = (Button) findViewById(R.id.salesReport);
        anotherReport = (Button) findViewById(R.id.summaryReport);
        dashboardButton = (Button) findViewById(R.id.dashboard_button);
//        buttonForAboutUs = (Button) findViewById(R.id.aboutUs);


        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        txtName.setText(name);
        txtEmail.setText(email);



        dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( MenueActivity.this, DashBoard.class);
                startActivity(i);
            }
        });


        salesForReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( MenueActivity.this, ReportActivity.class);
                startActivity(i);
            }
        });



        ///button for searchByKeyword
        anotherReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( MenueActivity.this, SummaryActivity.class);
                startActivity(i);
            }
        });

        //for book two
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }


    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MenueActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
