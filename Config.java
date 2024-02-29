public class Config {
    /*
     * Enter your student id and number, and the connection within the Sandcastle
     * should be taken care of.
     */
    /*-----------------------------------------------------------------*/
    public static final String STUDENT_ID = "kv20kh";
    public static final String STUDENT_NUMBER = "7129480";
    /*-----------------------------------------------------------------*/

    public static final String DB_DRIVER = "org.postgresql.Driver";
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/" + STUDENT_ID;
    public static final String DB_USERNAME = STUDENT_ID;
    public static final String DB_PASSWORD = STUDENT_NUMBER;
}
