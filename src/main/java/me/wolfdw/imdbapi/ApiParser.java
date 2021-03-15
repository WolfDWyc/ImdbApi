package me.wolfdw.imdbapi;

import me.wolfdw.imdbapi.titles.Episode;
import me.wolfdw.imdbapi.titles.Title;
import me.wolfdw.imdbapi.web.RestIMDb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import java.io.*;
import java.net.URL;
import java.util.zip.GZIPInputStream;

@SpringBootApplication
@ComponentScan({"me.wolfdw.imdbapi.web"})
@EntityScan("me.wolfdw.imdbapi.web")
public class ApiParser {

    public static void main(String[] args) {
        ApiParser apiParser = new ApiParser();
        IMDb imdb = null;
        try {
            imdb = apiParser.initializeApi();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RestIMDb.imdb = imdb;

        SpringApplication.run(ApiParser.class, args);

    }


    public IMDb initializeApi() throws IOException {
        System.out.println("Initializing API...");
        IMDb imdb = new IMDb();

        long l1 = System.currentTimeMillis();
        buildTitleBasics(imdb);
        buildTitleEpisodes(imdb);
        buildTitleRatings(imdb);


        System.out.println("Finished initializing API.");
        System.out.println("Took "+ (System.currentTimeMillis()-l1)/1000 +" seconds.");

        return imdb;
    }
    


    public IMDb buildTitleBasics(IMDb imdb) throws IOException {
        ByteArrayInputStream titleBasics = unGunzip(downloadUrl("https://datasets.imdbws.com/title.basics.tsv.gz"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(titleBasics));
        reader.readLine();
        String line;
        String lastline = "";

        String[] titleInfo;
        while ((line = reader.readLine()) != null) {
            if (!line.equals(lastline)) {
                titleInfo = line.split("\t");
                imdb.addTitle(Title.build(titleInfo),true);
            }
            lastline = line;
        }
        titleBasics.close();
        reader.close();
        return imdb;
    }

    public IMDb buildTitleEpisodes(IMDb imdb) throws IOException {
        ByteArrayInputStream titleEpisode = unGunzip(downloadUrl("https://datasets.imdbws.com/title.episode.tsv.gz"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(titleEpisode));

        reader.readLine();
        String line;
        String lastline = "";

        String[] episodeInfo;
        while ((line = reader.readLine()) != null) {
            if (!line.equals(lastline)) {
                episodeInfo = line.split("\t");
                if (line.contains("tt10145710"))
                    System.out.println(line);
                Episode.buildEpisode(episodeInfo, imdb);
            }
            lastline = line;
        }
        titleEpisode.close();
        reader.close();

        return imdb;
    }

    public IMDb buildTitleRatings(IMDb imdb) throws IOException {
        ByteArrayInputStream titleRatings = unGunzip(downloadUrl("https://datasets.imdbws.com/title.ratings.tsv.gz"));

        BufferedReader reader = new BufferedReader(new InputStreamReader(titleRatings));
        reader.readLine();
        String line;
        String lastline = "";

        String[] titleInfo;
        while ((line = reader.readLine()) != null) {
            if (!line.equals(lastline)) {
                titleInfo = line.split("\t");
                Title t = imdb.getTitle(titleInfo[0]);
                if (t != null)
                    t.addRating(Float.parseFloat(titleInfo[1]), Integer.parseInt(titleInfo[2]));
            }
            lastline = line;
        }
        titleRatings.close();
        reader.close();
        return imdb;
    }




    private ByteArrayOutputStream downloadUrl(String urlString) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] chunk = new byte[4096];
        int bytesRead;
        try {
            URL toDownload = new URL(urlString);
            InputStream stream = toDownload.openStream();

            while ((bytesRead = stream.read(chunk)) > 0) {
                outputStream.write(chunk, 0, bytesRead);
            }
            stream.close();
            System.out.println(urlString+" was downloaded successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream;
    }

    public ByteArrayInputStream unGunzip(ByteArrayOutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytes_read;

        ByteArrayInputStream fileIn = new ByteArrayInputStream(outputStream.toByteArray());
        GZIPInputStream gZIPInputStream = new GZIPInputStream(fileIn);
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();

        while ((bytes_read = gZIPInputStream.read(buffer)) > 0) {
            byteOutputStream.write(buffer, 0, bytes_read);
        }
        gZIPInputStream.close();
        outputStream.close();
        byteOutputStream.close();
        fileIn.close();

        System.out.println("unzipped successfully!");
        return new ByteArrayInputStream(byteOutputStream.toByteArray());
    }


}

//    public File downloadFileFromUrl(String urlString, String fileLocation) throws IOException {
//        String tmpDir = System.getProperty("java.io.tmpdir");
//        File tmpFileLocation = new File(tmpDir, fileLocation);
//        if (tmpFileLocation.exists())
//            return tmpFileLocation;
//
//        URL url = new URL(urlString);
//        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
//        FileOutputStream fileOutputStream = new FileOutputStream(tmpFileLocation);
//        FileChannel writeChannel = fileOutputStream.getChannel();
//        writeChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
//        System.out.println("The file was downloaded successfully! ("+tmpFileLocation.getAbsolutePath()+")");
//        return tmpFileLocation;
//    }
//
//
//
//    public File unGunzipFile(File compressedFile, String decompressedFile) throws IOException {
//        String tmpDir = System.getProperty("java.io.tmpdir");
//        File tmpFileLocation = new File(tmpDir, decompressedFile);
//
//        byte[] buffer = new byte[1024];
//
//        FileInputStream fileIn = new FileInputStream(compressedFile);
//        GZIPInputStream gZIPInputStream = new GZIPInputStream(fileIn);
//        FileOutputStream fileOutputStream = new FileOutputStream(tmpFileLocation);
//
//        int bytes_read;
//
//        while ((bytes_read = gZIPInputStream.read(buffer)) > 0) {
//
//            fileOutputStream.write(buffer, 0, bytes_read);
//        }
//
//        gZIPInputStream.close();
//        fileOutputStream.close();
//
//        System.out.println("The file was decompressed successfully! ("+tmpFileLocation.getAbsolutePath()+")");
//
//        return tmpFileLocation;
//
//    }




//        Series series = (Series) imdb.searchTitle("Breaking Bad");
//        System.out.println(series.getName()+ " ("+series.getStartYear()+" - "+series.getEndYear()+")");
//        for (Episode e : series.getSeason(1)) {
//            System.out.println(e.getName() + " (Season "+e.getSeasonNumber()+
//                    ", Episode "+e.getEpisodeNumber()+") - Rated "+e.getAverageRating()+" by "+e.getNumVotes()+" people.");
//        }
//
//        Series sherlock = (Series) imdb.getTitle("tt1475582");
//        Episode e = sherlock.getAllEpisodes().stream().max((x, y) -> (int) ((x.getAverageRating() - y.getAverageRating())*10)).get();
//        System.out.println("The highest rated episode in "+sherlock.getName()+"" +
//                " is \""+e.getName()+"\" from season "+e.getSeasonNumber()+" rated "+e.getAverageRating());



