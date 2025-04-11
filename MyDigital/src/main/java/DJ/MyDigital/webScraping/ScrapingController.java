package DJ.MyDigital.webScraping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ScrapingController {

    @Autowired
    private ScrapingService scrapingService;

    @GetMapping("/commodities")
    public String displayCommodities(Model model) {
        model.addAttribute("commodities", scrapingService.getCommodities());
        return "web6"; // Thymeleaf template for displaying commodities
    }

    @GetMapping("/scraped-data")
    public String getScrapedData(Model model) {
        model.addAttribute("data", scrapingService.scrapeTableData());
        return "web5"; // Thymeleaf template for displaying scraped data
    }

    @GetMapping("/weather")
    public String getWeatherData(Model model) {
        model.addAttribute("weatherList", scrapingService.getWeatherData());
        return "web3"; // Thymeleaf template for displaying weather data
    }

    @GetMapping("/go")
    public String getAdditionalData(Model model) {
        model.addAttribute("scrapedData", scrapingService.getdatabyclass(model));
        return "web4";  // Thymeleaf template for external data
    }




    @GetMapping("/web/scraping")
    public String showHomePage(Model model) {
        // Fetch the table data using the scraping service
        List<String> tableData = scrapingService.getTableData();
        // Add the data to the model for rendering in the view
        model.addAttribute("tableData", tableData);
        return "web"; // The Thymeleaf template to render
    }

    @PostMapping("/web/scraping")
    public String scrapeAndDisplayData(Model model) {
        List<String> tableData = scrapingService.getTableData();
        model.addAttribute("tableData", tableData);
        return "web"; // Render the data again after post
    }
}
