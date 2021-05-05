package in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentBean {
    @SerializedName("statusMsg")
    @Expose
    private String statusMsg;
    @SerializedName("paymentGatewayUrl")
    @Expose
    private String paymentGatewayUrl;
    @SerializedName("paymentRequest")
    @Expose
    private String paymentRequest;
    @SerializedName("status")
    @Expose
    private String status;

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getPaymentGatewayUrl() {
        return paymentGatewayUrl;
    }

    public void setPaymentGatewayUrl(String paymentGatewayUrl) {
        this.paymentGatewayUrl = paymentGatewayUrl;
    }

    public String getPaymentRequest() {
        return paymentRequest;
    }

    public void setPaymentRequest(String paymentRequest) {
        this.paymentRequest = paymentRequest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
