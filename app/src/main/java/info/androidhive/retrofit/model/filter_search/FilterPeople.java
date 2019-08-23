package info.androidhive.retrofit.model.filter_search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilterPeople {

        @SerializedName("popularity")
        @Expose
        private Double popularity;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("profile_path")
        @Expose
        private Object profilePath;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("known_for")
        @Expose
        private List<KnownFor> knownFor = null;
        @SerializedName("adult")
        @Expose
        private Boolean adult;

        public Double getPopularity() {
            return popularity;
        }

        public void setPopularity(Double popularity) {
            this.popularity = popularity;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Object getProfilePath() {
            return profilePath;
        }

        public void setProfilePath(Object profilePath) {
            this.profilePath = profilePath;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<KnownFor> getKnownFor() {
            return knownFor;
        }

        public void setKnownFor(List<KnownFor> knownFor) {
            this.knownFor = knownFor;
        }

        public Boolean getAdult() {
            return adult;
        }

        public void setAdult(Boolean adult) {
            this.adult = adult;
        }

    }
