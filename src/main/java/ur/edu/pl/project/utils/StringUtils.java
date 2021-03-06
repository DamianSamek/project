package ur.edu.pl.project.utils;

import org.springframework.stereotype.Service;

@Service
public class StringUtils {

    public boolean isEmptyOrWhitespaceOnly(String str) {

        if (str == null || str.length() == 0) {

            return true;

        }

        int length = str.length();

        for (int i = 0; i < length; i++) {

            if (!Character.isWhitespace(str.charAt(i))) {

                return false;

            }

        }
        return true;
    }
}