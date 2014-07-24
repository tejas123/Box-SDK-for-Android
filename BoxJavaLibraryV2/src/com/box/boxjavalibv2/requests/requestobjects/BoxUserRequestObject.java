package com.box.boxjavalibv2.requests.requestobjects;

import java.util.LinkedHashMap;
import java.util.Map;

import com.box.boxjavalibv2.dao.BoxUser;
import com.box.boxjavalibv2.jsonentities.BoxEnterpriseRequestEntity;
import com.box.boxjavalibv2.jsonentities.PairArrayJSONStringEntity;

public class BoxUserRequestObject extends BoxSimpleUserRequestObject {

    /**
     * Request entity to create an enterprise user.
     * 
     * @param login
     *            Login(email) of the user.
     * @param name
     *            name of the user
     * @return
     */
    public static BoxUserRequestObject createEnterpriseUserRequestObject(final String login, final String name) {
        BoxUserRequestObject obj = new BoxUserRequestObject();
        return obj.setLogin(login).setName(name);
    }

    /**
     * Request entity to update an enterprise user. Please note the returned object is not supposed to be used for updating the user's primary login. For that
     * purpose, please use the method: updateUserPrimaryLoginRequestObject(final String login)
     * 
     * @param notify
     *            whether to notify user if user is rolled out of enterprise
     * @return
     */
    public static BoxUserRequestObject updateUserInfoRequestObject(boolean notify) {
        BoxUserRequestObject obj = new BoxUserRequestObject();
        obj.setNotifyUser(notify);
        return obj;
    }

    public BoxUserRequestObject setName(String name) {
        put(BoxUser.FIELD_NAME, name);
        return this;
    }

    /**
     * Set Login(email) of the user.
     * 
     * @param login
     *            login
     * @return
     */
    public BoxUserRequestObject setLogin(final String login) {
        put(BoxUser.FIELD_LOGIN, login);
        return this;
    }

    /**
     * Set The the user's enterprise role. The role can be {@link BoxUser#ROLE_ADMIN} , {@link BoxUser#ROLE_COADMIN} or {@link BoxUser#ROLE_USER}.
     * 
     * @param role
     * @return
     */
    public BoxUserRequestObject setRole(String role) {
        put(BoxUser.FIELD_ROLE, role);
        return this;
    }

    /**
     * @param language
     *            the language to set
     * @return
     */
    public BoxUserRequestObject setLanguage(String language) {
        put(BoxUser.FIELD_LANGUAGE, language);
        return this;
    }

    /**
     * @param isSyncEnabled
     *            the isSyncEnabled to set
     * @return
     */
    public BoxUserRequestObject setSyncEnabled(boolean isSyncEnabled) {
        put(BoxUser.FIELD_IS_SYNC_ENABLED, Boolean.toString(isSyncEnabled));
        return this;
    }

    /**
     * @param jobTitle
     *            the jobTitle to set
     * @return
     */
    public BoxUserRequestObject setJobTitle(String jobTitle) {
        put(BoxUser.FIELD_JOB_TITLE, jobTitle);
        return this;
    }

    /**
     * @param phone
     *            the phone to set
     * @return
     */
    public BoxUserRequestObject setPhone(String phone) {
        put(BoxUser.FIELD_PHONE, phone);
        return this;
    }

    /**
     * @param address
     *            the address to set
     * @return
     */
    public BoxUserRequestObject setAddress(String address) {
        put(BoxUser.FIELD_ADDRESS, address);
        return this;
    }

    /**
     * @param spaceAmount
     *            the spaceAmount to set
     * @return
     */
    public BoxUserRequestObject setSpaceAmount(double spaceAmount) {
        put(BoxUser.FIELD_SPACE_AMOUNT, Double.toString(spaceAmount));
        return this;
    }

    /**
     * @param trackingCodes
     *            the trackingCodes to set
     * @return
     */
    public BoxUserRequestObject setTrackingCodes(final LinkedHashMap<String, String> trackingCodes) {
        PairArrayJSONStringEntity list = new PairArrayJSONStringEntity();
        for (Map.Entry<String, String> entry : trackingCodes.entrySet()) {
            list.put(entry.getKey(), entry.getValue());
        }
        put(BoxUser.FIELD_TRACKING_CODES, list);
        return this;
    }

    /**
     * @param canSeeManagedUsers
     *            the canSeeManagedUsers to set
     * @return
     */
    public BoxUserRequestObject setCanSeeManagedUsers(final boolean canSeeManagedUsers) {
        put(BoxUser.FIELD_CAN_SEE_MANAGED_USERS, Boolean.toString(canSeeManagedUsers));
        return this;
    }

    /**
     * @param status
     *            the status to set. Status of the user. This String can be {@link com.box.boxjavalibv2.dao.BoxUser#STATUS_ACTIVE} or
     *            {@link com.box.boxjavalibv2.dao.BoxUser#STATUS_INACTIVE}
     * @return
     */
    public BoxUserRequestObject setStatus(final String status) {
        put(BoxUser.FIELD_STATUS, status);
        return this;
    }

    /**
     * @param exemptFromDeviceLimits
     *            the exemptFromDeviceLimits to set
     * @return
     */
    public BoxUserRequestObject setExemptFromDeviceLimits(final boolean exemptFromDeviceLimits) {
        put(BoxUser.FIELD_EXEMPT_FROM_DEVICE_LIMITS, Boolean.toString(exemptFromDeviceLimits));
        return this;
    }

    /**
     * @param exemptFromLoginVerification
     *            the exemptFromLoginVerification to set
     * @return
     */
    public BoxUserRequestObject setExemptFromLoginVerification(final boolean exemptFromLoginVerification) {
        put(BoxUser.FIELD_EXEMPT_FROM_LOGIN_VERIFICATION, Boolean.toString(exemptFromLoginVerification));
        return this;
    }

    /**
     * @param enterprise
     *            the enterprise to set. Note when updating user information you can set this to null in order to roll the user out from enterprise.
     * @return
     */
    public BoxUserRequestObject setEnterprise(final BoxEnterpriseRequestEntity enterprise) {
        put(BoxUser.FIELD_ENTERPRISE, enterprise);
        return this;
    }
}
