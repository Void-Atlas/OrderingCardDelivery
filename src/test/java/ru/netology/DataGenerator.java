package ru.netology;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private static final Random random = new Random();

    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        LocalDate date = LocalDate.now().plusDays(shift);


        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    private static final String[] VALID_CITIES = {
            "Москва", "Санкт-Петербург", "Екатеринбург", "Новосибирск", "Казань",
            "Нижний Новгород", "Челябинск", "Красноярск", "Самара", "Уфа",
            "Ростов-на-Дону", "Омск", "Краснодар", "Воронеж", "Пермь", "Волгоград"
    };

    public static String generateCity(Faker faker) {
        return VALID_CITIES[random.nextInt(VALID_CITIES.length)];
    }


    public static String generateName(Faker faker) {
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generatePhone(Faker faker) {
        // +7 XXX XXX-XX-XX
        return "+7" + faker.number().digits(10);
    }

    public static class Registration {
        private static Faker faker;

        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            faker = new Faker(new Locale(locale));
            return new UserInfo(
                    generateCity(faker),
                    generateName(faker),
                    generatePhone(faker)
            );
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }

    public static UserInfo getRandomUserWithLocale(String locale) {
        return Registration.generateUser(locale);
    }

    public static UserInfo getRandomUserRu() {
        return getRandomUserWithLocale("ru");
    }
}