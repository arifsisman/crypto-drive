package Drive;

import CryptoDrive.Constants;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.DriveScopes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * @author Mustafa Sisman
 */
public class DriveService {
    private static final String APPLICATION_NAME = "CryptoDrive";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final java.util.Collection<String> SCOPES = Collections.singleton(DriveScopes.DRIVE);
    private static GoogleClientSecrets clientSecrets;
    public static com.google.api.services.drive.Drive service;
    public static String folderId;
    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        try{
        InputStream in = DriveService.class.getResourceAsStream(Constants.CREDENTIALS_FILE_PATH);
        //InputStream in = new FileInputStream("C:\\Users\\musta\\IdeaProjects\\CryptoDrive\\src\\main\\resources\\credentials.json");
        clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));}
        catch (Exception e){
            e.printStackTrace();
        }

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(Constants.RESOURCES_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        }

    public static com.google.api.services.drive.Drive initialize() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        service = new com.google.api.services.drive.Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        hasFolder("CryptoDrive");
        return service;
    }

    public static void hasFolder(String folderName){
        folderId = Search.searchFolder(folderName);
        if(folderId == null){
            //if there  is no folder named "CryptoDrive" then create new folder.
            Folder.createMainFolder();
            folderId = Search.searchFolder(folderName);
        }
    }

}