using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebApplication1.Models;

namespace WebApplication1.Utils
{
    public class DbHelper: DbContext
    {
        public DbSet<Cavalo> cavalos { get; set; }
        public DbSet<Coudelaria> coudelarias { get; set; }
        public DbSet<Classifics> classifics { get; set; }
        public DbSet<Prova> provas { get; set; }
        public DbSet<Criador> criadores { get; set; }

        public DbSet<Users> users { get; set; }


        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseSqlite("Data Source = coudelaria_dwm.db");
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Classifics>()
                .HasKey(c => new { c.cod_prova, c.cod_cavalo });

        }
    }
}
