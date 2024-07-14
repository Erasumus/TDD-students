package org.example.students;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Set;

public class ExaminationTest {

    private Examination examination;
    private Examination cachedExamination;

    @BeforeEach
    void setUp() {
        examination = new ExaminationImpl();
        cachedExamination = new CachedExamination((ExaminationImpl) examination, 3);
    }

    @Test
    void testAddAndGetScore() {
        Score score = new Score("John", "Math", 4);
        examination.addScore(score);

        Score retrievedScore = cachedExamination.getScore("John", "Math");
        Assertions.assertEquals(score, retrievedScore);
    }

    @Test
    void testGetAverageForSubject() {
        cachedExamination.addScore(new Score("John", "Math", 4));
        cachedExamination.addScore(new Score("Jane", "Math", 5));
        cachedExamination.addScore(new Score("Doe", "Math", 3));

        double average = cachedExamination.getAverageForSubject("Math");
        Assertions.assertEquals(4.0, average);

        // Test cache hit
        double cachedAverage = cachedExamination.getAverageForSubject("Math");
        Assertions.assertEquals(4.0, cachedAverage);
    }

    @Test
    void testMultipleSubmissionsStudentNames() {
        examination.addScore(new Score("John", "Math", 4));
        examination.addScore(new Score("John", "Physics", 3));
        examination.addScore(new Score("Jane", "Math", 5));
        examination.addScore(new Score("Doe", "Math", 3));

        Set<String> multipleSubmissions = cachedExamination.multipleSubmissionsStudentNames();
        Assertions.assertEquals(Set.of("John"), multipleSubmissions);
    }

    @Test
    void testLastFiveStudentsWithExcellentMarkOnAnySubject() {
        // Добавляем студентов с отличными оценками
        examination.addScore(new Score("John", "Math", 5));
        examination.addScore(new Score("Jane", "Physics", 5));
        examination.addScore(new Score("Jane", "Chemistry", 5));
        examination.addScore(new Score("Smith", "Biology", 5));
        examination.addScore(new Score("Doe", "Math", 5));
        examination.addScore(new Score("Alice", "Math", 5));
        examination.addScore(new Score("Bob", "Physics", 5));

        // Ожидаемый порядок имен
        List<String> expectedOrder = List.of("Jane", "Smith", "Doe", "Alice", "Bob");

        // Получаем результат от метода
        List<String> excellentStudents = cachedExamination.lastFiveStudentsWithExcellentMarkOnAnySubject();

        // Проверяем, что результат совпадает с ожидаемым порядком имен
        Assertions.assertEquals(expectedOrder, excellentStudents);
    }


    @Test
    void testGetAllScores() {
        Score score1 = new Score("John", "Math", 4);
        Score score2 = new Score("Jane", "Physics", 5);
        examination.addScore(score1);
        examination.addScore(score2);

        Assertions.assertEquals(2, cachedExamination.getAllScores().size());
    }
}
