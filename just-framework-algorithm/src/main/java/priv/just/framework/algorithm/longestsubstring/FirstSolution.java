package priv.just.framework.algorithm.longestsubstring;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class FirstSolution implements Solution {

    @Override
    public String invoke(String str) {
        int l = 0, max = 0, s = 0;
        StringBuilder slide = new StringBuilder();
        char[] chars = str.toCharArray();
        for (char c : chars) {
            while (slide.indexOf(String.valueOf(c)) != -1) {
                l++;
                slide.deleteCharAt(0);
            }
            slide.append(c);
            if (slide.length() > max) {
                max = slide.length();
                s = l;
            }
        }
        return str.substring(s, s + max);
    }

    @Test
    public void test() {
        log.info(invoke("abcabcdebb"));
    }

}
