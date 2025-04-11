package DJ.MyDigital.webScraping;

public class ScrapingModels {

    // Commodity Model Class
    public static class Commodity {
        private String name;
        private String kgPrice;
        private String quintalPrice;

        public Commodity(String name, String kgPrice, String quintalPrice) {
            this.name = name;
            this.kgPrice = kgPrice;
            this.quintalPrice = quintalPrice;
        }

        // Getters
        public String getName() { return name; }
        public String getKgPrice() { return kgPrice; }
        public String getQuintalPrice() { return quintalPrice; }
    }

    // ItemData Model Class
    public static class ItemData {
        private String name, commodity, variety, minPrice, maxPrice, modalPrice;

        public ItemData(String name, String commodity, String variety, String minPrice, String maxPrice, String modalPrice) {
            this.name = name;
            this.commodity = commodity;
            this.variety = variety;
            this.minPrice = minPrice;
            this.maxPrice = maxPrice;
            this.modalPrice = modalPrice;
        }

        // Getters
        public String getName() { return name; }
        public String getCommodity() { return commodity; }
        public String getVariety() { return variety; }
        public String getMinPrice() { return minPrice; }
        public String getMaxPrice() { return maxPrice; }
        public String getModalPrice() { return modalPrice; }
    }

    // CityWeather Model Class
    public static class CityWeather {
        private String city, temperature, weather, wind, humidity, humidity1;

        public CityWeather(String city, String temperature, String weather, String wind, String humidity, String humidity1) {
            this.city = city;
            this.temperature = temperature;
            this.weather = weather;
            this.wind = wind;
            this.humidity = humidity;
            this.humidity1 = humidity1;
        }

        // Getters
        public String getCity() { return city; }
        public String getTemperature() { return temperature; }
        public String getWeather() { return weather; }
        public String getWind() { return wind; }
        public String getHumidity() { return humidity; }
        public String getHumidity1() { return humidity1; }
    }
}
