package com.asofdate.dispatch.dto;

/**
 * Created by hzwy23 on 2017/6/26.
 */
public class BatchMonitoringDTO {
    public String as_of_date;
    public String ratio;

    public String getAsOfDate() {
        return as_of_date;
    }

    public void setAsOfDate(String as_of_date) {
        this.as_of_date = as_of_date;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    @Override
    public String toString() {
        return "BatchMonitoringDTO{" +
                "as_of_date='" + as_of_date + '\'' +
                ", ratio='" + ratio + '\'' +
                '}';
    }
}
