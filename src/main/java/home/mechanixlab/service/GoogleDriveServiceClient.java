package home.mechanixlab.service;

public class GoogleDriveServiceClient {

//	private static File insertFile(Drive service, String title, String description,
//			String parentId, String mimeType, String filename) {
		// File's metadata.
//		File body = new File();
//		body.setTitle(title);
//		body.setDescription(description);
//		body.setMimeType(mimeType);
//
//		// Set the parent folder.
//		if (parentId != null && parentId.length() > 0) {
//			body.setParents(
//					Arrays.asList(new ParentReference().setId(parentId)));
//		}
//
//		// File's content.
//		java.io.File fileContent = new java.io.File(filename);
//		FileContent mediaContent = new FileContent(mimeType, fileContent);
//		try {
//			File file = service.files().insert(body, mediaContent).execute();
//
//			// Uncomment the following line to print the File ID.
//			// System.out.println("File ID: " + file.getId());
//
//			return file;
//		} catch (IOException e) {
//			System.out.println("An error occured: " + e);
//			return null;
//		}\
//		return null;
//	}
	/*
	*//** Application name. *//*
    private static final String APPLICATION_NAME =
        "Drive API Java Quickstart";

    *//** Directory to store user credentials for this application. *//*
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/drive-java-quickstart");

    *//** Global instance of the {@link FileDataStoreFactory}. *//*
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    *//** Global instance of the JSON factory. *//*
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    *//** Global instance of the HTTP transport. *//*
    private static HttpTransport HTTP_TRANSPORT;

    *//** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/drive-java-quickstart
     *//*
    private static final List<String> SCOPES =
        Arrays.asList(DriveScopes.DRIVE_METADATA_READONLY);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    *//**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     *//*
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
        		GoogleDriveServiceClient.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    *//**
     * Build and return an authorized Drive client service.
     * @return an authorized Drive client service
     * @throws IOException
     *//*
    public static Drive getDriveService() throws IOException {
        Credential credential = authorize();
        return new Drive.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

	
	public static void main (String[] args) throws IOException {
		 // Build a new authorized API client service.
        Drive service = getDriveService();

//        // Print the names and IDs for up to 10 files.
        FileList result = service.files().list()
             .setPageSize(10)
             .setFields("nextPageToken, files(id, name)")
             .execute();
        List<File> files = result.getFiles();
        if (files == null || files.size() == 0) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
                System.out.printf("%s (%s)\n", file.getName(), file.getId());
            }
        }
	}*/
}
