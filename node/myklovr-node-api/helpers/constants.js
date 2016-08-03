var apppath = "/myklovr-rest-api-0.0.1-SNAPSHOT";

exports.CONFIG = Object.freeze({
    LISTEN_PORT: 3000,
    JBOSS_PORT: 8081,
    JBOSS_HOST: "localhost",
    APACHE_HOST: "localhost",
    APACHE_PORT: 61116,
    RESOURCE_PATH: "resource/",
    IMAGE_PATH: "image/"
});


exports.SERVICES = Object.freeze({
    USER:{
        REGISTER_USER: apppath + "/user/register",
        LOGIN_USER: apppath + "/user/login",
        LOGIN_EXTERNAL: apppath + "/user/loginExternalProvider",
        VALIDATE_CODE:  apppath + "/user/verifyCode",
        RECOVERY_PASSWORD: apppath + "/user/recoveryPassword",
        CHANGE_PASSWORD: apppath + "/user/changePasswordRecovery",
    },
    USER_PROFILE:{
        SET_SECTION: apppath + "userProfile/setSection",
        GET_SECTION: apppath + "userProfile/getSection",
    }
});