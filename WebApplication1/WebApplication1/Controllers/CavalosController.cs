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
    public class CavalosController : ControllerBase
    {
        // GET: api/<CavalosController>
        [HttpGet]
        public Cavalo[] Get()
        {
            using(var db = new DbHelper())
            {
                return db.cavalos.ToArray();
            }
        }

        // GET api/<CavalosController>/5
        [HttpGet("{id}")]
        public Cavalo Get(int id)
        {
            using(var db = new DbHelper())
            {
                return db.cavalos.Find(id);
            }
        }

        // POST api/<CavalosController>
        [HttpPost]
        public void Post([FromBody] Cavalo cavalo)
        {
            Random rd = new Random();
            var randomId = rd.Next(100, 200);
            var ocupado = true;
            using (var db = new DbHelper())
            {
                do
                {
                    if (db.cavalos.Find(randomId) == null)
                    {
                        ocupado = false;
                    }
                    else
                    {
                        randomId = rd.Next(100, 200);
                    }

                } while (ocupado);

                cavalo.cod_cavalo = randomId;

                db.cavalos.Add(cavalo);
                db.SaveChanges();
            }
        }

        // PUT api/<CavalosController>/5 [HttpPut("{id}")]
        [HttpPut]
        public void Put([FromBody] Cavalo cavalo)
        {
            using (var db = new DbHelper())
            {
                var cavaloUpdate = db.cavalos.Find(cavalo.cod_cavalo);
                if(cavaloUpdate != null)
                {
                    if (cavalo.nome_cavalo != null) cavaloUpdate.nome_cavalo = cavalo.nome_cavalo;
                    if (cavalo.data_nascimento != null) cavaloUpdate.data_nascimento = cavalo.data_nascimento;
                    if (cavalo.genero != null) cavaloUpdate.genero = cavalo.genero;
                    if (cavalo.pai != null) cavaloUpdate.pai = cavalo.pai;
                    if (cavalo.mae != null) cavaloUpdate.mae = cavalo.mae;
                    if (cavalo.cod_coudelaria_nasc != null) cavaloUpdate.cod_coudelaria_nasc = cavalo.cod_coudelaria_nasc;
                    if (cavalo.cod_coudelaria_resid != null) cavaloUpdate.cod_coudelaria_resid = cavalo.cod_coudelaria_resid;
                    db.cavalos.Update(cavaloUpdate);
                }
                else
                {
                    db.cavalos.Add(cavalo);
                }
                db.SaveChanges();
            }
        }

        // DELETE api/<CavalosController>/5  [HttpDelete("{id}")]  int id
        [HttpDelete]
        public void Delete([FromBody] Cavalo cavaloReceived)
        {
            using (var db = new DbHelper())
            {
                var cavalo = db.cavalos.Find(cavaloReceived.cod_cavalo);

                if(cavalo != null)
                    db.cavalos.Remove(cavalo);

                db.SaveChanges();
            }
        }
    }
}
