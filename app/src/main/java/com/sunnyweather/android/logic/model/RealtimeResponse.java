package com.sunnyweather.android.logic.model;

import com.google.gson.annotations.SerializedName;

public class RealtimeResponse {
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
        private Realtime realtime;

        public Realtime getRealtime() {
            return realtime;
        }

        public void setRealtime(Realtime realtime) {
            this.realtime = realtime;
        }
    }

    public class Realtime {
        private String skycon;
        private float temperature;
        @SerializedName("air_quality")
        private AirQuality airQuality;

        public String getSkycon() {
            return skycon;
        }

        public void setSkycon(String skycon) {
            this.skycon = skycon;
        }

        public float getTemperature() {
            return temperature;
        }

        public void setTemperature(float temperature) {
            this.temperature = temperature;
        }

        public AirQuality getAirQuality() {
            return airQuality;
        }

        public void setAirQuality(AirQuality airQuality) {
            this.airQuality = airQuality;
        }
    }

    public class AirQuality {
        private AQI aqi;

        public AQI getAqi() {
            return aqi;
        }

        public void setAqi(AQI aqi) {
            this.aqi = aqi;
        }
    }

    public class AQI {
        private float chn;

        public float getChn() {
            return chn;
        }

        public void setChn(float chn) {
            this.chn = chn;
        }
    }
}

