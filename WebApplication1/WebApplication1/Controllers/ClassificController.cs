using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebApplication1.Models;
using WebApplication1.Utils;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace WebApplication1.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ClassificController : ControllerBase
    {
        // GET: api/<ClassificController>
        [HttpGet]
        public Classifics[] Get()
        {
            using (var db = new DbHelper())
            {
                return db.classifics.ToArray();
            }
        }

        // GET api/<ClassificController>/5
        [HttpGet("{id}")]
        public Classifics Get(int cod_prova, int cod_cavalo)
        {
            using (var db = new DbHelper())
            {
                return db.classifics.Find(cod_prova, cod_cavalo);
            }
        }

        // POST api/<ClassificController>
        [HttpPost]
        public void Post([FromBody] Classifics classificacao)
        {
            Random rd = new Random();
            var randomId = rd.Next(100, 200);
            var ocupado = true;

            using (var db = new DbHelper())
            {
                do
                {
                    if (db.classifics.Find(randomId) == null)
                    {
                        ocupado = false;
                    }
                    else
                    {
                        randomId = rd.Next(100, 200);
                    }

                } while (ocupado);

                classificacao.cod_prova = randomId;

                db.classifics.Add(classificacao);
                db.SaveChanges();
            }
        }

        // PUT api/<ClassificController>/5
        [HttpPut("{id}")]
        public void Put(int id_prova, int id_cavalo, [FromBody] string value)
        {
           /* using (var db = new DbHelper())
            {
                var classificUpdate = db.classificacoes.Find(id);

                if (coudelariaUpdate != null)
                {
                    if (coudelaria.nome_coudelaria != null) coudelariaUpdate.nome_coudelaria = coudelaria.nome_coudelaria;
                    if (coudelaria.cod_criador != null) coudelariaUpdate.cod_criador = coudelaria.cod_criador;
                    if (coudelaria.data_inicio_actividade != null) coudelariaUpdate.data_inicio_actividade = coudelaria.data_inicio_actividade;
                    db.coudelarias.Update(coudelariaUpdate);
                }
                else
                {
                    db.coudelarias.Add(coudelaria);
                }

                db.SaveChanges();
            }*/
        }

        // DELETE api/<ClassificController>/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}
