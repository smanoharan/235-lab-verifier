package models;
 
import play.db.jpa.Blob;
import play.db.jpa.Model;
  
import javax.persistence.Entity;
   
@Entity
public class Machine extends Model 
{
  public Blob file;
}

