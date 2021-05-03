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
    public class ProvasController : ControllerBase
    {
        // GET: api/<ProvasController>
        [HttpGet]
        public Prova[] Get()
        {
            using (var db = new DbHelper())
            {
                return db.provas.ToArray();
            }
        }

        // GET api/<ProvasController>/5
        [HttpGet("{id}")]
        public Prova Get(int id)
        {
            using (var db = new DbHelper())
            {
                return db.provas.Find(id);
            }
        }

        // POST api/<ProvasController>
        [HttpPost]
        public void Post([FromBody] Prova prova)
        {
            Random rd = new Random();
            var randomId = rd.Next(100, 200);
            var ocupado = true;

            using (var db = new DbHelper())
            {
                do
                {
                    if (db.provas.Find(randomId) == null)
                    {
                        ocupado = false;
                    }
                    else
                    {
                        randomId = rd.Next(100, 200);
                    }

                } while (ocupado);

                prova.cod_prova = randomId;

                db.provas.Add(prova);
                db.SaveChanges();
            }
        }

        // PUT api/<ProvasController>/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] Prova prova)
        {
            using (var db = new DbHelper())
            {
                var provaUpdate = db.provas.Find(id);

                if (provaUpdate != null)
                {
                    if (prova.nome_prova != null) provaUpdate.nome_prova = prova.nome_prova;
                    if (prova.data != null) provaUpdate.data = prova.data;
                    db.provas.Update(provaUpdate);
                }
                else
                {
                    db.provas.Add(prova);
                }

                db.SaveChanges();
            }
        }

        // DELETE api/<ProvasController>/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
            using (var db = new DbHelper())
            {
                var prova = db.provas.Find(id);

                if (prova != null)
                    db.provas.Remove(prova);

                db.SaveChanges();
            }
        }
    }
}
