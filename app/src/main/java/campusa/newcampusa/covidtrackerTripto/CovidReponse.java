package campusa.newcampusa.covidtrackerTripto;

public class CovidReponse {

    /**
     * NewRecovered : 643
     * countryregion : BGD
     * NewDeaths : 30
     * TotalRecovered : 12804
     * latitude : 23.685
     * countrycode : {"iso3":"BGD"}
     * TotalCases : 60391
     * confirmed : 60391
     * SeriousCases : 1
     * ActiveCases : 46776
     * recovered : 12804
     * name : Bangladesh
     * TotalTests : 372365
     * bnName : বাংলাদেশ
     * NewCases : 2828
     * TotalDeaths : 811
     * RationPerMillion : 367
     * deaths : 811
     * longitude : 90.3563
     */

    private int NewRecovered;
    private String countryregion;
    private String NewDeaths;
    private String TotalRecovered;
    private String latitude;
    private String TotalCases;
    private String confirmed;
    private String SeriousCases;
    private String ActiveCases;
    private String recovered;
    private String name;
    private String TotalTests;
    private String bnName;
    private String NewCases;
    private String TotalDeaths;
    private String RationPerMillion;
    private String deaths;
    /**
     * countrycode : {"iso3":"BGD"}
     * longitude : 90.3563
     */

    private CountrycodeBean countrycode;
    private String longitude;

    public int getNewRecovered() {
        return NewRecovered;
    }

    public void setNewRecovered(int NewRecovered) {
        this.NewRecovered = NewRecovered;
    }

    public String getCountryregion() {
        return countryregion;
    }

    public void setCountryregion(String countryregion) {
        this.countryregion = countryregion;
    }

    public String getNewDeaths() {
        return NewDeaths;
    }

    public void setNewDeaths(String NewDeaths) {
        this.NewDeaths = NewDeaths;
    }

    public String getTotalRecovered() {
        return TotalRecovered;
    }

    public void setTotalRecovered(String TotalRecovered) {
        this.TotalRecovered = TotalRecovered;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }




    public String getTotalCases() {
        return TotalCases;
    }

    public void setTotalCases(String TotalCases) {
        this.TotalCases = TotalCases;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getSeriousCases() {
        return SeriousCases;
    }

    public void setSeriousCases(String SeriousCases) {
        this.SeriousCases = SeriousCases;
    }

    public String getActiveCases() {
        return ActiveCases;
    }

    public void setActiveCases(String ActiveCases) {
        this.ActiveCases = ActiveCases;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalTests() {
        return TotalTests;
    }

    public void setTotalTests(String TotalTests) {
        this.TotalTests = TotalTests;
    }

    public String getBnName() {
        return bnName;
    }

    public void setBnName(String bnName) {
        this.bnName = bnName;
    }

    public String getNewCases() {
        return NewCases;
    }

    public void setNewCases(String NewCases) {
        this.NewCases = NewCases;
    }

    public String getTotalDeaths() {
        return TotalDeaths;
    }

    public void setTotalDeaths(String TotalDeaths) {
        this.TotalDeaths = TotalDeaths;
    }

    public String getRationPerMillion() {
        return RationPerMillion;
    }

    public void setRationPerMillion(String RationPerMillion) {
        this.RationPerMillion = RationPerMillion;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public CountrycodeBean getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(CountrycodeBean countrycode) {
        this.countrycode = countrycode;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public static class CountrycodeBean {
        /**
         * iso3 : BGD
         */

        private String iso3;

        public String getIso3() {
            return iso3;
        }

        public void setIso3(String iso3) {
            this.iso3 = iso3;
        }
    }
}
