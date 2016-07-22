using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Klover.WebApiMock.Models
{
    public class LoginUser
    {
        public string Email { get; set; }
        public string Password { get; set; }
    }

    public class RegisterUser
    {
        public string Email { get; set; }
        public string Password { get; set; }
        public string ConfirmPassword { get; set; }
    }

    public class RecoveryPassword
    {
        public string Email { get; set; }
        public string Mode { get; set; }
    }

    public class ValidateCode
    {
        public string ValidationCode { get; set; }
    }

    public class ChangePasswordRecovery
    {
        public string Email { get; set; }
        public string Password { get; set; }
        public string ValidationCode { get; set; }
    }

    public class LoginUserExternal
    {
        public string Provider { get; set; }
        public string Code { get; set; }
    }
}