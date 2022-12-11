package pl.sda.j133.streams.podstawy.task1;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Person person1 = new Person("Jacek","Kowalski",18,true);
        Person person2 = new Person("Jacek","Górski",15,true);
        Person person3 = new Person("Andżelika","Dżoli",25,false);
        Person person4 = new Person("Wanda","Ibanda",12,false);
        Person person5 = new Person("Marek","Marecki",17,true);
        Person person6 = new Person("Johny","Brawo",25,true);
        Person person7 = new Person("Stary","Pan",80,true);
        Person person8 = new Person("Newbie","Noob",12,true);
        Person person9 = new Person("Newbies","Sister",19,false);
        List<Person> people = new ArrayList<>(Arrays.asList(person1, person2, person3,
                person4, person5, person6, person7, person8, person9));

//  a. uzyskaj listę mężczyzn
//        List<Person> wynik0 = people.stream()
//                .filter(Person::isMale)
//                .collect(Collectors.toList());

        List<Person> wynik1 = people.stream()
                .filter(Person::isMale).toList();

//        List<Person> wynik2 = people.stream()
//                .filter(new Predicate<Person>() {
//                    @Override
//                    public boolean test(Person person) {
//                        return person.isMale();
//                    }
//                })
//                .collect(Collectors.toList());

        // b. uzyskaj listę dorosłych kobiet (filter)
//        List<Person> wynikB = people.stream()
//                .filter(person -> !person.isMale())
//                .filter(person -> person.getAge() >= 18)
//                .collect(Collectors.toList());
        List<Person> wynikB = people.stream()
                .filter(person -> !person.isMale() && person.getAge() >= 18).toList();

        // c. uzyskaj Optional<Person> z dorosłym Jackiem findAny/findfirst
        Optional<Person> wynikC = people.stream()
                .filter(person -> person.getAge() >= 18 && person.getFirstName().equals("Jacek"))
                .findFirst();
        if (wynikC.isPresent()){
            Person personC = wynikC.get();
            System.out.println(personC);
        }

        // d. uzyskaj listę wszystkich nazwisk osób, które są w przedziale wiekowym: 15-19 (filter)
        List<String> wynikD = people.stream()
                .filter(person -> person.getAge() >= 15 && person.getAge() <=19).map(Person::getLastName)
                .toList();

        // e. * uzyskaj sumę wieku wszystkich osób (sum)
        int sum = people.stream().mapToInt(Person::getAge).sum();

        // f. * uzyskaj średnią wieku wszystkich mężczyzn (average)
        OptionalDouble average = people.stream().filter(Person::isMale).mapToInt(Person::getAge).average();

        // g. ** znajdź najstarszą osobę w liście (findFirst)
        Optional<Person> first = people.stream().min((personp2, personp1)
                -> Double.compare(personp1.getAge(), personp2.getAge()));

        Optional<Person> maxAgePerson = people.stream()
                .max(Comparator.comparingDouble(Person::getAge));
        if (maxAgePerson.isPresent()) {
            Person person = maxAgePerson.get();
            System.out.println("Wynik g:" + person);
        }

    }

}
