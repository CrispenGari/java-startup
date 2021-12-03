import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaworks.redis.*;

public class RedisApplication {
    public static void main(String[] args) {
        // redis connection string
        String connectionString = "redis://@localhost:6379";
        RedisClient client = new RedisClient(RedisURI.create(connectionString));

        ObjectMapper mapper = new ObjectMapper();

        try {
            RedisConnection<String, String> connection = client.connect();
            System.out.println(connection.get("client1"));
            connection.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
