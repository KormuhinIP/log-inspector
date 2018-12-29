package org.tlauncher.log.inspector.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class VersionValidator {
    private static final Pattern patter = Pattern.compile("^[0-9]{1,2}\\.[0-9]{1,3}");

    public boolean isValid(String version) {
        return patter.matcher(version).matches();
    }
}
