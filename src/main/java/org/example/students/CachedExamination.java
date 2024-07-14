package org.example.students;

import java.util.*;

public class CachedExamination implements Examination {

    private final ExaminationImpl examination;
    private final LRUCache<String, Double> averageCache;

    public CachedExamination(ExaminationImpl examination, int cacheSize) {
        this.examination = examination;
        this.averageCache = new LRUCache<>(cacheSize);
    }

    @Override
    public void addScore(Score score) {
        examination.addScore(score);
    }

    @Override
    public Score getScore(String name, String subject) {
        return examination.getScore(name, subject);
    }

    @Override
    public double getAverageForSubject(String subject) {
        if (!averageCache.containsKey(subject)) {
            double average = examination.getAverageForSubject(subject);
            averageCache.put(subject, average);
        }
        return averageCache.get(subject);
    }

    @Override
    public Set<String> multipleSubmissionsStudentNames() {
        return examination.multipleSubmissionsStudentNames();
    }

    @Override
    public List<String> lastFiveStudentsWithExcellentMarkOnAnySubject() {
        return examination.lastFiveStudentsWithExcellentMarkOnAnySubject();
    }

    @Override
    public Collection<Score> getAllScores() {
        return examination.getAllScores();
    }
}
