using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using WebApplication1.Models;
using WebApplication1.Utils;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace WebApplication1.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UsersController : ControllerBase
    {
        // GET: api/<UsersController>
        [HttpGet]
        public Users[] Get()
        {

            using (var db = new DbHelper())
            {
                return db.users.ToArray();
            }
        }

        // GET api/<UsersController>/5
        [HttpGet("{id}")]
        public Users Get(int id)
        {
            using (var db = new DbHelper())
            {
                return db.users.Find(id);
            }
        }

        // POST api/<UsersController>
        [HttpPost]
        public void Post([FromBody] Users user)
        {
            Random rd = new Random();
            var randomId = rd.Next(100, 200);
            var ocupado = true;

            using (var db = new DbHelper())
            {
                do
                {
                    if (db.users.Find(randomId) == null)
                    {
                        ocupado = false;
                    }
                    else
                    {
                        randomId = rd.Next(100, 200);
                    }

                } while (ocupado);

                user.id = randomId;
                var passwordBytes = Encoding.ASCII.GetBytes(user.password);
                var hash = new SHA256Managed().ComputeHash(passwordBytes);
                var hashPassword = "";

                foreach(byte b in hash)
                {
                    hashPassword.Concat(b.ToString("x2"));
                }

                //user.password = hashPassword;

                db.users.Add(user);
                db.SaveChanges();
            }
        }

        // PUT api/<UsersController>/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] Users user)
        {
            using (var db = new DbHelper())
            {
                var userUpdate = db.users.Find(id);

                if (userUpdate != null)
                {
                    if (user.username != null) userUpdate.username = user.username;
                    if (user.password != null) userUpdate.password = user.password;
                    db.users.Update(userUpdate);
                }
                else
                {
                    db.users.Add(userUpdate);
                }

                db.SaveChanges();
            }
        }

        // DELETE api/<UsersController>/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
            using (var db = new DbHelper())
            {
                var userRemovido = db.users.Find(id);

                if (userRemovido != null)
                    db.users.Remove(userRemovido);
                db.SaveChanges();
            }
        }
    }
}
