package mipt.hw.components.correctors;

import mipt.hw.domain.University;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UniversityCorrector {
    public static String getUniversityName(String name) {
        // Try to find match in raw name.
        Objects.requireNonNull(name);

        name = name.toLowerCase();

        Matcher univerMatcher = universityPattern.matcher(name);
        if (univerMatcher.find()) {
            return University.getUniversities().get(univerMatcher.group());
        }

        // Try to extract abbreviation.
        StringBuilder sb = new StringBuilder();

        Matcher abbrevMatcher = abbreviationPattern.matcher(name);
        while (abbrevMatcher.find()) {
            String replacement = abbrevMatcher.group();
            abbrevMatcher.appendReplacement(sb, replacement);
        }
        abbrevMatcher.appendTail(sb);

        String newName = abbrevMatcher.toString();
        univerMatcher = universityPattern.matcher(newName);
        if (univerMatcher.find()) {
            return University.getUniversities().get(univerMatcher.group());
        }

        return null;
    }

    static {
        String universityRegex = String.join("|", University.getUniversities().keySet());
        universityPattern = Pattern.compile( universityRegex);
    }

    private static final Pattern abbreviationPattern = Pattern.compile( "\\s*(.)[a-zA-Z._-]*\\s*");
    private static final Pattern universityPattern;
}
