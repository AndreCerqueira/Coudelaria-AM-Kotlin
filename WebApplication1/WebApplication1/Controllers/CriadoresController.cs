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
    public class CriadoresController : ControllerBase
    {
        // GET: api/<CriadoresController>
        [HttpGet]
        public Criador[] Get()
        {
            using (var db = new DbHelper())
            {
                return db.criadores.ToArray();
            }
        }

        // GET api/<CriadoresController>/5
        [HttpGet("{id}")]
        public Criador Get(int id)
        {
            using (var db = new DbHelper())
            {
                return db.criadores.Find(id);
            }
        }

        // POST api/<CriadoresController>
        [HttpPost]
        public void Post([FromBody] Criador criador)
        {
            Random rd = new Random();
            var randomId = rd.Next(100, 200);
            var ocupado = true;

            using (var db = new DbHelper())
            {
                do
                {
                    if (db.criadores.Find(randomId) == null)
                    {
                        ocupado = false;
                    }
                    else
                    {
                        randomId = rd.Next(100, 200);
                    }

                } while (ocupado);

                criador.cod_criador = randomId;

                db.criadores.Add(criador);
                db.SaveChanges();
            }
        }

        // PUT api/<CriadoresController>/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] Criador criador)
        {
            using (var db = new DbHelper())
            {
                var criadorUpdate = db.criadores.Find(id);

                if (criadorUpdate != null)
                {
                    if (criador.nome != null) criadorUpdate.nome = criador.nome;
                    db.criadores.Update(criadorUpdate);
                }
                else
                {
                    db.criadores.Add(criador);
                }

                db.SaveChanges();
            }
        }

        // DELETE api/<CriadoresController>/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
            using (var db = new DbHelper())
            {
                var criadorRemovido = db.criadores.Find(id);

                if(criadorRemovido != null)
                    db.criadores.Remove(criadorRemovido);
                db.SaveChanges();
            }
        }
    }
}
