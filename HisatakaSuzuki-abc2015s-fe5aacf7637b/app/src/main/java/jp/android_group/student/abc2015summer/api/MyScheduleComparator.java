package jp.android_group.student.abc2015summer.api;


import java.util.Comparator;

public class MyScheduleComparator implements Comparator<TimeTable> {
    @Override public int compare(TimeTable p1, TimeTable p2) {
        String[] p1_startTimes = p1.getStartTime().split(":", 0);
        String[] p2_startTimes = p2.getStartTime().split(":", 0);

        try {
            int p1_start_hour = Integer.parseInt(p1_startTimes[0]);
            int p2_start_hour = Integer.parseInt(p2_startTimes[0]);
            int p1_start_minute = Integer.parseInt(p1_startTimes[1]);
            int p2_start_minute = Integer.parseInt(p2_startTimes[1]);

            if (p1_start_hour == p2_start_hour) {
                if (p1_start_minute == p2_start_minute){
                    //同時刻であれば元の並びを維持する
                    return -1;
                }else if(p1_start_minute < p2_start_minute){
                    return -1;
                }else{
                    return 1;
                }
            }else if(p1_start_hour < p2_start_hour){
                return -1;
            }else{
                return 1;
            }
        } catch ( NumberFormatException e ) {
            return -1;
        }
    }
}
