/*
 * @lc app=leetcode id=207 lang=java
 *
 * [207] Course Schedule
 */

// @lc code=start

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = new ArrayList[numCourses];
        int[] indegree = new int[numCourses];

        for(int i=0; i<numCourses; i++){
            graph[i] = new ArrayList<>();
        }

        for(int[] pre : prerequisites){
            int course = pre[0];
            int preCourse = pre[1];

            graph[preCourse].add(course);
            indegree[course]++;
        }

        Queue<Integer> queue = new LinkedList<>();

        for(int i=0; i<numCourses; i++){
            if(indegree[i] == 0){
                queue.add(i);
            }
        }

        int count = 0;
        
        while(!queue.isEmpty()){
            int preCourse = queue.poll();
            count++;

            for(int course : graph[preCourse]){
                indegree[course]--;
                if(indegree[course] == 0){
                    queue.add(course);
                }
            }
        }

        return count == numCourses;
    }
}
// @lc code=end

