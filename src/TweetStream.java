
import twitter4j.*;
import twitter4j.conf.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;

public class TweetStream {

    String consumerKey;
    String consumerSecret;
    String accessToken;
    String accessSecret;

    ConfigurationBuilder cb;
    StatusListener listener;
    TwitterStream twitterStream;

    double minlat, maxlat;
    double minlon, maxlon;

    static final double margin = 0.5;

    
    TweetStream() {

    }

    public void getValuesfromPropFile(String propFile) throws IOException {
        Properties prop = new Properties();
        prop.load(new FileReader(propFile));

        consumerKey = prop.getProperty("consumerKey");
        consumerSecret = prop.getProperty("consumerSecret");
        accessToken = prop.getProperty("accessToken");
        accessSecret = prop.getProperty("accessSecret");

        double lat = Double.parseDouble(prop.getProperty("geolocation.lat"));
        double lon = Double.parseDouble(prop.getProperty("geolocation.long"));
        minlat = lat - margin;
        maxlat = lat + margin;
        minlon = lon - margin;
        maxlon = lon + margin;
    }

    public void ConfigurationBuild() throws Exception {
        cb = new ConfigurationBuilder();

        cb.setDebugEnabled(true); //Enables deubg output. Effective only with the embedded logger. default value is false.
        cb.setJSONStoreEnabled(true);   // this line is to enable json format output

        cb.setOAuthConsumerKey(consumerKey);
        cb.setOAuthConsumerSecret(consumerSecret);
        cb.setOAuthAccessToken(accessToken);
        cb.setOAuthAccessTokenSecret(accessSecret);

    }

    void listenToStream() throws Exception {

        twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        twitterStream.addListener(listener);

        // sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
        twitterStream.sample();

//            // print output after geo filtering
//            FilterQuery fq = new FilterQuery();
//            double[][] locations = {{minlon, minlat}, {maxlon, maxlat}};
//            fq.locations(locations);
//
//            twitterStream.filter(fq);
    }

    public void StatusListenerConfigure() {
        listener = new StatusListener() {

            @Override
            public void onException(Exception arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStallWarning(StallWarning warning) {
            }

            @Override
            public void onScrubGeo(long arg0, long arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStatus(Status status) {
                
                printStatus(status);
            }

            @Override
            public void onTrackLimitationNotice(int arg0) {
                // TODO Auto-generated method stub
            }

        };
    }

    private void printStatus(Status status) {
//            try {
//            System.setOut(new PrintStream("output.txt"));
//            
//        } catch (FileNotFoundException ex) {
//            java.util.logging.Logger.getLogger(TweetStream.class.getName()).log(Level.SEVERE, null, ex);
//        }
            String output=TwitterObjectFactory.getRawJSON(status);
            System.out.println(output);
            
//
//            User user = status.getUser();
//            long userId = user.getId();
//
//            // gets Username
//            String username = status.getUser().getScreenName();
//            long tweetId = status.getId();
//            String content = status.getText();
//            content = content.replace("\n", " ");
//            content = content.replace("\r\n", " ");
//            System.out.println(tweetId + "\t" + userId + "\t" + content);
    }

    public static void main(String[] args) {

        try {
            TweetStream TS = new TweetStream();
            
            TS.getValuesfromPropFile("etc/init.properties");
            TS.ConfigurationBuild();
            TS.StatusListenerConfigure();
            TS.listenToStream();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
