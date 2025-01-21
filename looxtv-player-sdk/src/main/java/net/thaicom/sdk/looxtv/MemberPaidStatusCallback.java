
package net.thaicom.sdk.looxtv;

public interface MemberPaidStatusCallback {
    void onSuccess(MemberInfo memberInfo);
    void onFailure(Throwable t);
}
