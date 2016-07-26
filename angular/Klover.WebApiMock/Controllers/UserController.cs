using Klover.WebApiMock.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Cors;

namespace Klover.WebApiMock.Controllers
{
    public class UserController : ApiController
    {
        [HttpPost]
        public IHttpActionResult LoginUser([FromBody] LoginUser request)
        {
            Response res;
            if (request.Email.ToLower().Contains("error"))
            {
                res = new Response()
                {
                    OperationStatus = -1,
                    Message = new Message
                    {
                        Text = "Wrong email/password combination",
                        Level = "Error"
                    },
                    OperationData = null
                };
            }
            else
            {
                res = new Response()
                {
                    OperationStatus = 0,
                    Message = new Message
                    {
                        Text = "Success",
                        Level = "Success"
                    },
                    OperationData = new
                    {
                        Token = "token",
                        UserImage = "image",
                        UserNickname = "nickname"
                    }
                };
            }

            return Ok(res);
        }

        [HttpPost]
        public IHttpActionResult RegisterUser([FromBody] RegisterUser request)
        {

            Response res;
            if (request.Email.ToLower().Contains("error"))
            {
                res = new Response()
                {
                    OperationStatus = -1,
                    Message = new Message
                    {
                        Text = "Invalid info",
                        Level = "Error"
                    },
                    OperationData = null
                };
            }
            else
            {
                res = new Response()
                {
                    OperationStatus = 0,
                    Message = new Message
                    {
                        Text = "Success",
                        Level = "Success"
                    },
                    OperationData = new
                    {
                        Token = "token"
                    }
                };
            }


            return Ok(res);
        }


        [HttpGet]
        public IHttpActionResult RecoveryMethods([FromUri]RecoveryMethodEmail request)
        {

            Response res;
            if (request.Email.ToLower().Contains("error"))
            {
                res = new Response()
                {
                    OperationStatus = -1,
                    Message = new Message
                    {
                        Text = "Invalid email",
                        Level = "Error"
                    },
                    OperationData = null
                };
            }
            else
            {
                res = new Response()
                {
                    OperationStatus = 0,
                    Message = new Message
                    {
                        Text = "Success",
                        Level = "Success"
                    },
                    OperationData = new[] 
                    { 
                        //new
                        //{
                        //    Contact="******123 (SMS)",
                        //    Method = "PHONE_CODE"
                        //},
                        new
                        {
                            Contact ="*********@gmail.com",
                            Method = "EMAIL"
                        }
                    }
                };
            }
            return Ok(res);
        }

        [HttpPost]
        public IHttpActionResult RecoveryPassword([FromBody] RecoveryPassword request)
        {
            var res = new Response()
            {
                OperationStatus = 0,
                Message = new Message
                {
                    Text = "Success",
                    Level = "Success"
                },
                OperationData = new { }
            };
            return Ok(res);
        }

        [HttpPost]
        public IHttpActionResult ValidateCode([FromBody] ValidateCode request)
        {

            Response res;
            if (request.ValidationCode.ToLower().Contains("error"))
            {
                res = new Response()
                {
                    OperationStatus = -1,
                    Message = new Message
                    {
                        Text = "Invalid code",
                        Level = "Error"
                    },
                    OperationData = null
                };
            }
            else
            {
                res = new Response()
                {
                    OperationStatus = 0,
                    Message = new Message
                    {
                        Text = "Success",
                        Level = "Success"
                    },
                    OperationData = new { }
                };
            }

            return Ok(res);
        }

        [HttpPost]
        public IHttpActionResult ChangePasswordRecovery([FromBody] ChangePasswordRecovery request)
        {
            var res = new Response()
            {
                OperationStatus = 0,
                Message = new Message
                {
                    Text = "Success",
                    Level = "Success"
                },
                OperationData = new
                {
                    Token = "token"
                }
            };
            return Ok(res);
        }

        [HttpPost]
        public IHttpActionResult LoginUserExternalProvider([FromBody] LoginUserExternal request)
        {
            var res = new Response()
            {
                OperationStatus = 0,
                Message = new Message
                {
                    Text = "Success",
                    Level = "Success"
                },
                OperationData = new
                {
                    Token = "token",
                    UserImage = "imageUrl",
                    UserNickName = "nick name"
                }
            };
            return Ok(res);
        }


    }
}
