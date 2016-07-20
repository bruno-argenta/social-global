var apppath = "/myklovr-rest-api-0.0.1-SNAPSHOT";

exports.CONFIG = Object.freeze({
    LISTEN_PORT: 3000,
    JBOSS_PORT: 8081,
    JBOSS_HOST: "localhost"
});


exports.SERVICES = Object.freeze({
    USER:{
        REGISTER_USER: apppath + "/user/register",
        LOGIN_USER: apppath + "/user/login",
    }

});