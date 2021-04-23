package com.sunnyweather.android.logic.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DailyResponse {
    private String status;
    private Result result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {
        private Daily daily;

        public Daily getDaily() {
            return daily;
        }

        public void setDaily(Daily daily) {
            this.daily = daily;
        }
    }

    public class Daily {
        private ArrayList<Temperature> temperature;
        private ArrayList<Skycon> skycon;
        @SerializedName("life_index")
        private LifeIndex lifeIndex;

        public List<Temperature> getTemperature() {
            return temperature;
        }

        public void setTemperature(ArrayList<Temperature> temperature) {
            this.temperature = temperature;
        }

        public List<Skycon> getSkycon() {
            return skycon;
        }

        public void setSkycon(ArrayList<Skycon> skycon) {
            this.skycon = skycon;
        }

        public LifeIndex getLifeIndex() {
            return lifeIndex;
        }

        public void setLifeIndex(LifeIndex lifeIndex) {
            this.lifeIndex = lifeIndex;
        }
    }

    public class Temperature {
        private float max;
        private float min;

        public float getMin() {
            return min;
        }

        public void setMin(float min) {
            this.min = min;
        }

        public float getMax() {
            return max;
        }

        public void setMax(float max) {
            this.max = max;
        }
    }

    public class Skycon {
        private String value;
        private Date date;//哪个Date类

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }

    public class LifeIndex {
        private List<LifeDescription> coldRisk;
        private List<LifeDescription> carWashing;
        private List<LifeDescription> ultraviolet;
        private List<LifeDescription> dressing;

        public List<LifeDescription> getColdRisk() {
            return coldRisk;
        }

        public void setColdRisk(List<LifeDescription> coldRisk) {
            this.coldRisk = coldRisk;
        }

        public List<LifeDescription> getCarWashing() {
            return carWashing;
        }

        public void setCarWashing(List<LifeDescription> carWashing) {
            this.carWashing = carWashing;
        }

        public List<LifeDescription> getUltraviolet() {
            return ultraviolet;
        }

        public void setUltraviolet(List<LifeDescription> ultraviolet) {
            this.ultraviolet = ultraviolet;
        }

        public List<LifeDescription> getDressing() {
            return dressing;
        }

        public void setDressing(List<LifeDescription> dressing) {
            this.dressing = dressing;
        }
    }

    public class LifeDescription {
        private String desc;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
