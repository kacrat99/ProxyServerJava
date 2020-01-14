import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import javax.imageio.ImageIO;


public class Polaczenia implements Runnable {

    String words[];
    String path;
    /**
     * Gniazdo połączone z klientem przekazane przez serwer proxy
     */
    Socket nmnmnmnmn;

    /**
     * Odczytywanie danych klienta wysyłanych do serwera proxy
     */
    BufferedReader bvbvbvc;

    /**
     * Wysyłanie danych od servera proxy do klienta
     */
    BufferedWriter zxzxzxzx;


    /**
     * Wątek używany do przesyłania danych odczytanych z klienta do serwera przy użyciu protokołu HTTPS
     * Odniesienie do tego jest wymagane, aby można było je zamknąć po zakończeniu.
     */
    private Thread asdsaddddd;


    /**
     * Tworzy obiekt klasy Polaczenia zdolny do obsługiwania żądań HTTP(S) GET
     * @param clientSocket
     */
    public Polaczenia(Socket clientSocket, String path, String[] words){
        this.nmnmnmnmn = clientSocket;
        this.path = path;
        this.words = words;
        try{
            this.nmnmnmnmn.setSoTimeout(2000);
            bvbvbvc = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            zxzxzxzx = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Odczytuje i analizuje łańcuch żądania i wywołuje odpowiednią metodę w oparciu o typ żądania.
     *
     */
    @Override
    public void run() {


        String xxxxxxz;
        try{
            xxxxxxz = bvbvbvc.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading request from client");
            return;
        }



        System.out.println("Reuest Received " + xxxxxxz);

        String request = xxxxxxz.substring(0,xxxxxxz.indexOf(' '));


        String eeeereURL = xxxxxxz.substring(xxxxxxz.indexOf(' ')+1);


        eeeereURL = eeeereURL.substring(0, eeeereURL.indexOf(' '));


        if(!eeeereURL.substring(0,4).equals("http")){
            String temp = "http://";
            eeeereURL = temp + eeeereURL;
        }







        if(request.equals("CONNECT")){
            System.out.println("HTTPS Request for : " + eeeereURL + "\n");
            zzzzHTTPSrequestsdfsfdsf(eeeereURL);
        }

        else{

            File file;
            if((file = ProxyServer.getCache(eeeereURL)) != null){
                System.out.println("Cached Copy found for : " + eeeereURL + "\n");
                ccccccCacheToClientsfdsf(file);
            } else {

                System.out.println("HTTP GET for : " + eeeereURL + "\n");
                fdafdsfNONcacheTOclientsfdsfa(eeeereURL);
            }
        }
    }


    /**
     * Wysyła określony plik w cachu do klienta
     * @param cachedFile
     */
    private void ccccccCacheToClientsfdsf(File cachedFile){



        try{

            String czczxczc = cachedFile.getName().substring(cachedFile.getName().lastIndexOf('.'));

            String response;
            if((czczxczc.contains(".png")) || czczxczc.contains(".jpg") ||
                    czczxczc.contains(".jpeg") || czczxczc.contains(".gif")){

                BufferedImage image = ImageIO.read(cachedFile);

                if(image == null ){
                    System.out.println("Image " + cachedFile.getName() + " was null");
                    response = "HTTP/1.0 404 NOT FOUND \n" +
                            "Proxy-agent: ProxyServer/1.0\n" +
                            "\r\n";
                    zxzxzxzx.write(response);
                    zxzxzxzx.flush();
                } else {
                    response = "HTTP/1.0 200 OK\n" +
                            "Proxy-agent: ProxyServer/1.0\n" +
                            "\r\n";
                    zxzxzxzx.write(response);
                    zxzxzxzx.flush();
                    ImageIO.write(image, czczxczc.substring(1), nmnmnmnmn.getOutputStream());
                }
            }


            else {
                BufferedReader cachedFileBufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(cachedFile)));

                response = "HTTP/1.0 200 OK\n" +
                        "Proxy-agent: ProxyServer/1.0\n" +
                        "\r\n";
                zxzxzxzx.write(response);
                zxzxzxzx.flush();



                String line;
                while((line = cachedFileBufferedReader.readLine()) != null){

                    zxzxzxzx.write(line);

                }
                zxzxzxzx.flush();


                if(cachedFileBufferedReader != null){
                    cachedFileBufferedReader.close();
                }
            }



            if(zxzxzxzx != null){
                zxzxzxzx.close();
            }

        } catch (IOException e) {
            System.out.println("Error Sending Cached file to client");
            e.printStackTrace();
        }
    }


    /**
     * Wysyła zawartość pliku określonego przez url do klienta
     * @param vvvvvbcccc URL
     */
    private void fdafdsfNONcacheTOclientsfdsfa(String vvvvvbcccc){

        try{


            int fileExtensionIndex = vvvvvbcccc.lastIndexOf(".");
            String ccczxczfdfff;


            ccczxczfdfff = vvvvvbcccc.substring(fileExtensionIndex, vvvvvbcccc.length());


            String khjkhk = vvvvvbcccc.substring(0,fileExtensionIndex);



            khjkhk = khjkhk.substring(khjkhk.indexOf('.')+1);


            khjkhk = khjkhk.replace("/", "__");
            khjkhk = khjkhk.replace('.','_');


            if(ccczxczfdfff.contains("/")){
                ccczxczfdfff = ccczxczfdfff.replace("/", "__");
                ccczxczfdfff = ccczxczfdfff.replace('.','_');
                ccczxczfdfff += ".html";
            }

            khjkhk = khjkhk + ccczxczfdfff;




            boolean caching = true;
            File bbbvbvbvbb = null;
            BufferedWriter xcccxcccc = null;

            try{

                bbbvbvbvbb = new File("C://cached//" + khjkhk);

                if(!bbbvbvbvbb.exists()){
                    bbbvbvbvbb.createNewFile();
                }


                xcccxcccc = new BufferedWriter(new FileWriter(bbbvbvbvbb));
            }
            catch (IOException e){
                System.out.println("Couldn't cache: " + khjkhk);
                caching = false;
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("NPE opening file");
            }






            if((ccczxczfdfff.contains(".png")) || ccczxczfdfff.contains(".jpg") ||
                    ccczxczfdfff.contains(".jpeg") || ccczxczfdfff.contains(".gif")){

                URL remoteURL = new URL(vvvvvbcccc);
                BufferedImage image = ImageIO.read(remoteURL);

                if(image != null) {

                    ImageIO.write(image, ccczxczfdfff.substring(1), bbbvbvbvbb);


                    String line = "HTTP/1.0 200 OK\n" +
                            "Proxy-agent: ProxyServer/1.0\n" +
                            "\r\n";
                    zxzxzxzx.write(line);
                    zxzxzxzx.flush();


                    ImageIO.write(image, ccczxczfdfff.substring(1), nmnmnmnmn.getOutputStream());


                } else {
                    System.out.println("Sending 404 to client as image wasn't received from server"
                            + khjkhk);
                    String error = "HTTP/1.0 404 NOT FOUND\n" +
                            "Proxy-agent: ProxyServer/1.0\n" +
                            "\r\n";
                    zxzxzxzx.write(error);
                    zxzxzxzx.flush();
                    return;
                }
            }


            else {


                URL hhhh = new URL(vvvvvbcccc);

                HttpURLConnection nmbbbnn = (HttpURLConnection)hhhh.openConnection();
                nmbbbnn.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                nmbbbnn.setRequestProperty("Content-Language", "en-US");
                nmbbbnn.setUseCaches(false);
                nmbbbnn.setDoOutput(true);


                BufferedReader cxzcxzaaaaaa = new BufferedReader(new InputStreamReader(nmbbbnn.getInputStream()));



                String line = "HTTP/1.0 200 OK\n" +
                        "Proxy-agent: ProxyServer/1.0\n" +
                        "\r\n";
                zxzxzxzx.write(line);



                while((line = cxzcxzaaaaaa.readLine()) != null){



                    if(line.toLowerCase().contains("bomba")){
                        line = line.toLowerCase().replace("bomba","!!!!!!!!!");
                        zxzxzxzx.write(line);
                    }else if(line.toLowerCase().contains("muchomor")){
                        line = line.toLowerCase().replace("muchomor","!!!!!!!!!");
                        zxzxzxzx.write(line);
                    }else if(line.toLowerCase().contains("atomowa")){
                        line = line.toLowerCase().replace("atomowa","!!!!!!!!!");
                        zxzxzxzx.write(line);
                    }else if(line.toLowerCase().contains("Twarda woda")){
                        line = line.toLowerCase().replace("Twarda woda","!!!!!!!!!");
                        zxzxzxzx.write(line);
                    }else
                    {

                        zxzxzxzx.write(line);
                    }


                    if(caching){
                        xcccxcccc.write(line);

                    }
                }


                zxzxzxzx.flush();


                if(cxzcxzaaaaaa != null){
                    cxzcxzaaaaaa.close();
                }
            }


            if(caching){

                xcccxcccc.flush();
                ProxyServer.addCache(vvvvvbcccc, bbbvbvbvbb);
            }


            if(xcccxcccc != null){
                xcccxcccc.close();
            }

            if(zxzxzxzx != null){
                zxzxzxzx.close();
            }
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Obsługuje żądania HTTPS pomiędzy klientem a zdalnym serwerem
     * @param mmmjjjjhhhhhf
     */
    private void zzzzHTTPSrequestsdfsfdsf(String mmmjjjjhhhhhf){
        // Extract the URL and port of remote
        String url = mmmjjjjhhhhhf.substring(7);
        String pieces[] = url.split(":");
        url = pieces[0];
        int port  = Integer.valueOf(pieces[1]);

        try{

            for(int i=0;i<5;i++){
                bvbvbvc.readLine();
            }


            InetAddress address = InetAddress.getByName(url);


            Socket proxyToServerSocket = new Socket(address, port);
            proxyToServerSocket.setSoTimeout(5000);


            String line = "HTTP/1.0 200 Connection established\r\n" +
                    "Proxy-Agent: ProxyServer/1.0\r\n" +
                    "\r\n";
            zxzxzxzx.write(line);
            zxzxzxzx.flush();







            BufferedWriter proxyToServerBW = new BufferedWriter(new OutputStreamWriter(proxyToServerSocket.getOutputStream()));


            BufferedReader proxyToServerBR = new BufferedReader(new InputStreamReader(proxyToServerSocket.getInputStream()));




            nbvnbvnvbcc clientToServerHttps =
                    new nbvnbvnvbcc(nmnmnmnmn.getInputStream(), proxyToServerSocket.getOutputStream());

            asdsaddddd = new Thread(clientToServerHttps);
            asdsaddddd.start();



            try {
                byte[] buffer = new byte[4096];
                int read;
                do {
                    read = proxyToServerSocket.getInputStream().read(buffer);
                    if (read > 0) {
                        nmnmnmnmn.getOutputStream().write(buffer, 0, read);
                        if (proxyToServerSocket.getInputStream().available() < 1) {
                            nmnmnmnmn.getOutputStream().flush();
                        }
                    }
                } while (read >= 0);
            }
            catch (SocketTimeoutException e) {

            }
            catch (IOException e) {
                e.printStackTrace();
            }



            if(proxyToServerSocket != null){
                proxyToServerSocket.close();
            }

            if(proxyToServerBR != null){
                proxyToServerBR.close();
            }

            if(proxyToServerBW != null){
                proxyToServerBW.close();
            }

            if(zxzxzxzx != null){
                zxzxzxzx.close();
            }


        } catch (SocketTimeoutException e) {
            String line = "HTTP/1.0 504 Timeout Occured after 10s\n" +
                    "User-Agent: ProxyServer/1.0\n" +
                    "\r\n";
            try{
                zxzxzxzx.write(line);
                zxzxzxzx.flush();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        catch (Exception e){
            System.out.println("Error on HTTPS : " + mmmjjjjhhhhhf );
            e.printStackTrace();
        }
    }




    /**
     * Słuchanie danych od klienta i przesyłanie ich na serwer.
     * Odbywa się to w oddzielnym wątku, co należy zrobić
     * asynchronicznie do odczytu danych z serwera i nadawania
     * tych dane do klienta.
     */
    class nbvnbvnvbcc implements Runnable{

        InputStream cccczzasdsa;
        OutputStream cccccccczzzzzzzda;

        /**
         * Tworzy obiekt do odsłuchiwania klienta i przekazuje te dane do serwera
         *
         */
        public nbvnbvnvbcc(InputStream cccczzasdsa, OutputStream cccccccczzzzzzzda) {
            this.cccczzasdsa = cccczzasdsa;
            this.cccccccczzzzzzzda = cccccccczzzzzzzda;
        }

        @Override
        public void run(){
            try {

                byte[] buffer = new byte[4096];
                int read;
                do {
                    read = cccczzasdsa.read(buffer);
                    if (read > 0) {
                        cccccccczzzzzzzda.write(buffer, 0, read);
                        if (cccczzasdsa.available() < 1) {
                            cccccccczzzzzzzda.flush();
                        }
                    }
                } while (read >= 0);
            }
            catch (SocketTimeoutException ste) {

            }
            catch (IOException e) {
                System.out.println("Proxy to client HTTPS read timed out");
                e.printStackTrace();
            }
        }
    }




}