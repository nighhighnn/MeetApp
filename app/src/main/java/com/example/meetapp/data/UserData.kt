package com.example.meetapp.data

object UserData {

    private var channel: String? = null
    private var appId: String? = null
    private var uid: String? = null
    private var rtcToken: String? = null

    // Getters
    fun getChannel(): String? = channel
    fun getAppId(): String? = appId
    fun getUid(): String? = uid
    fun getRtcToken(): String? = rtcToken

    // Setters
    fun setChannel(newChannel: String?) {
        channel = newChannel
    }

    fun setAppId(newAppId: String?) {
        appId = newAppId
    }

    fun setUid(newUid: String?) {
        uid = newUid
    }

    fun setRtcToken(newRtcToken: String?) {
        rtcToken = newRtcToken
    }

    // Method to update all data at once
    fun updateUserData(newChannel: String?, newAppId: String?, newUid: String?, newRtcToken: String?) {
        channel = newChannel
        appId = newAppId
        uid = newUid
        rtcToken = newRtcToken
    }
}
