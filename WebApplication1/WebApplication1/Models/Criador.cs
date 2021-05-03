using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication1.Models
{
    public class Criador
    {
        [Key]
        public int cod_criador { get; set; }
        public string nome { get; set; }
    }
}
