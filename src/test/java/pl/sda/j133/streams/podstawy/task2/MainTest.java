package pl.sda.j133.streams.podstawy.task2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.List.*;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    List<Programmer> programmers;

    @BeforeEach
    public void setUp() {

        Person person1 = new Person("Jacek", "Kowalski", 18, true);
        Person person2 = new Person("Jacek", "Górski", 15, true);
        Person person3 = new Person("Andżelika", "Dżoli", 25, false);
        Person person4 = new Person("Wanda", "Ibanda", 12, false);
        Person person5 = new Person("Marek", "Marecki", 17, true);
        Person person6 = new Person("Johny", "Brawo", 25, true);
        Person person7 = new Person("Stary", "Pan", 80, true);
        Person person8 = new Person("Newbie", "Noob", 12, true);
        Person person9 = new Person("Newbies", "Sister", 19, false);
        

        List<String> language1 = of("Java,Cobol,Cpp,Lisp,C#,Kotlin".split(","));
        List<String> language2 = of("Java,Lisp,C#".split(","));
        List<String> language3 = of("Java,Cpp,Lisp,C#".split(","));
        List<String> language4 = of("Java,Cobol,Lisp,C#".split(","));
        List<String> language5 = of("Java,Cobol".split(","));
        List<String> language6 = of("Java,Cobol,Cpp".split(","));
        List<String> language7 = of("Java,Cobol,Cpp,Lisp".split(","));
        List<String> language8 = Collections.emptyList();
        List<String> language9 = of("Java");

        Programmer programmer1 = new Programmer(person1, language1);
        Programmer programmer2 = new Programmer(person2, language2);
        Programmer programmer3 = new Programmer(person3, language3);
        Programmer programmer4 = new Programmer(person4, language4);
        Programmer programmer5 = new Programmer(person5, language5);
        Programmer programmer6 = new Programmer(person6, language6);
        Programmer programmer7 = new Programmer(person7, language7);
        Programmer programmer8 = new Programmer(person8, language8);
        Programmer programmer9 = new Programmer(person9, language9);

        programmers = new ArrayList<>(
                of(
                        programmer1,
                        programmer2,
                        programmer3,
                        programmer4,
                        programmer5,
                        programmer6,
                        programmer7,
                        programmer8,
                        programmer9
                ));
    }

    //        a. uzyskaj listę programistów, którzy są mężczyznami
    @Test
    public void shouldCalculateMales() {
        assertEquals(6, getActualMaleProgrammers().size());
        removeOneMaleProgrammer();
        assertEquals(5, getActualMaleProgrammers().size());
    }

    private List<Programmer> getActualMaleProgrammers() {
        return programmers.stream()
                .filter(programmer -> programmer.getPerson().getMale())
                .toList();
    }

    private void removeOneMaleProgrammer() {
        for (Programmer programmer : programmers) {
            if (programmer.getPerson().isMale()) {
                programmers.remove(programmer);
                break;
            }
        }
    }

    //        b. uzyskaj listę niepełnoletnich programistów (obydwóch płci), którzy piszą w Cobolu
    @Test
    public void shouldCalculateCobolUnderageProgrammers() {
        Predicate<Programmer> underage = p -> p.getPerson().getAge() < 18;
        Predicate<Programmer> cobol = p -> p.getLanguages().stream()
                .anyMatch(language -> language.equals("Cobol"));
        List<Programmer> programmersUnderageCobol = programmers.stream()
                .filter(underage.and(cobol))
                .toList();
        assertEquals(2, programmersUnderageCobol.size());
    }

    //        c. uzyskaj listę programistów, którzy znają więcej, niż jeden język programowania
    @Test
    public void shouldCalculatePolyglotProgrammers() {
        List<Programmer> programmersPolyglot = programmers.stream()
                .filter(p -> p.getLanguages().size() > 1)
                .toList();

        assertEquals(7, programmersPolyglot.size());
    }

//        d. uzyskaj listę programistek, które piszą w Javie i Cpp
    @Test
    public void shouldCalculateFemaleJavaAndCppProgrammers() {
        Predicate<Programmer> female = p -> !p.getPerson().isMale();
        Predicate<Programmer> javaAndCpp = getLanguage("Cpp").and(getLanguage("Java"));

        List<Programmer> programmersUnderageCobol = programmers.stream()
                .filter(javaAndCpp.and(female))
                .toList();
        assertEquals(1, programmersUnderageCobol.size());
}

    private Predicate<Programmer> getLanguage(String language) {
        return p -> p.getLanguages().stream()
                .anyMatch(lang -> lang.equals(language));
    }

//        e. uzyskaj listę męskich imion
    @Test
    public void shouldCalculateMalesName() {
    assertEquals(5, getActualMaleNameProgrammers().size());
}

    private List<String> getActualMaleNameProgrammers() {
        return programmers.stream()
            .filter(programmer -> programmer.getPerson().isMale())
            .map(programmer -> programmer.getPerson().getFirstName())
                .distinct()
                .toList();
}

//        f. uzyskaj set wszystkich języków opanowanych przez programistów
    @Test
    public void shouldCalculateLanguage() {
        Set<String> language = programmers.stream()
            .flatMap(programmer -> programmer.getLanguages().stream()).collect(Collectors.toSet());
        assertEquals(6, language.size());
    }

//        g. uzyskaj listę nazwisk programistów, którzy znają więcej, niż dwa języki
    @Test
    public void shouldCalculate2AndMoreLanguageProgrammers() {
    List<String> programmers2AndMoreLanguage = programmers.stream()
            .filter(p -> p.getLanguages().size() > 2)
            .map(programmer -> programmer.getPerson().getLastName())
            .toList();

    assertEquals(6, programmers2AndMoreLanguage.size());
}

//        h. * sprawdź, czy istnieje chociaż jedna osoba, która nie zna żadnego języka
    @Test
    public void shouldCalculateNonLanguageKnowProgrammers() {
    List<Programmer> programmersNonLanguageKnow = programmers.stream()
            .filter(p -> p.getLanguages().size() == 0)
            .toList();

    assertEquals(1, programmersNonLanguageKnow.size());
}

    @Test
    public void isAnyNonLanguageProgrammers() {
        boolean isAnyNonLanguageProgrammers = programmers.stream()
                .anyMatch(p -> p.getLanguages().size() == 0);

        assertTrue(isAnyNonLanguageProgrammers);
    }

//        i. * uzyskaj ilość wszystkich języków opanowanych przez programistki
    @Test
    public void shouldCalculateLanguageFemaleProgrammers() {
    Predicate<Programmer> female = p -> !p.getPerson().isMale();

    List<String> languageF= programmers.stream().filter(female)
                .flatMap(programmer -> programmer.getLanguages().stream()).distinct().toList();

    long countLanguageF = programmers.stream().filter(female)
                .flatMap(programmer -> programmer.getLanguages().stream()).distinct().count();

    assertEquals(5, languageF.size());
    assertEquals(5, countLanguageF);
    }

//   j. **Używając streamów znajdź długość najdłuższej linii w wybranym przez ciebie pliku.

}