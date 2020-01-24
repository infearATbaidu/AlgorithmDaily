import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/permutations/submissions/
 *
 * @author infear
 */
public class Permutations {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> r = new ArrayList<>();
        r.add(new ArrayList<>());

        int i = 0;
        while (i != nums.length) {
            int ll = r.size(), j = 0;
            while (j != ll) {
                int k = 0;
                while (k != r.get(j).size()) {
                    List<Integer> copy = new ArrayList<>();
                    copy.addAll(r.get(j));
                    copy.add(k, nums[i]);
                    k++;
                    r.add(copy);
                }
                r.get(j).add(nums[i]);
                j++;
            }
            i++;
        }
        return r;
    }
}
