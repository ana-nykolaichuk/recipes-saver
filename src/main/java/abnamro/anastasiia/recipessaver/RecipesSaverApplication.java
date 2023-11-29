package abnamro.anastasiia.recipessaver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
@EnableMongoRepositories
public class RecipesSaverApplication {

  public static void main(String[] args) {
    SpringApplication.run(RecipesSaverApplication.class, args);
  }

}
