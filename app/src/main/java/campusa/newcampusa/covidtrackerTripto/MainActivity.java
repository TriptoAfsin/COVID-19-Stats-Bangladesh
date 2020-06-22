package campusa.newcampusa.covidtrackerTripto;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import maes.tech.intentanim.CustomIntent;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity{

    private TextView totalCases, totalDeaths, totalRecovered, totalActive, country, date, dailyNewCases, dailyRecoredCases, dailyDeathCases,website;
    private Button moreBtn;
    private EditText name1, name2;
    private int matchStart;
    private RequestQueue mrequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalCases = (TextView)findViewById(R.id.textView_total);
        totalDeaths = (TextView)findViewById(R.id.textView_deaths_total);
        totalActive = (TextView)findViewById(R.id.textView_active_total);
        totalRecovered = (TextView)findViewById(R.id.textView_recoverd_total);
        country = (TextView)findViewById(R.id.textViewCountry);
        date = (TextView)findViewById(R.id.textView_date);
        dailyNewCases = (TextView)findViewById(R.id.textView_newCase_daily);
        dailyRecoredCases = (TextView)findViewById(R.id.textView_newRecoDaily);
        dailyDeathCases = (TextView)findViewById(R.id.textView_newDeathDaily);
        website = (TextView)findViewById(R.id.website);
        moreBtn = (Button)findViewById(R.id.more_btn);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MoreStats.class);
                startActivity(intent);
                CustomIntent.customType(MainActivity.this, "bottom-to-up");  //for animation
            }
        });



        date.setText(currentDate);
        try{
            //Toast.makeText(MainActivity.this, "Enterd try",Toast.LENGTH_SHORT).show();

            final OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url("http://covid19tracker.gov.bd/api/country/latest?onlyCountries=true&iso3=BGD")
                    .get()
                    .build();

            //Toast.makeText(MainActivity.this, "Build Done",Toast.LENGTH_SHORT).show();


            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                    if(response.isSuccessful()){
                        final String myJSOnResponse = response.body().string();
                        final String myJSONPercentage = myJSOnResponse.replaceAll("[^0-9]", "");
                        final String modifiedJSONresponse = "["+myJSOnResponse+"]"; //converting json object to json array


                        // gson to parse my json

                        Gson gson = new Gson();

                        Type collectionType = new TypeToken<Collection<CovidReponse>>(){}.getType();
                        Collection<CovidReponse> enums = gson.fromJson(myJSOnResponse, collectionType);
                        final CovidReponse[] covResponses = enums.toArray(new CovidReponse[enums.size()]);
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                country.setText(covResponses[0].getName());
                                //date.setText(covResponses[0].u);
                                totalCases.setText(covResponses[0].getTotalCases());
                                totalActive.setText(covResponses[0].getActiveCases());
                                totalDeaths.setText(covResponses[0].getTotalDeaths());
                                totalRecovered.setText(covResponses[0].getRecovered());
                                //website.setText(myJSOnResponse);
                                dailyNewCases.setText("+"+covResponses[0].getNewCases());
                                dailyRecoredCases.setText("+"+covResponses[0].getNewRecovered());
                                dailyDeathCases.setText("+"+covResponses[0].getNewDeaths());

                            }
                        });

                    }
                    else if(response.isSuccessful() == false){

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //country.setText("Error");

                                totalCases.setText("Error");
                                totalRecovered.setText("Error");
                                dailyNewCases.setText("Error");

                                dailyRecoredCases.setText("Error");
                                dailyDeathCases.setText("Error");


                                Toast.makeText(MainActivity.this, "Data Fetching Failed!",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }
            });


        }catch (Exception e){
            Toast.makeText(MainActivity.this, "Data Fetching Failed!",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                Intent intent = new Intent(MainActivity.this, About.class);
                startActivity(intent);
                break;
            case R.id.reload:
                this.recreate();
                break;
            case R.id.world_stat:
                Intent intent_world = new Intent(MainActivity.this, WorldActivity.class);
                startActivity(intent_world);
                CustomIntent.customType(MainActivity.this, "up-to-bottom");  //for animation
                break;
            case R.id.exit_app:
                super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }



}
