

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;



public class ProxyServer implements Runnable{



    public static void main(String[] args) throws IOException {

        String setup = "";
        String filepath = args[0];
        File file = new File("setup.txt");    //creates a new file instance
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                setup += line;
            }
        }

        int port;
        String [] words = new String[4];
        String path = "";
        //System.out.println(setup);
        port= Integer.parseInt(setup.substring(11,15));
        //System.out.println(port);

        words[0] = setup.substring(21,26);
        words[1] = setup.substring(27,35);
        words[2] = setup.substring(36,43);
        words[3] = setup.substring(44,55);

        /*System.out.println(words[0]);
        System.out.println(words[1]);
        System.out.println(words[2]);
        System.out.println(words[3]);*/

        path = setup.substring(65,72);
        //System.out.println(path);


        // Stworzenie instancji klasy Proxy ktory zaczyna nasłuchiwać

        ProxyServer myProxyServer = new ProxyServer(port);
        myProxyServer.listen(path,words);
    }


    private ServerSocket serverSocket;

    /**
     * Semafor dla proxy
     */
    private volatile boolean running = true;


    /**
     * Struktura danych dla ciągłego wyszukiwania pozycji w pamięci podręcznej.
     * Klucz: URL żądanej strony/obrazka.
     * Wartość: Plik w pamięci masowej powiązany z tym kluczem.
     */
    static HashMap<String, File> vcvbxb;



    /**
     * ArrayLista wątków, które są obecnie uruchamiane i serwisowane.
     * Lista ta jest wymagana, aby dołączyć do wszystkich wątków po zamknięciu serwera
     */
    static ArrayList<Thread> czxczcxz;



    /**
     * Stworzenie Serwera Proxy
     * @param port numer portu na ktorym bedzie sluchal serwer
     */
    public ProxyServer(int port) {



        czxczcxz = new ArrayList<>();

        vcvbxb = new HashMap<>();






        new Thread(this).start();
        try{
            // Load in cached sites from file
            File dsadsa = new File("cachedSites.txt");
            if(!dsadsa.exists()){
                System.out.println("No cached sites found - creating new file");
                dsadsa.createNewFile();
            } else {
                FileInputStream fileInputStream = new FileInputStream(dsadsa);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                vcvbxb = (HashMap<String,File>)objectInputStream.readObject();
                fileInputStream.close();
                objectInputStream.close();
            }


        } catch (IOException e) {
            System.out.println("Error loading previously cached sites file");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found loading in preivously cached sites file");
            e.printStackTrace();
        }

        try {

            serverSocket = new ServerSocket(port);


            System.out.println("Listening on Port " + serverSocket.getLocalPort() + "..");
            running = true;
        }


        catch (SocketException se) {
            System.out.println("Socket Exception when connecting to client");
            se.printStackTrace();
        }
        catch (SocketTimeoutException ste) {
            System.out.println("Timeout occured while connecting to client");
        }
        catch (IOException io) {
            System.out.println("IO exception when connecting to client");
        }
    }


    /**
     * Przesłuchuje port i akceptuje nowe połączenia gniazd.
     * Tworzy nowy wątek do obsługi żądania i przekazuje mu połączenie z gniazdem i kontynuuje odsłuchiwanie.
     */
    public void listen(String path,String words[]){

        while(running){
            try {

                Socket socket = serverSocket.accept();


                Thread thread = new Thread(new Polaczenia(socket,path,words));


                czxczcxz.add(thread);

                thread.start();
            } catch (SocketException e) {

                System.out.println("Server closed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Zapisuje zbuforowane strony do pliku, aby można je było ponownie załadować w późniejszym czasie.
     * Łączy również wszystkie wątki klasy Polaczenia, które są obecnie serwisowane.
     */
    private void closeServer(){
        System.out.println("\nClosing Server..");
        running = false;
        try{
            FileOutputStream fileOutputStream = new FileOutputStream("cachedSites.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(vcvbxb);
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("Cached Sites written");


            try{
                // Close all servicing threads
                for(Thread thread : czxczcxz){
                    if(thread.isAlive()){
                        System.out.print("Waiting on "+  thread.getId()+" to close..");
                        thread.join();
                        System.out.println(" closed");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("Error saving cache sites");
            e.printStackTrace();
        }

        // Close Server Socket
        try{
            System.out.println("Terminating Connection");
            serverSocket.close();
        } catch (Exception e) {
            System.out.println("Exception closing proxy's server socket");
            e.printStackTrace();
        }

    }


    /**
     * Szuka pliku w cache'u
     * @param url
     */
    public static File getCache(String url){
        return vcvbxb.get(url);
    }


    /**
     * Dodaje nowe strony do cache'a
     * @param urlString
     * @param fileToCache
     */
    public static void addCache(String urlString, File fileToCache){
        vcvbxb.put(urlString, fileToCache);
    }







    /**
     * Tworzy interfejs zarządzania, który może dynamicznie aktualizować konfiguracje proxy
     *
     *  	close : closing server
     *
     */
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        String command;
        System.out.println("Enter \"close\" to shut down server");
        while(running){

            command = scanner.nextLine();



            if(command.equals("close")){
                running = false;
                closeServer();
            }



        }
        scanner.close();
    }

}
