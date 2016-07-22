using Klover.WebApiMock.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Klovr.WebApiMock.Controllers
{
    public class ResourceController : ApiController
    {
        private const string BASE_URL = "http://localhost:63811/";
        private static Dictionary<string, string> imageResource = new Dictionary<string, string>()
        {
            {"login.scroller1.image","Assets/LogIn/image-1.jpg"},
            {"login.scroller2.image","Assets/LogIn/image-2.jpg"},
            {"login.scroller3.image","Assets/LogIn/image-3.jpg"},
            {"login.scroller4.image","Assets/LogIn/image-4.jpg"},
        };

        private static Dictionary<string, string> textResource = new Dictionary<string, string>()
        {
            {"login.scroller1.text","text1"},
            {"login.scroller2.text","text2"},
            {"login.scroller3.text","text3"},
            {"login.scroller4.text","text4"},
        };

        [HttpGet]
        public IHttpActionResult GetResource(string ResourceId)
        {

            Response res;
            var url = imageResource[ResourceId];
            if (string.IsNullOrWhiteSpace(url))
            {
                res = new Response()
                {
                    OperationStatus = -1,
                    Message = new Message
                    {
                        Text = "Resource not found",
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
                        ResourceUrl = BASE_URL + url
                    }
                };
            }
            return Ok(res);
            
        }

        [HttpGet]
        public IHttpActionResult GetResourceText(string resourceId)
        {

            Response res;
            var text = textResource[resourceId];
            if (string.IsNullOrWhiteSpace(text))
            {
                res = new Response()
                {
                    OperationStatus = -1,
                    Message = new Message
                    {
                        Text = "Resource not found",
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
                        ResourceText = text
                    }
                };
            }
            return Ok(res);
        }
    }
}
