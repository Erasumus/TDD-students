package org.example.students;

import java.util.*;
import java.util.stream.Collectors;

public class ExaminationImpl implements Examination {

    // Хранилище оценок, где ключом является комбинация имени студента и предмета
    private final Map<String, Score> scores = new LinkedHashMap<>();

    // Метод для добавления оценки студента
    @Override
    public void addScore(Score score) {
        String key = score.name() + "-" + score.subject();
        scores.put(key, score);
    }

    // Метод для получения оценки по имени студента и предмету
    @Override
    public Score getScore(String name, String subject) {
        String key = name + "-" + subject;
        return scores.get(key);
    }

    // Метод для вычисления средней оценки по предмету
    @Override
    public double getAverageForSubject(String subject) {
        // Фильтруем оценки по предмету, вычисляем среднее значение
        return scores.values().stream()
                .filter(score -> score.subject().equals(subject))
                .mapToInt(Score::score)
                .average()
                .orElse(0.0); // Если нет оценок по предмету, возвращаем 0
    }

    // Метод для получения имен студентов, у которых более одной сдачи
    @Override
    public Set<String> multipleSubmissionsStudentNames() {
        // Группируем оценки по имени студента и подсчитываем количество сдач
        Map<String, Long> studentSubmissionCounts = scores.values().stream()
                .collect(Collectors.groupingBy(Score::name, Collectors.counting()));

        // Фильтруем имена студентов, у которых количество сдач больше одной
        return studentSubmissionCounts.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    @Override
    public List<String> lastFiveStudentsWithExcellentMarkOnAnySubject() {
        // Фильтруем оценки, оставляем только те, которые равны 5 (отлично)
        List<String> excellentStudents = scores.values().stream()
                .filter(score -> score.score() == 5)
                .map(Score::name) // Получаем имена студентов
                .collect(Collectors.toList());

        // Берем последние пять студентов, если их больше пяти
        int start = Math.max(0, excellentStudents.size() - 5);
        List<String> lastFive = excellentStudents.subList(start, Math.min(excellentStudents.size(), start + 5));

        return lastFive;
    }


    // Метод для получения всех оценок
    @Override
    public Collection<Score> getAllScores() {
        return scores.values(); // Возвращаем коллекцию всех оценок
    }
}
