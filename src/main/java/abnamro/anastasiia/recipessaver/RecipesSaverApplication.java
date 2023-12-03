package abnamro.anastasiia.recipessaver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class RecipesSaverApplication {
  public static void main(String[] args) {
    SpringApplication.run(RecipesSaverApplication.class, args);
  }
}
