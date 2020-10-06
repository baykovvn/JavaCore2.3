import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        final int BASE_SIZE = 15_000_000;
        int procPrev = 0;
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John", "Leon", "Paul", "Holmes");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown", "Strong", "Swan", "Sherlock");
        ArrayList<Person> persons = new ArrayList<>();
        for (int i = 0; i < BASE_SIZE; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
            int proc = (i * 100 / BASE_SIZE);
            if (procPrev != proc) {
               System.out.println("Идет генерация базы данных - " + proc + " % завершено");
            }
            procPrev = proc;
        }
        System.out.println(" --- Генерация данных завершена --- ");
        System.out.println("------ Рассчет с применением Stream ------");
        calculate(persons);
        System.out.println("------ Рассчет с применением Parallel Stream ------");
        calculateParallels(persons);
    }

    public static void calculate(ArrayList<Person> persons) {
        long startTime = System.nanoTime();
        Stream<Person> stream = persons.stream();
        int a = (int) stream
                .filter((x) -> x.getAge() < 18)
                .count();
        System.out.println("Количество несовершеннолетних: " + a);
        Stream<Person> stream1 = persons.stream();
        List<String> string = stream1
                .filter((x) -> x.getAge() > 18 && x.getAge() < 27 && x.getSex() == Sex.MAN)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println("Количество призывников: " + string.size());
        Stream<Person> stream2 = persons.stream();
        List<Person> listPerson = stream2
                .filter((x) -> (x.getAge() > 18 && x.getAge() < 65 && x.getSex() == Sex.MAN) || (x.getAge() > 18 && x.getAge() < 60 && x.getSex() == Sex.WOMEN))
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        System.out.println("Количество трудоспособных граждан: " + listPerson.size());
        // расчет времени работы программы
        long stopTime = System.nanoTime();
        double processTime = (double) (stopTime - startTime) / 1_000_000_000.0;
        System.out.println("Process time: " + processTime + " s");
    }

    public static void calculateParallels(ArrayList<Person> persons) {
        long startTime = System.nanoTime();
        Stream<Person> stream = persons.parallelStream();
        int a = (int) stream
                .filter((x) -> x.getAge() < 18)
                .count();
        System.out.println("Количество несовершеннолетних: " + a);
        Stream<Person> stream1 = persons.parallelStream();
        List<String> string = stream1
                .filter((x) -> x.getAge() > 18 && x.getAge() < 27 && x.getSex() == Sex.MAN)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println("Количество призывников: " + string.size());
        Stream<Person> stream2 = persons.parallelStream();
        List<Person> listPerson = stream2
                .filter((x) -> (x.getAge() > 18 && x.getAge() < 65 && x.getSex() == Sex.MAN) || (x.getAge() > 18 && x.getAge() < 60 && x.getSex() == Sex.WOMEN))
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        System.out.println("Количество трудоспособных граждан: " + listPerson.size());
        // расчет времени работы программы
        long stopTime = System.nanoTime();
        double processTime = (double) (stopTime - startTime) / 1_000_000_000.0;
        System.out.println("Process time: " + processTime + " s");
    }
}




