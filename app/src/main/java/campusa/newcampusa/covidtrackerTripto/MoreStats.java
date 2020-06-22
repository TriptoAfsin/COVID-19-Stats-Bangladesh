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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MoreStats extends AppCompatActivity {

    private TextView totalCases, totalDeaths, totalRecovered, totalActive, country, date, dailyNewCases, dailyRecoredCases, dailyDeathCases, website, deathpercent,
            recoverpercent, activepercent, totaltests, rationsMillion;

    private Button calcBtn;
    private EditText name1, name2;
    private int matchStart;
    private RequestQueue mrequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_stats);

        getSupportActionBar().setTitle("More Stats");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        totalCases = (TextView) findViewById(R.id.textView_total);
        totalDeaths = (TextView) findViewById(R.id.textView_deaths_total);
        totalActive = (TextView) findViewById(R.id.textView_active_total);
        totalRecovered = (TextView) findViewById(R.id.textView_recoverd_total);
        country = (TextView) findViewById(R.id.textViewCountry);
        date = (TextView) findViewById(R.id.textView_date);
        dailyNewCases = (TextView) findViewById(R.id.textView_newCase_daily);
        dailyRecoredCases = (TextView) findViewById(R.id.textView_newRecoDaily);
        dailyDeathCases = (TextView) findViewById(R.id.textView_newDeathDaily);
        website = (TextView) findViewById(R.id.website);
        deathpercent = (TextView) findViewById(R.id.textView_deathPercent);
        recoverpercent = (TextView) findViewById(R.id.textView_recopercent);
        activepercent = (TextView) findViewById(R.id.textView_activePercent);
        totaltests = (TextView) findViewById(R.id.textView_total_tests);
        rationsMillion = (TextView) findViewById(R.id.textView_ration);


        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());


        date.setText(currentDate);
        try {
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
                    if (response.isSuccessful()) {
                        final String myJSOnResponse = response.body().string();
                        final String myJSONPercentage = myJSOnResponse.replaceAll("[^0-9]", "");
                        final String modifiedJSONresponse = "[" + myJSOnResponse + "]"; //converting json object to json array


                        // gson to parse my json

                        Gson gson = new Gson();

                        Type collectionType = new TypeToken<Collection<CovidReponse>>() {
                        }.getType();
                        Collection<CovidReponse> enums = gson.fromJson(myJSOnResponse, collectionType);
                        final CovidReponse[] covResponses = enums.toArray(new CovidReponse[enums.size()]);

                        final Double totalInt = Double.parseDouble(covResponses[0].getTotalCases());
                        final Double totalDeathInt = Double.parseDouble(covResponses[0].getDeaths());
                        final Double TotalRecoInt = Double.parseDouble(covResponses[0].getTotalRecovered());
                        final Double TotalActiveInt = Double.parseDouble(covResponses[0].getActiveCases());

                        final double deathPercenInt = Math.round((totalDeathInt / totalInt) * 100 * 10D) / 10D;
                        final double RecopercentInt = Math.round((TotalRecoInt / totalInt) * 100 * 10D) / 10D;
                        final double TotalActiveIntPer = Math.round((TotalActiveInt / totalInt) * 100 * 10D) / 10D;


                        MoreStats.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                country.setText(covResponses[0].getName());
                                //date.setText(covResponses[0].u);
                                totalCases.setText(covResponses[0].getTotalCases());
                                totalActive.setText(covResponses[0].getActiveCases());
                                totalDeaths.setText(covResponses[0].getTotalDeaths());
                                totalRecovered.setText(covResponses[0].getRecovered());
                                //website.setText(myJSOnResponse);
                                rationsMillion.setText(covResponses[0].getRationPerMillion());
                                totaltests.setText(covResponses[0].getTotalTests() + " Tests");
                                deathpercent.setText(deathPercenInt + "%");
                                recoverpercent.setText(RecopercentInt + "%");
                                activepercent.setText(TotalActiveIntPer + "%");

                            }
                        });

                    } else if (response.isSuccessful() == false) {
                        country.setText("Error");
                        Toast.makeText(MoreStats.this, "Data Fetching Failed!", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        } catch (Exception e) {
            Toast.makeText(MoreStats.this, "Data Fetching Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reload_only, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.reload_2:
                this.recreate();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MoreStats.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}

