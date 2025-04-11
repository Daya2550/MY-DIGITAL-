package DJ.MyDigital.webScraping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import DJ.MyDigital.webScraping.ScrapingModels.CityWeather;
import DJ.MyDigital.webScraping.ScrapingModels.Commodity;
import DJ.MyDigital.webScraping.ScrapingModels.ItemData;

@Service
public class ScrapingService {

    public List<Commodity> getCommodities() {
        List<Commodity> commodities = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://www.kisandeals.com/mandiprices/ALL/MAHARASHTRA/ALL").get();
            Elements rows = doc.select("table tbody tr");

            rows.forEach(row -> commodities.add(new Commodity(
                row.select("td:nth-child(1)").text(),
                row.select("td:nth-child(2)").text(),
                row.select("td:nth-child(3)").text()
            )));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commodities;
    }

    public List<ItemData> scrapeTableData() {
        List<ItemData> scrapedData = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://mandibhav.khetigyan.in/state/maharashtra/").get();
            Elements rows = doc.select("table tbody tr");

            rows.forEach(row -> {
                Elements details = row.select("td.data_title div.bhav_detail span");
                scrapedData.add(new ItemData(
                    row.select("td.m_td div.name a").text(),
                    details.get(0).text(),
                    details.get(1).text(),
                    details.get(2).text(),
                    details.get(3).text(),
                    details.get(4).text()
                ));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scrapedData;
    }

    public List<CityWeather> getWeatherData() {
        List<CityWeather> weatherList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://www.timeanddate.com/weather/india?lang=en").get();
            Elements rows = doc.select("table.zebra tbody tr");

            rows.forEach(row -> {
                weatherList.add(new CityWeather(
                    row.select("td:nth-child(1)").text(),
                    row.select("td:nth-child(4)").text(),
                    row.select("td:nth-child(5)").text(),
                    row.select("td:nth-child(8)").text(),
                    row.select("td:nth-child(9)").text(),
                    row.select("td:nth-child(12)").text()
                ));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weatherList;
    }

    public List<String> getExternalData() {
        List<String> data = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://shuru.co.in/mandi/solapur-mandi-bhav").get();
            Element table = doc.select("div.mb-8.mt-8").first();

            table.select("td").forEach(td -> data.add(td.text()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }





    public List<String> getTableData() {
        List<String> tableData = new ArrayList<>();
        try {
            // Scrape the URL content
            Document document = Jsoup.connect("https://www.numbeo.com/food-prices/in/Mumbai").get();

            // Find the table in the document (adjust the selector based on the specific table)
            Element table = document.select("table.data_wide_table").first();
            
            if (table != null) {
                // Iterate through each row in the table
                for (Element row : table.select("tr")) {
                    // For each row, iterate through each column (td)
                    for (Element col : row.select("td")) {
                        // Get the text from each column and clean unwanted tags
                        String value = col.text();
                        tableData.add(value);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tableData;
    }




    public String getdatabyclass(Model model){
        try {
            StringBuilder data = new StringBuilder();
        
            Document document = Jsoup.connect("https://whitespace.great-site.net/").get();
        Elements elements = document.select( "col-lg-6");
        int i=0;
        for (Element element : elements) {
            data.append(element.text()).append("\n"); 
        }
     //   model.addAttribute("scrapedData", data.toString());
           return data.toString();
        } catch (IOException e) {
           // model.addAttribute("error", "Failed to fetch the data");
            return "errorPage";  // A view to show an error message
        }
       
    }
}
