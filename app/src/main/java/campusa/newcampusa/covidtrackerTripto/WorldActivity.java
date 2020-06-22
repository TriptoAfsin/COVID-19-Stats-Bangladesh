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

public class WorldActivity extends AppCompatActivity{

    private TextView totalCases, totalDeaths, totalRecovered, totalActive, country, date, dailyNewCases, dailyRecoredCases, dailyDeathCases,affectedCountries
            ,totalPercentAffected, deathWorldPercent, recoverWorldPercent, activeWorldPercent;
    private Button moreBtn;
    private EditText name1, name2;
    private int matchStart;
    private RequestQueue mrequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("World Stats");

        totalCases = (TextView)findViewById(R.id.textView_total);
        totalDeaths = (TextView)findViewById(R.id.textView_deaths_total);
        totalActive = (TextView)findViewById(R.id.textView_active_total);
        totalRecovered = (TextView)findViewById(R.id.textView_recoverd_total);
        country = (TextView)findViewById(R.id.textViewCountry);
        date = (TextView)findViewById(R.id.textView_date);
        dailyNewCases = (TextView)findViewById(R.id.textView_newCase_daily);
        dailyRecoredCases = (TextView)findViewById(R.id.textView_newRecoDaily);
        dailyDeathCases = (TextView)findViewById(R.id.textView_newDeathDaily);
        //website = (TextView)findViewById(R.id.website);
        affectedCountries = (TextView)findViewById(R.id.textView_countries_affected);
        totalPercentAffected = (TextView)findViewById(R.id.textView_totalpercen);

        recoverWorldPercent = (TextView)findViewById(R.id.textView_RecoverWorldPercent);
        deathWorldPercent = (TextView)findViewById(R.id.textView_deathWorld_Percen);
        activeWorldPercent = (TextView)findViewById(R.id.textView_active_world_percent);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());




        date.setText(currentDate);
        try{
            //Toast.makeText(MainActivity.this, "Enterd try",Toast.LENGTH_SHORT).show();

            final OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url("https://corona.lmao.ninja/v2/all")
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

                        Type collectionType = new TypeToken<Collection<WorldResponse>>(){}.getType();
                        Collection<WorldResponse> enums = gson.fromJson(modifiedJSONresponse, collectionType);
                        final WorldResponse[] covResponses = enums.toArray(new WorldResponse[enums.size()]);


                        WorldActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                String totalPopuStr = covResponses[0].getPopulation();
                                Double totalPopuFloat = Double.parseDouble(totalPopuStr);
                                String CasesStr = covResponses[0].getCases();
                                Double casesFloat = Double.parseDouble(CasesStr);

                                String deathStr = covResponses[0].getDeaths();
                                Double deathDouble = Double.parseDouble(deathStr);

                                String recoverStr = covResponses[0].getRecovered();
                                Double recoverDouble = Double.parseDouble(recoverStr);

                                String activestr = covResponses[0].getActive();
                                Double activeDouble = Double.parseDouble(activestr);



                                final double affectedpercentDouble = Math.round((casesFloat / totalPopuFloat) * 100 * 100D) / 100D;

                                final double deathDoublePercen = Math.round((deathDouble / casesFloat) * 100 * 10D) / 10D;
                                final double recoverDoublePercen = Math.round((recoverDouble / casesFloat) * 100 * 10D) / 10D;
                                final double acticeDoublePer = Math.round((activeDouble / casesFloat) * 100 * 10D) / 10D;


                                String affectedPercentStr = String.valueOf(affectedpercentDouble);
                                String deathDoublePercenStr = String.valueOf(deathDoublePercen);
                                String recoverDoublePercenStr = String.valueOf(recoverDoublePercen);
                                String acticeDoublePerStr = String.valueOf(acticeDoublePer);

                                //country.setText(covResponses[0].getName());
                                //date.setText(covResponses[0].u);
                                totalCases.setText(covResponses[0].getCases());
                                totalActive.setText(covResponses[0].getActive());
                                totalDeaths.setText(covResponses[0].getDeaths());
                                totalRecovered.setText(covResponses[0].getRecovered());
                                //website.setText(myJSOnResponse);
                                dailyNewCases.setText("+"+covResponses[0].getTodayCases());
                                dailyRecoredCases.setText("+"+covResponses[0].getTodayRecovered());
                                dailyDeathCases.setText("+"+covResponses[0].getTodayDeaths());
                                affectedCountries.setText(covResponses[0].getAffectedCountries()+" Countries Affected");
                                totalPercentAffected.setText(affectedPercentStr+ "% affected of total population");

                                recoverWorldPercent.setText(recoverDoublePercenStr+"%");
                                deathWorldPercent.setText(deathDoublePercenStr+"%");
                                activeWorldPercent.setText(acticeDoublePerStr+"%");

                            }
                        });

                    }
                    else if(response.isSuccessful() == false){
                        WorldActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                totalCases.setText("Error");
                                totalRecovered.setText("Error");
                                dailyDeathCases.setText("Error");
                                dailyNewCases.setText("Error");
                                dailyRecoredCases.setText("Error");
                                Toast.makeText(WorldActivity.this, "Data Fetching Failed!",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            });
        }catch (Exception e){
            Toast.makeText(WorldActivity.this, "Data Fetching Failed!",Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(WorldActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

}
