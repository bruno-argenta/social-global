using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Klover.WebApiMock.Models
{
    public class Response
    {
        public int OperationStatus { get; set; }
        public Message Message { get; set; }
        public object OperationData { get; set; }
    }

    public class Message
    {
        public string Text { get; set; }
        public string Level { get; set; }
    }
}