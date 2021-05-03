using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication1.Models
{
    public class Coudelaria
    {
        [Key]
        public int cod_coudelaria { get; set; }
        public string nome_coudelaria { get; set; }
        public DateTime? data_inicio_actividade { get; set; }
        public int? cod_criador { get; set; }
    }
}
