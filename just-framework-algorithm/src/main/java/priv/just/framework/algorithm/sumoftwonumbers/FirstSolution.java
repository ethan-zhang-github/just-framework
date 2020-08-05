package priv.just.framework.algorithm.sumoftwonumbers;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FirstSolution implements Solution {

    @Override
    public int[] invoke(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        for (int i = 0;  i < nums.length; i++) {
            int num = nums[i];
            if (map.containsKey(target - num)) {
                return new int[] {i, map.get(target - num)};
            }
            map.put(num, i);
        }
        return new int[] {};
    }

    @Test
    public void test() {
        int[] res = invoke(new int[]{2, 7, 11, 15}, 9);
        log.info(Arrays.toString(res));
    }

}
