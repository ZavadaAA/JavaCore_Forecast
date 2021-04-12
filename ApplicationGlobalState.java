package Lesson_7;

public class ApplicationGlobalState {
    private static ApplicationGlobalState INSTANCE;
    private String selectedCity=null;
    private final String API_KEY="bY9sFyXGO5fB2bwx9ueDnA6mmqcow3Hn";
    public static ApplicationGlobalState getInstance(){
        if(INSTANCE ==null) {
            INSTANCE =new ApplicationGlobalState();
        }
        return INSTANCE;
    }

    public String getAPIKey()
    {
        return API_KEY;
    }
}