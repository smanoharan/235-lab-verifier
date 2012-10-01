package models;
 
import play.db.jpa.Blob;
import play.db.jpa.Model;

import javax.persistence.Entity;
   
@Entity
public class InputMachine extends Model
{
    public Blob file;
}

