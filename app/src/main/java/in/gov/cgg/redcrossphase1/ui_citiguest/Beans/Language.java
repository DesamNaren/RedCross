package in.gov.cgg.redcrossphase1.ui_citiguest.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Language {

    @SerializedName("language_short_code")
    @Expose
    private String languageShortCode;
    @SerializedName("language_id")
    @Expose
    private String languageId;
    @SerializedName("language_name")
    @Expose
    private String languageName;

    public String getLanguageShortCode() {
        return languageShortCode;
    }

    public void setLanguageShortCode(String languageShortCode) {
        this.languageShortCode = languageShortCode;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

}
