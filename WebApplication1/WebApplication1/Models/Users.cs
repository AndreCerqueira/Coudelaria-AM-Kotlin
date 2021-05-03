using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication1.Models
{
    public class Users
    {
        [Key]
        public int id { get; set; }
        public string username { get; set; }
        public string password { get; set; }

    }
}
