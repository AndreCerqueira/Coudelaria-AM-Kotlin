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
    public class CoudelariasController : ControllerBase
    {
        // GET: api/<CoudelariasController>
        [HttpGet]
        public Coudelaria[] Get()
        {
            using (var db = new DbHelper())
            {
                return db.coudelarias.ToArray();
            }
        }

        // GET api/<CoudelariasController>/5
        [HttpGet("{id}")]
        public Coudelaria Get(int id)
        {
            using (var db = new DbHelper())
            {
                return db.coudelarias.Find(id);
            }
        }

        // POST api/<CoudelariasController>
        [HttpPost]
        public void Post([FromBody] Coudelaria coudelaria)
        {
            Random rd = new Random();
            var randomId = rd.Next(100, 200);
            var ocupado = true;

            using (var db = new DbHelper())
            {
                do
                {
                    if (db.coudelarias.Find(randomId) == null)
                    {
                        ocupado = false;
                    }
                    else
                    {
                        randomId = rd.Next(100, 200);
                    }

                } while (ocupado);

                coudelaria.cod_coudelaria = randomId;

                db.coudelarias.Add(coudelaria);
                db.SaveChanges();
            }
        }

        // PUT api/<CoudelariasController>/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] Coudelaria coudelaria)
        {
            using (var db = new DbHelper())
            {
                var coudelariaUpdate = db.coudelarias.Find(id);

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
            }
        }

        // DELETE api/<CoudelariasController>/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
            using (var db = new DbHelper())
            {
                var coudelaria = db.coudelarias.Find(id);

                if (coudelaria != null)
                    db.coudelarias.Remove(coudelaria);

                db.SaveChanges();
            }
        }
    }
}
