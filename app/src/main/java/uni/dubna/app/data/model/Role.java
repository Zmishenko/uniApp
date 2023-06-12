package uni.dubna.app.data.model;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Keep
public enum Role {
   @SerializedName("student") STUDENT,@SerializedName("teacher") TEACHER,@SerializedName("admin") ADMIN, UNKNOWN,
    ;

    @NonNull
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
