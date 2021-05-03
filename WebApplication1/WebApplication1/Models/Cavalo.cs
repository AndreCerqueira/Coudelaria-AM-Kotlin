using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication1.Models
{
    public class Cavalo
    {
        [Key]
        public int cod_cavalo { get; set; }
        public string nome_cavalo { get; set; }
        public DateTime? data_nascimento { get; set; }
        public string genero { get; set; }
        public int? mae { get; set; }
        public int? pai { get; set; }
        public int? cod_coudelaria_nasc { get; set; }
        public int? cod_coudelaria_resid { get; set; }

    }
}
