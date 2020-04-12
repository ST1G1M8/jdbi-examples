package user;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()){
            UserDao dao = handle.attach(UserDao.class);
            dao.createTable();
            User user = User.builder()
                    .username("007")
                    .password("secret")
                    .name("James Bond")
                    .email("bond007@agent.com")
                    .gender(User.Gender.MALE)
                    .dob(LocalDate.parse("1920-11-11"))
                    .enabled(true)
                    .build();

            User user2 = User.builder()
                    .username("kingarthur")
                    .password("excalibur")
                    .name("King Arthur")
                    .email("kingarthur@sword.io")
                    .gender(User.Gender.MALE)
                    .dob(LocalDate.parse("0577-10-18"))
                    .enabled(true)
                    .build();

            dao.insert(user);
            dao.insert(user2);
            System.out.println("All users: ");
            dao.list().stream().forEach(System.out::println);
            System.out.println("findById: ");
            System.out.println(dao.findById(1));
            System.out.println("findByUsername: ");
            System.out.println(dao.findByUsername("kingarthur"));
            dao.delete(user);
            System.out.println("After a delete, the rest of the users: ");
            dao.list().stream().forEach(System.out::println);

        }
    }
}
