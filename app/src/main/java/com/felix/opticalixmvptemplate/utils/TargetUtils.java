package com.felix.opticalixmvptemplate.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Opticalix on 2015/8/13.
 * Multi-Task may use this..
 */
public class TargetUtils {
    static Map<String, Integer> targetMap = new HashMap<>();


    /**
     * new target or update target
     * @param missionTag  tag of this target, must be unique
     * @param ownPos      bit position
     * @param targetCount total count
     * @return true indicate finish
     */
    public synchronized static boolean contributeTarget(String missionTag, int ownPos, int targetCount) {
        if (!targetMap.containsKey(missionTag)) {
            targetMap.put(missionTag, 0);
        }
        Integer target = targetMap.get(missionTag);
        target |= (1 << ownPos);
        targetMap.put(missionTag, target);
        boolean ret = target == (1 << targetCount) - 1;
        if (ret) targetMap.remove(missionTag);//clear
        return ret;
    }

    /**
     * remove target
     * @param missionTag tag of this target, must be unique
     */
    public static void removeTarget(String missionTag){
        targetMap.remove(missionTag);
    }

}
