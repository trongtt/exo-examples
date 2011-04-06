package quizzes;

import org.chromattic.api.annotations.ManyToOne;
import org.chromattic.api.annotations.Name;
import org.chromattic.api.annotations.OneToMany;
import org.chromattic.api.annotations.PrimaryType;
import org.chromattic.api.annotations.Property;

import java.util.List;

/**
 * @author <a href="mailto:trongtt@gmail.com">Tran The Trong</a>
 * @version $Revision$
 */
@PrimaryType(name = "qz:category")
public abstract class Category
{
   /**
    * Return the category name
    * @return the category name
    */
   @Name
   public abstract String getName();
   
   /**
    * Return the category title
    * 
    * @return the category title
    */
   @Property(name = "title")
   public abstract String getTitle();
   
   /**
    * Update the category title
    * @param title the new category title
    */
   public abstract void setTitle(String title);
   
   /**
    * Return the list of category children
    * @return
    */
   @OneToMany
   public abstract List<Category> getChildren();
   
   /**
    * Return the category parent
    * @return the category parent
    */
   @ManyToOne
   public abstract Category getParent();
   
   /**
    * Update the category parent
    * @param cat the category parent
    */
   public abstract void setParent(Category cat);
}
