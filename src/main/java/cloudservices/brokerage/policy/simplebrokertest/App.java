package cloudservices.brokerage.policy.simplebrokertest;

import cloudservices.brokerage.policy.simplebrokertest.utils.logging.LoggerSetup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App {

    private final static Logger LOGGER = Logger.getLogger(App.class
            .getName());

    public static void main(String[] args) {
        try {
            LoggerSetup.setup("log.txt", "log.html");
            List<String> seeds = new ArrayList<>();
//            String[] s = "http://www.serviceobjects.com/news-and-press/newsletters".split(",");
//            seeds.addAll(Arrays.asList(s));
//            seeds.add("http://www.concordia.ca/");
//            seeds.add("http://www.xmethods.com/ve2/index.po");
            seeds.add("http://www.arashkhodadadi.com/");
            App app = new App();
            long startTime = System.currentTimeMillis();
            LOGGER.log(Level.INFO, "Broker Test Start");
            LOGGER.log(Level.INFO, "Seeds= {0}", seeds);

            List<String> level1=app.websphinxCrawl(seeds);
            LOGGER.log(Level.INFO, "Level-1 {0} Results= {1}", new Object[]{level1.size(), level1});
            List<String> level2=app.crawler4jCrawl(level1);
            LOGGER.log(Level.INFO, "Level-2 {0} Results= {1}", new Object[]{level2.size(), level2});
            List<String> level3=app.crawler4jCrawl(level2);
            LOGGER.log(Level.INFO, "Level-3 {0} Results= {1}", new Object[]{level3.size(), level3});

            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            LOGGER.log(Level.INFO, "Broker Test End in {0}ms", totalTime);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private List<String> crawler4jCrawl(List<String> seeds) throws Exception {
        cloudservices.brokerage.policy.crawling_services.crawler4jservice.service.Crawler4JWS_Service service = new cloudservices.brokerage.policy.crawling_services.crawler4jservice.service.Crawler4JWS_Service();
        cloudservices.brokerage.policy.crawling_services.crawler4jservice.service.Crawler4JWS port = service.getCrawler4JWSPort();
        return port.crawl(seeds);
    }

    private List<String> crawler4jFilteredCrawl(List<String> seeds) throws Exception {
        cloudservices.brokerage.policy.crawling_services.crawler4jservice.service.Crawler4JWS_Service service = new cloudservices.brokerage.policy.crawling_services.crawler4jservice.service.Crawler4JWS_Service();
        cloudservices.brokerage.policy.crawling_services.crawler4jservice.service.Crawler4JWS port = service.getCrawler4JWSPort();
        return port.filteredCrawl(seeds);
    }

    private List<String> websphinxCrawl(List<String> seeds) throws Exception {
        cloudservices.brokerage.policy.crawling_services.websphinxservice.service.WebsphinxWS_Service service = new cloudservices.brokerage.policy.crawling_services.websphinxservice.service.WebsphinxWS_Service();
        cloudservices.brokerage.policy.crawling_services.websphinxservice.service.WebsphinxWS port = service.getWebsphinxWSPort();
        return port.crawl(seeds);
    }
}
