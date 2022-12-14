import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        // Search for minor
        System.out.println("Number of minors:");
        long count = persons.stream().filter(w -> w.getAge() < 18).count();
        System.out.println(count);

        // Get a list of names of conscripts
        System.out.println("List of names of conscripts:");
        List<String> conscript = persons.stream()
                .filter(w -> w.getAge() < 27 && w.getAge() > 18)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        for (String s : conscript) {System.out.println(s);}

        // Get a list sorted by last name of potentially employable people with higher education in the sample
        System.out.println("Employable people with higher education:");
        List<Person> employable = persons.stream()
                .filter(w -> w.getEducation() == Education.HIGHER)
                .filter(w -> w.getAge() < 65 && w.getAge() > 18 && w.getSex() == Sex.MAN ||
                        w.getAge() < 60 && w.getAge() > 18 && w.getSex() == Sex.WOMAN)
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        for (Person s : employable) {
            System.out.print(s.toString());
        }
    }
}